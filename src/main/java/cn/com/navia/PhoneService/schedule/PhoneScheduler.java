package cn.com.navia.PhoneService.schedule;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.com.navia.PhoneService.mq.MQService;
import cn.com.navia.PhoneService.server.web.RestClient;


@Service
public class PhoneScheduler {

	@Resource
	private MQService mqService;

	@Resource
	private RestClient restClient;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
//	@Scheduled(initialDelay=1000*5, fixedRate=100)
//	private void activeMqSend(){
//		try{
//			mqService.pollAndSend();
//		}
//		catch (Exception e) {
//			log.error("PhoneScheduler activeMqSend error: message: {}, StackTrace: {}", e.getMessage(), e.getStackTrace());
//		}
//	}

	@Scheduled(initialDelay=1000*10, fixedRate=1000*120)
	private void requestHeartbeat(){
		try{
			restClient.requestHeartbeatAction();
		}
		catch (Exception e) {
			log.error("PhoneScheduler activeMqSend error: message: {}, StackTrace: {}", e.getMessage(), e.getStackTrace());
		}
	}

}
