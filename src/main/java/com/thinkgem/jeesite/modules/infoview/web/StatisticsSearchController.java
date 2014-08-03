/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.infoview.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.infoview.entity.StatisticsSearch;
import com.thinkgem.jeesite.modules.infoview.service.StatisticsSearchService;

/**
 * 统计查询模块Controller
 * @author Cloudman
 * @version 2014-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/infoview/statisticsSearch")
public class StatisticsSearchController extends BaseController {

	@Autowired
	private StatisticsSearchService statisticsSearchService;
	
	@ModelAttribute
	public StatisticsSearch get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return statisticsSearchService.get(id);
		}else{
			return new StatisticsSearch();
		}
	}
	
	@RequiresPermissions("infoview:statisticsSearch:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsSearch statisticsSearch, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			statisticsSearch.setCreateBy(user);
		}
        Page<StatisticsSearch> page = statisticsSearchService.find(new Page<StatisticsSearch>(request, response), statisticsSearch); 
        model.addAttribute("page", page);
		return "infoview/statisticsSearchList";
	}

	@RequiresPermissions("infoview:statisticsSearch:view")
	@RequestMapping(value = "form")
	public String form(StatisticsSearch statisticsSearch, Model model) {
		model.addAttribute("statisticsSearch", statisticsSearch);
		return "infoview/statisticsSearchForm";
	}

	@RequiresPermissions("infoview:statisticsSearch:edit")
	@RequestMapping(value = "save")
	public String save(StatisticsSearch statisticsSearch, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, statisticsSearch)){
			return form(statisticsSearch, model);
		}
		statisticsSearchService.save(statisticsSearch);
		addMessage(redirectAttributes, "保存统计查询模块'" + statisticsSearch.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/infoview/statisticsSearch/?repage";
	}
	
	@RequiresPermissions("infoview:statisticsSearch:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		statisticsSearchService.delete(id);
		addMessage(redirectAttributes, "删除统计查询模块成功");
		return "redirect:"+Global.getAdminPath()+"/infoview/statisticsSearch/?repage";
	}

}
