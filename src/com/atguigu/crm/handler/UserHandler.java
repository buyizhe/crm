package com.atguigu.crm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.UserService;
import com.atguigu.crm.shiro.Navigation;
import com.atguigu.crm.utils.DataUtils;

@RequestMapping("/user")
@Controller
public class UserHandler {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	private static Map<Integer,String> allStatus = new HashMap<>();
	
	static{
		allStatus.put(1, "有效");
		allStatus.put(0, "无效");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public String deleteUser(@PathVariable("id") Long id,RedirectAttributes attributes){
		userService.deleteUser(id);
		attributes.addFlashAttribute("message", "删除成功！");
		return "redirect:/user/list";
	}
	
	
	/**
	 * 保存编辑后的用户
	 * @param user
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.PUT)
	public String updateUser(User user,RedirectAttributes attributes){
		userService.updateUser(user);
		attributes.addFlashAttribute("message", "编辑修改成功！");
		return "redirect:/user/list";
	}
	
	
	/**
	 * 进入编辑页面回显
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/create/{id}",method=RequestMethod.GET)
	public String toEditUI(@PathVariable("id") Long id,Map<String,Object> map){
		User user = userService.getById(id);
		map.put("user",user );
		map.put("allStatus", allStatus);
		map.put("roles", userService.getRoles());
		return "user/input";
	}
	
	
	/**
	 * 添加用户
	 * @param user
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String saveUser(User user,RedirectAttributes attributes){
		userService.saveUser(user);
		attributes.addFlashAttribute("message", "用户保存成功");
		return "redirect:/user/list";
	}
	
	/**
	 * 进入添加界面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/create")
	public String toaddUI(Map<String,Object>map){
		map.put("user", new User());
		map.put("allStatus", allStatus);
		map.put("roles", userService.getRoles());
		return "user/input";
	}
	
	
	/**
	 * 用户名列表
	 * @param pageNoStr
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String shouList(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String,Object>map,
			HttpServletRequest request){
		//获取页码
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String user = DataUtils.encodeParamsToQueryString(params);
		map.put("user", user);
		Page<User> page = userService.getPage(pageNo, params);
		map.put("page", page);
		return "user/list";
	}
	
	
	/**
	 * 1. 使用 SpringMVC 提供的 RedirectAttributes API 可以把 key-value 对重定向的情况下, 在页面上给予显示.
	 * 1). 在目标方法的参数中添加 RedirectAttributes 参数.
	 * 2). 具体调用 RedirectAttributes 的 addFlashAttribute(key, val) 来添加键值对.
	 * 3). 重定向到目标资源. 但不能直接重定向到其物理页面. 而需要经过 SpringMVC 处理一下.
	 * <mvc:view-controller path="/index" view-name="index"/>
	 * 4). 页面上使用 javascript 和 JSTL 标签结合来显示错误消息. 
	 * 
	 * 2. 错误消息如何放在国际化资源文件中. 
	 * 1). 在 SpringMVC 中配置国际化资源文件
	 * 配置 org.springframework.context.support.ResourceBundleMessageSource bean. 
	 * 且 id 必须为 messageSource
	 * 2). 在类路径下新建国际化资源文件, 加入 key-value 对.
	 * 3). 在 Handler 中自动装配 ResourceBundleMessageSource 属性
	 * 4). 调用  getMessage(String code, Object[] args, Locale locale) 方法来获取国际化资源文件中的 value 值.
	 * 5). Locale 可以直接在目标方法中传入. 
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam(value="name",required=false) String name,
			@RequestParam(value="password", required=false) String password,
			HttpSession session,
			RedirectAttributes attributes,
			Locale locale){
		User user = userService.login(name, password);
		if(user == null){
			String code = "error.user.login";
			String message = messageSource.getMessage(code, null, locale);
			attributes.addFlashAttribute("message", message);
			return "redirect:/index";
		}
		
		session.setAttribute("user", user);
		return "home/success";
	}
	
	//===========================================================
	/**
	 *  动态获取树状图
	 *  思路：将树状图中的信息封装到一个类中，
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/navigate",method=RequestMethod.GET)
	public List<Navigation> navigation(HttpSession session){
		//通过session获取user
		User user = (User)session.getAttribute("user");
		//创建一个Navigation集合
		List<Navigation> navigations = new ArrayList<Navigation>();
		//获取上下文路径
		String contextPath = session.getServletContext().getContextPath();
		Navigation top = new Navigation(Long.MAX_VALUE, "客户关系管理系统");
		navigations.add(top);
		
		Map<Long,Navigation> parentNavigations = new HashMap<Long,Navigation>();
		
		for (Authority authority: user.getRole().getAuthorities()) {
			Navigation navigation = new Navigation(authority.getId(),authority.getDisplayName());
			navigation.setUrl(contextPath+authority.getUrl());
			
			//获取父权限
			Authority parentAuthority = authority.getParentAuthority();
			Navigation parentNavigation = parentNavigations.get(parentAuthority.getId());
			
			if(parentNavigation==null){
				parentNavigation = new Navigation(parentAuthority.getId(),parentAuthority.getDisplayName());
				parentNavigation.setState("closed");
				
				parentNavigations.put(parentAuthority.getId(), parentNavigation);
				top.getChildren().add(parentNavigation);
				
			}
			
			parentNavigation.getChildren().add(navigation);
			
		}
		
		return navigations;
	}
	
	@RequestMapping(value="/shiro-login", method=RequestMethod.POST)
	public String login2(@RequestParam(value="name",required=false) String name,
			@RequestParam(value="password", required=false) String password,
			HttpSession session,
			RedirectAttributes attributes,
			Locale locale){
		
		// 获取当前 User: 调用了 SecurityUtils.getSubject() 方法. 
		Subject currentUser = SecurityUtils.getSubject();

		// 检测用户是否已经被认证. 即用户是否登录. 调用 Subject 的 isAuthenticated()
		if (!currentUser.isAuthenticated()) {
			// 把用户名和密码封装为一个 UsernamePasswordToken 对象. 
		    UsernamePasswordToken token = new UsernamePasswordToken(name, password);
		    token.setRememberMe(true);
		    try {
		    	// 执行登录. 调用 Subject 的 login(UsernamePasswordToken) 方法.
		        currentUser.login(token);
		    } 
		    // 认证时的异常. 所有的认证时的异常的父类. 
		    catch (AuthenticationException ae) {
		    	String code = "error.user.login";
				String message = messageSource.getMessage(code, null, locale);
				attributes.addFlashAttribute("message", message);
				return "redirect:/index";
		    }
		}
		session.setAttribute("user", currentUser.getPrincipals().getPrimaryPrincipal());
		return "home/success";
	}
	
}
