package com.tistory.lte36.travolo2.dto;

public class Regist {
	private String uid, pwd, uname, gender;
	private String birthdate, pass, car;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	@Override
	public String toString() {
		return "Regist [uid=" + uid + ", pwd=" + pwd + ", uname=" + uname + ", gender=" + gender + ", birthdate="
				+ birthdate + ", pass=" + pass + ", car=" + car + "]";
	}
}
