package cn.com.navia.PhoneService.bean;

public class BeanRetVehicle {
	private String vehCode;
	private String nextStatSeq;
	private String nextDistance;
	private String nextStatTime;
	private String dstStatTime;
	private String endStat;
	private String startStat;
	private String gpsTime;
	

	public BeanRetVehicle() {
		super();
	}


	public BeanRetVehicle(String vehCode, String nextStatSeq, String nextDistance, String nextStatTime,
			String dstStatTime, String endStat, String startStat, String gpsTime) {
		super();
		this.vehCode = vehCode;
		this.nextStatSeq = nextStatSeq;
		this.nextDistance = nextDistance;
		this.nextStatTime = nextStatTime;
		this.dstStatTime = dstStatTime;
		this.endStat = endStat;
		this.startStat = startStat;
		this.gpsTime = gpsTime;
	}


	public String getEndStat() {
		return endStat;
	}


	public void setEndStat(String endStat) {
		this.endStat = endStat;
	}


	public String getStartStat() {
		return startStat;
	}


	public void setStartStat(String startStat) {
		this.startStat = startStat;
	}


	public String getGpsTime() {
		return gpsTime;
	}


	public void setGpsTime(String gpsTime) {
		this.gpsTime = gpsTime;
	}


	public String getVehCode() {
		return vehCode;
	}

	public void setVehCode(String vehCode) {
		this.vehCode = vehCode;
	}

	public String getNextStatSeq() {
		return nextStatSeq;
	}

	public void setNextStatSeq(String nextStatSeq) {
		this.nextStatSeq = nextStatSeq;
	}

	public String getNextDistance() {
		return nextDistance;
	}

	public void setNextDistance(String nextDistance) {
		this.nextDistance = nextDistance;
	}

	public String getNextStatTime() {
		return nextStatTime;
	}

	public void setNextStatTime(String nextStatTime) {
		this.nextStatTime = nextStatTime;
	}

	public String getDstStatTime() {
		return dstStatTime;
	}

	public void setDstStatTime(String dstStatTime) {
		this.dstStatTime = dstStatTime;
	}

	@Override
	public String toString() {
		return "BeanRetVehicle [vehCode=" + vehCode + ", nextStatSeq="
				+ nextStatSeq + ", nextDistance=" + nextDistance
				+ ", nextStatTime=" + nextStatTime + ", dstStatTime="
				+ dstStatTime + "]";
	}

}
