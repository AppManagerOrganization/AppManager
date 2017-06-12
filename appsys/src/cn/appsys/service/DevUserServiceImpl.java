package cn.appsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.DevUserMapper;
import cn.appsys.pojo.Devuser;
@Service("devUserService")
public class DevUserServiceImpl implements DevUserService {
	@Autowired
	DevUserMapper devUserMapper;
	@Override
	public Devuser selectToLogin(String devCode, String devPassword) {
		return devUserMapper.selectToLogin(devCode, devPassword);
	}

}
