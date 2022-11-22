package com.example.Note.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Note.dto.UserRequest;
import com.example.Note.models.LoginModel;



@Controller
public class LoginController {
	
	@Autowired
	private LoginModel loginModel;
	
	@GetMapping(value="/")
	public String displayLogin() {
//		model.addAttribute("loginRequest", new UserRequest());
		return "/Login";
	}
	
	@PostMapping(value="/SignUp")
	public String signUp(@ModelAttribute UserRequest userRequest, Model model) {
		if(userRequest.getUsername() == null || userRequest.getPassword() == null) {
			model.addAttribute("loginRequest", new UserRequest());
			return "/EmptyError";
		}
		
		loginModel.signUp(userRequest.getUsername(), userRequest.getPassword());
		model.addAttribute("loginRequest", new UserRequest());
		return "/Login";
	}
	
	@GetMapping(value="/Login")
	public String login(@ModelAttribute UserRequest userRequest, Model model) {
		if(userRequest.getUsername() == null || userRequest.getPassword() == null) {
			model.addAttribute("loginRequest", new UserRequest());
			return "/EmptyError";
		}
		
		if(!loginModel.isLogin(userRequest.getUsername(), userRequest.getPassword()) == false) {
			return "/LoginError";
		}
		
		return "/Main";
	}
}