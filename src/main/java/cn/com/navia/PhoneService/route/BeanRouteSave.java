package cn.com.navia.PhoneService.route;

import java.util.Arrays;


public class BeanRouteSave {
	private BeanVertex[] vList;
	private BeanEdge[] eList;

	public BeanRouteSave() {
		super();
	}

	public BeanRouteSave(BeanVertex[] vList, BeanEdge[] eList) {
		super();
		this.vList = vList;
		this.eList = eList;
	}

	public BeanVertex[] getvList() {
		return vList;
	}

	public void setvList(BeanVertex[] vList) {
		this.vList = vList;
	}

	public BeanEdge[] geteList() {
		return eList;
	}

	public void seteList(BeanEdge[] eList) {
		this.eList = eList;
	}

	@Override
	public String toString() {
		return "BeanRouteSave [vList=" + Arrays.toString(vList) + ", eList="
				+ Arrays.toString(eList) + "]";
	}

}
