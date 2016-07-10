package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.mapper.CustomerMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class CustomerService {

	@Autowired
	private CustomerMapper customerMapper;
	
	@Transactional
	public void insertCustomerForFinisChance(Customer customer) {
		customerMapper.insertCustomerForFinisChance(customer);
	}

	@Transactional
	public Customer getById(Long customerId) {
		return customerMapper.getById(customerId);
	}
	@Transactional
	public void delete(Integer id, String state) {
		customerMapper.delete(id, state);
	}

	@Transactional(readOnly = true)
	public Page<Customer> getPage(int pageNo, Map<String, Object> params) {
		Page<Customer> page = new Page<>();
		page.setPageNo(pageNo);
		
		//获取总记录数
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = customerMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//获取当前页面的list
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);

		List<Customer> content = customerMapper.getContent(mybatisParams);
		page.setContent(content);
		
		return page;
	}

	@Transactional
	public void updateCustomer(Customer customer) {
		customerMapper.updateCustomer(customer);
	}
	@Transactional(readOnly = true)
	public Customer getCustomerById(Integer id) {
		return customerMapper.getCustomerById(id);
	}
	
	@Transactional(readOnly=true)
	public List<String> getRegions(){
		return customerMapper.getRegions("地区");
	}
	@Transactional(readOnly=true)
	public List<String> getCredits(){
		return customerMapper.getCredits("信用度");
	}
	@Transactional(readOnly=true)
	public List<String> getSatisfies(){
		return customerMapper.getSatisfies("满意度");
	}
	@Transactional(readOnly=true)
	public List<String> getLevels(){
		return customerMapper.getLevels("客户等级");
	}
	@Transactional(readOnly=true)
	public List<Contact> getManagersById(Integer id){
		return customerMapper.getManagersById(id);
	}
	
}
