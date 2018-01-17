package cn.com.navia.PhoneService.bean;


public class BeanItemBody {
	private int itemId;
	private String title;
	private String pubTime;
	private int pv;
	private String pic;
	private String body;

	public BeanItemBody() {
		super();
	}

	public BeanItemBody(int itemId, String title, String pubTime, int pv,
			String pic, String body) {
		super();
		this.itemId = itemId;
		this.title = title;
		this.pubTime = pubTime;
		this.pv = pv;
		this.pic = pic;
		this.body = body;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "BeanItemBody [itemId=" + itemId + ", title="
				+ title + ", pubTime=" + pubTime + ", pv=" + pv + ", pic="
				+ pic + ", body=" + body + "]";
	}

}
