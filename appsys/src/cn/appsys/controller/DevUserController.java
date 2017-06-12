package cn.appsys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import cn.appsys.dao.AppinfoMapper;
import cn.appsys.pojo.Appinfo;
import cn.appsys.pojo.Devuser;

import cn.appsys.service.DevUserService;
@RequestMapping("/dev")
@Controller
public class DevUserController {
	
	@Autowired
	DevUserService devUserService;
	@Autowired
	AppinfoMapper appinfoMapper;
	@RequestMapping(value="/login.html",method=RequestMethod.GET)
	public String login(){
		return "devlogin";
	}
	
	@RequestMapping(value="/dologin.html",method=RequestMethod.POST)
	public String login(@RequestParam String devCode,@RequestParam String devPassword,HttpServletRequest request,HttpSession session){
		
		Devuser devUser=  devUserService.selectToLogin(devCode, devPassword);
		if(devUser !=null){
			session.setAttribute("devUserSession",devUser);
			return "/developer/main";
		}else{
			request.setAttribute("error", "用户名或密码错误！");
			return "/devlogin";
		}
	}
	@RequestMapping(value="/logout.html")
	public String logout(HttpSession session){
		session.invalidate();
		return "/devlogin";
	}
	@RequestMapping(value="/flatform/app/list")
	public String applist(HttpServletRequest request){
		List<Appinfo> appList = appinfoMapper.selectApplist();
		request.setAttribute("appInfoList", appList);
		return "/developer/appinfolist";
	}
}
