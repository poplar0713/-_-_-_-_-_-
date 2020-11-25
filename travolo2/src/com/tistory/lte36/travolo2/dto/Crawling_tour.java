package com.tistory.lte36.travolo2.dto;

public class Crawling_tour {
	private int tid;
	private String address, baby, category, closed, creditCard;
	private String discount, fee, location, occupancy, parking;
	private String pet, postalCode, scale, tel, opentime;
	private String travelTime, description, type, label, uri;
	private String gps_lat, gps_long, depiction, tname;
	private int vote_count;
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBaby() {
		return baby;
	}
	public void setBaby(String baby) {
		this.baby = baby;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getClosed() {
		return closed;
	}
	public void setClosed(String closed) {
		this.closed = closed;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOccupancy() {
		return occupancy;
	}
	public void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}
	public String getParking() {
		return parking;
	}
	public void setParking(String parking) {
		this.parking = parking;
	}
	public String getPet() {
		return pet;
	}
	public void setPet(String pet) {
		this.pet = pet;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getOpentime() {
		return opentime;
	}
	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}
	public String getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
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
	public String getDepiction() {
		return depiction;
	}
	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public int getVote_count() {
		return vote_count;
	}
	public void setVote_count(int vote_count) {
		this.vote_count = vote_count;
	}
	@Override
	public String toString() {
		return "Crawling_tour [tid=" + tid + ", address=" + address + ", baby=" + baby + ", category=" + category
				+ ", closed=" + closed + ", creditCard=" + creditCard + ", discount=" + discount + ", fee=" + fee
				+ ", location=" + location + ", occupancy=" + occupancy + ", parking=" + parking + ", pet=" + pet
				+ ", postalCode=" + postalCode + ", scale=" + scale + ", tel=" + tel + ", opentime=" + opentime
				+ ", travelTime=" + travelTime + ", description=" + description + ", type=" + type + ", label=" + label
				+ ", uri=" + uri + ", gps_lat=" + gps_lat + ", gps_long=" + gps_long + ", depiction=" + depiction
				+ ", tname=" + tname + ", vote_count=" + vote_count + "]";
	}	
}
