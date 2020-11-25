package com.tistory.lte36.travolo2.dao;

import static com.tistory.lte36.travolo2.dao.EffectSqls.*;
import static com.tistory.lte36.travolo2.dao.SignUpSqls.PLUS_VOTE_COUNT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tistory.lte36.travolo2.dto.Crawling_tour;
import com.tistory.lte36.travolo2.dto.Schedule;

@Repository
public class EffectDao {
	private NamedParameterJdbcTemplate jdbc;	//이름을 이용하여 결과 값을 가져오는 객체
	private RowMapper<Crawling_tour> crawRowMapper = BeanPropertyRowMapper.newInstance(Crawling_tour.class);	//select 한 건 한 건의 결과를 DTO에 저장하는 객체, BeanPropertyRowMapper를 통해 DB column의 값을 자동으로 dto에 넣어주게 된다.
	private SimpleJdbcInsert scheduleInsertAction;
	public EffectDao(BasicDataSource dataSource) {	//DBConfig class에서 등록한 DB 설정이 BasicDataSource에 있기 때문에 객체를 생성하기만 해도 된다.
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.scheduleInsertAction = new SimpleJdbcInsert(dataSource).withTableName("schedule");
	}
	
	// 주소 검색 기능
	public List<ModelMap> Search() {
	return jdbc.query(TourList, Collections.emptyMap(), new RowMapper<ModelMap> () {
	       public ModelMap mapRow (ResultSet resultSet, int i) throws SQLException {
	    	   ModelMap model = new ModelMap();
	    	   model.addAttribute("name",resultSet.getString(1));
	           return model;
	        }
	});
	}
	
	// 일정 리스트 표시 기능
	public List<ModelMap> ScheduleList(String uid){
		return jdbc.query(printScheduleList, new MapSqlParameterSource().addValue("uid", uid), new RowMapper<ModelMap> () {
		       public ModelMap mapRow (ResultSet resultSet, int i) throws SQLException {
		    	   ModelMap model = new ModelMap();
		    	   model.addAttribute("start",resultSet.getString(1));
		    	   model.addAttribute("end", resultSet.getString(2));
		    	   model.addAttribute("schedule_name",resultSet.getString(3));
		    	   model.addAttribute("group_no", resultSet.getString(4));
		    	   model.addAttribute("count", resultSet.getInt(5));
		           return model;
		        }
		});
	}
	
	// 만든 일정 출력 기능
	String date2 = "";
	int flagcnt2=-1;
	public List<ModelMap> makeSchedule(String uid, String group_no){	//group_no 필요
		List<ModelMap> result = new ArrayList<ModelMap>();
		try {
		result = jdbc.query(makeSchedule, new MapSqlParameterSource().addValue("group_no", group_no).addValue("uid", uid), new RowMapper<ModelMap> () {
		       public ModelMap mapRow (ResultSet resultSet, int i) throws SQLException {
		    	   ModelMap model = new ModelMap();
		    	   model.addAttribute("name",resultSet.getString(1));
		    	   model.addAttribute("img",resultSet.getString(2));
		    	   model.addAttribute("info",resultSet.getString(3));
		    	   model.addAttribute("tid", resultSet.getString(4));
		    	   model.addAttribute("date", resultSet.getString(5));
		    	   model.addAttribute("time", resultSet.getString(6));
		    	   model.addAttribute("address", resultSet.getString(7));
		    	   model.addAttribute("end",resultSet.getString(8));
		    	   model.addAttribute("start", resultSet.getString(9));
		    	   model.addAttribute("gps_lat", resultSet.getString(10));
		    	   model.addAttribute("gps_long", resultSet.getString(11));
                   if(!date2.equals(resultSet.getString(5))) {
                   	date2=resultSet.getString(5);
                   	flagcnt2++;
                   }
                   model.addAttribute("flag", flagcnt2);
		           return model;
		        }
		});
		flagcnt2 = -1;
		date2="";
		}catch(Exception e) {
			ModelMap model = new ModelMap();
			model.addAttribute("error", e.getMessage());
			result.add(model);
		}
		return result;
	}
	
	// 선택 일정 표시 기능
	public List<ModelMap> printSchedule(String uid, String group_no){
		return jdbc.query(printSchedule, new MapSqlParameterSource().addValue("uid", uid).addValue("group_no", group_no), new RowMapper<ModelMap> () {
		       public ModelMap mapRow (ResultSet resultSet, int i) throws SQLException {
		    	   ModelMap model = new ModelMap();
		    	   model.addAttribute("name",resultSet.getString(1));
		    	   model.addAttribute("img",resultSet.getString(2));
		    	   model.addAttribute("tid", resultSet.getString(3));
		           return model;
		        }
		});
	}
	
	//여행지 검색 기능
	public List<ModelMap> searchTour(String key, String base_point) {
		return jdbc.query(searchTour, new MapSqlParameterSource().addValue("base_point", "%"+base_point+"%").addValue("key", "%"+key+"%"), new RowMapper<ModelMap> () {
		       public ModelMap mapRow (ResultSet resultSet, int i) throws SQLException {
		    	   ModelMap model = new ModelMap();
		    	   model.addAttribute("tid", resultSet.getString(1));
		    	   model.addAttribute("img", resultSet.getString(2));
		    	   model.addAttribute("name", resultSet.getString(3));
		    	   model.addAttribute("info", resultSet.getString(4));
		           return model;
		        }
		});
	}
	
