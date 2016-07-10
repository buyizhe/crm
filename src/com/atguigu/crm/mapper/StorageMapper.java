package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Product;
import com.atguigu.crm.entity.Storage;

public interface StorageMapper {

	long getTotalElements(Map<String, Object> mybatisParams);

	List<Storage> getContent(Map<String, Object> mybatisParams);

	List<Product> getProducts();

	void saveProduct(Storage storage);

	Storage getById(@Param("id") long id);

	void deleteStorage(@Param("id") Long id);

	void updateStorage(Storage storage);
	
}
