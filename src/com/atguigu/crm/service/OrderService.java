package com.atguigu.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.Order;
import com.atguigu.crm.mapper.OrderMapper;
import com.atguigu.crm.orm.Page;

@Service
public class OrderService {
	
	@Autowired
	private OrderMapper orderMapper;

	public Page<Order> getPageByCustomerId(int pageNo, Integer id) {
		Page<Order> page = new Page<>();
		page.setPageNo(pageNo);
		long totalElements = orderMapper.getTotalElements(id);
		page.setTotalElements((int)totalElements);
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		List<Order> content = orderMapper.getContent(id, fromIndex, endIndex);
		page.setContent(content);
		return page;
	}

	public Order getOrder(Integer id) {
		return orderMapper.getOrder(id);
	}

}
