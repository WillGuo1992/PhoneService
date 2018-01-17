package cn.com.navia.PhoneService.bean;

public class BeanTrans {
	private String caption;
	private String route;
	private byte updown;
	private String start;
	private String end;
	private String first;
	private String last;
	private byte real;

	public BeanTrans() {
		super();
	}

	public BeanTrans(String route, byte updown, String start, String end,
			String first, String last, byte real) {
		super();
		this.caption = route;
		this.route = route;
		this.updown = updown;
		this.start = start;
		this.end = end;
		this.first = first;
		this.last = last;
		this.real = real;
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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public byte getReal() {
		return real;
	}

	public void setReal(byte real) {
		this.real = real;
	}

	@Override
	public String toString() {
		return "BeanTrans [caption=" + caption + ", route=" + route
				+ ", updown=" + updown + ", start=" + start + ", end=" + end
				+ ", first=" + first + ", last=" + last + ", real=" + real
				+ "]";
	}

}
