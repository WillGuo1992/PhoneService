package cn.com.navia.PhoneService.bean;

public class BeanCoachItem {
	private String drvTime;
	private String schId;
	private String routeName;
	private String price;
	private String seats;
	private String rest;
	private String km;

	public BeanCoachItem() {
		super();
	}

	public BeanCoachItem(String drvTime, String schId, String routeName,
			String price, String seats, String rest, String km) {
		super();
		this.drvTime = drvTime;
		this.schId = schId;
		this.routeName = routeName;
		this.price = price;
		this.seats = seats;
		this.rest = rest;
		this.km = km;
	}

	public String getDrvTime() {
		return drvTime;
	}

	public void setDrvTime(String drvTime) {
		this.drvTime = drvTime;
	}

	public String getSchId() {
		return schId;
	}

	public void setSchId(String schId) {
		this.schId = schId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public String getRest() {
		return rest;
	}

	public void setRest(String rest) {
		this.rest = rest;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	@Override
	public String toString() {
		return "BeanCoachItem [drvTime=" + drvTime + ", schId=" + schId
				+ ", routeName=" + routeName + ", price=" + price + ", seats="
				+ seats + ", rest=" + rest + ", km=" + km + "]";
	}

}
