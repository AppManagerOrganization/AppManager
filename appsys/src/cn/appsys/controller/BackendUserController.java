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
		return "/backendlogin";
	}
	
	
	@RequestMapping(value="auditing")
	public String applist(@RequestParam(required=false) String querySoftwareName,@RequestParam(required=false) String queryStatus,
			  @RequestParam(required=false) String queryFlatformId,@RequestParam(required=false) String queryCategoryLevel1,
			  @RequestParam(required=false) String queryCategoryLevel2,@RequestParam(required=false) String queryCategoryLevel3,
			  @RequestParam(required=false) String pageIndex,HttpServletRequest request){
				Map<Object, Object> map = new HashMap<Object, Object>();
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
				Appinfo appinfo = new Appinfo();
				appinfo.setSoftwareName(querySoftwareName);
				appinfo.setStatus(Integer.parseInt(queryStatus));
				appinfo.setFlatformId(Integer.parseInt(queryFlatformId));
				appinfo.setCategoryLevel1(Integer.parseInt(queryCategoryLevel1));
				appinfo.setCategoryLevel2(Integer.parseInt(queryCategoryLevel2));
				appinfo.setCategoryLevel3(Integer.parseInt(queryCategoryLevel3));
				
				List<Appinfo> appInfoList = appinfoService.selectApplist(querySoftwareName, Integer.parseInt(queryStatus), Integer.parseInt(queryFlatformId), Integer.parseInt(queryCategoryLevel1),Integer.parseInt(queryCategoryLevel2),Integer.parseInt(queryCategoryLevel3),Integer.parseInt(pageIndex), 3);
				System.out.println(appInfoList.size()+"***********************");
				List<Datadictionary> statusList = dataDictionaryService.selectStatus();
				List<Datadictionary> flatFormList = dataDictionaryService.selectFlatform();
				List<Appcategory> categoryLevel1List = appCategoryService.selectSecond(0);
				int totalCount = appinfoService.selectCount(appinfo);
				
				int totalPageCount = totalCount%3==0?totalCount/3:totalCount/3+1;
				
				map.put("currentPageNo",pageIndex);
				map.put("totalPageCount", totalPageCount);
				map.put("totalCount", totalCount);
				request.setAttribute("pages", map);
				request.setAttribute("appInfoList",appInfoList);
				request.setAttribute("categoryLevel1List",categoryLevel1List);
				request.setAttribute("statusList",statusList);
				request.setAttribute("flatFormList", flatFormList);
				request.setAttribute("querySoftwareName", querySoftwareName);
				request.setAttribute("queryStatus",queryStatus);
				request.setAttribute("queryFlatformId", queryFlatformId);
				request.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
				return "backend/applist";
		}
	
	@RequestMapping(value="/categorylevellist")
	@ResponseBody
	public Object categorylevellist(String pid){
		List<Appcategory> appcateList = appCategoryService.selectSecond(Integer.parseInt(pid));
		return JSONArray.toJSONString(appcateList);
	}
}
