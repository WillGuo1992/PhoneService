package cn.com.navia.PhoneService.assist;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShaUtil {

	private static Logger log = LoggerFactory.getLogger("ShaUtil");
	private static MessageDigest shaMD;
	
	static {
		try{
			shaMD = MessageDigest.getInstance("SHA", "SUN");
		}
		catch (Exception e) {
			log.error("MessageDigest.getInstance error: {}, {}", e.getClass().getName(), e.getMessage());
		}
	}

	public static String shaDigest(String message) throws Exception{
		StringBuffer shaStr = new StringBuffer();
	    byte[] digestByte = shaMD.digest(message.getBytes());
	    int dLength = digestByte.length;
	    for(byte i=0; i<dLength; i++)
	    	shaStr.append(HexUtil.byte2Hex(digestByte[i]));
	    return shaStr.toString();
	}
	
}
