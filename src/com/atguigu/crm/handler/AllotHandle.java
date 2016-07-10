package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.CustomerService;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.AllotService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/allot")
public class AllotHandle {

	@Autowired
	private AllotService allotService;
	
	@RequestMapping(value="/list")
	public String showList(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String,Object> map,
			HttpServletRequest request){
		
		//获取页码
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String allot = DataUtils.encodeParamsToQueryString(params);
		map.put("allot", allot);
		Page<CustomerService> page = allotService.getPage(pageNo, params);
		map.put("page", page);
		return "service/allot/list";
	}
	
}
