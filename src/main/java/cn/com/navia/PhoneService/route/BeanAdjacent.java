package cn.com.navia.PhoneService.route;

public class BeanAdjacent {
	public int vid;
	public int eSeq = -1;
	public int eNum = 0;

	public BeanAdjacent(int vid, int eSeq) {
		super();
		this.vid = vid;
		this.eSeq = eSeq;
	}

	@Override
	public String toString() {
		return "BeanAdjacent [vid=" + vid + ", eSeq=" + eSeq + ", eNum=" + eNum
				+ "]";
	}

}
