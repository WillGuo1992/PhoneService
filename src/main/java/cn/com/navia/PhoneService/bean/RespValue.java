package cn.com.navia.PhoneService.bean;

public class RespValue {
	private short code;
	private String desc;

	public RespValue() {
		super();
	}

	public RespValue(short code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}

	public short getCode() {
		return code;
	}

	public void setCode(short code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "RespValue [code=" + code + ", desc=" + desc + "]";
	}

}
