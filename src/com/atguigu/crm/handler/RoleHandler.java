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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.Role;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.AuthorityService;
import com.atguigu.crm.service.RoleService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/role")
public class RoleHandler {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthorityService authorityService;
	
	/**
	 * 保存更改的权限
	 * @param id
	 * @param role
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String updateAssignPower(@PathVariable("id") Long id,Role role,
			RedirectAttributes attributes){
		role.setId(id);
		roleService.update(role);
		attributes.addFlashAttribute("message", "权限分配成功！");
		return "redirect:/role/list";
	}
	
	
	/**
	 * 查询指定角色的所有权限
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/assign/{id}",method=RequestMethod.GET)
	public String toAssignPower(@PathVariable("id") Long id,Map<String,Object> map){
		Role role = roleService.savaRole(id);
		//查询authority中的子权限
		List<Authority> parentAuthorities = authorityService.saveAuthority();
		map.put("role", role);
		map.put("parentAuthorities", parentAuthorities);
		return "role/assign";
	}
	
	
	/**
	 *显示角色列表
	 * @param pageNoStr
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String showList(@RequestParam(value="pageNo", required=false) String pageNoStr,
			Map<String,Object> map,
			HttpServletRequest request){
		
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		Page<Role> page = roleService.getPage(pageNo);
		map.put("page", page);
		
		return "role/list";
	}
	
}
