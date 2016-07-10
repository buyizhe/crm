package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Dict;
import com.atguigu.crm.entity.Product;
import com.atguigu.crm.entity.Storage;
import com.atguigu.crm.mapper.StorageMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class StorageService {

	@Autowired
	private StorageMapper storageMapper;

	@Transactional
	public Page<Storage> getPage(int pageNo, Map<String, Object> params) {
		Page<Storage> page = new Page<>();
		page.setPageNo(pageNo);
		
		//获取总记录数
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);
		long totalElements = storageMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//获取当前页面的list
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Storage> content = storageMapper.getContent(mybatisParams);
		page.setContent(content);
		
		return page;
	}

	@Transactional
	public List<Product> getProducts() {
		return storageMapper.getProducts();
	}

	@Transactional
	public void saveProduct(Storage storage) {
		storageMapper.saveProduct(storage);
		
	}

	@Transactional
	public Storage getById(long id) {
		
		return storageMapper.getById(id);
	}

	@Transactional
	public void deleteStorage(Long id) {
		storageMapper.deleteStorage(id);
		
	}

	@Transactional
	public void updateStorage(Storage storage) {
		storageMapper.updateStorage(storage);
		
	}
	
}
