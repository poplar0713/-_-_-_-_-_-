package com.tistory.lte36.travolo2.service;

import org.springframework.ui.ModelMap;

public interface SignUpService {
	public ModelMap signUp(ModelMap model);
	public ModelMap InsertPreference(ModelMap model);
	public ModelMap withDrawal(ModelMap model);
	public ModelMap findInfo(ModelMap model);
}
