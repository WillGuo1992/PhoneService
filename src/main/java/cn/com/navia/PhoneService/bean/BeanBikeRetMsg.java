package cn.com.navia.PhoneService.bean;

public class BeanBikeRetMsg {
	private String info;
	private String msg;
	private String publishTime;
	private String msKey;
	

	private BeanRetBike statInfo = null;

	public BeanBikeRetMsg() {
		super();
	}


	
	public BeanBikeRetMsg(String info, String msg, String publishTime, String msKey, BeanRetBike statInfo) {
		super();
		this.info = info;
		this.msg = msg;
		this.publishTime = publishTime;
		this.msKey = msKey;
		this.statInfo = statInfo;
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

	public BeanRetBike getStatInfo() {
		return statInfo;
	}

	public void setStatInfo(BeanRetBike statInfo) {
		this.statInfo = statInfo;
	}

	@Override
	public String toString() {
		return "BeanBikeRetMsg [info=" + info + ", msg=" + msg
				+ ", publishTime=" + publishTime + ", statInfo=" + statInfo
				+ "]";
	}

}
