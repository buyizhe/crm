package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import com.atguigu.crm.entity.CustomerService;


public interface AllotMapper {

	long getTotalElements(Map<String, Object> mybatisParams);

	List<CustomerService> getContent(Map<String, Object> mybatisParams);

}
