package cn.com.navia.PhoneService.bean;

import org.eclipse.jetty.util.ajax.JSONObjectConvertor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class RespHub {
	private byte ret;
	private String desc;
	private Object data = null;

	public RespHub() {
		super();
	}

	public RespHub(byte ret, String desc) {
		super();
		this.ret = ret;
		this.desc = desc;
	}

	public RespHub(byte ret, String desc, Object data)  {
		super();
		this.ret = ret;
		this.desc = desc;
		this.data = data;
		/*
		try {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writeValueAsString(this));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}*/
	}

	public byte getRet() {
		return ret;
	}

	public void setRet(byte ret) {
		this.ret = ret;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@JsonInclude(Include.NON_NULL)
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RespHub [ret=" + ret + ", desc=" + desc + ", data=" + data
				+ "]";
	}

}
