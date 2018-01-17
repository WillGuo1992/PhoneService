package cn.com.navia.PhoneService.bean;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RespList {
	private String base = null;
	private Object[] list = null;

	public RespList() {
		super();
	}

	public RespList(String base, Object[] list) {
		super();
		this.base = base;
		this.list = list;
	}

	public RespList(String base) {
		super();
		this.base = base;
	}

	public RespList(Object[] list) {
		super();
		this.list = list;
	}

	@JsonInclude(Include.NON_NULL)
	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	@JsonInclude(Include.NON_NULL)
	public Object[] getList() {
		return list;
	}

	public void setList(Object[] list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "RespList [base=" + base + ", list=" + Arrays.toString(list)
				+ "]";
	}

}
