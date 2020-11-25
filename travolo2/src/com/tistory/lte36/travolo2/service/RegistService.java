package com.tistory.lte36.travolo2.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.tistory.lte36.travolo2.dto.Regist;

public interface RegistService {
	public int deleteRegist(String id);
	public int addRegist(Regist regist);
	public ModelMap login(String id, String pwd);
	public String checkId(String id);
	
	public List<ModelMap> selectRandomLabel();
}
