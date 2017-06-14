package cn.appsys.service;

import java.util.List;

import cn.appsys.pojo.Datadictionary;

public interface DataDictionaryService {
	public List<Datadictionary> selectStatus();
	
	public List<Datadictionary> selectFlatform();
}
