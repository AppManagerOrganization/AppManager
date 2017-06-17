package cn.appsys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.DataDictionaryMapper;
import cn.appsys.pojo.Datadictionary;
@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService {
	@Autowired
	public DataDictionaryMapper dataDictionaryMapper;
	@Override
	public List<Datadictionary> selectStatus() {
		return dataDictionaryMapper.selectStatus();
	}
	@Override
	public List<Datadictionary> selectFlatform() {
		return dataDictionaryMapper.selectFlatform();
	}

}
