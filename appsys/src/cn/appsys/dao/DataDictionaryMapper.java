package cn.appsys.dao;

import java.util.List;

import cn.appsys.pojo.Datadictionary;

public interface DataDictionaryMapper {
	public List<Datadictionary> selectStatus();
	
	public List<Datadictionary> selectFlatform();
}
