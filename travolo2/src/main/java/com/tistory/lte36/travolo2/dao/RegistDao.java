package com.tistory.lte36.travolo2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.tistory.lte36.travolo2.dto.Regist;
import static com.tistory.lte36.travolo2.dao.RegistSqls.*;

@Repository
public class RegistDao {
	private NamedParameterJdbcTemplate jdbc;	//이름을 이용하여 결과 값을 가져오는 객체
	private RowMapper<Regist> rowMapper = BeanPropertyRowMapper.newInstance(Regist.class);	//select 한 건 한 건의 결과를 DTO에 저장하는 객체, BeanPropertyRowMapper를 통해 DB column의 값을 자동으로 dto에 넣어주게 된다.

	public RegistDao(BasicDataSource dataSource) {	//DBConfig class에서 등록한 DB 설정이 BasicDataSource에 있기 때문에 객체를 생성하기만 해도 된다.
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	// 아이디 중복 확인
	public Regist checkId(String id) {
		Regist result = new Regist();
		try {
			Map<String, ?> params = Collections.singletonMap("id",id);
			result = jdbc.queryForObject(CHECK_ID, params, rowMapper);	//1건 SELECT를 할 때 queryForObject를 사용한다.
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			result = null;												//없을 경우 null을 return 한다. (주의!)
		}
		return result;
	}
	
	// 여행지 지역이 1종류일 때 실행
	public List<ModelMap> selectOneAreaLabel(String area){
		List<ModelMap> result=null;
		try {
			result = jdbc.query(SELECT_ONE_AREA_LABEL, new MapSqlParameterSource().addValue("area", area+"%"), new RowMapper<ModelMap> () {
		       public ModelMap mapRow (ResultSet resultSet, int i) throws SQLException {
		    	   ModelMap model = new ModelMap();
		    	   model.addAttribute("name",resultSet.getString(1));
		    	   model.addAttribute("img",resultSet.getString(2));
		    	   model.addAttribute("tid", resultSet.getString(3));
		           return model;
		        }
		});	//query 메소드는 rowMapper에 의해 생성된 dto를 반환형에 맞게 넣어준다. 결과가 여러 건일 경우 여러 번 반복하여 넣어준다.
		}catch(Exception e) {
			ModelMap errModel = new ModelMap();
			errModel.addAttribute("error", "-1");
			errModel.addAttribute("desc", e.getMessage());
			result.add(errModel);
		}
		return result;
	}
	
	// 여행지 지역이 2종류일 때 실행
	public List<ModelMap> selectTwoAreaLabel(String area1, String area2){
		List<ModelMap> result=null;
		try {
			result = jdbc.query(SELECT_TWO_AREA_LABEL, new MapSqlParameterSource().addValue("area1", area1+"%").addValue("area2", area2+"%"), new RowMapper<ModelMap> () {
		       public ModelMap mapRow (ResultSet resultSet, int i) throws SQLException {
		    	   ModelMap model = new ModelMap();
		    	   model.addAttribute("name",resultSet.getString(1));
		    	   model.addAttribute("img",resultSet.getString(2));
		    	   model.addAttribute("tid", resultSet.getString(3));
		           return model;
		        }
		});	//query 메소드는 rowMapper에 의해 생성된 dto를 반환형에 맞게 넣어준다. 결과가 여러 건일 경우 여러 번 반복하여 넣어준다.
		}catch(Exception e) {
			ModelMap errModel = new ModelMap();
			errModel.addAttribute("error", "-1");
			errModel.addAttribute("desc", e.getMessage());
			result.add(errModel);
		}
		return result;
	}
}