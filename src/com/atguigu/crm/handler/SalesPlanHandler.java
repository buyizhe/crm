package com.atguigu.crm.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.SalesPlan;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SalesChanceService;
import com.atguigu.crm.service.SalesPlanService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/plan")
public class SalesPlanHandler {
	
	@Autowired
	private SalesChanceService salesChanceService;
	
	@Autowired
	private SalesPlanService salesPlanService;
	
	@RequestMapping(value="/execute/{chanceId}",method=RequestMethod.PUT)
	@ResponseBody
	public String updatePlanResult(@PathVariable("chanceId") Long chanceId,
			@RequestParam("result") String result) throws IOException {
		
		SalesPlan salesPlan = salesPlanService.getById(chanceId);
		
		salesPlan.setResult(result);
		
		salesPlanService.updateResult(salesPlan);
		
		return "1";
	}
	
	@RequestMapping(value="/execute/{chanceId}",method=RequestMethod.GET)
	public String executePlan(@PathVariable("chanceId") long chanceId,
			Map<String, Object> map) {
		SalesChance salesChance = salesChanceService.getByIdWithUser(chanceId);
		map.put("salesChance", salesChance);
		return "plan/exec";
	}
	
	@RequestMapping(value="/list")
	public String list(@RequestParam(value="pageNo", required=false) String pageNoStr, 
			Map<String, Object> map, 
			HttpServletRequest request) {
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataUtils.encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		User createBy = (User) request.getSession().getAttribute("user");
		params.put("EQO_createBy", createBy);
		params.put("EQI_status", 1);
		
		Page<SalesChance> page = salesPlanService.getPage(pageNo, params);
		map.put("page", page);
		return "plan/list";
	}
	
	@RequestMapping(value="/make/{planId}", method=RequestMethod.PUT)
	@ResponseBody
	public String updatePlanTodo(@PathVariable("planId") Long planId, HttpServletResponse response,
			@RequestParam("todo") String todo) throws IOException {
		SalesPlan salesPlan = salesPlanService.getById(planId);
		salesPlan.setTodo(todo);
		salesPlanService.update(salesPlan);
		return "1";
	}
	
	@RequestMapping(value="/make/{planId}", method=RequestMethod.DELETE)
	@ResponseBody
	public String deletePlan(@PathVariable("planId") Long planId, HttpServletResponse response) throws IOException {
		salesPlanService.delete(planId);
		return "1";
	}
	
	@RequestMapping(value="/make/{chanceId}", method=RequestMethod.POST)
	@ResponseBody
	public String createPlan(@PathVariable("chanceId") Long chanceId,
			SalesPlan salesPlan) {
		SalesChance chance = salesChanceService.getById(chanceId);
		salesPlan.setChance(chance);
		salesPlanService.save(salesPlan);
		return salesPlan.getId().toString();
	}
	
	@RequestMapping(value="/make/{chanceId}", method=RequestMethod.GET)
	public String showPlan(@PathVariable("chanceId") Long chanceId,
			Map<String, Object> map) {
		SalesChance salesChance = salesChanceService.getByIdWithUser(chanceId);
		map.put("salesChance", salesChance);
		return "plan/make";
	}

}
