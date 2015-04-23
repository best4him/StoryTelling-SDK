package com.andrei.storytelling.models;

public enum ViewPositionEnum {
	
	PARENT_LEFT(-1000),
	PARENT_RIGHT(-2000),
	PARENT_TOP(-3000),
	PARENT_BOTTOM(-4000);
	
	private int weight;
	private ViewPositionEnum(int weight) {
		this.weight = weight;
	}
	
	public int getWeight() {
		return weight;
	}
	
	
}
