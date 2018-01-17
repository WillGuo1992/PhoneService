package cn.com.navia.PhoneService.bean;


public class BeanShop {
	private String name;
	private String info = "";
	private String logo = "";
	private String poiId = "";
	private String floorId = "";
	private String addr = "";
	private String tele = "";
	private String intro = "";

	public BeanShop() {
		super();
	}

	public BeanShop(String name, String info, String logo, String poiId,
			String tele, String intro) {
		super();
		this.name = name;
		this.info = info;
		this.logo = logo;
		this.poiId = poiId;
		this.tele = tele;
		this.intro = intro;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPoiId() {
		return poiId;
	}

	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Override
	public String toString() {
		return "BeanShop [name=" + name + ", info=" + info + ", logo=" + logo
				+ ", poiId=" + poiId + ", floorId=" + floorId + ", addr="
				+ addr + ", tele=" + tele + ", intro=" + intro + "]";
	}

}
