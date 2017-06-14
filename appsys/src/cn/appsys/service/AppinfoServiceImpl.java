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
	public int selectCount(Appinfo appinfo) {
		return appinfoMapper.selectCount(appinfo);
	}

	@Override
	public List<Appinfo> selectApplist(String softwareName, int status,
			int flatformId, int categoryLevel1, int categoryLevel2,
			int categoryLevel3, int index, int pageSize) {
		return appinfoMapper.selectApplist(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, (index-1)*pageSize, pageSize);
	}

	@Override
	public Appinfo selectAppByid(int id) {
		return appinfoMapper.selectAppByid(id);
	}

	@Override
	public boolean addapp(Appinfo appinfo) {
		int row = appinfoMapper.addApp(appinfo);
		if(row==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean selectByAPKName(String APKName) {
		if(appinfoMapper.selectByAPKName(APKName) != null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean deleteApp(int id) {
		int row = appinfoMapper.deleteApp(id);
		if(row ==1){
			return true;
		}else{
			return false;
		}	
	}

	@Override
	public boolean selectById(int id) {
		Appinfo appinfo = appinfoMapper.selectById(id);
		if(appinfo ==null){
			return true;
		}else{
			return false;
		}	
	}

}
