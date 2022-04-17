package com.semiz.entity;

public class DetailRecord {
	
	private int id;
	private int value;
	private DetailRecord next;
	
	public DetailRecord() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public DetailRecord getNext() {
		return next;
	}

	public void setNext(DetailRecord next) {
		this.next = next;
	}
	
	@Override
	public String toString() {
		return "{id="+this.id+", value="+value+",next:"+ (this.next!=null?this.next.toString():"") +"}";
	}

	
}
