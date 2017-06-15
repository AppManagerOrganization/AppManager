package cn.appsys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.AppversionMapper;
import cn.appsys.pojo.Appversion;
@Service("appversionService")
public class AppversionServiceImpl implements AppversionService {
	@Autowired
	AppversionMapper appversionMapper;

	@Override
	public List<Appversion> selectVersion(int appId) {
		return appversionMapper.selectVersion(appId);
	}

	@Override
	public boolean addVersion(Appversion appversion) {
		int row = appversionMapper.addVersion(appversion);
		if(row ==1){
			return true;
		}else{
			return false;
		}	
	}

	@Override
	public int selectId() {
		return appversionMapper.selectId();
	}

	

}
