package cn.com.navia.PhoneService.bean;

public class BeanInit {
	private String token;
	private String base;
	private String curVer;
	private String apkUrl;
	private byte force;

	public BeanInit() {
		super();
	}

	public BeanInit(String token, String base, String curVer, String apkUrl,
			byte force) {
		super();
		this.token = token;
		this.base = base;
		this.curVer = curVer;
		this.apkUrl = apkUrl;
		this.force = force;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getCurVer() {
		return curVer;
	}

	public void setCurVer(String curVer) {
		this.curVer = curVer;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public byte getForce() {
		return force;
	}

	public void setForce(byte force) {
		this.force = force;
	}

	@Override
	public String toString() {
		return "BeanInit [token=" + token + ", base=" + base + ", curVer="
				+ curVer + ", apkUrl=" + apkUrl + ", force=" + force + "]";
	}

}
