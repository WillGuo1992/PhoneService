package cn.com.navia.PhoneService.bean;

public class BeanSpecInfo {
	private String curVer;
	private String spec;

	public BeanSpecInfo() {
		super();
	}

	public BeanSpecInfo(String curVer, String spec) {
		super();
		this.curVer = curVer;
		this.spec = spec;
	}

	public String getCurVer() {
		return curVer;
	}

	public void setCurVer(String curVer) {
		this.curVer = curVer;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@Override
	public String toString() {
		return "BeanSpecInfo [curVer=" + curVer + ", spec=" + spec + "]";
	}

}
