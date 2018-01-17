package cn.com.navia.PhoneService.bean;

public class BeanAB {
	private int itemId;
	private String pic;

	public BeanAB() {
		super();
	}

	public BeanAB(int itemId, String pic) {
		super();
		this.itemId = itemId;
		this.pic = pic;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Override
	public String toString() {
		return "BeanAB [itemId=" + itemId + ", pic=" + pic + "]";
	}

}
