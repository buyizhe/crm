package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.ContactMapper;
import com.atguigu.crm.mapper.CustomerMapper;
import com.atguigu.crm.mapper.SalesChanceMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class SalesChanceService {

	@Autowired
	private SalesChanceMapper salesChanceMapper;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private ContactMapper contactMapper;
	
	@Transactional
	public void updateDispatch(SalesChance chance) {
		salesChanceMapper.updateDispatch(chance);
	}

	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return salesChanceMapper.getAllUsers();
	}

	@Transactional
	public void update(SalesChance chance) {
		salesChanceMapper.update(chance);
	}

	@Transactional(readOnly = true)
	public SalesChance getById(long id) {
		return salesChanceMapper.getById(id);
	}

	@Transactional
	public void delete(Long id) {
		salesChanceMapper.delete(id);
	}

	@Transactional
	public void save(SalesChance salesChance) {
		salesChanceMapper.save(salesChance);
	}

	/**
	 * 从 Handler 中传入的 params 能否直接传给 Mapper 方法呢 ? 1. 表单传入的一定是一个字符串. 例如: 1990-1-1.
	 * 而目标的属性类型为 Date 类型. 则字符串不能直接使用. 而需要进行类型的转换. 2. 若进行的是 LIKE 比较. 则需要把 value
	 * 值的前后加上 %%.
	 * 
	 * 所以传入的参数名中必须包含额外的信息: 比较的方式, 和目标属性的类型.
	 * <input type="text" name="search_LIKES_contact" />
	 * 
	 * search_ 为前缀. 可以在批量获取参数时使用. contact 属性名. LIKES 可以在分为两部分. LIKE 为比较方式。 S
	 * 为目标属性的类型.
	 * 
	 * 所以可以对查询属性进行进一步的封装. class PropertyFilter{ private String propertyName;
	 * //contact private Oject propertyVal; //属性值. 由用户输入
	 * 
	 * private class propertyType; //属性的类型. 有 S 来标记 private MatchType matchType;
	 * //比较方式.
	 * 
	 * public enum MatchType{ LIKE, EQ, GT, GE, LT, LE; }
	 * 
	 * public enum PropertyType{ S(String.class), I(Integer.class),
	 * B(Boolean.class), F(Float.class), D(Date.class);
	 * 
	 * private Class propertyType;
	 * 
	 * private PropertyType(Class propertyType){ this.propertyType =
	 * propertyType; }
	 * 
	 * public Class getPropertyType(){ return this.propertyType; } }
	 * 
	 * //getter, setter. }
	 * 
	 * 3. 先把由 handler 传入的 params 转为 PropertyFilter 的集合. 再把该集合转为 mybatis 可用的
	 * params.
	 */
	@Transactional(readOnly = true)
	public Page<SalesChance> getPage(int pageNo, Map<String, Object> params) {
		Page<SalesChance> page = new Page<>();
		page.setPageNo(pageNo);

		// 1. 获取总的记录数
		// 1.1 把传入的 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		// 1.2 把 RropertyFilter 的集合转为 mybatis 可用的 params
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);

		long totalElements = salesChanceMapper.getTotalElements2(mybatisParams);
		page.setTotalElements((int) totalElements);

		// 2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);

		List<SalesChance> content = salesChanceMapper.getContent2(mybatisParams);
		page.setContent(content);

		// 3. 组装 Page 并返回
		return page;
	}

	/*
	 * static{ DateConverter dateConverter = new DateConverter();
	 * dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd hh:mm:ss"
	 * }); ConvertUtils.register(dateConverter, Date.class); }
	 * 
	 * private Map<String, Object> parsePropertyFiltersToMyBatisParmas(
	 * List<PropertyFilter> filters) { Map<String, Object> params = new
	 * HashMap<String, Object>(); for(PropertyFilter filter: filters){ String
	 * propertyName = filter.getPropertyName();
	 * 
	 * Object propertyVal = filter.getPropertyVal(); Class propertyType =
	 * filter.getPropertyType(); propertyVal = ConvertUtils.convert(propertyVal,
	 * propertyType);
	 * 
	 * MatchType matchType = filter.getMatchType(); if(matchType ==
	 * MatchType.LIKE){ propertyVal = "%" + propertyVal + "%"; }
	 * 
	 * params.put(propertyName, propertyVal); }
	 * 
	 * return params; }
	 * 
	 * private List<PropertyFilter> parseHandlerParamsToPropertyFilters(
	 * Map<String, Object> params) { List<PropertyFilter> filters = new
	 * ArrayList<>();
	 * 
	 * for(Map.Entry<String, Object> entry: params.entrySet()){ String fieldName
	 * = entry.getKey(); Object fieldVal = entry.getValue();
	 * 
	 * PropertyFilter filter = new PropertyFilter(fieldName, fieldVal);
	 * filters.add(filter); }
	 * 
	 * return filters; }
	 */

	/**
	 * 1. 因为 mybatis 的 Mapper 都是接口. 而接口中是没有方法实现的. 所以, 只能在 Service 方法中完成 Page
	 * 对象的组装. 2. 关于分页: 1). 传入 pageNo 和查询条件. 2). 先查询 totalElements. 即总的记录数 3).
	 * 利用总的记录数: ①. 计算总的页数 ②. 校验 pageNo 的合法性. 4). 再查询当前页面的 List. 即当前页面要显示的元素的集合.
	 * ①. 利用 pageNo 和 pageSize 来计算具体分页的 fromIndex 和 endIndex 5). 组装为 Page 对象.
	 */
	@Transactional(readOnly = true)
	public Page<SalesChance> getPage(int pageNo, User createBy, int status) {
		Page<SalesChance> page = new Page<>();
		page.setPageNo(pageNo);

		// 1. 获取总的记录数
		long totalElements = salesChanceMapper.getTotalElements(createBy, status);
		page.setTotalElements((int) totalElements);

		// 2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		List<SalesChance> content = salesChanceMapper.getContent(createBy, status, fromIndex, endIndex);
		page.setContent(content);

		// 3. 组装 Page 并返回
		return page;
	}

	@Transactional(readOnly = true)
	public SalesChance getByIdWithUser(long chanceId) {
		return salesChanceMapper.getByIdWithUser(chanceId);
	}

	@Transactional
	public void updateFinishStatus(SalesChance salesChance) {
		
		Customer customer = new Customer();
		
		customer.setState("正常");
		customer.setName(salesChance.getCustName());
		customer.setNo(DataUtils.getUUID());
		
		Contact contact = new Contact();
		
		customerMapper.insertCustomerForFinisChance(customer);
		
		contact.setName(salesChance.getContact());
		contact.setTel(salesChance.getContactTel());
		contact.setCustomer(customer);
		
		contactMapper.insertContactForFinshChance(contact);
		salesChanceMapper.updateFinishStatus(salesChance);
		
	}

	public void updateStop(long id) {
		salesChanceMapper.updateStop(id);
	}

}
