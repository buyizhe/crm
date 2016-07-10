package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Product;

public interface ProductMapper {

	List<Product> getContent(Map<String, Object> mybatisParams);

	long getTotalElements(Map<String, Object> mybatisParams);

	void saveProduct(Product product);

	Product getById(@Param("id") Long id);

	void updateProduct(Product product);

	void deleteProduct(@Param("id") Long id);

}
