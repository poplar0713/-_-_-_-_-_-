package com.tistory.lte36.travolo2.dto;

public class Analysis_tour {
	private int tour_no, tid;
	private String uid;
	private double grade;
	public int getTour_no() {
		return tour_no;
	}
	public void setTour_no(int tour_no) {
		this.tour_no = tour_no;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Analysis_tour [tour_no=" + tour_no + ", tid=" + tid + ", uid=" + uid + ", grade=" + grade + "]";
	}
}
