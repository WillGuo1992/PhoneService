package cn.com.navia.PhoneService.schedule;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.com.navia.PhoneService.mq.MQService;


@Service
public class PhoneScheduler {

	@Resource
	private MQService mqService;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Scheduled(initialDelay=1000*5, fixedRate=1000)
	private void activeMqSend(){
		try{
			mqService.pollAndSend();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("PhoneScheduler activeMqSend error: {}", e.getMessage());
		}
	}

}
