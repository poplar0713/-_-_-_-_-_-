package com.tistory.lte36.travolo2.dto;

public class Schedule {
	private int schedule_no;
	private String uid;
	private int tid;
	private String base_point;
	private String date;
	private String group_no;
	private int time;
	private String schedule_name;

	public int getSchedule_no() {
		return schedule_no;
	}

	public void setSchedule_no(int schedule_no) {
		this.schedule_no = schedule_no;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getBase_point() {
		return base_point;
	}

	public void setBase_point(String base_point) {
		this.base_point = base_point;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGroup_no() {
		return group_no;
	}

	public void setGroup_no(String group_no) {
		this.group_no = group_no;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getSchedule_name() {
		return schedule_name;
	}

	public void setSchedule_name(String schedule_name) {
		this.schedule_name = schedule_name;
	}

	@Override
	public String toString() {
		return "Schedule [schedule_no=" + schedule_no + ", uid=" + uid + ", tid=" + tid + ", base_point=" + base_point
				+ ", date=" + date + ", group_no=" + group_no + ", time=" + time + ", schedule_name=" + schedule_name
				+ "]";
	}
}
