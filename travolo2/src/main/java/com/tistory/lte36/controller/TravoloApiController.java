package com.tistory.lte36.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tistory.lte36.travolo2.service.RegistService;

@Controller
public class TravoloApiController {
	@Autowired
	private RegistService registService;
	
	@GetMapping("/get/api")
	@ResponseBody
	public ModelMap getapi() {
		ModelMap model = new ModelMap();
		model.addAttribute("uid","kim");
		model.addAttribute("pwd","jong");
		model.addAttribute("uname", "min");
		model.addAttribute("gender", 1);
		return model;
	}
	
	@PostMapping("/post/api")
	@ResponseBody
	public ModelMap api(@RequestBody ModelMap model) {	//Request user_id, user_pw
		String uid = model.get("user_id").toString();
		String pwd = model.get("user_pw").toString();
		String result = registService.login(uid, pwd);
		ModelMap resultModel = new ModelMap();
		resultModel.addAttribute("success", result);
		return resultModel;
	}
	/*
	@DeleteMapping("/{id}")
	public Map<String, String> delete(@PathVariable(name="id") String id) {
		int deleteCount = travoloService.deleteTravolo(id);
		return Collections.singletonMap("success", deleteCount > 0 ? "true" : "false");
	}
	*/
}
