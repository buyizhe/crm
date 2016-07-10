package com.atguigu.crm.mapper;

import java.util.List;

import com.atguigu.crm.entity.CustomerService;


public interface CustomerServiceMapper {

	List<String> getAllType();

	void saveService(CustomerService customerService);

}
