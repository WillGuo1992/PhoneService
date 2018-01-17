package cn.com.navia.PhoneService.bean;

public class BeanFloor {
	private String floorId;
	private String mapName = "";
	private String label;
	private String name;
	private String info;

	public BeanFloor() {
		super();
	}

	public BeanFloor(String floorId, String mapName, String label, String name, String info) {
		super();
		this.floorId = floorId;
		this.mapName = mapName;
		this.label = label;
		this.name = name;
		this.info = info;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	@Override
	public String toString() {
		return "BeanFloor [floorId=" + floorId + ", mapName=" + mapName
				+ ", label=" + label + ", name=" + name + ", info=" + info
				+ "]";
	}

}
