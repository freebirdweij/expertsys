/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.experts.web;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.experts.entity.Expert;
import com.thinkgem.jeesite.modules.experts.service.ExpertService;

/**
 * 专家Controller
 * @author Cloudman
 * @version 2014-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/experts/expert")
public class ExpertController extends BaseController {

	@Autowired
	private ExpertService expertService;
	
	@ModelAttribute
	public Expert get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return expertService.get(id);
		}else{
			return new Expert();
		}
	}
	
	@RequiresPermissions("experts:expert:view")
	@RequestMapping(value = {"list", ""})
	public String list(Expert expert, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expert.setCreateBy(user);
		}
        Page<Expert> page = expertService.find(new Page<Expert>(request, response), expert); 
        model.addAttribute("page", page);
		return "experts/expertList";
	}

	@RequiresPermissions("experts:expert:view")
	@RequestMapping(value = "form")
	public String form(Expert expert, Model model) {
		model.addAttribute("expert", expert);
		return "experts/expertForm";
	}

	@RequiresPermissions("experts:expert:edit")
	@RequestMapping(value = "save")
	public String save(Expert expert, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expert)){
			return form(expert, model);
		}
		expertService.save(expert);
		addMessage(redirectAttributes, "保存专家'" + expert.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/experts/expert/?repage";
	}
	
	@RequiresPermissions("experts:expert:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		expertService.delete(id);
		addMessage(redirectAttributes, "删除专家成功");
		return "redirect:"+Global.getAdminPath()+"/experts/expert/?repage";
	}

}
