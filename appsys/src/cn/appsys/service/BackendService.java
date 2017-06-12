package cn.appsys.service;


import cn.appsys.pojo.Backenduser;

public interface BackendService {
	public Backenduser selectToLogin(String userCode,String userPassword);
}
