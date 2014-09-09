package cn.com.navia.PhoneService.bean;

import java.util.Date;

public class UserLogInfo {

	private int uid; //用户ID
	
	private String name; //用户名
	
	private String device_mac; //登录设备Mac地址
	
	private Date login_time; //本次登陆时间

	public UserLogInfo() {
		super();
	}

	public UserLogInfo(int uid, String name, String device_mac, Date login_time) {
		super();
		this.uid = uid;
		this.name = name;
		this.device_mac = device_mac;
		this.login_time = login_time;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDevice_mac() {
		return device_mac;
	}

	public void setDevice_mac(String device_mac) {
		this.device_mac = device_mac;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	@Override
	public String toString() {
		return "LogInfo [uid=" + uid + ", name=" + name + ", device_mac="
				+ device_mac + ", login_time=" + login_time + "]";
	}

}
