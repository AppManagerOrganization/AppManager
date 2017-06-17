package cn.appsys.service;

import java.util.Date;
import java.util.List;


import cn.appsys.pojo.Appinfo;

public interface AppinfoService {
	public List<Appinfo> selectApplist(String softwareName,int status,int flatformId,int categoryLevel1, int categoryLevel2,int categoryLevel3, int index, int pageSize);
	
	public List<Appinfo> selectCheckApplist(String softwareName,int status,int flatformId,int categoryLevel1, int categoryLevel2,int categoryLevel3, int index, int pageSize);
	
	public int selectCount(Appinfo appinfo);
	
	public int selectCheckCount(Appinfo appinfo);
	
	public Appinfo selectAppByid(int id);
	
	public boolean addapp(Appinfo appinfo);
	
	public boolean selectByAPKName(String APKName);
	
	public boolean deleteApp(int id);
	
	public boolean selectById(int id);
	
	public Appinfo selectToChange(int id);
	
	public boolean editappinfo(Appinfo appinfo);
	
	public boolean changeVersionId(Appinfo appinfo);
	
	public boolean onsale(int id,Date onSaleDate);
	
	public boolean offsale(int id,Date offSaleDate);
	
	public Appinfo selectToCheck(int id);
	
	public boolean checksave(Appinfo appinfo);
}
