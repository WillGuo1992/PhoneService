package cn.com.navia.PhoneService.bean;


public class EntityBody {
	private int code;
	private String action;
	private EntityMsg msg = null;


	public EntityBody() {
		super();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public EntityMsg getMsg() {
		return msg;
	}

	public void setMsg(EntityMsg msg) {
		this.msg = msg;
	}

}
