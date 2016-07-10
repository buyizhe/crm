package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.mapper.ContactMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class ContactService {

	@Autowired
	private ContactMapper contactMapper;
	
	@Transactional
	public void insertContactForFinshChance(Contact contact) {
		contactMapper.insertContactForFinshChance(contact);
	}

	@Transactional
	public Page<Contact> getPage(Integer pageNo, Map<String, Object> params) {
		Page<Contact> page = new Page<Contact>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		// 1.2 把 RropertyFilter 的集合转为 mybatis 可用的 params
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);

		long totalElements = contactMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Contact> content = contactMapper.getContect(mybatisParams);
		page.setContent(content);
		
		return page;
	}

	@Transactional
	public void save(Contact contact) {
		contactMapper.save(contact);
	}

	@Transactional
	public void delete(Long contactId) {
		contactMapper.delete(contactId);
	}

	@Transactional(readOnly=true)
	public Contact getById(Long contactId) {
		return contactMapper.getById(contactId);
	}

	@Transactional
	public void update(Contact contact) {
		contactMapper.update(contact);
	}
	
	@Transactional
	public long getCountForCustomerId(Long customerId) {
		return contactMapper.getCountForCustomerId(customerId);
	}
}
