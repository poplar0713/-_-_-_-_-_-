package com.tistory.lte36.travolo2.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tistory.lte36.travolo2.dto.Analysis_tour;
import com.tistory.lte36.travolo2.dto.Crawling_tour;
import com.tistory.lte36.travolo2.dto.Regist;

import static com.tistory.lte36.travolo2.dao.EffectSqls.reflectGrade;
import static com.tistory.lte36.travolo2.dao.SignUpSqls.*;
@Repository
public class SignUpDao {
	private NamedParameterJdbcTemplate jdbc;	//이름을 이용하여 결과 값을 가져오는 객체
	private RowMapper<Regist> rowMapper = BeanPropertyRowMapper.newInstance(Regist.class);	//select 한 건 한 건의 결과를 DTO에 저장하는 객체, BeanPropertyRowMapper를 통해 DB column의 값을 자동으로 dto에 넣어주게 된다.
	private SimpleJdbcInsert insertAction; //INSERT INTO문 사용하기 위한 것.
	private SimpleJdbcInsert analInsertAction;
	
	public SignUpDao(BasicDataSource dataSource) {	//DBConfig class에서 등록한 DB 설정이 BasicDataSource에 있기 때문에 객체를 생성하기만 해도 된다.
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("client"); //TABLE 이름이 test2인 TABLE에 데이터를 INSERT
		this.analInsertAction = new SimpleJdbcInsert(dataSource).withTableName("analysis_tour");
	}
	//가입된 사용자인지 확인. 있으면 id, 없으면 0, 에러는 -1 return
	public String checkMember(String uname, String birthdate) {
		String result="-1";
		try {
			Map<String, String> params = new HashMap<>();
			params.put("uname", uname);
			params.put("birthdate", birthdate);
			
			// 아이디가 이미 있고, 에러가 나지 않을 경우 result에 uid 저장.
			// result == [uid]
			result = jdbc.queryForObject(MEMBER_CHECK, params, rowMapper).getUid();
		}catch (Exception e) {
			// TODO: handle exception
			if(e instanceof IncorrectResultSizeDataAccessException) {
				// 아이디가 없을 경우(결과 값이 null일 경우)
				// result == "0"
				result = "0";
			}
			else {
				// 아이디가 이미 있을 경우 (결과 값이 null이 아님), 에러가 일어남.
				// result == "-1"
				System.out.println(e.toString());
			}
		}
		return result;
	}
	
	//회원가입
	public int addRegist(Regist regist) {
		int result = 0;
		try {
			SqlParameterSource params = new BeanPropertySqlParameterSource(regist);
			result = insertAction.execute(params);	//SimpleJdbcInsert 내장 메소드인 execute를 통해 값을 알아서 저장하게 한다.
			if(result == 0)
				throw new SQLException("insert failed");
		}catch(Exception e) {
			return -1;
		}
		return result;
	}
	
	//회원탈퇴
	public String withDrawal(String uid) {
		int result = 0;
		try {
			// Collections에 singletonMap을 사용하여 속성 명, sql안에 들어갈 숫자를 입력해준다.
			Map<String, ?> params = Collections.singletonMap("uid", uid);
			result = jdbc.update(DELETE_USER, params);
			// BeanPropertySqlParameterSource를 통해 dto를 Map형태로 반환된 것과 마찬가지로 delete 역시 Map 형태로 데이터를 넣은 후 update에 넣어준다.
			if(result !=1 && result !=0)
				throw new SQLException("delete user failed");
		}catch(Exception e) {
			result = -1;
		}
		return Integer.toString(result);
	}
	
	//사용자 선호도 삽입
	public int insertPreferenceImpl(ModelMap model) {
		int result = -1;
		String uid = model.get("user_id").toString();
		@SuppressWarnings("unchecked")
		ArrayList<ModelMap> arrModel = (ArrayList<ModelMap>)model.get("item");
		ObjectMapper objMapper = new ObjectMapper();
		try {
			for(int i=0;i<arrModel.size(); i++) {
				ModelMap m = objMapper.convertValue(arrModel.get(i), ModelMap.class);
				int tid = Integer.parseInt(m.get("tid").toString());
				Crawling_tour craw = new Crawling_tour();
				craw.setTid(tid);
				SqlParameterSource updateParams = new BeanPropertySqlParameterSource(craw);
				jdbc.update(PLUS_VOTE_COUNT, updateParams);		//vote_count 증가
				
				Regist regist = new Regist();
				regist.setUid(uid);
				updateParams = new BeanPropertySqlParameterSource(regist);
				jdbc.update(CHANGE_PASS, updateParams);		//pass 1 변경
				
				Analysis_tour anal = new Analysis_tour();
				anal.setTid(tid);
				anal.setUid(uid);
				anal.setGrade(5.0);
				SqlParameterSource insertionParams = new BeanPropertySqlParameterSource(anal);
				analInsertAction.execute(insertionParams);		//analysis_tour insertion
				if(!plusGrade(tid))
					throw new SQLException("Crawling_tour Table Grade is not correct");
			}
			result = 1;
		}catch(Exception e) {
			System.out.println(e.toString());
			result = 0;
		}
		return result;
	}
	// 사용자 선호도 삽입 - Crawling_tour Table Grade 추가
	public boolean plusGrade(int tid) {
		try {
			Crawling_tour craw = new Crawling_tour();
			Map<String, String> params = new HashMap<>();
			params.put("tid", Integer.toString(tid));
			RowMapper<Crawling_tour> crawRowMapper = BeanPropertyRowMapper.newInstance(Crawling_tour.class);
			craw = jdbc.queryForObject(FETCH_GRADE, params,crawRowMapper);
			double grade = craw.getGrade();
			grade+=5.0;
			params.put("grade", Double.toString(grade));
			if(jdbc.update(reflectGrade, params)>0)
				return true;
			else
				throw new SQLException("Crawling_tour Table Grade is not correct");
		}catch(Exception e) {
			return false;
		}
	}
	
	//비밀번호 찾기. 성공시 pwd, 실패시 에러 메시지 return
	public String findPwd(String uid) {
		String pwd = "0";
		try {
			Map<String, String> params = new HashMap<>();
			params.put("uid", uid);
			pwd = jdbc.queryForObject(FIND_PWD, params, rowMapper).getPwd();
		}catch(Exception e) {
			pwd="-1";
		}
		return pwd;
	}
	// 비밀번호 변경. 성공시 1, 실패시 0 return
	public String exchangePwd(String uid, String pwd) {
		int result = 0;
		try {
			Map<String, String> params = new HashMap<>();
			params.put("uid", uid);
			params.put("pwd", pwd);
			result = jdbc.update(UPDATE_PWD, params);
		}catch(Exception e) {
			result = -1;
		}
		return Integer.toString(result);
	}
	
//	public boolean test() {
//		Regist regist = new Regist();
//		int i=0;
//		boolean result =false;
//		try {
//			for(i=1010;i<1160;i++) {
//				regist.setUid(String.valueOf(i));
//				regist.setPwd("1234");
//				regist.setUname("dummy"+i);
//				regist.setBirthdate("20200921");
//				regist.setGender("0");
//				regist.setPass("0");
//				regist.setCar("0");
//				SqlParameterSource insertionParams = new BeanPropertySqlParameterSource(regist);
//				insertAction.execute(insertionParams);		//analysis_tour insertion
//			}
//			result=true;
//		}catch(Exception e) {
//			result = false;
//		}
//		return result;
//	}
}
