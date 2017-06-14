package cn.appsys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.AppCategoryMapper;
import cn.appsys.pojo.Appcategory;
@Service(" appCategoryService")
public class AppCategoryServiceImpl implements AppCategoryService {
	@Autowired
	AppCategoryMapper appCategoryMapper;
	
	@Override
	public List<Appcategory> selectFirst() {
		return appCategoryMapper.selectFirst();
	}
	
	@Override
	public List<Appcategory> selectSecond(int parentId) {
		return appCategoryMapper.selectSecond(parentId);
	}

}
