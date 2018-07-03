package cn.dabaisk.icauto.entity;

import java.util.Comparator;

public class Numbers implements Comparator<Numbers> {

	@Override
	public int compare(Numbers arg0, Numbers arg1) {
		if (arg0.getCount() < arg1.getCount()) {
			return 1;
		} else {
			return -1;
		}
	}

	private String number;
	private int count;

	public Numbers() {
		super();
	}

	public Numbers(String number, int count) {
		super();
		this.number = number;
		this.count = count;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "[" + number + ", " + count + "]";
	}

}
