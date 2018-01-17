package cn.com.navia.PhoneService.bean;

public class BeanCoachBasic {
	private String start;
	private String day1;
	private String day2;
	private String day3;

	public BeanCoachBasic() {
		super();
	}

	public BeanCoachBasic(String start, String day1, String day2, String day3) {
		super();
		this.start = start;
		this.day1 = day1;
		this.day2 = day2;
		this.day3 = day3;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDay1() {
		return day1;
	}

	public void setDay1(String day1) {
		this.day1 = day1;
	}

	public String getDay2() {
		return day2;
	}

	public void setDay2(String day2) {
		this.day2 = day2;
	}

	public String getDay3() {
		return day3;
	}

	public void setDay3(String day3) {
		this.day3 = day3;
	}

	@Override
	public String toString() {
		return "BeanCoachBasic [start=" + start + ", day1=" + day1 + ", day2="
				+ day2 + ", day3=" + day3 + "]";
	}

}
