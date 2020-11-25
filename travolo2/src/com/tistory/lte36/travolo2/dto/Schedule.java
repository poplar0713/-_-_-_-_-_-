package com.tistory.lte36.travolo2.dto;

import java.sql.Date;
import java.sql.Time;

public class Schedule {
	private int schedule_no, tid;
	private String uid, schedule_name;
	private int group_no;
	private Date date;
	private double distance;
	private Time time;
	public int getSchedule_no() {
		return schedule_no;
	}
	public void setSchedule_no(int schedule_no) {
		this.schedule_no = schedule_no;
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
	public String getSchedule_name() {
		return schedule_name;
	}
	public void setSchedule_name(String schedule_name) {
		this.schedule_name = schedule_name;
	}
	public int getGroup_no() {
		return group_no;
	}
	public void setGroup_no(int group_no) {
		this.group_no = group_no;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Schedule [schedule_no=" + schedule_no + ", tid=" + tid + ", uid=" + uid + ", schedule_name="
				+ schedule_name + ", group_no=" + group_no + ", date=" + date + ", distance=" + distance + ", time="
				+ time + "]";
	}
}
