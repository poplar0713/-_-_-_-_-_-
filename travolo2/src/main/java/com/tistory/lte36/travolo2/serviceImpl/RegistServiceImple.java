package com.tistory.lte36.travolo2.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tistory.lte36.travolo2.dao.RegistDao;
import com.tistory.lte36.travolo2.dto.Regist;
import com.tistory.lte36.travolo2.service.RegistService;

@Service
public class RegistServiceImple implements RegistService {
	@Autowired
	RegistDao registDao;
	
	@Override
	@Transactional(readOnly=false)
	public int deleteRegist(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(readOnly=false)
	public int addRegist(Regist regist) {
		// TODO Auto-generated method stub
		int num=0;
		try {
			num = registDao.insert(regist);
		}catch(Exception e) {
			return 0;
		}
		return num;
	}

	@Override
	public String login(String id, String pwd) {
		// TODO Auto-generated method stub
		try {
			Regist regist = registDao.select_one(id);
			if(!regist.getPwd().equals(pwd))
				return "0"; 
		}catch(Exception e) {
		return "0";
		}
		return "1";
	}
	
}
