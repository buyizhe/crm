package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.SalesPlan;
import com.atguigu.crm.mapper.SalesPlanMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class SalesPlanService {
	
	@Autowired
	private SalesPlanMapper salesPlanMapper;
	
	@Transactional(readOnly=true)
	public Page<SalesChance> getPage(int pageNo, Map<String, Object> params){
		Page<SalesChance> page = new Page<>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = salesPlanMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<SalesChance> content = salesPlanMapper.getContent(mybatisParams);
		page.setContent(content);
		
		return page;
	}
	
	@Transactional
	public void save(SalesPlan salesPlan) {
		salesPlanMapper.save(salesPlan);
	}
	
	@Transactional
	public void delete(Long id) {
		salesPlanMapper.delete(id);
	}
	
	@Transactional
	public void update(SalesPlan salesPlan) {
		salesPlanMapper.update(salesPlan);
	}
	
	@Transactional(readOnly=true)
	public SalesPlan getById(Long id) {
		return salesPlanMapper.getById(id);
	}
	
	@Transactional
	public void updateResult(SalesPlan salesPlan) {
		salesPlanMapper.updateResult(salesPlan);
	}

}
