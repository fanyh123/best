package com.ran.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestCaptcha {
	
	@RequestMapping("/testc/test")
	public String test(HttpServletRequest request, HttpServletResponse response, Model model){
		
		// 注册图形验证码key
		model.addAttribute("RCaptchaKey", "R" + request.getSession().getId());
		return "index";
	}

}
