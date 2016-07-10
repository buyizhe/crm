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

import com.atguigu.crm.entity.Storage;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.StorageService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/storage")
public class StorageHandler {

	@Autowired
	private StorageService storageService;
	
	/**
	 * 删除数据
	 * @param id
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public String deleteStorage(@PathVariable("id") Long id,RedirectAttributes attributes){
		storageService.deleteStorage(id);
		attributes.addFlashAttribute("message", "删除成功！！");
		return "redirect:/storage/list";
	}
	
	
	/**
	 * 编辑修改数据
	 * @param storage
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.PUT)
	public String updateStorage(@RequestParam("incrementCount") Integer incrementCount ,
			Storage storage,RedirectAttributes attributes){
		storage.setStockCount(storage.getStockCount()+incrementCount);
		storageService.updateStorage(storage);
		attributes.addFlashAttribute("message", "编辑修改成功");
		return "redirect:/storage/list";
	}
	
	/**
	 * 进入编辑页面，回显
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/create/{id}",method=RequestMethod.GET)
	public String toInput(@PathVariable("id") long id,Map<String,Object> map){
		map.put("products", storageService.getProducts());
		map.put("storage", storageService.getById(id));
		return "storage/input";
	}
	
	/**
	 * 添加元素
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String saveStorage(Storage storage,RedirectAttributes attributes){
		storageService.saveProduct(storage);
		attributes.addFlashAttribute("message", "保存成功！！");
		return "redirect:/storage/list";
	}
	
	
	/**
	 * 进入添加页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String saveInput(Map<String,Object> map){
		map.put("products", storageService.getProducts());
		map.put("storage", new Storage());
		return "storage/input";
	}

	
	/**
	 * 显示storage页面集合
	 * @param pageNoStr
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/list")
	public String listStorage(@RequestParam(value="pageNo",required=false) String pageNoStr,
			HttpServletRequest request,
			Map<String,Object> map){
		//获取页码
		int pageNo=1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String storage = DataUtils.encodeParamsToQueryString(params);
		map.put("storage", storage);
		Page<Storage> page = storageService.getPage(pageNo, params);
		map.put("page", page);
		
		
		return "storage/list";
	}
	
}
