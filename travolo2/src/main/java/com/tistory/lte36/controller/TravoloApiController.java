package com.tistory.lte36.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tistory.lte36.travolo2.service.EffectService;
import com.tistory.lte36.travolo2.service.RegistService;
import com.tistory.lte36.travolo2.service.SignUpService;

@Controller
public class TravoloApiController {
	@Autowired
	private RegistService registService;

	@Autowired
	private SignUpService signUpService;

	@Autowired
	private EffectService effectService;

	@GetMapping("/get/api") // get 테스트
	@ResponseBody // JSON파일로 반환
	public ModelMap getapi() {
		ModelMap model = new ModelMap();
		model.addAttribute("uid", "kims");
		model.addAttribute("pwd", "jong");
		model.addAttribute("uname", "min");
		model.addAttribute("gender", 1);
		return model;
	}

	// 로그인
	@PostMapping("/post/login")
	@ResponseBody
	public ModelMap login(@RequestBody ModelMap model) {
		return registService.login(model);
	}

	// 아이디 중복 확인
	@PostMapping("/post/checkId")
	@ResponseBody
	public ModelMap checkId(@RequestBody ModelMap model) {
		return registService.checkId(model);
	}

	// 랜덤 지역 여행지 전송(선호도 조사)
	@PostMapping("/post/randomLabel")
	@ResponseBody
	public List<ModelMap> randomLabel(@RequestBody List<ModelMap> model) {
		return registService.selectRandomLabel(model);
	}

	// 회원가입
	@PostMapping("/post/signUp")
	@ResponseBody
	public ModelMap signUp(@RequestBody ModelMap model) {
		return signUpService.signUp(model);
	}
	
	// 회원탈퇴
	@PostMapping("/post/withDrawal")
	@ResponseBody
	public ModelMap withDrawal(@RequestBody ModelMap model) {
		return signUpService.withDrawal(model);
	}

	// 사용자 선호도 수신
	@PostMapping("/post/preference")
	@ResponseBody
	public ModelMap preference(@RequestBody ModelMap model) {
		return signUpService.InsertPreference(model);
	}
	
	//정보 찾기
	@PostMapping("/post/findInfo")
	@ResponseBody
	public ModelMap findInfo(@RequestBody ModelMap model) {
		return signUpService.findInfo(model);
	}

	// 검색 자동 완성
	@PostMapping("/post/search")
	@ResponseBody
	public List<ModelMap> search() {
		List<ModelMap> resultModel = effectService.Search();
		return resultModel;
	}
	
	// 일정 리스트 표시 기능
	@PostMapping("/post/scheduleList")
	@ResponseBody
	public List<ModelMap> printScheduleList(@RequestBody List<ModelMap> model) {
		return effectService.ScheduleList(model);
	}
	
	// 만든 일정 표시 기능 (uid, group_no)
	@PostMapping("/post/makeSchedule")
	@ResponseBody
	public List<ModelMap> makeSchedule(@RequestBody List<ModelMap> model) {
		return effectService.Schedule(model,1);
	}
	
	// 선택 일정 표시 기능 (uid, group_no)
	@PostMapping("/post/printSchedule")
	@ResponseBody
	public List<ModelMap> printSchedule(@RequestBody List<ModelMap> model){
		return effectService.Schedule(model,2);
	}

	//여행 일정 편집 기능
	@PostMapping("/post/editSchedule")
	@ResponseBody
	public ModelMap editSchedule(@RequestBody ModelMap model){
		return effectService.editSchedule(model);
	}
	
	//일정 이름 변경 기능
	@PostMapping("/post/changeScheduleName")
	@ResponseBody
	public ModelMap changeScheduleName(@RequestBody ModelMap model) {
		return effectService.changeScheduleName(model);
	}
	
	//여행지 상세 설명 페이지 표시 기능
	@PostMapping("/post/tourInfo")
	@ResponseBody
	public ModelMap printTourInfo(@RequestBody ModelMap model) {
		return effectService.TourInfo(model);
	}
	
	//여행지 평가 기능
	@PostMapping("/post/evaluateTour")
	@ResponseBody
	public List<ModelMap> evaluateGrade(@RequestBody List<ModelMap> model){
		return effectService.evaluateGrade(model);
	}
	
	//최적 경로 표시 기능
	@PostMapping("/post/optimalRoad")
	@ResponseBody
	public List<ModelMap> optimalRoad(@RequestBody List<ModelMap> model){
		return effectService.optimalRoad(model);
	}
	
	//여행지명 검색
	@PostMapping("/post/searchTour")
	@ResponseBody
	public List<ModelMap> searchTour(@RequestBody List<ModelMap> model){
		return effectService.searchTour(model);
	}
}
