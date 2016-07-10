package com.atguigu.crm.handler;

import java.util.HashMap;
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

import com.atguigu.crm.entity.Dict;
import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.DictService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/dict")
public class dictHandler {

	@Autowired
	private DictService dictService;
	
	/**
	 * 进入dict/list界面
	 * @param pageNoStr
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/list")
	public String dictList(@RequestParam(value="pageNo", required=false) String pageNoStr,
			Map<String,Object> map,
			HttpServletRequest request){
		//获取页码
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String dict = DataUtils.encodeParamsToQueryString(params);
		map.put("dict", dict);
		Page<Dict> page = dictService.getPage(pageNo, params);
		map.put("page", page);
	
		return "dict/list";
	}
	
	/**
	 * 进入input页面回显
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/create/{id}",method=RequestMethod.GET)
	public String input(@PathVariable("id") Long id,Map<String,Object> map){
		map.put("dict", dictService.getById(id));
		return "dict/input";
	}
	
	
	/**
	 * 编辑dict
	 * @param dict
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.PUT)
	public String updateDict(Dict dict,
			RedirectAttributes attributes){
		dictService.updateDict(dict);
		attributes.addFlashAttribute("message","编辑操作成功！！！");
		return "redirect:/dict/list";
	}
	
	/**
	 * 删除指定dict
	 * @param dictId
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/delete/{dictId}",method=RequestMethod.DELETE)
	public String deleteDict(@PathVariable("dictId") Long dictId,
			RedirectAttributes attributes){
		dictService.deleteDict(dictId);
		attributes.addFlashAttribute("message", "删除成功！！！");
		return "redirect:/dict/list";
		
	}
	
	/**
	 * 进入添加页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String input(Map<String,Object> map){
		map.put("dict", new Dict());
		return "dict/input";
	}
	
	/**
	 * 保存数据
	 * @param dict
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String toInput(Dict dict,RedirectAttributes attributes){
		dictService.saveDict(dict);
		attributes.addFlashAttribute("message", "保存操作成功！！");
		return "redirect:/dict/list";
	}
	
}
