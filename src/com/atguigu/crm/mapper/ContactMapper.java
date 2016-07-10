package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Contact;

public interface ContactMapper {

	int insertContactForFinshChance(Contact contact);
	
	long getTotalElements(Map<String, Object> params);

	List<Contact> getContect(Map<String, Object> params);

	void save(Contact contact);

	void delete(@Param("id") Long id);

	Contact getById(@Param("id") Long id);

	void update(Contact contact);

	long getCountForCustomerId(@Param("customerId") Long customerId);
	
}
