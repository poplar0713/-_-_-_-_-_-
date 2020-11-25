package com.tistory.lte36.travolo2.dao;

public class EffectSqls {
	public static final String TourList="SELECT base_address FROM point";

	public static final String makeSchedule="SELECT c.label, c.depiction, c.description, c.tid, s.date, s.time, s.base_point, MAX(ss.date), MIN(ss.date), c.gps_lat, c.gps_long FROM schedule ss INNER JOIN schedule s ON ss.group_no=s.group_no INNER JOIN crawling_tour c ON s.tid=c.tid WHERE s.uid=:uid AND s.group_no=:group_no GROUP BY c.label, c.depiction, c.description, c.tid, s.date, s.time, s.base_point, c.gps_lat, c.gps_long ORDER BY s.date, s.time;";
	public static final String printSchedule="SELECT c.label, c.depiction, c.tid FROM crawling_tour c, schedule s WHERE s.uid=:uid AND s.tid=c.tid AND s.group_no =:group_no";
	
	public static final String searchTour="SELECT tid, depiction, label, description FROM crawling_tour WHERE (address LIKE :base_point AND label LIKE :key)";
	
	public static final String printScheduleList="SELECT min(sf.date), max(sf.date), ss.schedule_name, sf.group_no, max(sf.date)-min(sf.date)+1 FROM schedule sf INNER JOIN schedule ss ON sf.group_no=ss.group_no WHERE sf.uid=:uid GROUP BY sf.group_no, ss.schedule_name";
	public static final String DetailTourInfo="SELECT label, address, tel, opentime, closed, fee, uri, gps_lat, gps_long, depiction, description FROM crawling_tour WHERE tid=:tid";
	
	public static final String evaluateGrade="SELECT vote_count, grade FROM crawling_tour WHERE tid=:tid";
	public static final String reflectGrade="UPDATE crawling_tour SET grade=:grade where tid=:tid";
	
	public static final String optimalRoad="SELECT c.gps_lat, c.gps_long, s.date FROM schedule s, crawling_tour c WHERE s.tid=c.tid AND s.group_no=:group_no AND s.uid=:uid";
	
	public static final String FETCH_REMAINDER="SELECT DISTINCT base_point, schedule_name FROM schedule WHERE uid=:uid AND group_no=:group_no";
	public static final String DELETE_SCHEDULE="DELETE FROM schedule WHERE uid=:uid AND group_no=:group_no";
	
	public static final String CHANGE_SCHEDULE_NAME="UPDATE schedule SET schedule_name=:schedule_name WHERE uid=:uid AND group_no=:group_no";
}