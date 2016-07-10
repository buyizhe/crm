package com.atguigu.crm.handler;

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

import com.atguigu.crm.entity.Product;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ProductService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/product")
public class ProductHandler {

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public String deleteProduct(@PathVariable("id") Long id,RedirectAttributes attributes){
		productService.deleteProduct(id);
		attributes.addFlashAttribute("message", "删除操作成功");
		return "redirect:/product/list";
	}
	
	/**
	 * 保存编辑信息
	 * @param product
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.PUT)
	public String updateProduct(Product product,RedirectAttributes attributes){
		productService.updateProduct(product);
		attributes.addFlashAttribute("message", "信息编辑保存成功！！！");
		return "redirect:/product/list";
	}
	
	
	/**
	 * 进入编辑界面回显
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/create/{id}",method=RequestMethod.GET)
	public String toUpdate(@PathVariable("id") Long id,Map<String,Object> map){
		map.put("product", productService.getById(id));
		return "product/input";
	}
	
	
	/**
	 * 保存信息
	 * @param product
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String saveProduct(Product product,RedirectAttributes attributes){
		productService.saveProduct(product);
		attributes.addFlashAttribute("message", "产品信息保存成功！！！");
		return "redirect:/product/list";
	}
	
	/**
	 * 进入添加界面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String input(Map<String,Object> map){
		map.put("product", new Product());
		return "product/input";
	}
	
	/**
	 * 显示product数据
	 */
	@RequestMapping("/list")
	public String productList(@RequestParam(value="pageNo",required=false) String pageNoStr, 
			Map<String,Object> map,
			HttpServletRequest request){
		//获取页码
		int pageNo=1;
		try {
			pageNo=Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String product = DataUtils.encodeParamsToQueryString(params);
		map.put("product", product);
		
		Page<Product> page = productService.getPage( pageNo,params);
		map.put("page", page);
		
		return "product/list";
	}
	
}
