package cn.com.navia.PhoneService.bean;

import java.util.Arrays;

public class BeanCoachRetMsg {
	private String info;
	private String msg;
	private String publishTime;
	private String msKey;

	private BeanCoachRetItem[] schList = null;

	public BeanCoachRetMsg() {
		super();
	}

	public BeanCoachRetMsg(String info, String msg, String publishTime,
			BeanCoachRetItem[] schList) {
		super();
		this.info = info;
		this.msg = msg;
		this.publishTime = publishTime;
		this.schList = schList;
	}
	

	public BeanCoachRetMsg(String info, String msg, String publishTime, String msKey, BeanCoachRetItem[] schList) {
		super();
		this.info = info;
		this.msg = msg;
		this.publishTime = publishTime;
		this.msKey = msKey;
		this.schList = schList;
	}

	public String getMsKey() {
		return msKey;
	}

	public void setMsKey(String msKey) {
		this.msKey = msKey;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public BeanCoachRetItem[] getSchList() {
		return schList;
	}

	public void setSchList(BeanCoachRetItem[] schList) {
		this.schList = schList;
	}

	@Override
	public String toString() {
		return "BeanCoachRetMsg [info=" + info + ", msg=" + msg
				+ ", publishTime=" + publishTime + ", schList="
				+ Arrays.toString(schList) + "]";
	}

}
