package com.atguigu.crm.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ContactService;
import com.atguigu.crm.service.CustomerService;

@Controller
@RequestMapping("/contact")
public class ContactHandler {
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/create", method=RequestMethod.PUT)
	public String update(Contact contact, @RequestParam("customer.id") Long customerId,RedirectAttributes attributes) {
		contactService.update(contact);
		attributes.addFlashAttribute("message", "保存成功");
		return "redirect:/contact/list/"+customerId;
	}
	
	@RequestMapping(value="/create/{contactId}/{customerId}",method=RequestMethod.GET)
	public String toUpdateUI(@PathVariable("contactId") Long contactId, 
			@PathVariable("customerId") Long customerId, 
			Map<String, Object> map) {
		Contact contact = contactService.getById(contactId);
		map.put("contact", contact);
		map.put("customerId", customerId);
		return "contact/input";
	}
	
	@RequestMapping(value="/delete/{contactId}/{customerId}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("contactId") Long contactId, @PathVariable("customerId") Long customerId, 
			RedirectAttributes attributes) {
		long count = contactService.getCountForCustomerId(customerId);
		if(count == 1){
			attributes.addFlashAttribute("message", "必须保留一个联系人");
			return "redirect:/contact/list/"+customerId;
		}
		contactService.delete(contactId);
		attributes.addFlashAttribute("message", "删除成功");
		return "redirect:/contact/list/"+customerId;
	}
	
	@RequestMapping(value="/create/{customerId}",method=RequestMethod.GET)
	public String toCreateUI(@PathVariable("customerId") Long customerId, Map<String, Object> map) {
		map.put("customerId", customerId);
		map.put("contact", new Contact());
		return "contact/input";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String create(Contact contact,@RequestParam("customer.id") Long customerId, RedirectAttributes attributes) {
		contactService.save(contact);
		attributes.addFlashAttribute("message", "保存成功");
		return "redirect:/contact/list/"+customerId;
	}

	@RequestMapping("/list/{customerId}")
	public String list(@PathVariable("customerId") Long customerId,
			@RequestParam(value="pageNo", required=false) String pageNoStr,
			Map<String, Object> map) {
		
		Customer customer = customerService.getById(customerId);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Integer pageNo = 1;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {}
		
		params.put("EQI_customerId", customerId);
		
		Page<Contact> page = contactService.getPage(pageNo, params);
		
		map.put("page", page);
		map.put("customer",customer);
		
		return "contact/list";
	}
	
}
