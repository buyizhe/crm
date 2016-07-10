package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Role;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.UserMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Transactional(readOnly=true)
	public User login(String name, String password){
		User user = userMapper.getByName(name);
		
		if(user != null 
				&& user.getEnabled() == 1
				&& user.getPassword().equals(password)){
			return user;
		}
		
		return null;
	}

	@Transactional
	public Page<User> getPage(int pageNo, Map<String, Object> params) {
		Page<User> page = new Page<>();
		page.setPageNo(pageNo);
		
		//获取总记录数
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);
		long totalElements = userMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//获取当前页面的list
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<User> content = userMapper.getContent(mybatisParams);
		page.setContent(content);
		
		return page;
	}

	@Transactional
	public List<Role> getRoles() {
		return userMapper.getRoles();
	}

	@Transactional
	public void saveUser(User user) {
		userMapper.saveUser(user);
		
	}

	@Transactional
	public User getById(Long id) {
		return userMapper.getById(id);
	}

	@Transactional
	public void updateUser(User user) {
		userMapper.updateUser(user);
		
	}

	@Transactional
	public void deleteUser(Long id) {
		userMapper.deleteUser(id);
		
	}
	
	@Transactional(readOnly=true)
	public User getByUserName(String username){
		return userMapper.getByName(username);
	}
}
