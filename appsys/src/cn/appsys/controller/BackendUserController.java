package cn.appsys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;


import cn.appsys.pojo.Appcategory;
import cn.appsys.pojo.Appinfo;
import cn.appsys.pojo.Appversion;
import cn.appsys.pojo.Backenduser;
import cn.appsys.pojo.Datadictionary;



import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppinfoService;
import cn.appsys.service.AppversionService;
import cn.appsys.service.BackendService;
import cn.appsys.service.DataDictionaryService;
@RequestMapping("/manager")
@Controller
public class BackendUserController {
	
	@Autowired
	BackendService backendService;
	@Autowired
	AppinfoService appinfoService;
	@Autowired
	DataDictionaryService dataDictionaryService;
	@Autowired
	AppCategoryService appCategoryService;
	@Autowired
	AppversionService appversionService;
	//登录
	@RequestMapping(value="/login.html",method=RequestMethod.GET)
	public String login(){
		return "backendlogin";
	}
	//登录验证
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
	//注销
	@RequestMapping(value="/logout.html")
	public String logout(HttpSession session){
		session.invalidate();
		return "login";
	}
	
	
	@RequestMapping(value="/list")
	public String applist(@RequestParam(required=false) String querySoftwareName,@RequestParam(required=false) String queryStatus,
			  @RequestParam(required=false) String queryFlatformId,@RequestParam(required=false) String queryCategoryLevel1,
			  @RequestParam(required=false) String queryCategoryLevel2,@RequestParam(required=false) String queryCategoryLevel3,
			  @RequestParam(required=false) String pageIndex,HttpServletRequest request){
				
		if(queryStatus==null || queryStatus.equals("")){
			queryStatus="0";
		}
		if(queryFlatformId == null || queryFlatformId.equals("")){ 
			queryFlatformId="0";
		}
		if(queryCategoryLevel1==null || queryCategoryLevel1.equals("")){
			queryCategoryLevel1="0";
		}
		if(queryCategoryLevel2==null || queryCategoryLevel2.equals("")){
			queryCategoryLevel2="0";
		}
		if(queryCategoryLevel3==null || queryCategoryLevel3.equals("")){
			queryCategoryLevel3="0";
		}
		if(pageIndex==null){
			pageIndex = "1";
		}
		List<Appinfo> applist = appinfoService.selectCheckApplist(querySoftwareName, Integer.parseInt(queryStatus),Integer.parseInt(queryFlatformId), Integer.parseInt(queryCategoryLevel1), Integer.parseInt(queryCategoryLevel2), Integer.parseInt(queryCategoryLevel3), Integer.parseInt(pageIndex), 3);
		Appinfo appinfo = new Appinfo();
		appinfo.setSoftwareName(querySoftwareName);
		appinfo.setFlatformId(Integer.parseInt(queryFlatformId));
		appinfo.setCategoryLevel1(Integer.parseInt(queryCategoryLevel1));
		appinfo.setCategoryLevel2(Integer.parseInt(queryCategoryLevel2));
		appinfo.setCategoryLevel3(Integer.parseInt(queryCategoryLevel3));
		int totalCount = appinfoService.selectCheckCount(appinfo);
		List<Appcategory> appcatelist = appCategoryService.selectSecond(0);
		
		
		List<Datadictionary> list = dataDictionaryService.selectFlatform();
		request.setAttribute("categoryLevel1List", appcatelist);
		int totalPageCount = totalCount%3==0?totalCount/3:totalCount/3+1;
		Map<String, Object> map = new HashMap<>();
		map.put("currentPageNo",pageIndex);
		map.put("totalPageCount", totalPageCount);
		map.put("totalCount", totalCount);
		request.setAttribute("flatFormList",list);
		request.setAttribute("querySoftwareName", querySoftwareName);
		request.setAttribute("pages", map);
		request.setAttribute("appInfoList",applist);
		request.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
		return "backend/applist";
	}
	
	@RequestMapping(value="/categorylevellist",produces="application/json;charset=utf-8")
	@ResponseBody
	public Object categorylevellist(String pid){
		List<Appcategory> appcateList = appCategoryService.selectSecond(Integer.parseInt(pid));
		return JSONArray.toJSONString(appcateList);
	}
	@RequestMapping(value="/check",method=RequestMethod.GET)
	public String check(@RequestParam(required=false) String aid,@RequestParam(required=false) String vid,HttpServletRequest request){
		Appinfo appinfo = appinfoService.selectToCheck(Integer.parseInt(aid));
		Appversion appversion = appversionService.selectVersionCheck(Integer.parseInt(vid));
		request.setAttribute("appInfo",appinfo);
		request.setAttribute("appVersion", appversion);
		return "backend/appcheck";
	}
	
	
	@RequestMapping(value="/checksave")
	public String check(Appinfo appinfo){
		System.out.println(appinfo.getStatus());
		System.out.println(appinfo.getStatusName());
		
		boolean flag = appinfoService.checksave(appinfo);
		if(flag){
			return "redirect:list";
		}else{
			return "backend/appcheck";
		}
		
	}
}
