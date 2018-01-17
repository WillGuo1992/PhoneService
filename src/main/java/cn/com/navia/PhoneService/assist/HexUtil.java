package cn.com.navia.PhoneService.assist;

public class HexUtil {

	public static String byte2Hex(byte inByte){
		StringBuffer hexStr = new StringBuffer(2);
		int tempInt = inByte & 0xff;
		if (tempInt < 16)
			hexStr.append("0");
		hexStr.append(Integer.toHexString(tempInt).toUpperCase());

		return hexStr.toString();
	}

	public static String bytes2hexStr(byte[] inBytes){
		StringBuffer hexString = new StringBuffer();
		StringBuffer realStr = new StringBuffer(16);
		String huanhang = System.getProperty("line.separator");
		String[] zeroStr = {"0000","000","00","0",""};
		String idxStr;
		hexString.append(huanhang + "0x0000" + "    ");
		int bytesLen = inBytes.length;
		for (int i=1; i < bytesLen +1; i++){
			hexString.append(byte2Hex(inBytes[i-1]));
			hexString.append(" ");
			if (inBytes[i-1] > 31)
				realStr.append(new String(inBytes, i-1, 1));
			else
				realStr.append(".");
			
			if (i % 8 == 0){
				hexString.append("- ");
			}
			if (i % 16 == 0){
				hexString.append(realStr.toString());
				realStr.setLength(0);
				hexString.append(huanhang);
				idxStr = Integer.toHexString(i).toUpperCase();
				hexString.append("0x" + zeroStr[idxStr.length()] + idxStr + "    ");
			}
		}
		if (realStr.length() > 0)
			hexString.append(" - " + realStr.toString());
		return hexString.toString();
	}
	
	public static String byteNeg2LowerHex(byte inByte){
		StringBuffer hexStr = new StringBuffer(2);
		inByte = (byte) ~inByte;
		int tempInt = inByte & 0xff;
		if (tempInt < 16)
			hexStr.append("0");
		hexStr.append(Integer.toHexString(tempInt).toLowerCase());

		return hexStr.toString();
	}

}
