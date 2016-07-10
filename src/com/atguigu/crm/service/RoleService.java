package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Role;
import com.atguigu.crm.mapper.RoleMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Transactional
	public Page<Role> getPage(int pageNo) {
		Page<Role> page = new Page<>();
		page.setPageNo(pageNo);
		
		//获取总记录数
		long totalElements = roleMapper.getTotalElements();
		page.setTotalElements((int)totalElements);
		
		//获取当前页面的list
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		Map<String,Object> mybatisParams = new HashMap<>();
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Role> content = roleMapper.getContent(mybatisParams);
		page.setContent(content);

		return page;
	}

	@Transactional
	public Role savaRole(Long id) {
		
		return roleMapper.saveRole(id);
	}

	@Transactional
	public void update(Role role) {
		//先删除原来的权限，
		roleMapper.deleteById(role.getId());
		//在将最新的权限保存到数据库中
		roleMapper.update(role);
		
	}

	
}
