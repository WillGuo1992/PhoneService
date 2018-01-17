package cn.com.navia.PhoneService.bean;

import java.util.Arrays;

public class BeanRetMsg {
	private String info;
	private String msg;
	private String msKey;
	private String publishTime;
	private BeanRetVehicle[] statTimeList = null;

	public BeanRetMsg() {
		super();
	}

	public BeanRetMsg(String info, String msg, String publishTime,
			BeanRetVehicle[] statTimeList) {
		super();
		this.info = info;
		this.msg = msg;
		this.publishTime = publishTime;
		this.statTimeList = statTimeList;
	}

	public BeanRetMsg(String info, String msg, String msKey, String publishTime, BeanRetVehicle[] statTimeList) {
		super();
		this.info = info;
		this.msg = msg;
		this.msKey = msKey;
		this.publishTime = publishTime;
		this.statTimeList = statTimeList;
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

	public BeanRetVehicle[] getStatTimeList() {
		return statTimeList;
	}

	public void setStatTimeList(BeanRetVehicle[] statTimeList) {
		this.statTimeList = statTimeList;
	}

	@Override
	public String toString() {
		return "BeanRetMsg [info=" + info + ", msg=" + msg + ", publishTime="
				+ publishTime + ", statTimeList="
				+ Arrays.toString(statTimeList) + "]";
	}

}
