package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Role;

public interface RoleMapper {

	long getTotalElements();

	List<Role> getContent(Map<String, Object> mybatisParams);

	Role saveRole(@Param("id") Long id);

	void update(Role role);

	void deleteById(@Param("id") Long id);
	
}
