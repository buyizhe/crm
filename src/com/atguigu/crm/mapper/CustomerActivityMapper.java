package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.CustomerActivity;

public interface CustomerActivityMapper {

	long getTotalElements(Map<String, Object> myBatisParmas);

	List<CustomerActivity> getContent(Map<String, Object> myBatisParmas);

	void save(CustomerActivity activity);

	CustomerActivity getCustomerActivityById(@Param("id") Integer id);

	void update(CustomerActivity activity);

	void delete(@Param("activityId")Integer activityId);

}
