package cn.com.navia.PhoneService.bean;

public class BeanMB {
	private short mbId;
	private String pic;
	private String link = "";

	public BeanMB() {
		super();
	}

	public BeanMB(short mbId, String pic, String link) {
		super();
		this.mbId = mbId;
		this.pic = pic;
		this.link = link;
	}

	public short getMbId() {
		return mbId;
	}

	public void setMbId(short mbId) {
		this.mbId = mbId;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "BeanMB [mbId=" + mbId + ", pic=" + pic + ", link=" + link + "]";
	}

}
