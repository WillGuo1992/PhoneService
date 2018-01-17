package cn.com.navia.PhoneService.route;

public class BeanVertex {
	public int vid;
	public String flr;
	public double lon;
	public double lat;

	public BeanVertex() {
		super();
	}

	public BeanVertex(int vid, String flr, double lon, double lat) {
		super();
		this.vid = vid;
		this.flr = flr;
		this.lon = lon;
		this.lat = lat;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public String getFlr() {
		return flr;
	}

	public void setFlr(String flr) {
		this.flr = flr;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "BeanVertex [vid=" + vid + ", flr=" + flr + ", lon=" + lon
				+ ", lat=" + lat + "]";
	}

}
