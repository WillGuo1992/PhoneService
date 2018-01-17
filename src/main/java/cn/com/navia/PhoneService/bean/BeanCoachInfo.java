package cn.com.navia.PhoneService.bean;

import java.util.Arrays;

public class BeanCoachInfo {
	private String pubTime;
	private String drvDate;
	private String start;
	private String dest;
	private String day1;
	private String day2;
	private String day3;
	private BeanCoachItem[] list = {};

	public BeanCoachInfo() {
		super();
	}

	public BeanCoachInfo(String drvDate, String start, String day1, String day2, String day3) {
		super();
		this.drvDate = drvDate;
		this.start = start;
		this.day1 = day1;
		this.day2 = day2;
		this.day3 = day3;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public String getDrvDate() {
		return drvDate;
	}

	public void setDrvDate(String drvDate) {
		this.drvDate = drvDate;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
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

	public BeanCoachItem[] getList() {
		return list;
	}

	public void setList(BeanCoachItem[] list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "BeanCoachInfo [pubTime=" + pubTime + ", drvDate=" + drvDate
				+ ", start=" + start + ", dest=" + dest + ", day1=" + day1
				+ ", day2=" + day2 + ", day3=" + day3 + ", list="
				+ Arrays.toString(list) + "]";
	}

}
