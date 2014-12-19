package cn.com.navia.PhoneService.server.web;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import cn.com.navia.PhoneService.bean.BeanRetMsg;
import cn.com.navia.PhoneService.bean.EntityBody;
import cn.com.navia.PhoneService.bean.EntityMsg;



@Service
public class RestClient {

	private Logger log;
	private PoolingHttpClientConnectionManager poolingHCCM;
	private CloseableHttpClient httpClient;
	private HttpComponentsClientHttpRequestFactory httpFactory;
	private RestTemplate restTemplate;
	
	@Value("${phone.location.host}")
	private String host;

	@Value("${phone.location.port}")
	private String port;
	
	@Value("${phone.location.path}")
	private String path;

	@Value("${trans.request.baseurl}")
	private String transBaseurl;

	@Value("${trans.ask.password}")
	private String askPassword;

	public RestClient() {
		super();
		log = LoggerFactory.getLogger(this.getClass());

		try{
			poolingHCCM = new PoolingHttpClientConnectionManager();  
			httpClient = HttpClients.createMinimal(poolingHCCM);
			httpFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			httpFactory.setConnectTimeout(10 * 1000); //设置超时为10秒
			httpFactory.setReadTimeout(10 * 1000);
			restTemplate = new RestTemplate(httpFactory);

			List<HttpMessageConverter<?>> hmcs = restTemplate.getMessageConverters();
			MappingJackson2HttpMessageConverter mj2hmc = null;
			for (byte i=0; i<hmcs.size(); i++){
				HttpMessageConverter<?> mc = hmcs.get(i);
				if (mc instanceof MappingJackson2HttpMessageConverter){
					mj2hmc = (MappingJackson2HttpMessageConverter) mc;
					hmcs.remove(i);
				}
			}
			if (mj2hmc == null)
				mj2hmc = new MappingJackson2HttpMessageConverter();
			Charset utf8Charset = Charset.forName("UTF-8");
			MediaType[] mediaTypes = {new MediaType("application", "json", utf8Charset),
					new MediaType("application", "*+json", utf8Charset),
					new MediaType("text","plain", utf8Charset)};
			mj2hmc.setSupportedMediaTypes(Arrays.asList(mediaTypes));
			hmcs.add(0, mj2hmc);
//			log.info("SupportedMediaTypes {}", mj2hmc.getSupportedMediaTypes());
//			log.info("HttpMessageConverters: {}", hmcs);
			restTemplate.setMessageConverters(hmcs);

			log.info("RestClient init success!");
		}
		catch (Exception e){
			log.error("RestClient init error: message: {}, StackTrace: {}", e.getMessage(), e.getStackTrace());
		}
	}

	public EntityMsg getLocationByMac(String device_mac){
		String url = "http://" + host + ":" + port + path + "/" + device_mac;
		log.info("getLocationByMac URL: {}", url);
		ResponseEntity<EntityBody> entity;
		EntityMsg entityMsg = null;
		EntityBody body;
		try{
			entity = restTemplate.getForEntity(url, EntityBody.class);
			if ((entity.getStatusCode().value() == 200) && (entity.hasBody())){
				body = entity.getBody();
				if (body.getCode() == 0)
					entityMsg = body.getMsg();
				else
					log.error("getLocationByMac error: action: {}, msg: {}", body.getAction(), body.getMsg());
			}
		}
		catch (Exception e){
			if (e instanceof HttpClientErrorException){
				HttpClientErrorException hcee = (HttpClientErrorException) e;
				log.error("getLocationByMac: HttpClientErrorException: {}" + hcee.getResponseBodyAsString());
			}
			log.error("getLocationByMac error: {}", e.getMessage());
	    }
		return entityMsg;
	}


	@Async
	public void requestHeartbeatAction() {
		String reqUrl = transBaseurl + "LoadBusRealDataJson?ask=" + askPassword + "&route=1&updown=0&statseq=1";
		try{
			restTemplate.getForEntity(reqUrl, BeanRetMsg.class);
			log.info("requestHeartbeatAction performed!");
		}
		catch (Exception e){
			if (e instanceof HttpClientErrorException){
				HttpClientErrorException hcee = (HttpClientErrorException) e;
				log.error("requestHeartbeatAction: HttpClientErrorException: {}" + hcee.getResponseBodyAsString());
			}
			if (e instanceof ResourceAccessException){
				try{
					restTemplate.getForEntity(reqUrl, BeanRetMsg.class);
					log.info("requestHeartbeatAction try again!");
				}
				catch (Exception retryException){
					log.error("requestHeartbeatAction retry error: {}, {}", e.getClass().getName(), e.getMessage());
				}
			}
			else
				log.error("requestHeartbeatAction error: {}, {}", e.getClass().getName(), e.getMessage());
	    }
	}

	public BeanRetMsg getBusRealtimeData(String route, byte updown, byte seq){
		String funcStr;
		if (route.substring(0, 1).compareTo("机") == 0){
			funcStr = "LoadJcdbRealDataJson";
			byte i, j;
			for (i=0; i<route.length(); i++)
				if (Character.isDigit(route.charAt(i)))
					break;
			for (j=i; j<route.length(); j++)
				if ( ! Character.isDigit(route.charAt(j)))
					break;
			if (i < j)
				route = route.substring(i, j);
		}
		else
			funcStr = "LoadBusRealDataJson";

		String reqUrl = transBaseurl + funcStr +"?ask=" + askPassword +
				"&route=" + route + "&updown=" + updown + "&statseq=" + seq;
		BeanRetMsg retMsg = null;
		ResponseEntity<BeanRetMsg> entity;
		try{
			entity = restTemplate.getForEntity(reqUrl, BeanRetMsg.class);
			if ((entity.getStatusCode().value() == 200) && (entity.hasBody()))
				retMsg = entity.getBody();
		}
		catch (Exception e){
			if (e instanceof HttpClientErrorException){
				HttpClientErrorException hcee = (HttpClientErrorException) e;
				log.error("getBusRealtimeData: HttpClientErrorException: {}" + hcee.getResponseBodyAsString());
			}
			if (e instanceof ResourceAccessException){
				try{
					entity = restTemplate.getForEntity(reqUrl, BeanRetMsg.class);
					if ((entity.getStatusCode().value() == 200) && (entity.hasBody()))
						retMsg = entity.getBody();
					log.info("getBusRealtimeData try again!");
				}
				catch (Exception retryException){
					log.error("getBusRealtimeData retry error: {}, {}", e.getClass().getName(), e.getMessage());
				}
			}
			else
				log.error("getBusRealtimeData error: {}, {}", e.getClass().getName(), e.getMessage());
	    }

		return retMsg;
	}




}
