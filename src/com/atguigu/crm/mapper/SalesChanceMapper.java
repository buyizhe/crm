package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;

public interface SalesChanceMapper {
	
	long getTotalElements2(Map<String, Object> params);

	List<SalesChance> getContent2(Map<String, Object> params);
	
	void update(SalesChance chance);
	
	SalesChance getById(@Param("id") long id);
	
	void delete(@Param("id") long id);

	void save(SalesChance chance);
	
	long getTotalElements(@Param("createBy") User createBy, 
			@Param("status") int status);

	List<SalesChance> getContent(@Param("createBy") User createBy, @Param("status") int status, 
			@Param("fromIndex") int fromIndex,
			@Param("endIndex") int endIndex);
	

	SalesChance getByIdWithUser(@Param("id") long id);

	List<User> getAllUsers();

	void updateDispatch(SalesChance chance);

	void updateFinishStatus(SalesChance salesChance);

	void updateStop(long id);


}
