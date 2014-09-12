package cn.com.navia.PhoneService.bean;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class UdpServIn {
	private String idFV = null; // identifierForVendor of iOS device
	private String phoneMac = null;
	private UdpRecvAP[] wifis = null;
	private PhoneAcc acc = null;
	private Float v = null;
	private Short ori = null;
	private long sendTime;


	public UdpServIn() {
		super();
	}

	public UdpServIn(String idFV, String phoneMac, UdpRecvAP[] wifis,
			PhoneAcc acc, Float v, Short ori, long sendTime) {
		super();
		this.idFV = idFV;
		this.phoneMac = phoneMac;
		this.wifis = wifis;
		this.acc = acc;
		this.v = v;
		this.ori = ori;
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "UdpServIn [idFV=" + idFV + ", phoneMac=" + phoneMac
				+ ", wifis=" + Arrays.toString(wifis) + ", acc=" + acc + ", v="
				+ v + ", ori=" + ori + ", sendTime=" + sendTime + "]";
	}

	@JsonInclude(Include.NON_NULL)
	public String getIdFV() {
		return idFV;
	}

	public void setIdFV(String idFV) {
		this.idFV = idFV;
	}

	@JsonInclude(Include.NON_NULL)
	public String getPhoneMac() {
		return phoneMac;
	}

	public void setPhoneMac(String phoneMac) {
		this.phoneMac = phoneMac;
	}

	@JsonInclude(Include.NON_NULL)
	public UdpRecvAP[] getWifis() {
		return wifis;
	}

	public void setWifis(UdpRecvAP[] wifis) {
		this.wifis = wifis;
	}

	@JsonInclude(Include.NON_NULL)
	public PhoneAcc getAcc() {
		return acc;
	}

	public void setAcc(PhoneAcc acc) {
		this.acc = acc;
	}

	@JsonInclude(Include.NON_NULL)
	public Float getV() {
		return v;
	}

	public void setV(Float v) {
		this.v = v;
	}

	@JsonInclude(Include.NON_NULL)
	public Short getOri() {
		return ori;
	}

	public void setOri(Short ori) {
		this.ori = ori;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
	
}
