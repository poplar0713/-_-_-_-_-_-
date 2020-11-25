package com.tistory.lte36.travolo2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.tistory.lte36.travolo2.dto.Regist;
import static com.tistory.lte36.travolo2.dao.RegistSqls.*;

@Repository
public class RegistDao {
	private NamedParameterJdbcTemplate jdbc;	//이름을 이용하여 결과 값을 가져오는 객체
	private RowMapper<Regist> rowMapper = BeanPropertyRowMapper.newInstance(Regist.class);	//select 한 건 한 건의 결과를 DTO에 저장하는 객체, BeanPropertyRowMapper를 통해 DB column의 값을 자동으로 dto에 넣어주게 된다.
	private SimpleJdbcInsert insertAction; //INSERT INTO문 사용하기 위한 것.
	public RegistDao(BasicDataSource dataSource) {	//DBConfig class에서 등록한 DB 설정이 BasicDataSource에 있기 때문에 객체를 생성하기만 해도 된다.
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("client"); //TABLE 이름이 test2인 TABLE에 데이터를 INSERT
	}
	
	public List<Regist> selectAll(){
		return jdbc.query(SELECT_ALL, Collections.emptyMap(), rowMapper);	//query 메소드는 rowMapper에 의해 생성된 dto를 반환형에 맞게 넣어준다. 결과가 여러 건일 경우 여러 번 반복하여 넣어준다.
	}
	
	public List<ModelMap> selectAllLabel(){
		return jdbc.query(SELECT_ALL_LABEL, Collections.emptyMap(), new RowMapper<ModelMap> () {
		       public ModelMap mapRow (ResultSet resultSet, int i) throws SQLException {
		    	   ModelMap model = new ModelMap();
		    	   model.addAttribute("name",resultSet.getString(1));
		    	   model.addAttribute("img",resultSet.getString(2));
		    	   model.addAttribute("tid", resultSet.getString(3));
		           return model;
		        }
		      });	//query 메소드는 rowMapper에 의해 생성된 dto를 반환형에 맞게 넣어준다. 결과가 여러 건일 경우 여러 번 반복하여 넣어준다.
	}
	
	public int insert(Regist regist) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(regist);	//Role 객체를 받아서 BeanPropertySqlParameterSource를 통해 Map 형태로 바꾸어 준다. 이 때, DB의 속성이 role_id이고, dto 변수가 roleId일 경우 dto를 알아서 DB 속성에 맞게 변환후 입력해준다.
		return insertAction.execute(params);	//SimpleJdbcInsert 내장 메소드인 execute를 통해 값을 알아서 저장하게 한다.
	}
	
	public int update(Regist regist) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(regist);
		return jdbc.update(UPDATE, params);
	}
	
	public int delete_one(String id) {
		Map<String, ?> params = Collections.singletonMap("id", id); //Collections에 singletonMap을 사용하여 속성 명, sql안에 들어갈 숫자를 입력해준다.
		return jdbc.update(DELETE_ONE, params);		//BeanPropertySqlParameterSource를 통해 dto를 Map형태로 반환된 것과 마찬가지로 delete 역시 Map 형태로 데이터를 넣은 후 update에 넣어준다.
	}
	
	public Regist select_one(String id) {
		try {
			Map<String, ?> params = Collections.singletonMap("id",id);
			return jdbc.queryForObject(SELECT_ONE, params, rowMapper);	//1건 SELECT를 할 때 queryForObject를 사용한다.
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return null;												//없을 경우 null을 return 한다. (주의!)
		}
	}
}