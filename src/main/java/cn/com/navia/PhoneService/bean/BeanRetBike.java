package cn.com.navia.PhoneService.bean;

public class BeanRetBike {
	private String statCode;
	private short lockedNum;
	private short unlockNum;
	private String getTime;

	public BeanRetBike(String statCode, short lockedNum, short unlockNum, String getTime) {
		super();
		this.statCode = statCode;
		this.lockedNum = lockedNum;
		this.unlockNum = unlockNum;
		this.getTime = getTime;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public BeanRetBike() {
		super();
	}

	public BeanRetBike(String statCode, short lockedNum, short unlockNum) {
		super();
		this.statCode = statCode;
		this.lockedNum = lockedNum;
		this.unlockNum = unlockNum;
	}

	public String getStatCode() {
		return statCode;
	}

	public void setStatCode(String statCode) {
		this.statCode = statCode;
	}

	public short getLockedNum() {
		return lockedNum;
	}

	public void setLockedNum(short lockedNum) {
		this.lockedNum = lockedNum;
	}

	public short getUnlockNum() {
		return unlockNum;
	}

	public void setUnlockNum(short unlockNum) {
		this.unlockNum = unlockNum;
	}

	@Override
	public String toString() {
		return "BeanRetBike [statCode=" + statCode + ", lockedNum=" + lockedNum
				+ ", unlockNum=" + unlockNum + "]";
	}

}
