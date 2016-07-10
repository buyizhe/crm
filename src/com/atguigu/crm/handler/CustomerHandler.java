package com.atguigu.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerService;
import com.atguigu.crm.service.DictService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/customer")
public class CustomerHandler {

	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DictService dictService;

	@RequestMapping(value="/{customer.id}")
	public String toInput(@PathVariable("customer.id") Integer id,Map<String,Object> map){
		
		Customer customer=customerService.getCustomerById(id);
		map.put("customer",customer);
		map.put("managers",customerService.getManagersById(id));
		map.put("regions",customerService.getRegions());
		map.put("credits",customerService.getCredits());
		map.put("levels",customerService.getLevels());
		map.put("satisfies",customerService.getSatisfies());
		return "customer/input";
	}
	
	@RequestMapping(value="/{customer.id}",method=RequestMethod.PUT)
	public String update(@PathVariable("customer.id") Integer id,Customer customer,RedirectAttributes atttibutes){
		
		customerService.updateCustomer(customer);
	
		atttibutes.addFlashAttribute("message", "客户信息更新成功！");
		return "redirect:/customer/list";
	}

	
	@RequestMapping(value="/delete/{customerId}", method=RequestMethod.PUT)
	@ResponseBody
	public String updateCustomerState(@PathVariable("customerId") Integer id) {
		String state = "删除";
		customerService.delete(id, state);
		return "1";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(value="pageNo", required=false) String pageNoStr,
			Map<String, Object> map,
			HttpServletRequest request){
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataUtils.encodeParamsToQueryString(params);
		System.out.println(queryString);
		map.put("queryString", queryString);
		
		//获取页码
		int pageNo=1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		Page<Customer> page = customerService.getPage(pageNo, params);
		map.put("page", page);
		List<String> regions = dictService.getDict("地区");
		List<String> levels = dictService.getDict("客户等级");
		map.put("regions", regions);
		map.put("levels", levels);
		return "customer/list";
	}
	
}
