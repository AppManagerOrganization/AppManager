package cn.appsys.service;

import java.util.Date;
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

	@Override
	public Appinfo selectToChange(int id) {
		return appinfoMapper.selectAppByid(id);
	}

	@Override
	public boolean editappinfo(Appinfo appinfo) {
		int row = appinfoMapper.editappinfo(appinfo);
		if(row ==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean changeVersionId(Appinfo appinfo) {
		int row =appinfoMapper.changeVersionId(appinfo);
		if(row ==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean onsale(int id,Date onSaleDate) {
		int row = appinfoMapper.onsale(id,onSaleDate);
		if(row ==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean offsale(int id,Date offSaleDate) {
		int row = appinfoMapper.offsale(id,offSaleDate);
		if(row ==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<Appinfo> selectCheckApplist(String softwareName, int status,
			int flatformId, int categoryLevel1, int categoryLevel2,
			int categoryLevel3, int index, int pageSize) {
		return appinfoMapper.selectCheckApplist(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, (index-1)*pageSize, pageSize)
				;
	}

	@Override
	public int selectCheckCount(Appinfo appinfo) {
		return appinfoMapper.selectCheckCount(appinfo);
	}

	@Override
	public Appinfo selectToCheck(int id) {
		return appinfoMapper.selectToCheck(id);
	}

	@Override
	public boolean checksave(Appinfo appinfo) {
		int row = appinfoMapper.checksave(appinfo);
		if(row ==1){
			return true;
		}else{
			return false;
		}
	}

	

	
}