	//일정 편집 기능
	public String editSchedule(ModelMap model){
		String result = "0";
		String uid = model.get("user_id").toString();
		String group_no = model.get("group_no").toString();
		@SuppressWarnings("unchecked")
		ArrayList<ModelMap> arrModel = (ArrayList<ModelMap>)model.get("item");
		ObjectMapper objMapper = new ObjectMapper();
		String compareDate="-1";
		int time=-1;
		try {
			Schedule schedule = new Schedule();
//			result+="+start"; //검산용 result
			//Base_point와 Schedule_name을 가져온다. (Select)
			Map<String, String> params = new HashMap<>();
			params.put("uid", uid);
			params.put("group_no", group_no);
			RowMapper<Schedule> scheRowMapper = BeanPropertyRowMapper.newInstance(Schedule.class);
			schedule = jdbc.queryForObject(FETCH_REMAINDER, params,scheRowMapper);
//			System.out.println(schedule.toString());
//			result+="+select"; //검산용 result
			//선택한 모든 일정 삭제. (Delete)
			schedule.setUid(uid);
			schedule.setGroup_no(group_no);
//			System.out.println(schedule.toString());
			SqlParameterSource updateParams = new BeanPropertySqlParameterSource(schedule);
			jdbc.update(DELETE_SCHEDULE, updateParams);
//			result+="+delete"; //검산용 result
			for(int i=0;i<arrModel.size();i++) {
				ModelMap iterModel = objMapper.convertValue(arrModel.get(i), ModelMap.class);
				int tid = Integer.parseInt(iterModel.get("tid").toString());
				String date = iterModel.get("date").toString();
				
				//선택한 일정 생성. (Insert)
				schedule.setTid(tid);
				schedule.setDate(date);
				if(!date.equals(compareDate)) {
					compareDate=date;
					time=0;
				}
				schedule.setTime(time++);
				System.out.println(schedule.toString());
				SqlParameterSource insertionParams = new BeanPropertySqlParameterSource(schedule);
				int sqlResult = scheduleInsertAction.execute(insertionParams);		//analysis_tour insertion
				if(sqlResult!=1)
					throw new SQLException("Schedule Table can not insert row");
			}
//			result+="+1";
			result="1";
		}catch(Exception e) {
			return result+=("+"+e.toString());
		}
		return result;
	}
	
	// 일정 이름 변경 기능
	public int changeScheduleName(String schedule_name, String group_no, String uid) {
		Schedule schedule = new Schedule();
		schedule.setSchedule_name(schedule_name);
		schedule.setGroup_no(group_no);
		schedule.setUid(uid);
		SqlParameterSource updateParams = new BeanPropertySqlParameterSource(schedule);
		return jdbc.update(CHANGE_SCHEDULE_NAME, updateParams);
	}
	
	// 여행지 상세 설명 페이지 표시 기능
	public Crawling_tour TourInfo(String tid) {
		try {
			Map<String, ?> params = Collections.singletonMap("tid",tid);
			return jdbc.queryForObject(DetailTourInfo, params, crawRowMapper);	//1건 SELECT를 할 때 queryForObject를 사용한다.
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return null;	//없을 경우 null을 return 한다. (주의!)
		}
	}
	
	// 여행지 평가 반영 기능
	public String evaluate(double grade, String tid) {
		double voteCount = -1, baseGrade = -1;
		Crawling_tour craw = new Crawling_tour();
		try {
			Map<String, String> params = new HashMap<>();
			params.put("tid", tid);
			craw = jdbc.queryForObject(evaluateGrade, params,crawRowMapper);	//평점 반영
			voteCount = craw.getVote_count();
			baseGrade = craw.getGrade();
			if(voteCount == -1 || baseGrade == -1)
				throw new NullPointerException("vote_count or base_grade is NULL!");
			else {
				// 평점 환산 방식: (평점 * vote_count + input_data) / (vote_count + 1)
				double result = (baseGrade * voteCount + grade) / (voteCount + 1);
				craw.setTid(Integer.parseInt(tid));
				craw.setGrade(result);
				SqlParameterSource updateParams = new BeanPropertySqlParameterSource(craw);
				int updateResult = jdbc.update(reflectGrade, updateParams);	
				jdbc.update(PLUS_VOTE_COUNT, updateParams);		//vote_count 증가
				
				if(updateResult == 0)
					return "0";
				else
					return "1";
			}
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	//최적 경로 표시 기능
	String date = "";
	int flagcnt=-1;
	public List<ModelMap> optimalRoad(String uid, String group_no) {
		List<ModelMap> result = new ArrayList<ModelMap>();
		try {
			// 위도 경도 산출
			result = jdbc.query(optimalRoad, new MapSqlParameterSource().addValue("uid", uid).addValue("group_no", group_no), new RowMapper<ModelMap> () {
                public ModelMap mapRow (ResultSet resultSet, int i) throws SQLException {
                	ModelMap model = new ModelMap();
                    model.addAttribute("gps_lat",resultSet.getString(1));
                    model.addAttribute("gps_long",resultSet.getString(2));
                    if(!date.equals(resultSet.getString(3))) {
                    	date=resultSet.getString(3);
                    	flagcnt++;
                    }
                    model.addAttribute("flag", flagcnt);
                    return model;
                    }
                });
			flagcnt=-1;
			date="";
		}catch(Exception e) {
			ModelMap model = new ModelMap();
			model.addAttribute("error", e.getMessage());
			result.add(model);
		}
		return result;
	}
}