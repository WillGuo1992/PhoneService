package cn.com.navia.PhoneService.route;

public class BeanEdge {
	public int eid;
	public int from;
	public int to;
	public int len;

	public BeanEdge() {
		super();
	}

	public BeanEdge(int eid, int from, int to, int len) {
		super();
		this.eid = eid;
		this.from = from;
		this.to = to;
		this.len = len;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	@Override
	public String toString() {
		return "BeanEdge [eid=" + eid + ", from=" + from + ", to=" + to
				+ ", len=" + len + "]";
	}

}
