package com.tistory.lte36.travolo2.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.tistory.lte36.travolo2.dao.SignUpDao;
import com.tistory.lte36.travolo2.dto.Regist;
import com.tistory.lte36.travolo2.service.SignUpService;

@Service
public class SignUpServiceImple implements SignUpService{
	@Autowired
	SignUpDao signUpDao;

	@Override
	@Transactional(readOnly=false)
	public String signUp(Regist regist) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			if(!signUpDao.checkMember(regist.getUname(), regist.getBirthdate()))
				return result;
			
			if(signUpDao.addRegist(regist)>0)
				result = "1";
		}catch(Exception e) {
			System.out.println(e.toString());
			result = "-1";
		}
		return result;
	}

	@Override
	@Transactional(readOnly=false)
	public String InsertPreference(ModelMap model) {
		// TODO Auto-generated method stub
		String result="0";
		try {
			if(signUpDao.insertPreferenceImpl(model)) {
				result = "1";
			}
		}catch(Exception e) {
			result="0";
		}
		return result;
	}

	@Override
	@Transactional(readOnly=false)
	public String test() {
		// TODO Auto-generated method stub
		String result="0";
		try {
			if(signUpDao.test()) {
				result = "1";
			}
		}catch(Exception e) {
			result="0";
		}
		return result;
	}

}
