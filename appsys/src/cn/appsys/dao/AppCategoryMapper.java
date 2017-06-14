package cn.appsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appcategory;

public interface AppCategoryMapper {
	public List<Appcategory> selectFirst();
	
	public List<Appcategory> selectSecond(@Param("parentId") int parentId);
}
