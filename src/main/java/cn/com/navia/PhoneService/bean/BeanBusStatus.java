package cn.com.navia.PhoneService.bean;

public class BeanBusStatus {
	private String pubTime;
	private String bus1;
	private String bus2;
	private String bus3;

	public BeanBusStatus() {
		super();
	}

	public BeanBusStatus(String pubTime, String bus1, String bus2, String bus3) {
		super();
		this.pubTime = pubTime;
		this.bus1 = bus1;
		this.bus2 = bus2;
		this.bus3 = bus3;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public String getBus1() {
		return bus1;
	}

	public void setBus1(String bus1) {
		this.bus1 = bus1;
	}

	public String getBus2() {
		return bus2;
	}

	public void setBus2(String bus2) {
		this.bus2 = bus2;
	}

	public String getBus3() {
		return bus3;
	}

	public void setBus3(String bus3) {
		this.bus3 = bus3;
	}

	@Override
	public String toString() {
		return "BeanBusStatus [pubTime=" + pubTime + ", bus1=" + bus1
				+ ", bus2=" + bus2 + ", bus3=" + bus3 + "]";
	}

}
