package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.entity.Dict;
import com.atguigu.crm.entity.Product;
import com.atguigu.crm.mapper.ProductMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Transactional
	public Page<Product> getPage(int pageNo, Map<String, Object> params) {
		Page<Product> page = new Page<>();
		page.setPageNo(pageNo);
		
		//获取总记录数
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = productMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//获取当前页面的list
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Product> content = productMapper.getContent(mybatisParams);
		page.setContent(content);
		
		return page;
	}

	//保存产品信息
	public void saveProduct(Product product) {
		productMapper.saveProduct(product);
		
	}

	public Product getById(Long id) {
		return productMapper.getById(id);
	}

	public void updateProduct(Product product) {
		productMapper.updateProduct(product);

		
	}

	public void deleteProduct(Long id) {
		productMapper.deleteProduct(id);
		
	}
}
