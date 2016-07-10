package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Role;
import com.atguigu.crm.entity.User;

public interface UserMapper {

	User getByName(@Param("name") String name);

	long getTotalElements(Map<String, Object> mybatisParams);

	List<User> getContent(Map<String, Object> mybatisParams);

	List<Role> getRoles();

	void saveUser(User user);

	User getById (@Param("id") Long id);

	void updateUser(User user);

	void deleteUser(@Param("id") Long id);
	
}
