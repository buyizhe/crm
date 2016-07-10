package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Dict;

public interface DictMapper {

	List<String> getDict(@Param("type") String type);

	//查询所有Dict集合
	List<Dict> getContent(Map<String, Object> mybatisParams);

	//查询总记录数
	long getTotalElements(Map<String, Object> mybatisParams);

	//通过id查询dict
	Dict getById(@Param("id") Long id);

	//修改数据
	void updateDict(Dict dict);

	void deleteDict(@Param("id") Long id);

	void saveDict(Dict dict);
	

}
