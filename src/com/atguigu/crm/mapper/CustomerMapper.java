package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;

public interface CustomerMapper {

	public int insertCustomerForFinisChance(Customer customer);

	public Customer getById(@Param("id") Long id);
	
	public void delete(@Param("id") Integer id, @Param("state") String state);

	public long getTotalElements(Map<String, Object> mybatisParams);

	public List<Customer> getContent(Map<String, Object> mybatisParams);

	public void updateCustomer(Customer customer);

	public Customer getCustomerById(Integer id);
	
	public List<String> getRegions(String type);
	
	public List<String> getLevels(String type);
	
	public List<String> getSatisfies(String type);
	
	public List<String> getCredits(String type);
	
	public List<Contact> getManagersById(Integer id);

	public List<Customer> getAllCustomer();
	

}
