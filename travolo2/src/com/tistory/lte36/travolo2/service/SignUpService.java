package com.tistory.lte36.travolo2.service;

import org.springframework.ui.ModelMap;

import com.tistory.lte36.travolo2.dto.Regist;

public interface SignUpService {
	public String signUp(Regist regist);
	public String InsertPreference(ModelMap model);
	public String test();
}
