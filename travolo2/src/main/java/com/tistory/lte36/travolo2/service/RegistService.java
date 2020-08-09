package com.tistory.lte36.travolo2.service;

import com.tistory.lte36.travolo2.dto.Regist;

public interface RegistService {
	public int deleteRegist(String id);
	public int addRegist(Regist regist);
	public String login(String id, String pwd);
}
