package cn.appsys.service;


import cn.appsys.pojo.Devuser;

public interface DevUserService {
	public Devuser selectToLogin(String devCode,String devPassword);
}
