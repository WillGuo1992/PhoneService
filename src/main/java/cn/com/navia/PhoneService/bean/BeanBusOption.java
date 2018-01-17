package cn.com.navia.PhoneService.bean;

public class BeanBusOption {
	private String caption;
	private String route;
	private byte updown;

	public BeanBusOption() {
		super();
	}

	public BeanBusOption(String caption, String route, byte updown) {
		super();
		this.caption = caption;
		this.route = route;
		this.updown = updown;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public byte getUpdown() {
		return updown;
	}

	public void setUpdown(byte updown) {
		this.updown = updown;
	}

	@Override
	public String toString() {
		return "BeanBusOption [caption=" + caption + ", route=" + route
				+ ", updown=" + updown + "]";
	}

}
