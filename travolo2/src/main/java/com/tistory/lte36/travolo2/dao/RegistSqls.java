package com.tistory.lte36.travolo2.dao;

public class RegistSqls {
	//:id 는 parameter로 가져온 값(id)을 binding할 장소이다.
	//그러므로 dto변수와 table 스키마 속성은 동일해야한다.
	public static final String SELECT_ALL="SELECT * FROM client";
	public static final String UPDATE="UPDATE client set uid = :cid WHERE uid=:uid";
	
	public static final String SELECT_ONE="SELECT * FROM client WHERE uid = :id";
	public static final String DELETE_ONE="DELETE FROM client WHERE uid = :id";
}
