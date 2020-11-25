package com.tistory.lte36.travolo2.dao;

public class RegistSqls {
	//:id 는 parameter로 가져온 값(id)을 binding할 장소이다.
	//그러므로 dto변수와 table 스키마 속성은 동일해야한다.
	public static final String SELECT_ALL="SELECT * FROM client";
	
	public static final String CHECK_ID="SELECT * FROM client WHERE uid = :id";
	public static final String DELETE_ONE="DELETE FROM client WHERE uid = :id";
	
	public static final String SELECT_ONE_AREA_LABEL="SELECT label, depiction, tid FROM crawling_tour WHERE label IS NOT NULL AND depiction IS NOT NULL AND (category LIKE '%카페%' OR category LIKE '%자연%' OR category LIKE '%인문%' OR category LIKE '%레포츠%' OR category LIKE '%쇼핑%') AND address LIKE :area ORDER BY RAND() LIMIT 300";
	public static final String SELECT_TWO_AREA_LABEL="SELECT label, depiction, tid FROM crawling_tour WHERE label IS NOT NULL AND depiction IS NOT NULL AND (category LIKE '%카페%' OR category LIKE '%자연%' OR category LIKE '%인문%' OR category LIKE '%레포츠%' OR category LIKE '%쇼핑%') AND address IN (SELECT address from crawling_tour WHERE address LIKE :area1 OR address LIKE :area2) ORDER BY RAND() LIMIT 300";
}
