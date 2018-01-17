package cn.com.navia.PhoneService.bean;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BeanTransInfo {
	private String route;
	private byte updown;
	private String start;
	private String end;
	private String first;
	private String last;
	private String extra = "";
	private String poiId = "";
	private String floorId = "";
	private String addr = "";
	private byte real;
	private byte curSeq;
	private BeanStation[] list = null;

	public BeanTransInfo() {
		super();
	}

	public BeanTransInfo(String route, byte updown, String start, String end,
			String first, String last, String extra, String poiId, byte real,
			byte curSeq) {
		super();
		this.route = route;
		this.updown = updown;
		this.start = start;
		this.end = end;
		this.first = first;
		this.last = last;
		this.extra = extra;
		this.poiId = poiId;
		this.real = real;
		this.curSeq = curSeq;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public byte getUpdown() {
		return updown;
	}

	public void setUpdown(byte updown) {
		this.updown = updown;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getPoiId() {
		return poiId;
	}

	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public byte getReal() {
		return real;
	}

	public void setReal(byte real) {
		this.real = real;
	}

	public byte getCurSeq() {
		return curSeq;
	}

	public void setCurSeq(byte curSeq) {
		this.curSeq = curSeq;
	}

	@JsonInclude(Include.NON_NULL)
	public BeanStation[] getList() {
		return list;
	}

	public void setList(BeanStation[] list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "BeanTransInfo [route=" + route + ", updown=" + updown
				+ ", start=" + start + ", end=" + end + ", first=" + first
				+ ", last=" + last + ", extra=" + extra + ", poiId=" + poiId
				+ ", floorId=" + floorId + ", addr=" + addr + ", real=" + real
				+ ", curSeq=" + curSeq + ", list=" + Arrays.toString(list)
				+ "]";
	}

}
