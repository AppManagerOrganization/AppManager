package cn.appsys.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appinfo;

public interface AppinfoMapper {
	public List<Appinfo> selectApplist(@Param("softwareName") String softwareName,@Param("status") int status,@Param("flatformId") int flatformId,@Param("categoryLevel1") int categoryLevel1,@Param("categoryLevel2") int categoryLevel2,@Param("categoryLevel3") int categoryLevel3,@Param("index") int index,@Param("pageSize") int pageSize);

	public List<Appinfo> selectCheckApplist(@Param("softwareName") String softwareName,@Param("status") int status,@Param("flatformId") int flatformId,@Param("categoryLevel1") int categoryLevel1,@Param("categoryLevel2") int categoryLevel2,@Param("categoryLevel3") int categoryLevel3,@Param("index") int index,@Param("pageSize") int pageSize);
	
	public int selectCount(Appinfo appinfo);
	
	public int selectCheckCount(Appinfo appinfo);
	
	public Appinfo selectAppByid(@Param("id") int id);
	
	public int addApp(Appinfo appinfo);
	
	public Appinfo selectByAPKName(@Param("APKName") String APKName);
	
	public int deleteApp(@Param("id") int id);
	
	public Appinfo selectById(@Param("id") int id);
	
	public int editappinfo(Appinfo appinfo);
	
	public int changeVersionId(Appinfo appinfo);
	
	public int onsale(@Param("id") int id,@Param("onSaleDate") Date onSaleDate);
	
	public int offsale(@Param("id") int id,@Param("offSaleDate") Date offSaleDate);
	
	public Appinfo selectToCheck(@Param("id") int id);
	
	public int checksave(Appinfo appinfo);
}
