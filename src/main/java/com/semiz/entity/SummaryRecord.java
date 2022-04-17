package com.semiz.entity;

public class SummaryRecord {
	
	private int id;
	private long total = 0L;
	
	public SummaryRecord() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Summary={id="+this.id+", total="+total+"}";
	}
	
}
