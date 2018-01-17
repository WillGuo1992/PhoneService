package cn.com.navia.PhoneService.server.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.navia.PhoneService.module.PhoneBaseModule;

@Service
public class PhoneServer extends PhoneBaseModule {
	
//	@Value("${phone.udp.host}")
//	private String host;
//
//	@Value("${phone.udp.port}")
//	private int port;

	private EventLoopGroup boss = new NioEventLoopGroup();
	private Bootstrap bootstrap;
	
	@Override
	public void init() throws Exception {
		super.init();

		try {
			bootstrap = new Bootstrap();
			bootstrap.group(boss);
			bootstrap.channel(NioDatagramChannel.class);
//			bootstrap.handler(new ChannelInitializer<NioDatagramChannel>() {
//				@Override
//				public void initChannel(NioDatagramChannel ch) throws Exception {
//					ch.pipeline().addLast(PhoneDecoder.class.getSimpleName(), new PhoneDecoder(getApplicationContext()));
//					ch.pipeline().addLast(PhoneServerHandler.class.getSimpleName(), new PhoneServerHandler(getApplicationContext()));
//				}
//			});

//			if (host == null || host == "") {
//				bootstrap.bind(new InetSocketAddress(port));
//			} else {
//				bootstrap.bind(new InetSocketAddress(host, port));
//			}
//
//			log.info("Phone UDPServer start listen to=>{}:{}", host, port);

		} catch (Exception e) {
			throw new RuntimeException("UDPServer init error:", e);
		}
	}
}
