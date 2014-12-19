package cn.com.navia.PhoneService.mq;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.com.navia.PhoneService.bean.UdpServIn;


@Service
public class MQService {

	@Resource(name = "phoneJmsTemplate")
	private JmsTemplate phoneJmsTemplate;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private LinkedBlockingQueue<UdpServIn> udpItemLBQueue = new LinkedBlockingQueue<UdpServIn>(1024);

	@Async
	public void pollAndSend() {
		UdpServIn udpItem = null;
		while (true) {
			try {
				udpItem = udpItemLBQueue.poll(50, TimeUnit.MILLISECONDS);
				if (udpItem == null)
					break;

				phoneJmsTemplate.convertAndSend(udpItem, new MessagePostProcessor(){
					@Override
					public Message postProcessMessage(Message message) throws JMSException {
						message.setLongProperty("stime", System.currentTimeMillis());
						return message;
					}
				});
				long phoneSendTime = udpItem.getSendTime();
				log.info("Finish sending to MQ, phoneMac:{}, sendTime:{}, totally {} ms.", udpItem.getPhoneMac(), phoneSendTime, (System.currentTimeMillis() - phoneSendTime));
			} 
			catch (JmsException e) {
				log.error("MQService pollAndSend JmsException: code: {}, message: {}", e.getErrorCode(), e.getMessage());
			}
			catch (Exception e) {
				log.error("MQService pollAndSend error: message: {}, StackTrace: {}", e.getMessage(), e.getStackTrace());
			}
		}
	}

	@Async
	public void offerAndCache(UdpServIn udpItem) {
		try{
			if(!(udpItemLBQueue.offer(udpItem, 10, TimeUnit.MILLISECONDS)))
				log.error("MQService offerAndCache failed! The LinkedBlockingQueue is full (1024 elements)!");;
		}
		catch (Exception e) {
			log.error("MQService offerAndCache error: message: {}, StackTrace: {}", e.getMessage(), e.getStackTrace());
		}
		//log.info("MQService offerAndCache success!");
	}

}
