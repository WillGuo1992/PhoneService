package cn.com.navia.PhoneService.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RespBody {

	private RespValue state = null;
	private EntityMsg msg = null;

	public RespBody() {
		super();
	}

	public RespBody(RespValue state) {
		super();
		this.state = state;
	}

	public RespBody(RespValue state, EntityMsg msg) {
		super();
		this.state = state;
		this.msg = msg;
	}

	public RespValue getState() {
		return state;
	}

	public void setState(RespValue state) {
		this.state = state;
	}

	@JsonInclude(Include.NON_NULL)
	public EntityMsg getMsg() {
		return msg;
	}

	public void setMsg(EntityMsg msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "RespBody [state=" + state + ", msg=" + msg + "]";
	}

}
