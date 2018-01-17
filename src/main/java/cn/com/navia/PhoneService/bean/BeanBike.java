package cn.com.navia.PhoneService.bean;

public class BeanBike {
	private String statCode;
	private String name;
	private String address;
	private float lon;
	private float lat;

	public BeanBike() {
		super();
	}

	public BeanBike(String statCode, String name, String address, float lon,
			float lat) {
		super();
		this.statCode = statCode;
		this.name = name;
		this.address = address;
		this.lon = lon;
		this.lat = lat;
	}

	public String getStatCode() {
		return statCode;
	}

	public void setStatCode(String statCode) {
		this.statCode = statCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "BeanBike [statCode=" + statCode + ", name=" + name
				+ ", address=" + address + ", lon=" + lon + ", lat=" + lat
				+ "]";
	}

}
