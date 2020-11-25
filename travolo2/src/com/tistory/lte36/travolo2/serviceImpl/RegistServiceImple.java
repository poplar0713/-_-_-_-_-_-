package com.tistory.lte36.travolo2.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

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
	@Transactional(readOnly=false)
	public ModelMap login(String id, String pwd) {
		// TODO Auto-generated method stub
		ModelMap resultModel = new ModelMap();
		String resultStr = null;
		String pass = null;
		try {
			Regist regist = registDao.select_one(id);
			if(regist.getPwd().equals(pwd)) {
				resultStr = "1";
				pass = regist.getPass();
			}
			else {
				resultStr="0";
				pass = "-1";
			}
		}catch(Exception e) {
			resultModel.addAttribute("error", e.toString());
			return resultModel;
		}
		resultModel.addAttribute("success", resultStr);
		resultModel.addAttribute("preference", pass);
		return resultModel;
	}
		

	@Override
	@Transactional(readOnly=false)
	public String checkId(String id) {
		// TODO Auto-generated method stub
		try {
			if(registDao.select_one(id)==null)
				return "1";
		}catch(Exception e) {
			return e.toString();
		}
		return "0";
	}

	@Override
	@Transactional(readOnly=false)
	public List<ModelMap> selectRandomLabel() {
		// TODO Auto-generated method stub
		List<ModelMap> arr=null;
		try {
			arr = registDao.selectAllLabel();
		}catch(Exception e) {
			e.printStackTrace();
			return arr;
		}
		return arr;
	}
}
