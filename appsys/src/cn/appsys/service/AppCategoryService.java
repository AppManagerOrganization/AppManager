package cn.appsys.service;

import java.util.List;


import cn.appsys.pojo.Appcategory;

public interface AppCategoryService {
	
	public List<Appcategory> selectSecond(int parentId);
}
