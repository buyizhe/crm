package com.atguigu.crm.handler;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerService;
import com.atguigu.crm.service.CustomerServicesService;

@Controller
@RequestMapping("/service")
public class CustomerServiceHandler {

	@Autowired
	private CustomerServicesService customerServicesService;
	
	
	/**
	 * 进入创建客户服务界面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toAddUI(Map<String,Object> map){
		map.put("customers", customerServicesService.getAllCustomer());
		map.put("serviceTypes", customerServicesService.getAllType());
		map.put("customerService", new CustomerService());
		return "service/input";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String saveCustomerService(@RequestParam("createDate") Date createDate,
			CustomerService customerService,
			RedirectAttributes attributes){
		customerService.setCreateDate(createDate);
		System.out.println(customerService.getCreateDate());
		customerServicesService.saveService(customerService);
		attributes.addFlashAttribute("message", "保存成功！！");
		return "redirect:/service/create";
	}
	
}
