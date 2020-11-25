package com.tistory.lte36.travolo2.serviceImpl;

import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.tistory.lte36.travolo2.dao.RegistDao;
import com.tistory.lte36.travolo2.dto.Regist;
import com.tistory.lte36.travolo2.service.RegistService;

class RegistFuncs{
	public String[] twoAreaCheck(List<ModelMap> model) {
		String areaCode = model.get(0).get("area").toString();
		String[] areaSqls = new String[2];
		areaSqls[0] = areaCode;
		//충청북도, 충북처럼 여행지 address의 지역이 2종류로 DB에 저장된 경우 실행
		switch (areaCode) {
		case "충청북도":
			areaSqls[1] = "충북";
			break;
		case "충청남도":
			areaSqls[1] = "충남";
			break;
		case "경상북도":
			areaSqls[1] = "경북";
			break;
		case "경상남도":
			areaSqls[1] = "경남";
			break;
		case "전라북도":
			areaSqls[1] = "전북";
			break;
		case "전라남도":
			areaSqls[1] = "전남";
			break;
		default:
			areaSqls[1] = null;
			break;
		}
		return areaSqls;
	}
}
@Service
public class RegistServiceImple implements RegistService {
	@Autowired
	RegistDao registDao;
	
	// 로그인
	@Override
	@Transactional(readOnly=false)
	public ModelMap login(ModelMap model) {
		// TODO Auto-generated method stub
		ModelMap result = new ModelMap();
		String id = model.get("user_id").toString();
		String pwd = model.get("user_pw").toString();
		String succ=null, pref=null;
		try {
			// 해당 id에 대한 정보를 가져온다.
			Regist regist = registDao.checkId(id);
			if(regist.getPwd().equals(pwd)) {
				// 비밀번호가 일치하는 경우
				succ = "1";
				pref = regist.getPass();
			}
			else {
				// 비밀번호가 일치하지 않는 경우 예외처리.
				throw new InputMismatchException("pwd is not equal");
			}
		}catch(Exception e) {
			succ = "0";
			pref = "0";
			// service 에서 예외처리가 일어날 시
			result.addAttribute("error", "-2");
			result.addAttribute("desc", e.getMessage());
		}finally {
			// 로그인 결과 전송
			result.addAttribute("success", succ);
			// 선호도 조사 여부 전송
			result.addAttribute("preference", pref);
		}
		return result;
	}
	
	// 아이디 중복 확인
	@Override
	@Transactional(readOnly=false)
	public ModelMap checkId(ModelMap model) {
		// TODO Auto-generated method stub
		ModelMap result = new ModelMap();
		String id = model.get("user_id").toString();
		String succ = null;
		try {
			if(registDao.checkId(id)==null) {
				// 동일한 id가 없을 때
				succ="1";
			}else {
				// 동일한 id가 있을 때
				succ="0";
			}
		}catch(Exception e) {
			// service 에서 예외가 일어날 시
			result.addAttribute("error", "-2");
			result.addAttribute("desc", e.getMessage());
		}finally {
			result.addAttribute("success", succ);
		}
		return result;
	}
	
	// 랜덤 지역 여행지 전송(선호도 조사)
	@Override
	@Transactional(readOnly=false)
	public List<ModelMap> selectRandomLabel(List<ModelMap> model) {
		// TODO Auto-generated method stub
		// 여행지 지역 종류 확인
		RegistFuncs registfunc = new RegistFuncs();
		String[] areaSqls = registfunc.twoAreaCheck(model);
		
		List<ModelMap> result=null;
		try {
			if(areaSqls[1]==null) {
				// 여행지 지역이 1종류일 때 실행
				result = registDao.selectOneAreaLabel(areaSqls[0]);
			}
			else {
				// 여행지 지역이 2종류일 때 실행
				result = registDao.selectTwoAreaLabel(areaSqls[0], areaSqls[1]);
			}
		}catch(Exception e) {
			// service 에서 예외가 일어날 시
			ModelMap errModel = new ModelMap();
			errModel.addAttribute("error", "-2");
			errModel.addAttribute("desc",e.getMessage());
			result.add(errModel);
		}
		return result;
	}
}
