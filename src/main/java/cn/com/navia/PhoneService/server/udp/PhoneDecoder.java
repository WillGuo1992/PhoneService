package cn.com.navia.PhoneService.server.udp;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import cn.com.navia.PhoneService.assist.HexUtil;
import cn.com.navia.PhoneService.bean.UdpRecvAP;
import cn.com.navia.PhoneService.bean.UdpServIn;
import cn.com.navia.PhoneService.bean.PhoneAcc;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

public class PhoneDecoder extends MessageToMessageDecoder<DatagramPacket> {

	private byte PROTO_HEAD;
	private String PROTO_PHONE;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private StringBuffer mac = new StringBuffer(17);

	public PhoneDecoder(ApplicationContext appCtx) {
		try{
			PROTO_HEAD = Byte.parseByte(appCtx.getEnvironment().getProperty("phone.udp.proto.head"));
			PROTO_PHONE = appCtx.getEnvironment().getProperty("phone.udp.proto.identifier");
		}
		catch (NumberFormatException e){
			log.error("PROTO_HEAD parseByte error: {}", e.getMessage());
			PROTO_HEAD = 0x05;
		}
		finally{
			if (PROTO_PHONE == null)
				PROTO_PHONE = "PHON";
		}
		
	}	
	
	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out){

		InetSocketAddress sender = msg.sender();
		ByteBuf buf = msg.content();
		UdpServIn phoneUdpItem = null;
		UdpRecvAP[] wifis = null;
		PhoneAcc acc = null;
		Short ori = null;
		boolean retCode = false;
		String idFV = null;
		String phoneMac = null;
		String identifier, bssid, ssid;
		byte proto, version, apNum, freq, ssidLen;
		long seq, scantime, sendtime;
		short channel, rssi;
		float x, y, z;
		byte[] fourBytes = new byte[4];
		byte[] sixBytes = new byte[6];
		byte[] ssidBytes;
		seq = -1; // Return sequence -1 to the sender if fail to read packet.

		try {
			debugMsg(buf.copy());

			proto = buf.readByte();
			if (proto != PROTO_HEAD){
				log.error("PhoneUDP Decoder PROTO_HEAD error: PROTO_HEAD={}", proto);
				return;
			}

			version = buf.readByte();
			if (version < 0){
				buf = buf.order(ByteOrder.LITTLE_ENDIAN);
				buf.readBytes(fourBytes);
				identifier = new String(fourBytes);
				if (identifier.compareTo(PROTO_PHONE) != 0){
					log.error("PhoneUDP Decoder PROTO_PHONE error: PROTO_PHONE={}", identifier);
					return;
				}
				log.info("seq: {}", buf.readLong());
				byte[] thirtySixBytes = new byte[36];
				buf.readBytes(thirtySixBytes);
				log.info("idFV: {}", new String(thirtySixBytes));
				log.info("v: {}", buf.readFloat());
				log.info("ori: {}", buf.readInt());
				log.info("time: {}", buf.readLong());
				return;
			}
			
			
			buf.readBytes(fourBytes);
			identifier = new String(fourBytes);
			if (identifier.compareTo(PROTO_PHONE) != 0){
				log.error("PhoneUDP Decoder PROTO_PHONE error: PROTO_PHONE={}", identifier);
				return;
			}

			seq = buf.readLong();
			buf.readBytes(sixBytes);
			phoneMac = macStr(sixBytes);
			apNum = buf.readByte();
			if (apNum > 0){
				wifis = new UdpRecvAP[apNum];
				for(byte i=0; i < apNum; i++){
					scantime = buf.readLong();
					buf.readBytes(sixBytes);
					bssid = macStr(sixBytes);
					channel = buf.readUnsignedByte();
					freq = buf.readByte();
					rssi = buf.readShort();
					ssidLen = buf.readByte();
					ssidBytes = new byte[ssidLen];
					buf.readBytes(ssidBytes);
					ssid = new String(ssidBytes);
					wifis[i] = new UdpRecvAP(ssid, bssid, channel, freq, rssi, scantime);
				}				
			}

			if (buf.readByte() == 1){
				x = buf.readFloat();
				y = buf.readFloat();
				z = buf.readFloat();
				acc = new PhoneAcc(x, y ,z);
			}

			if (buf.readByte() == 1)
				ori = new Short(buf.readShort());

			sendtime = buf.readLong();
			phoneUdpItem = new UdpServIn(idFV, phoneMac, wifis, acc, ori, sendtime);
			retCode = true;
		}
		catch (Exception e){
			e.printStackTrace();
			log.error("PhoneUDP Decoder error:{}", e.getMessage());
		}
		finally {
			if (phoneUdpItem != null)
				out.add(phoneUdpItem);

			ByteBuf respData = Unpooled.buffer(17);
			respData.writeByte(PROTO_HEAD);
			respData.writeBytes(PROTO_PHONE.getBytes());
			respData.writeInt(retCode ? 0 : -1);
			respData.writeLong(seq);
			ctx.writeAndFlush(new DatagramPacket(respData, sender));
		}
	}

	private String macStr(byte[] byteMac){
		mac.setLength(0);
		for(byte i=0; i < 6; i++){
			mac.append(HexUtil.byte2Hex(byteMac[i]));
			if (i < 5)
				mac.append(":");
		}
		
		return mac.toString();
	}

	private void debugMsg(ByteBuf bufCopy){
		int readLen = bufCopy.readableBytes();
		byte[] recvBytes = new byte[readLen];
		bufCopy.readBytes(recvBytes);
		String debugStr = HexUtil.bytes2hexStr(recvBytes);
		log.info("PhoneUDP: readLength{}, {}", readLen, debugStr);
	}

}
