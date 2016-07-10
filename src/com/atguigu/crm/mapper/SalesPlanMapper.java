package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.SalesPlan;

public interface SalesPlanMapper {

	public void save(SalesPlan salesPlan);
	
	public void delete(@Param("id") Long id);
	
	public void update(SalesPlan salesPlan);
	
	public void updateResult(SalesPlan salesPlan);
	
	public SalesPlan getById(@Param("id") Long id);
	
	long getTotalElements(Map<String, Object> params);

	List<SalesChance> getContent(Map<String, Object> params);

}
