package cn.appsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appversion;

public interface AppversionMapper {
	public List<Appversion> selectVersion(@Param("appId") int appId);
	
	public int addVersion(Appversion appversion);
	
	public int selectId();
	
	public int changeVersion(Appversion appversion);
	
	public Appversion selectVersionByAid(@Param("id") int id);
	
	public Appversion selectVersionCheck(@Param("id") int id);
}
