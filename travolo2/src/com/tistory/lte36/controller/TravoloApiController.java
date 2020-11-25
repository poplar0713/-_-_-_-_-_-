package com.tistory.lte36.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tistory.lte36.travolo2.dto.Regist;
import com.tistory.lte36.travolo2.service.RegistService;
import com.tistory.lte36.travolo2.service.SignUpService;

@Controller
public class TravoloApiController {
	@Autowired
	private RegistService registService;
	
	@Autowired
	private SignUpService signUpService;
	
	@GetMapping("/get/api")	//get 테스트
	@ResponseBody			//JSON파일로 반환
	public ModelMap getapi() {
		ModelMap model = new ModelMap();
		model.addAttribute("uid","kims");
		model.addAttribute("pwd","jong");
		model.addAttribute("uname", "min");
		model.addAttribute("gender", 1);
		return model;
	}
	
	@PostMapping("/post/login")	//로그인
	@ResponseBody
	public ModelMap api(@RequestBody ModelMap model) {	//Request user_id, user_pw
		String uid = model.get("user_id").toString();
		String pwd = model.get("user_pw").toString();
		
//		String result = registService.login(uid, pwd);
//		ModelMap resultModel = new ModelMap();
//		resultModel.addAttribute("success", result);
		return registService.login(uid, pwd);
	}
	
	@PostMapping("/post/checkId")	//아이디 중복 확인
	@ResponseBody
	public ModelMap checkId(@RequestBody ModelMap model) {	//Request user_id, user_pw
		String uid = model.get("user_id").toString();
		String result = registService.checkId(uid);
		ModelMap resultModel = new ModelMap();
		resultModel.addAttribute("success", result);
		return resultModel;
	}
	
	@PostMapping("/post/randomLabel")	//랜덤 여행지 전송(선호도 조사)
	@ResponseBody
	public List<ModelMap> randomLabel() {
		List<ModelMap> result = registService.selectRandomLabel();
		
		return result;
	}
	
	@PostMapping("/post/signUp")	//회원가입
	@ResponseBody
	public ModelMap signUp(@RequestBody ModelMap model) {
		ModelMap resultModel = new ModelMap();
		Regist regist = new Regist();
		
		regist.setUid(model.get("user_id").toString());
		regist.setPwd(model.get("user_pwd").toString());
		regist.setUname(model.get("user_name").toString());
		regist.setBirthdate(model.get("user_birthdate").toString());
		regist.setGender(model.get("user_gender").toString());
		regist.setPass("0");
		
		String result = signUpService.signUp(regist);
		resultModel.addAttribute("success", result);
		return resultModel;
	}
	
	@PostMapping("/post/preference")	//사용자 선호도 수신
	@ResponseBody
	public ModelMap preference(@RequestBody ModelMap model) {
		ModelMap resultModel = new ModelMap();
		String result = signUpService.InsertPreference(model);
		return resultModel.addAttribute("success", result);
	}
	
	@PostMapping("/test")	//사용자 선호도 수신
	@ResponseBody
	public ModelMap createClient() {
		ModelMap resultModel = new ModelMap();
		String result = signUpService.test();
		return resultModel.addAttribute("success", result);
	}
	
	/*
	@DeleteMapping("/{id}")
	public Map<String, String> delete(@PathVariable(name="id") String id) {
		int deleteCount = travoloService.deleteTravolo(id);
		return Collections.singletonMap("success", deleteCount > 0 ? "true" : "false");
	}
	*/
}
