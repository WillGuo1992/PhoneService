package cn.com.navia.PhoneService.server.web;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cn.com.navia.PhoneService.bean.EntityBody;
import cn.com.navia.PhoneService.bean.EntityMsg;
import cn.com.navia.module.BaseModule;


@Service
public class RestClient extends BaseModule {

	private PoolingHttpClientConnectionManager poolingHCCM;
	private CloseableHttpClient httpClient;
	private HttpComponentsClientHttpRequestFactory httpFactory;
	private RestTemplate restTemplate;
	private String url;
	
	@Value("${phone.location.host}")
	private String host;

	@Value("${phone.location.port}")
	private String port;
	
	@Value("${phone.location.path}")
	private String path;
	
	@Override
	public void init() throws Exception {
		super.init();
		
		try{
			poolingHCCM = new PoolingHttpClientConnectionManager();  
			httpClient = HttpClients.createMinimal(poolingHCCM);
			httpFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			httpFactory.setConnectTimeout(5 * 1000); //设置超时为5秒
			httpFactory.setReadTimeout(5 * 1000);
			restTemplate = new RestTemplate(httpFactory);
			log.info("RestClient init success!");
		}
		catch (Exception e){
			log.error("RestClient init error: {}", e.getMessage());
			e.printStackTrace();
		}
	}

	public EntityMsg getLocationByMac(String device_mac){
		url = "http://" + host + ":" + port + path + "/" + device_mac;
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
			e.printStackTrace();
	       }
		return entityMsg;
	}
}
