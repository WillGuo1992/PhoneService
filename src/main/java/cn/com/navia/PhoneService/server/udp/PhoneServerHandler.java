package cn.com.navia.PhoneService.server.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import cn.com.navia.PhoneService.bean.UdpServIn;
import cn.com.navia.PhoneService.mq.MQService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PhoneServerHandler extends ChannelInboundHandlerAdapter {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private MQService mqService;

	public PhoneServerHandler(ApplicationContext applicationContext) {
		mqService = applicationContext.getBean(MQService.class);
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
		if(msg instanceof UdpServIn){
			UdpServIn phoneUdpItem = (UdpServIn) msg;
			long phoneSendTime = phoneUdpItem.getSendTime();
			log.info("channelRead: apNum:{}, phoneMac:{}, sendTime:{}, cost {} ms.", phoneUdpItem.getWifis().length, phoneUdpItem.getPhoneMac(), phoneSendTime, (System.currentTimeMillis() - phoneSendTime));
//			mqService.offerAndCache(phoneUdpItem);
		}
		
	}
	
}
