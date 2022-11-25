package com.example.Note.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Note.dto.NoteRequest;
import com.example.Note.dto.UserRequest;
import com.example.Note.entity.User;
import com.example.Note.models.LoginModel;
import com.example.Note.models.MainModel;



@Controller
public class LoginController {
	
	@Autowired
	private LoginModel loginModel;
	@Autowired
	private MainModel mainModel;
	@Autowired
	HttpSession session;
	
	@GetMapping(value="/")
	public String displayLogin(Model model) {
		model.addAttribute("loginRequest", new UserRequest());
		return "/Login";
	}
	
	@GetMapping(value="/moveSignUp")
	public String displaySignUp(Model model) {
		model.addAttribute("signUpRequest", new UserRequest());
		return "/SignUp";
	}
	
	@PostMapping(value="/SignUp")
	public String signUp(HttpServletRequest request, HttpServletResponse response, @ModelAttribute UserRequest userRequest, Model model) {
		if(loginModel.isExist(userRequest.getUsername()) != false) {
			model.addAttribute("signUpRequest", new UserRequest());
            return "/SignUpError";
        }
		if(userRequest.getUsername() == "" || userRequest.getPassword() == "") {
			model.addAttribute("signUpRequest", new UserRequest());
			return "/SignUpError";
		}
		
		loginModel.signUp(userRequest.getUsername(), userRequest.getPassword());
		
		User user = new User();
		user = loginModel.isLogin(userRequest.getUsername(), userRequest.getPassword());
		
		session = request.getSession();
		session.setAttribute("userInf", user);
		
		model.addAttribute("noteRequest", new NoteRequest());
		model.addAttribute("noteList", mainModel.getNoteList(user));
		
		return "/Main";
	}
	
	@GetMapping(value="/Login")
	public String login(HttpServletRequest request, HttpServletResponse response, @ModelAttribute UserRequest userRequest, Model model) {
		if(userRequest.getUsername() == "" || userRequest.getPassword() == "") {
			model.addAttribute("loginRequest", new UserRequest());
			return "/EmptyError";
		}
		
		if(loginModel.isLogin(userRequest.getUsername(), userRequest.getPassword()) == null) {
			model.addAttribute("loginRequest", new UserRequest());
			return "/LoginError";
		}
		session = request.getSession();
		
		User user = loginModel.isLogin(userRequest.getUsername(), userRequest.getPassword());
		
		session.setAttribute("userInf", user);
		
		model.addAttribute("noteList", mainModel.getNoteList(user));
		model.addAttribute("noteRequest", new NoteRequest());
		
		return "/Main";
	}
	
	@PostMapping(value="/createNote")
	public String createNote(@ModelAttribute NoteRequest noteRequest, Model model) {
		User user = (User) session.getAttribute("userInf");
		mainModel.createNote(user, noteRequest.getTitle(), noteRequest.getContents());
		model.addAttribute("noteList", mainModel.getNoteList(user));
		model.addAttribute("noteRequest", new NoteRequest());
		
		return "/Main";
	}
	
	@PostMapping(value="/deleteNote")
	public String deleteNote(@ModelAttribute NoteRequest noteRequest, Model model) {
		mainModel.deleteNote(noteRequest.getTitle());
		User user = (User) session.getAttribute("userInf");
		model.addAttribute("noteList", mainModel.getNoteList(user));
		model.addAttribute("noteRequest", new NoteRequest());
		
		return "/Main";
	}
	
	@PostMapping(value="/updateNote")
	public String updateNote(@ModelAttribute NoteRequest noteRequest, Model model) {
		mainModel.updateNote(noteRequest.getTitle(), noteRequest.getContents());
		User user = (User) session.getAttribute("userInf");
		model.addAttribute("noteList", mainModel.getNoteList(user));
		model.addAttribute("noteRequest", new NoteRequest());
		
		return "/Main";
	}
	
	@GetMapping(value="/signOut")
	public String signOut(Model model) {
		model.addAttribute("loginRequest", new UserRequest());
		session.invalidate();
		
		return "/Login";
	}
}