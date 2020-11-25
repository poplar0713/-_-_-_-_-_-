package com.tistory.lte36.travolo2.dao;

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

import com.tistory.lte36.travolo2.dto.Analysis_tour;
import com.tistory.lte36.travolo2.dto.Crawling_tour;
import com.tistory.lte36.travolo2.dto.Regist;
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
	
	public boolean checkMember(String uname, String birthdate) {
		boolean result = false;
		try {
			Map<String, String> params = new HashMap<>();
			params.put("uname", uname);
			params.put("birthdate", birthdate);
			jdbc.queryForObject(MEMBER_CHECK, params, rowMapper);
		}catch (Exception e) {
			// TODO: handle exception
			if(e instanceof IncorrectResultSizeDataAccessException) {
				result = true;
			}
			else {
				System.out.println(e.toString());
				result = false;
			}
		}
		return result;
	}
	
	public int addRegist(Regist regist) {
		int result = 0;
		try {
			SqlParameterSource params = new BeanPropertySqlParameterSource(regist);
			result = insertAction.execute(params);	//SimpleJdbcInsert 내장 메소드인 execute를 통해 값을 알아서 저장하게 한다.
		}catch(Exception e) {
			System.out.println(e.toString());
			return -1;
		}
		return result;
	}
	
	public boolean insertPreferenceImpl(ModelMap model) {
		boolean result = false;
		String uid = model.get("user_id").toString();
		for(String key:model.keySet()) {
			if(key.equals("user_id")) {
				result = true;
				continue;
			}
			System.out.println("\""+key+"\":\""+model.get(key)+"\"");
//			Map<String, String> params = new HashMap<>();
//			params.put("tlabel", key);
//			int tid = jdbc.queryForObject(SELECT_TID, params, Integer.class);	//tour id 추출
//			params.clear();
			int tid = Integer.parseInt(key);
			Crawling_tour craw = new Crawling_tour();
			craw.setTid(tid);
			SqlParameterSource updateParams = new BeanPropertySqlParameterSource(craw);
			jdbc.update(PLUS_VOTE_COUNT, updateParams);		//vote_count 증가
//			params.clear();
			
			Regist regist = new Regist();
			regist.setUid(uid);
			updateParams = new BeanPropertySqlParameterSource(regist);
			jdbc.update(CHANGE_PASS, updateParams);		//pass 1 변경
//			params.clear();
			
			Analysis_tour anal = new Analysis_tour();
			anal.setTid(tid);
			anal.setUid(uid);
			anal.setGrade(5.0);
			SqlParameterSource insertionParams = new BeanPropertySqlParameterSource(anal);
			analInsertAction.execute(insertionParams);		//analysis_tour insertion
		}
		return result;
	}
	
	public boolean test() {
		Regist regist = new Regist();
		int i=0;
		boolean result =false;
		try {
			for(i=100;i<300;i++) {
				regist.setUid(String.valueOf(i));
				regist.setPwd("1234");
				regist.setUname("dummy"+i);
				regist.setBirthdate("20200911");
				regist.setGender("0");
				regist.setPass("0");
				regist.setCar("0");
				SqlParameterSource insertionParams = new BeanPropertySqlParameterSource(regist);
				insertAction.execute(insertionParams);		//analysis_tour insertion
			}
			result=true;
		}catch(Exception e) {
			result = false;
		}
		return result;
	}
}
