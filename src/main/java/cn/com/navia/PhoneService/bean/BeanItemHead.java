package cn.com.navia.PhoneService.bean;

public class BeanItemHead {
	private int itemId;
	private String title;
	private String icon;
	private String head;

	public BeanItemHead() {
		super();
	}

	public BeanItemHead(int itemId, String title, String icon, String head) {
		super();
		this.itemId = itemId;
		this.title = title;
		this.icon = icon;
		this.head = head;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	@Override
	public String toString() {
		return "BeanItemHead [itemId=" + itemId + ", title=" + title
				+ ", icon=" + icon + ", head=" + head + "]";
	}

}
