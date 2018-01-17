package cn.com.navia.PhoneService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class BeanPoiSearch {
	private String caption;
	private String info = "";
	private String poiId = "";
	private int areaType = 0;
	private String clon = "";
	private String clat = "";
	private String mapName = "";
	private String floorId = "";

	public BeanPoiSearch() {
		super();
	}

	public BeanPoiSearch(String caption, String poiId, int areaType,
			String clon, String clat) {
		super();
		this.caption = caption;
		this.poiId = poiId;
		this.areaType = areaType;
		this.clon = clon;
		this.clat = clat;
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

	@JsonIgnore
	public String getPoiId() {
		return poiId;
	}

	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}

	@JsonIgnore
	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

	public String getClon() {
		return clon;
	}

	public void setClon(String clon) {
		this.clon = clon;
	}

	public String getClat() {
		return clat;
	}

	public void setClat(String clat) {
		this.clat = clat;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	@Override
	public String toString() {
		return "BeanPoiSearch [caption=" + caption + ", info=" + info
				+ ", poiId=" + poiId + ", areaType=" + areaType + ", clon="
				+ clon + ", clat=" + clat + ", mapName=" + mapName
				+ ", floorId=" + floorId + "]";
	}

}
