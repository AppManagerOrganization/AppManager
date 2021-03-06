package cn.appsys.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.Appcategory;
import cn.appsys.pojo.Appinfo;
import cn.appsys.pojo.Appversion;
import cn.appsys.pojo.Datadictionary;
import cn.appsys.pojo.Devuser;


import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppinfoService;
import cn.appsys.service.AppversionService;
import cn.appsys.service.DataDictionaryService;
import cn.appsys.service.DevUserService;
@RequestMapping("/dev")
@Controller
public class DevUserController {
	
	@Autowired
	DevUserService devUserService;
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
		return "devlogin";
	}
	//登录验证
	@RequestMapping(value="/dologin.html",method=RequestMethod.POST)
	public String login(@RequestParam String devCode,@RequestParam String devPassword,HttpServletRequest request,HttpSession session){
		
		Devuser devUser=  devUserService.selectToLogin(devCode, devPassword);
		if(devUser !=null){
			session.setAttribute("devUserSession",devUser);
			return "developer/main";
		}else{
			request.setAttribute("error","用户名或密码错误！");
			return "devlogin";
		}
	}
	//注销
	@RequestMapping(value="/logout.html")
	public String logout(HttpSession session){
		session.invalidate();
		return "/login";
	}
	
	//列表页面
	
	@RequestMapping(value="/list")
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
		return "developer/appinfolist";
		
		
	}
	
	//一级菜单
		@RequestMapping(value = "/categorylevellistOne",produces="application/json;charset=utf-8")
		@ResponseBody
		public Object first(String pid){
			if(pid==null || pid.equals("")){
				pid = "0";
			}
			List<Appcategory> categoryLevel1List = appCategoryService.selectSecond(Integer.parseInt(pid));
			return JSONArray.toJSONString(categoryLevel1List);
		}
	//二级菜单
	@RequestMapping(value="/second",produces="application/json;charset=utf-8")
	@ResponseBody
	public Object second(String pid){
		if(pid==null || pid.equals("")){
			pid = "0";
		}
		List<Appcategory> categoryLevel2List = appCategoryService.selectSecond(Integer.parseInt(pid));
		
		return JSONArray.toJSONString(categoryLevel2List);

	}
	//三级菜单
	@RequestMapping(value="/third",produces="application/json;charset=utf-8")
	@ResponseBody
	public Object third(String pid){
		List<Appcategory> categoryLevel3List = appCategoryService.selectSecond(Integer.parseInt(pid));
		return JSONArray.toJSONString(categoryLevel3List);
	}
	//app信息
	@RequestMapping(value="/appview/{appinfoid}")
	public String appView(@PathVariable String appinfoid,HttpServletRequest request){
		Appinfo appinfo = appinfoService.selectAppByid(Integer.parseInt(appinfoid));
		List<Appversion> appVersionList = appversionService.selectVersion(Integer.parseInt(appinfoid));
		request.setAttribute("appVersionList",appVersionList);
		request.setAttribute("appInfo",appinfo);
		return "developer/appinfoview";
	}
	//所属平台
	@RequestMapping(value = "/datalist",produces="application/json;charset=utf-8")
	@ResponseBody
	public Object datadictionarylist(){
		List<Datadictionary> flatFormList = dataDictionaryService.selectFlatform();
		return JSONArray.toJSONString(flatFormList);
	}
	
	//添加app页面
	@RequestMapping(value="/appinfoadd",method=RequestMethod.GET)
	public String appadd(){
		return "developer/appinfoadd";
	}
	//添加app
	@RequestMapping(value="/appinfoadd",method=RequestMethod.POST)
	public String addApp(@RequestParam(required=false) String softwareName,@RequestParam(required=false) String APKName,
						 @RequestParam(required=false) String supportROM,@RequestParam(required=false) String interfaceLanguage,
						 @RequestParam(required=false) String softwareSize,@RequestParam(required=false) String downloads,
						 @RequestParam(required=false) String flatformId,@RequestParam(required=false) String categoryLevel1,
						 @RequestParam(required=false) String categoryLevel2,@RequestParam(required=false) String categoryLevel3,
						 @RequestParam(required=false) String appInfo,HttpServletRequest request,HttpSession session,
						 @RequestParam(value="a_logoPicPath",required=false) MultipartFile attach){
		String logoPicPath=request.getParameter("hid_logoPicPath");
		String idPicPath = null;
		if(!attach.isEmpty()){
				String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
				String oldFileName = attach.getOriginalFilename();//原文件名
				String prefix = FilenameUtils.getExtension(oldFileName);//原文件名后缀
				
				int filesize = 5000000;
				
				if(attach.getSize()>filesize){
					request.setAttribute("fileUploadError","上传文件不得超过500k");
					return "developer/appinfoadd";
				}else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") || prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){
					String fileName = System.currentTimeMillis()+"_Personal.jpg";
					File targetFile = new File(path,fileName);
					if(!targetFile.exists()){
						targetFile.mkdirs();
					}
						try {
							attach.transferTo(targetFile);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
							request.setAttribute("fileUploadError","*上传失败");
							return "developer/appinfoadd";
						}
					idPicPath = path +File.separator+fileName;
				}else{
					request.setAttribute("fileUploadError","*上传文件格式不正确");
					return "developer/appinfoadd";
				}
			}
		Devuser devuser = (Devuser)session.getAttribute("devUserSession");
		
		Appinfo appinfo = new Appinfo();
		appinfo.setDevId(devuser.getId());
		appinfo.setCreatedBy(devuser.getId());
		appinfo.setCreationDate(new Date());
		appinfo.setSoftwareName(softwareName);
		appinfo.setAPKName(APKName);
		appinfo.setSupportROM(supportROM);
		appinfo.setInterfaceLanguage(interfaceLanguage);
		appinfo.setDownloads(Integer.parseInt(downloads));
		appinfo.setSoftwareSize(Double.parseDouble(softwareSize));
		appinfo.setFlatformId(Integer.parseInt(flatformId));
		appinfo.setCategoryLevel1(Integer.parseInt(categoryLevel1));
		appinfo.setCategoryLevel2(Integer.parseInt(categoryLevel2));
		appinfo.setCategoryLevel3(Integer.parseInt(categoryLevel3));
		appinfo.setAppInfo(appInfo);
		appinfo.setLogoLocPath(idPicPath);
		appinfo.setLogoPicPath(logoPicPath);
		appinfo.setVersionId(41);
		appinfo.setStatus(1);
		boolean flag= appinfoService.addapp(appinfo);
		
		if(flag){
			return "redirect:list";
		}else{
			return "developer/appinfoadd";
		}
	}	
	//apkname验证
	@RequestMapping(value="/apkexist")
	@ResponseBody
	public Object apkApp(String APKName){
		HashMap<String,String> map = new HashMap<String,String>();
		if(APKName.trim().length()==0){
			map.put("APKName","empty");
		}else{
			boolean flag = appinfoService.selectByAPKName(APKName);
			
			if(flag==false){
				map.put("APKName","exist");
			}else{
				map.put("APKName","noexist");
			}	
		}
		return JSONArray.toJSONString(map);
	}
	
	
	//删除app
	@RequestMapping(value="/delapp")
	@ResponseBody
	public Object deleApp(String id){
		HashMap<Object, Object> map = new HashMap<>();
		boolean result = appinfoService.selectById(Integer.parseInt(id));
		if(result){
			map.put("delResult","notexist");
		}else{
			boolean flag = appinfoService.deleteApp(Integer.parseInt(id));
			if(flag){
				map.put("delResult", "true");
			}else{
				map.put("delResult", "false");
			}
		}	
		return JSONArray.toJSONString(map);	
	}
	
	
	//修改app
	@RequestMapping(value="/appinfomodify",method=RequestMethod.GET)
	public String changeApp(HttpServletRequest request,String id){
		Appinfo appinfo = appinfoService.selectToChange(Integer.parseInt(id));
		request.setAttribute("appInfo",appinfo);
		return "/developer/appinfomodify";
	}
	//修改页面
	@RequestMapping(value="/appinfomodify",method=RequestMethod.POST)
	public String editApp(
							@RequestParam(required=false) String logoPicPath,		//原 本地路径
							@RequestParam(required=false) String logoLocPath,	Appinfo appinfo,	
							@RequestParam(value="attach",required=false) MultipartFile  attach,
							HttpServletRequest request,HttpSession session){
			String reviselogoPicPath=request.getParameter("hid_logoPicPath");
			if(reviselogoPicPath.equals(null)||reviselogoPicPath.equals("")){
				appinfo.setLogoPicPath(logoPicPath);
				appinfo.setLogoLocPath(logoLocPath);
				System.out.println("未修改图片，本地路径为:"+appinfo.getLogoPicPath());
				System.out.println("未修改图片，服务器路径为:"+appinfo.getLogoLocPath());
			}else{
				String idPicPath = null;
				System.out.println("图片路径已修改");
				//判断文件是否为空
				if(!attach.isEmpty()){
				System.out.println("修改的图片服务器端路径不为空");	
				String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");			
					String oldFileName = attach.getOriginalFilename();//原文件名		
					String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀    	       
					
					int filesize = 500000;
					
			        if(attach.getSize() >  filesize){//上传大小不得超过 500k
		            	request.setAttribute("fileUploadError", " * 上传大小不得超过 500k");
		            	System.out.println("* 上传大小不得超过 500M");
			        	return "developer/appinfoadd";
		            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
		            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
		            	String fileName = System.currentTimeMillis()+"_Personal.jpg";  
		               
		                File targetFile = new File(path, fileName);  
		                if(!targetFile.exists()){  
		                    targetFile.mkdirs();  
		                }  
		                //保存  
		                try {  
		                	attach.transferTo(targetFile);  
		                } catch (Exception e) {  
		                    e.printStackTrace();  
		                    request.setAttribute("fileUploadError", " * 上传失败！");
		                 	System.out.println(" * 上传失败！");
		                    return "developer/appinfoadd";
		                }  
		                idPicPath = path+File.separator+fileName;
		            }else{
		            	request.setAttribute("fileUploadError", " * 上传图片格式不正确");
		             	System.out.println("* 上传图片格式不正确");
		            	return "developer/appinfoadd";
		            }
				}
				appinfo.setLogoPicPath(reviselogoPicPath);
				appinfo.setLogoLocPath(idPicPath);
				System.out.println("修改图片，本地路径为:"+appinfo.getLogoPicPath());
				System.out.println("修改图片，服务器路径为:"+appinfo.getLogoLocPath());
			}
		Devuser devuser = (Devuser)session.getAttribute("devUserSession");
		
		appinfo.setModifyBy(devuser.getId());
		appinfo.setModifyDate(new Date());
		
		boolean flag = appinfoService.editappinfo(appinfo);
		if(flag){
			System.out.println("chenggong@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			return "redirect:list";
		}else{
			System.out.println("shibai!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			return "developer/appinfomodify";
		}
	}
	
	
	
	//新增版本
	@RequestMapping(value="/appversionadd",method=RequestMethod.GET)
	public String addVersion(HttpServletRequest request,String id){
		Map<String, String> m = new HashMap<String, String>();
		m.put("appId", id);
		request.setAttribute("appVersion", m);
		List<Appversion> appVersionList= appversionService.selectVersion(Integer.parseInt(id));
		request.setAttribute("appVersionList",appVersionList);
		return "/developer/appversionadd";
	}
	
	@RequestMapping(value="/appversionadd",method=RequestMethod.POST)
	public String versionAdd(HttpServletRequest request,Appversion appversion,HttpSession session,
							 @RequestParam(value="a_downloadLink",required=false) MultipartFile attach){
		String reviselogoPicPath=request.getParameter("hid_logoPicPath");//获得图片本地路径
		String	apkFileName=null;
		String idPicPath=null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件名后缀
			apkFileName=oldFileName;
			int filesize = 5000000;
			
			if(attach.getSize()>filesize){
				request.setAttribute("fileUploadError","上传文件不得超过500k");
				return "developer/appversionadd";
			}else if(prefix.equalsIgnoreCase("apk")){
				String fileName = System.currentTimeMillis()+"_Personal.jpg";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()){
					targetFile.mkdirs();
				}
					try {
						attach.transferTo(targetFile);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
						request.setAttribute("fileUploadError","*上传失败");
						return "developer/appversionadd";
					}
				idPicPath = path +File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError","*上传文件格式不正确");
				return "developer/appversionadd";
			}
		}
		Devuser devuser = (Devuser)session.getAttribute("devUserSession");
		appversion.setCreatedBy(devuser.getId());
		appversion.setCreationDate(new Date());
		appversion.setApkLocPath(idPicPath);
		appversion.setApkFileName(apkFileName);
		appversion.setPublishStatus(3);
		appversion.setDownloadLink(reviselogoPicPath);
		boolean flag = appversionService.addVersion(appversion);
		if(flag){
			Appinfo appinfo = new Appinfo();
			appinfo.setModifyBy(devuser.getId());
			appinfo.setModifyDate(new Date());
			int versionId = appversionService.selectId();
			appinfo.setVersionId(versionId);
			appinfo.setId(appversion.getAppId());
			boolean result = appinfoService.changeVersionId(appinfo);
			if(result){
				return "redirect:list";
			}else{
				return "/developer/appversionadd";
			}
		}else{
			return "/developer/appversionadd";
		}
	}
	
	//删除图片
	@RequestMapping(value="/delfile")
	@ResponseBody
	public Object delePic(){
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("result","success");
		return JSONArray.toJSONString(result);
	}
	//修改版本
	@RequestMapping(value="/appversionmodify",method=RequestMethod.GET)
	public String changeVersion(HttpServletRequest request,@RequestParam(required=false) String vid,@RequestParam(required=false) String aid,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",vid);
		map.put("appId",aid);
		
		Appversion appversion = appversionService.selectVersionByAid(Integer.parseInt(aid));

		map.put("versionNo",appversion.getVersionNo());
		map.put("versionSize",appversion.getVersionSize());
		map.put("versionInfo",appversion.getVersionInfo());
		map.put("downloadLink",appversion.getDownloadLink() );
		map.put("apkLocPath", appversion.getApkLocPath());
		map.put("apkFileName", appversion.getApkFileName());
		session.setAttribute("Appver",appversion );
		request.setAttribute("appVersion",map);
		
		List<Appversion> appVersionList= appversionService.selectVersion(Integer.parseInt(aid));
		request.setAttribute("appVersionList",appVersionList );
		return "developer/appversionmodify";
	}
	//修改提交页面
	
	@RequestMapping(value="/appversionmodify",method=RequestMethod.POST)
	public String editVersion(@RequestParam(required=false) 	String id,				//当前APP版本id
			@RequestParam(required=false) 	String appId,				//当前APPid
			@RequestParam(required=false)	String versionNo, 			//版本号
			@RequestParam(required=false)	String versionSize, 		//版本大小
			@RequestParam(required=false)	String versionInfo,			//版本简介
			@RequestParam(required=false) 	String downloadLink,		//原 本地路径(下载路径)
			@RequestParam(required=false) 	String apkLocPath,		//原 服务器端路径
			@RequestParam(required=false) 	String apkFileName,		//原 apkName
			@RequestParam(value="attach",required=false) MultipartFile  attach,
			HttpServletRequest request	 	){
		
		String reviselogoPicPath=request.getParameter("hid_logoPicPath");//获得更改后的图片本地路径
		System.out.println("下载路径（本地路径）:"+reviselogoPicPath);
		Appversion appversion=new Appversion();
		String	apkFileNames=null;//apk文件
		
		if(!reviselogoPicPath.equals(null)&&!reviselogoPicPath.equals("")){
			String idPicPath = null;
			System.out.println("图片路径已修改");
			//判断文件是否为空
			if(!attach.isEmpty()){
			System.out.println("修改的图片服务器端路径不为空");	
				String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");			
				String oldFileName = attach.getOriginalFilename();//原文件名		
				String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀    	       
				apkFileNames=oldFileName;
		
				System.out.println("文件大小:"+attach.getSize());
		       if(prefix.equalsIgnoreCase("apk")){//上传图片格式不正确
	            	String fileName = System.currentTimeMillis()+"_Personal.jpg";  
	            	System.out.println(fileName+"&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
	                File targetFile = new File(path, fileName);  
	                if(!targetFile.exists()){  
	                    targetFile.mkdirs();  
	                }  
	                //保存  
	                try {  
	                	attach.transferTo(targetFile);  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                    request.setAttribute("fileUploadError", " * 上传失败！");
	                 	System.out.println(" * 上传失败！");
	                    return "developer/appversionmodify";
	                }  
	                idPicPath = path+File.separator+fileName;
	            }else{
	            	request.setAttribute("fileUploadError", " * 上传图片格式不正确");
	             	System.out.println("* 上传图片格式不正确");
	             	return "developer/appversionmodify";
	            }
			}
				appversion.setDownloadLink(reviselogoPicPath);
				appversion.setApkLocPath(idPicPath);
				appversion.setApkFileName(apkFileName);
		}else{
		/*	APK未修改*/
			appversion.setDownloadLink(downloadLink);
			appversion.setApkLocPath(apkLocPath);
			appversion.setApkFileName(apkFileNames);
		}
			appversion.setId(Integer.parseInt(id));
			appversion.setAppId(Integer.parseInt(appId));
			appversion.setVersionNo(versionNo);
			appversion.setVersionSize(Double.parseDouble(versionSize));
			appversion.setVersionInfo(versionInfo);
		
		boolean flag = appversionService.changeVersion(appversion);
		if(flag){
			return "redirect:list";
		}else{
			return "redirect:appversionmodify";
		}
		
	}
	
	
	//上下架
	@RequestMapping(value="{appId}/{saleSwitch}/sale")
	@ResponseBody
	public Object sale(@PathVariable String appId,@PathVariable String saleSwitch){
		HashMap<String, String> map = new HashMap<String, String>();
		if(saleSwitch.equals("open")){
			boolean flag = appinfoService.onsale(Integer.parseInt(appId),new Date());
			if(flag){
				map.put("errorCode","0");
				map.put("resultMsg", "success");
			}else{
				map.put("resultMsg","failed");
			}
		}else if(saleSwitch.equals("close")){
			boolean result = appinfoService.offsale(Integer.parseInt(appId),new Date());
			if(result){
				map.put("errorCode","0");
				map.put("resultMsg", "success");
			}else{
				map.put("resultMsg","failed");
			}
		}
		return JSONArray.toJSONString(map);
	}
	
	
}
