package com.atguigu.crm.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerActivity;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerActivityService;
import com.atguigu.crm.service.CustomerService;

@Controller
@RequestMapping("/activity")
public class CustomerActivityHandler {

	@Autowired
	private CustomerActivityService customerActivityService;
	
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/delete/{activityId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("activityId") Integer activityId,
			@RequestParam(value = "customerId") Integer customerId, @RequestParam("pageNo") Integer pageNo) {
		
		customerActivityService.delete(activityId);
		
		return "redirect:/activity/list/"+ customerId + "?pageNo="+pageNo;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public String update(CustomerActivity activity,@RequestParam("pageNo") Integer pageNo) {

		customerActivityService.update(activity);
		return "redirect:/activity/list/" + activity.getCustomer().getId() + "?pageNo=" + pageNo;
	}

	@RequestMapping(value = "/toEditUI/{id}", method = RequestMethod.GET)
	public String toEditUI(@PathVariable("id") Integer id, Map<String, Object> map) {

		CustomerActivity activity = customerActivityService.getCustomerActivityById(id);
		map.put("activity", activity);

		return "activity/input";
	}

	@RequestMapping(value = "/addActivity", method = RequestMethod.POST)
	public String addCustomerActivity(CustomerActivity activity) {

		customerActivityService.saveCustomerActivity(activity);

		return "redirect:/activity/list/" + activity.getCustomer().getId();
	}

	@RequestMapping("/toAddUI/{customerId}")
	public String toAddUI(@PathVariable("customerId") long customerId, Map<String, Object> map) {
		CustomerActivity activity = new CustomerActivity();
		Customer customer = new Customer();
		customer.setId(customerId);
		activity.setCustomer(customer);

		map.put("activity", activity);
		return "activity/input";
	}

	@RequestMapping(value = "/list/{customerId}", method = RequestMethod.GET)
	public String showList(@PathVariable("customerId") Integer customerId,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			Map<String, Object> map) {

		Map<String, Object> params = new HashMap<>();

		params.put("EQI_customerId", customerId);

		Page<CustomerActivity> page = customerActivityService.getPage(pageNo, params);

		Customer customer = customerService.getById((long)customerId);

		map.put("customer", customer);

		map.put("page", page);
		return "activity/list";
	}

}
