package cn.com.navia.PhoneService.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BeanSearchItem {
	private byte type = 0;		//1-公共设施, 2-商家, 3-车站
	private String caption;
	private String info = "";
	private String poiId = "";
	private String floorId = "";
	private short shopId = 0;
	private String route = null;
	private byte updown = -1;

	public BeanSearchItem() {
		super();
	}

	public BeanSearchItem(byte type, String caption, String poiId) {
		super();
		this.type = type;
		this.caption = caption;
		this.poiId = poiId;
	}

	public BeanSearchItem(byte type, String caption, String poiId, short shopId) {
		super();
		this.type = type;
		this.caption = caption;
		this.poiId = poiId;
		this.shopId = shopId;
	}

	public BeanSearchItem(byte type, String caption, String info, String poiId,
			String route, byte updown) {
		super();
		this.type = type;
		this.caption = caption;
		this.info = info;
		this.poiId = poiId;
		this.route = route;
		this.updown = updown;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPoiId() {
		return poiId;
	}

	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	@JsonInclude(Include.NON_DEFAULT)
	public short getShopId() {
		return shopId;
	}

	public void setShopId(short shopId) {
		this.shopId = shopId;
	}

	@JsonInclude(Include.NON_NULL)
	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	@JsonInclude(Include.NON_DEFAULT)
	public byte getUpdown() {
		return updown;
	}

	public void setUpdown(byte updown) {
		this.updown = updown;
	}

	@Override
	public String toString() {
		return "BeanSearchItem [type=" + type + ", caption=" + caption
				+ ", info=" + info + ", poiId=" + poiId + ", floorId="
				+ floorId + ", shopId=" + shopId + ", route=" + route
				+ ", updown=" + updown + "]";
	}

}
