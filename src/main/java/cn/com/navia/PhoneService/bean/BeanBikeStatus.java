package cn.com.navia.PhoneService.bean;

public class BeanBikeStatus {
	private String pubTime;
	private short totalNum;
	private short lockedNum;

	public BeanBikeStatus() {
		super();
	}

	public BeanBikeStatus(String pubTime, short totalNum, short lockedNum) {
		super();
		this.pubTime = pubTime;
		this.totalNum = totalNum;
		this.lockedNum = lockedNum;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public short getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(short totalNum) {
		this.totalNum = totalNum;
	}

	public short getLockedNum() {
		return lockedNum;
	}

	public void setLockedNum(short lockedNum) {
		this.lockedNum = lockedNum;
	}

	@Override
	public String toString() {
		return "BeanBikeStatus [pubTime=" + pubTime + ", totalNum=" + totalNum
				+ ", lockedNum=" + lockedNum + "]";
	}

}
