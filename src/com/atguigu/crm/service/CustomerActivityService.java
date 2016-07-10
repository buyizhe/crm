package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.CustomerActivity;
import com.atguigu.crm.mapper.CustomerActivityMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class CustomerActivityService {

	@Autowired
	private CustomerActivityMapper customerActivityMapper;
	
	@Transactional(readOnly=true)
	public Page<CustomerActivity> getPage(Integer pageNo, Map<String, Object> params) {
	
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> myBatisParmas = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = customerActivityMapper.getTotalElements(myBatisParmas);
		
		Page<CustomerActivity> page = new Page<>();
		page.setPageNo(pageNo);
		page.setTotalElements((int)totalElements);
		
		int fromIndex = (page.getPageNo() -1) * page.getPageSize() + 1;
		int endIndex = fromIndex + page.getPageSize();
		
		myBatisParmas.put("fromIndex", fromIndex);
		myBatisParmas.put("endIndex", endIndex);
		
		List<CustomerActivity> content = customerActivityMapper.getContent(myBatisParmas);
		page.setContent(content);
		
		return page;
	}

	public void saveCustomerActivity(CustomerActivity activity) {
		customerActivityMapper.save(activity);
		
	}

	public CustomerActivity getCustomerActivityById(Integer id) {
		return customerActivityMapper.getCustomerActivityById(id);
	}

	public void update(CustomerActivity activity) {
		customerActivityMapper.update(activity);
		
	}

	public void delete(Integer activityId) {
		
		customerActivityMapper.delete(activityId);
		
		
	}

	
}
