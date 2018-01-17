package cn.com.navia.PhoneService.bean;

public class BeanHub {
	private short hid;
	private String name;
	private float lon;
	private float lat;
	private String brief;
	private String mapUrl;

	public BeanHub() {
		super();
	}

	public BeanHub(short hid, String name, float lon, float lat, String brief,
			String mapUrl) {
		super();
		this.hid = hid;
		this.name = name;
		this.lon = lon;
		this.lat = lat;
		this.brief = brief;
		this.mapUrl = mapUrl;
	}

	public short getHid() {
		return hid;
	}

	public void setHid(short hid) {
		this.hid = hid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getMapUrl() {
		return mapUrl;
	}

	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	@Override
	public String toString() {
		return "BeanHub [hid=" + hid + ", name=" + name + ", lon=" + lon
				+ ", lat=" + lat + ", brief=" + brief + ", mapUrl=" + mapUrl
				+ "]";
	}

}
