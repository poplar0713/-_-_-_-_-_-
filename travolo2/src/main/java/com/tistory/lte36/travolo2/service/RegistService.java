package com.tistory.lte36.travolo2.service;

import java.util.List;

import org.springframework.ui.ModelMap;

public interface RegistService {
	public ModelMap login(ModelMap model);
	public ModelMap checkId(ModelMap model);
	
	public List<ModelMap> selectRandomLabel(List<ModelMap> model);
}
