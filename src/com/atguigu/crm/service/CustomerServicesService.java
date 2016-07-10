package com.atguigu.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerService;
import com.atguigu.crm.mapper.CustomerMapper;
import com.atguigu.crm.mapper.CustomerServiceMapper;

@Service
public class CustomerServicesService {

	@Autowired
	private CustomerServiceMapper customerServiceMapper;
	
	@Autowired
	private CustomerMapper customerMapper;

	@Transactional
	public List<String> getAllType() {
		
		return customerServiceMapper.getAllType();
	}

	
	@Transactional
	public List<Customer> getAllCustomer() {
		
		return customerMapper.getAllCustomer();
	}

	@Transactional
	public void saveService(CustomerService customerService) {
		customerServiceMapper.saveService(customerService);
		
	}

	
}
