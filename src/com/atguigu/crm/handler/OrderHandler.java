package com.atguigu.crm.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.entity.Order;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderHandler {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/details/{orderId}")
	public String details(@PathVariable("orderId") Integer id, 
			Map<String, Object> map) {
		Order order = orderService.getOrder(id);
		map.put("order", order);
		return "order/details";
	}
	
	@RequestMapping(value="/list/{customerId}")
	public String getListPage(@PathVariable("customerId") Integer id, 
			Map<String, Object> map, 
			@RequestParam(value="pageNo", required=false) String pageNoStr) {
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		Page<Order> page = orderService.getPageByCustomerId(pageNo, id);
		map.put("page", page);
		return "order/list";
	}

}
