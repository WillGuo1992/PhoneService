package cn.com.navia.PhoneService.bean;

public class BeanCoachRetItem {
	private String schid;
	private String drvdate;
	private String drvtime;
	private String startStat;
	private String schType;
	private String routeName;
	private String seats;
	private String endStat;
	private String mile;
	private String runInterval;
	private String price;
	private String yuPiao;

	public BeanCoachRetItem() {
		super();
	}

	public BeanCoachRetItem(String schid, String drvdate, String drvtime,
			String startStat, String schType, String routeName, String seats,
			String endStat, String mile, String runInterval, String price,
			String yuPiao) {
		super();
		this.schid = schid;
		this.drvdate = drvdate;
		this.drvtime = drvtime;
		this.startStat = startStat;
		this.schType = schType;
		this.routeName = routeName;
		this.seats = seats;
		this.endStat = endStat;
		this.mile = mile;
		this.runInterval = runInterval;
		this.price = price;
		this.yuPiao = yuPiao;
	}

	public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}

	public String getDrvdate() {
		return drvdate;
	}

	public void setDrvdate(String drvdate) {
		this.drvdate = drvdate;
	}

	public String getDrvtime() {
		return drvtime;
	}

	public void setDrvtime(String drvtime) {
		this.drvtime = drvtime;
	}

	public String getStartStat() {
		return startStat;
	}

	public void setStartStat(String startStat) {
		this.startStat = startStat;
	}

	public String getSchType() {
		return schType;
	}

	public void setSchType(String schType) {
		this.schType = schType;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public String getEndStat() {
		return endStat;
	}

	public void setEndStat(String endStat) {
		this.endStat = endStat;
	}

	public String getMile() {
		return mile;
	}

	public void setMile(String mile) {
		this.mile = mile;
	}

	public String getRunInterval() {
		return runInterval;
	}

	public void setRunInterval(String runInterval) {
		this.runInterval = runInterval;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getYuPiao() {
		return yuPiao;
	}

	public void setYuPiao(String yuPiao) {
		this.yuPiao = yuPiao;
	}

	@Override
	public String toString() {
		return "BeanCoachRetItem [schid=" + schid + ", drvdate=" + drvdate
				+ ", drvtime=" + drvtime + ", startStat=" + startStat
				+ ", schType=" + schType + ", routeName=" + routeName
				+ ", seats=" + seats + ", endStat=" + endStat + ", mile="
				+ mile + ", runInterval=" + runInterval + ", price=" + price
				+ ", yuPiao=" + yuPiao + "]";
	}

}
