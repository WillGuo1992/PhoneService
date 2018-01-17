package cn.com.navia.PhoneService.bean;

public class BeanStation {
	private byte seq;
	private String name;

	public BeanStation() {
		super();
	}

	public BeanStation(byte seq, String name) {
		super();
		this.seq = seq;
		this.name = name;
	}

	public byte getSeq() {
		return seq;
	}

	public void setSeq(byte seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BeanStation [seq=" + seq + ", name=" + name + "]";
	}

}
