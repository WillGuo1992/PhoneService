package cn.com.navia.PhoneService.bean;

public class BeanCoachStat {
	private String caption;
	private String name;

	public BeanCoachStat() {
		super();
	}

	public BeanCoachStat(String caption, String name) {
		super();
		this.caption = caption;
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BeanCoachStat [caption=" + caption + ", name=" + name + "]";
	}

}
