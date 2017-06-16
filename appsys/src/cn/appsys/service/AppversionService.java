package cn.appsys.service;

import java.util.List;


import cn.appsys.pojo.Appversion;

public interface AppversionService {
	public List<Appversion> selectVersion(int appId);
	
	public boolean addVersion(Appversion appversion);
	
	public int selectId();
	
	public boolean changeVersion(Appversion appversion);
	
	public Appversion selectVersionByAid(int id);
}
