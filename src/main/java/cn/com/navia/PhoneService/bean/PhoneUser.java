package cn.com.navia.PhoneService.bean;

public class PhoneUser {

	private int uid; //用户唯一id

	private String email; //用户邮箱

	private String name; //用户名

	private String password; //用户密码

	private String did; //用户设备Mac地址

	private byte state; //用户状态(0:禁用,1:正常)

	private boolean reg; //是否注册


	public PhoneUser() {
		super();
	}

	public PhoneUser(int uid, String email, String name, String password,
			String did, byte state, boolean reg) {
		super();
		this.uid = uid;
		this.email = email;
		this.name = name;
		this.password = password;
		this.did = did;
		this.state = state;
		this.reg = reg;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public boolean isReg() {
		return reg;
	}

	public void setReg(boolean reg) {
		this.reg = reg;
	}

	@Override
	public String toString() {
		return "PhoneUser [uid=" + uid + ", email=" + email + ", name=" + name
				+ ", password=" + password + ", did=" + did + ", state="
				+ state + ", reg=" + reg + "]";
	}

}
