package com.tistory.lte36.travolo2.dto;

public class Point {
	int Field;
	String base_address, gps_lat, gps_long;
	public int getField() {
		return Field;
	}
	public void setField(int field) {
		Field = field;
	}
	public String getBase_address() {
		return base_address;
	}
	public void setBase_address(String base_address) {
		this.base_address = base_address;
	}
	public String getGps_lat() {
		return gps_lat;
	}
	public void setGps_lat(String gps_lat) {
		this.gps_lat = gps_lat;
	}
	public String getGps_long() {
		return gps_long;
	}
	public void setGps_long(String gps_long) {
		this.gps_long = gps_long;
	}
	@Override
	public String toString() {
		return "Point [Field=" + Field + ", base_address=" + base_address + ", gps_lat=" + gps_lat + ", gps_long="
				+ gps_long + "]";
	}
}