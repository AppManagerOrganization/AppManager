package cn.appsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.BackendMapper;
import cn.appsys.pojo.Backenduser;
@Service("backendService")
public class BackendServiceImpl implements BackendService {
	@Autowired
	public BackendMapper backendMapper;
	
	@Override
	public Backenduser selectToLogin(String userCode, String userPassword) {
		return backendMapper.selectToLogin(userCode, userPassword);
	}

}
