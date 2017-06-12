package cn.appsys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.Backenduser;
import cn.appsys.service.BackendService;
@RequestMapping("/manager")
@Controller
public class BackendUserController {
	
	@Autowired
	BackendService backendService;
	@RequestMapping(value="/login.html",method=RequestMethod.GET)
	public String login(){
		return "backendlogin";
	}
	
	@RequestMapping(value="/dologin.html",method=RequestMethod.POST)
	public String login(@RequestParam String userCode,@RequestParam String userPassword,HttpServletRequest request,HttpSession session){
		
		Backenduser backendUser = backendService.selectToLogin(userCode, userPassword);
		if(backendUser !=null){
			session.setAttribute("userSession", backendUser);
			return "/backend/main";
		}else{
			request.setAttribute("error", "用户名或密码错误！");
			return "/backendlogin";
		}
	}
	
	@RequestMapping(value="/logout.html")
	public String logout(HttpSession session){
		session.invalidate();
		return "/backendlogin";
	}
}
