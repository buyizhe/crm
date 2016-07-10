package com.atguigu.crm.utils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;

public class DataUtils {
	
	static{
		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd hh:mm:ss"});
		ConvertUtils.register(dateConverter, Date.class);
	}
	
	/**
	 * 把传入的 params 转为 PropertyFilter 的集合
	 * 
	 * @param filters
	 * @return
	 */
	public static Map<String, Object> parsePropertyFiltersToMyBatisParmas(
			List<PropertyFilter> filters) {
		Map<String, Object> params = new HashMap<String, Object>();
		for(PropertyFilter filter: filters){
			String propertyName = filter.getPropertyName();
			
			Object propertyVal = filter.getPropertyVal();
			Class propertyType = filter.getPropertyType();
			propertyVal = ConvertUtils.convert(propertyVal, propertyType);
			
			MatchType matchType = filter.getMatchType();
			if(matchType == MatchType.LIKE){
				propertyVal = "%" + propertyVal + "%";
			}
			
			params.put(propertyName, propertyVal);
		}
		
		return params;
	}

	/**
	 * 把 RropertyFilter 的集合转为 mybatis 可用的 params
	 * 
	 * @param params
	 * @return
	 */
	public static List<PropertyFilter> parseHandlerParamsToPropertyFilters(
			Map<String, Object> params) {
		List<PropertyFilter> filters = new ArrayList<>();
		
		for(Map.Entry<String, Object> entry: params.entrySet()){
			String fieldName = entry.getKey();
			Object fieldVal = entry.getValue();
			
			PropertyFilter filter = new PropertyFilter(fieldName, fieldVal);
			filters.add(filter);
		}
		
		return filters;
	}
	
	public static String encodeParamsToQueryString(Map<String, Object> params) {
		StringBuilder result = new StringBuilder();
		
		for(Map.Entry<String, Object> entry: params.entrySet()){
			String key = entry.getKey();
			Object val = entry.getValue();
			
			if(val == null || val.toString().trim().equals("")){
				continue;
			}
			
			result.append("&")
			      .append("search_")
			      .append(key)
			      .append("=")
			      .append(val);
		}
		
		return result.toString();
	}
	
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

}
