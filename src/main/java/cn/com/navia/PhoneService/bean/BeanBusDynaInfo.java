package cn.com.navia.PhoneService.bean;

public class BeanBusDynaInfo {
	private long milliSecond;
	private int nextDistance;
	private byte nextStatSeq;

	public BeanBusDynaInfo() {
		super();
	}

	public BeanBusDynaInfo(long milliSecond, int nextDistance, byte nextStatSeq) {
		super();
		this.milliSecond = milliSecond;
		this.nextDistance = nextDistance;
		this.nextStatSeq = nextStatSeq;
	}

	public long getMilliSecond() {
		return milliSecond;
	}

	public void setMilliSecond(long milliSecond) {
		this.milliSecond = milliSecond;
	}

	public int getNextDistance() {
		return nextDistance;
	}

	public void setNextDistance(int nextDistance) {
		this.nextDistance = nextDistance;
	}

	public byte getNextStatSeq() {
		return nextStatSeq;
	}

	public void setNextStatSeq(byte nextStatSeq) {
		this.nextStatSeq = nextStatSeq;
	}

	@Override
	public String toString() {
		return "BeanBusDynaInfo [milliSecond=" + milliSecond
				+ ", nextDistance=" + nextDistance + ", nextStatSeq="
				+ nextStatSeq + "]";
	}

}
