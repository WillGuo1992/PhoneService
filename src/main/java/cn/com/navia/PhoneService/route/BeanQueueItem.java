package cn.com.navia.PhoneService.route;

public class BeanQueueItem {
	public int vid;
	public int dist;

	public BeanQueueItem(int vid, int dist) {
		super();
		this.vid = vid;
		this.dist = dist;
	}

	@Override
	public String toString() {
		return "BeanQueueItem [vid=" + vid + ", dist=" + dist + "]";
	}

}
