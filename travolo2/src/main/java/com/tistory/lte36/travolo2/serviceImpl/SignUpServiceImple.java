package com.tistory.lte36.travolo2.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.tistory.lte36.travolo2.dao.SignUpDao;
import com.tistory.lte36.travolo2.dto.Regist;
import com.tistory.lte36.travolo2.service.SignUpService;

class signUpFunc {
	public Regist inputRegist(ModelMap model) {
		Regist regist = new Regist();
		try {
			regist.setUid(model.get("user_id").toString());
			regist.setPwd(model.get("user_pwd").toString());
			regist.setUname(model.get("user_name").toString());
			regist.setBirthdate(model.get("user_birthdate").toString());
			regist.setGender(model.get("user_gender").toString());
			regist.setPass("0");
		} catch (Exception e) {
			regist = null;
		}
		return regist;
	}
}

@Service
public class SignUpServiceImple implements SignUpService {
	@Autowired
	SignUpDao signUpDao;

	// 회원가입
	@Override
	@Transactional(readOnly = false)
	public ModelMap signUp(ModelMap model) {
		// TODO Auto-generated method stub
		// json으로 들어온 정보를 regist로 등록
		signUpFunc sf = new signUpFunc();
		Regist regist = sf.inputRegist(model);
		ModelMap result = new ModelMap();
		// SQL의 결과값을 저장하는 변수
		String sqlResult = null;

		// 입력 json 정보가 잘못됐을 error 처리
		if (regist == null)
			throw new NullPointerException("regist is null");

		try {
			sqlResult = signUpDao.checkMember(regist.getUname(), regist.getBirthdate());
			// 아이디가 이미 있거나, DAO에서 에러가 난 경우
			if (sqlResult != "0")
				sqlResult = "0";
			// 아이디가 없을 경우(결과 값이 null일 경우) result에는 0이 저장됨.
			else
				sqlResult = Integer.toString(signUpDao.addRegist(regist));
		} catch (Exception e) {
			// service 에서 예외가 일어날 시
			result.addAttribute("error", "-2");
			result.addAttribute("desc", e.getMessage());
		} finally {
			result.addAttribute("success", sqlResult);
		}
		return result;
	}

	// 회원탈퇴
	@Override
	@Transactional(readOnly = false)
	public ModelMap withDrawal(ModelMap model) {
		// TODO Auto-generated method stub
		ModelMap result = new ModelMap();
		String uid = null;
		try {
			uid = model.get("user_id").toString();
			if (uid == null)
				throw new NullPointerException("uid is null");
		} catch (Exception e) {
			// service 에서 예외가 일어날 시
			result.addAttribute("error", "-2");
			result.addAttribute("desc", e.getMessage());
		} finally {
			result.addAttribute("success", signUpDao.withDrawal(uid));
		}
		return result;
	}

	// 선호도 삽입
	@Override
	@Transactional(readOnly = false)
	public ModelMap InsertPreference(ModelMap model) {
		// TODO Auto-generated method stub
		ModelMap result = new ModelMap();
		int sqlResult = 0;
		try {
			sqlResult = signUpDao.insertPreferenceImpl(model);
			if (sqlResult == -1) {
				result.addAttribute("error", "-1");
				sqlResult = 0;
			}
		} catch (Exception e) {
			// service 에서 예외가 일어날 시
			result.addAttribute("error", "-2");
			sqlResult = 0;
		} finally {
			result.addAttribute("success", Integer.toString(sqlResult));
		}
		return result;
	}

	// 사용자 정보 찾기
	@Override
	@Transactional(readOnly = false)
	public ModelMap findInfo(ModelMap model) {
		// TODO Auto-generated method stub
		int flag = Integer.valueOf(model.get("flag").toString());
		ModelMap result = new ModelMap();
		String sqlResult = null;
		try {
			switch (flag) {
			case 0:
				// data1 == uname, data2 == birthdate
				sqlResult = signUpDao.checkMember(model.get("data1").toString(), model.get("data2").toString());
				break;
			case 1:
				// data2 == uid
				sqlResult = signUpDao.findPwd(model.get("data2").toString());
				break;
			case 2:
				sqlResult = signUpDao.exchangePwd(model.get("user_id").toString(), model.get("user_pw").toString());
				break;
			}
		} catch (Exception e) {
			// service 에서 예외가 일어날 시
			sqlResult = "-2";
		}

		String success = "1";
		switch (sqlResult) {
		case "0":
		case "-1":
		case "-2":
			success = "0";
		}
		result.addAttribute("success", success);
		result.addAttribute("result", sqlResult);
		return result;
	}
}
