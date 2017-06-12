package cn.appsys.dao;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Devuser;

public interface DevUserMapper {
	public Devuser selectToLogin(@Param("devCode") String devCode,@Param("devPassword")String devPassword);
}
