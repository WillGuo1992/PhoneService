package cn.com.navia.PhoneService.bean;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BeanPoiSelected {
	private byte type = 0;		//1-公共设施, 2-商家, 3-车站
	private String name;
	private String info = "";
	private String logo = "";
	private short shopId = 0;
	private BeanBusOption[] options = null;

	public BeanPoiSelected() {
		super();
	}

	public BeanPoiSelected(byte type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public BeanPoiSelected(byte type, String name, String info, String logo,
			short shopId) {
		super();
		this.type = type;
		this.name = name;
		this.info = info;
		this.logo = logo;
		this.shopId = shopId;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@JsonInclude(Include.NON_DEFAULT)
	public short getShopId() {
		return shopId;
	}

	public void setShopId(short shopId) {
		this.shopId = shopId;
	}

	@JsonInclude(Include.NON_NULL)
	public BeanBusOption[] getOptions() {
		return options;
	}

	public void setOptions(BeanBusOption[] options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "BeanPoiSelected [type=" + type + ", name=" + name + ", info="
				+ info + ", logo=" + logo + ", shopId=" + shopId + ", options="
				+ Arrays.toString(options) + "]";
	}

}
