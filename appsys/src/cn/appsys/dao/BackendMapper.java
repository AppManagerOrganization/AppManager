package cn.appsys.dao;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Backenduser;

public interface BackendMapper {
	public Backenduser selectToLogin(@Param("userCode") String userCode,@Param("userPassword") String userPassword);
}
