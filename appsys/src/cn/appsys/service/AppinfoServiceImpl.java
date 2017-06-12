package cn.appsys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.AppinfoMapper;
import cn.appsys.pojo.Appinfo;
@Service("appinfoService")
public class AppinfoServiceImpl implements AppinfoService {
	@Autowired
	AppinfoMapper appinfoMapper;
	@Override
	public List<Appinfo> selectApplist() {
		return appinfoMapper.selectApplist();
	}

}
