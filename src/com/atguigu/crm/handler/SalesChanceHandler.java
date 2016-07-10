package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SalesChanceService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/chance")
public class SalesChanceHandler {

	@Autowired
	private SalesChanceService salesChanceService;
	
	// 查看开发详细情况
	@RequestMapping("/detail/{chanceId}")
	public String detail(@PathVariable("chanceId") Long chanceId,
			Map<String ,Object> map) {
		SalesChance salesChance = salesChanceService.getByIdWithUser(chanceId);
		map.put("salesChance", salesChance);
		return "plan/detail";
	}
	
	//客户开发失败(将status修改为4)
	@RequestMapping(value="/stop/{id}",method=RequestMethod.PUT)
	public String stop(@PathVariable("id") Long id,RedirectAttributes atttibutes){
		salesChanceService.updateStop(id);
		atttibutes.addFlashAttribute("message", "终止开发成功");
		return "redirect:/plan/chance/list";
	}
	
	@RequestMapping(value="/finish/{chanceId}", method=RequestMethod.GET) 
	public String finishSalesChancePlan(@PathVariable("chanceId") Long chanceId, RedirectAttributes attributes) {
		SalesChance salesChance = salesChanceService.getById(chanceId);
		salesChance.setStatus(3);
		salesChanceService.updateFinishStatus(salesChance);
		attributes.addFlashAttribute("message", "操作成功!");
		return "redirect:/plan/chance/list";
	}

	//完成指派任务
	@RequestMapping(value="/dispatch/{id}", method=RequestMethod.PUT)
	public String updateDispatch(@PathVariable("id") Long id,SalesChance chance, RedirectAttributes attributes){
		chance.setId(id);
		chance.setStatus(2);
		salesChanceService.updateDispatch(chance);
		attributes.addFlashAttribute("message", "操作成功!");
		
		return "redirect:/chance/list";
	}
	
	//去指派页面
	@RequestMapping(value="/dispatch/{id}", method=RequestMethod.GET)
	public String todiapach(@PathVariable("id") Long id, Map<String, Object> map){
		map.put("chance", salesChanceService.getById(id));
		map.put("users",salesChanceService.getAllUsers());
		return "chance/dispatch";
	}
	
	@RequestMapping(value="/list")
	public String list(@RequestParam(value="pageNo", required=false) String pageNoStr,
			Map<String, Object> map,
			HttpServletRequest request){
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataUtils.encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		
		//1. 获取 pageNo
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		//2. 获取当前的登录用户
		User createBy = (User) request.getSession().getAttribute("user");
		params.put("EQO_createBy", createBy);
		params.put("EQI_status", 1);
		
		//3. 调用 Service 方法获取 Page 对象
		Page<SalesChance> page = salesChanceService.getPage(pageNo, params);
		//4. 把 Page 对象加入到 request 域对象中
		map.put("page", page);
		//5. 转发页面
		return "chance/list";
	}
	
	/*private String encodeParamsToQueryString(Map<String, Object> params) {
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
	}*/

	/**
	 * 完成修改操作
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String update(@PathVariable("id") Long id,
			SalesChance chance, 
			RedirectAttributes attributes,
			@RequestParam(value="pageNo", required=false, defaultValue="1") Integer pageNo){
		chance.setId(id);
		salesChanceService.update(chance);
		attributes.addFlashAttribute("message", "操作成功!");
		
		return "redirect:/chance/list?pageNo=" + pageNo;
	}
	
	/**
	 * 表单回显
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String input(@PathVariable("id") Long id, Map<String, Object> map){
		map.put("chance", salesChanceService.getById(id));
		return "chance/input";
	}
	
	/**
	 * 如何把一个 a 超链接转为一个 DELETE 请求. 
	 * 1. 新建一个隐藏的表单
	 * 2. 使用 js 提交表单. 
	 * 3. 如何保持分页的信息 ? 把分页的 pageNo 提交过来再重定向过去. 
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes attributes,
			@RequestParam(value="pageNo", required=false, defaultValue="1") Integer pageNo){
		salesChanceService.delete(id);
		attributes.addFlashAttribute("message","操作成功!");
		
		return "redirect:/chance/list?pageNo=" + pageNo;
	}
	
	/**
	 * 1. 时间如何由字符串转为对应的 Date 类型 ? 
	 * 在时间字段前添加 @DateTimeFormat(pattern="yyyy-MM-dd") 标签
	 * 
	 * @DateTimeFormat(pattern="yyyy-MM-dd")
	 * private Date createDate;
	 * 
	 * 2. 参加人从 HttpSession 中获取. 或添加隐藏域. 
	 * 3. 需要设置 status 属性值为 1
	 */
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String save(SalesChance salesChance, RedirectAttributes attributes){
		salesChance.setStatus(1);
		salesChanceService.save(salesChance);
		attributes.addFlashAttribute("message", "操作成功!");
		return "redirect:/chance/list";
	}
	
	/**
	 * 显示表单录入页面.
	 * 1. 在 request 中必须存放 modelAttribute 对应的  bean
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String input(Map<String, Object> map){
		map.put("chance", new SalesChance());
		return "chance/input";
	}
	
	/**
	 * 1. 查询所有的 SalesChance.
	 * 1). 查询的是当前登录用户参加的 SalesChance. 即 createBy 为当前的登录用户. 
	 * 2). 查询才创建的 SalesChance. 即 status 属性值等于 1 的 SalesChance.
	 * 
	 * 3). URL: /chance/list GET
	 * 4). 响应页面: chance/list.jsp
	 */
	@RequestMapping(value="/list2", method=RequestMethod.GET)
	public String list2(@RequestParam(value="pageNo", required=false) String pageNoStr,
			Map<String, Object> map,
			HttpSession session){
		//1. 获取 pageNo
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		//2. 获取当前的登录用户
		User createBy = (User) session.getAttribute("user");
		//3. 调用 Service 方法获取 Page 对象
		Page<SalesChance> page = salesChanceService.getPage(pageNo, createBy, 1);
		//4. 把 Page 对象加入到 request 域对象中
		map.put("page", page);
		//5. 转发页面
		return "chance/list";
	}
	
}
