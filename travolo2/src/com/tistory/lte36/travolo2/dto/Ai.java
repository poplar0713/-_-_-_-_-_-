package com.tistory.lte36.travolo2.dto;

public class Ai {
	private int lid;
	private double weight;
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "Ai [lid=" + lid + ", weight=" + weight + "]";
	}
}
