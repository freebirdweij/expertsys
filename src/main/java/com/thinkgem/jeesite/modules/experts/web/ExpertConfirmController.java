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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.experts.entity.ExpertConfirm;
import com.thinkgem.jeesite.modules.experts.service.ExpertConfirmService;

/**
 * 专家Controller
 * @author Cloudman
 * @version 2014-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/experts/expertConfirm")
public class ExpertConfirmController extends BaseController {

	@Autowired
	private ExpertConfirmService expertConfirmService;
	
	@ModelAttribute
	public ExpertConfirm get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return expertConfirmService.get(id);
		}else{
			return new ExpertConfirm();
		}
	}
	
	@RequiresPermissions("experts:expertConfirm:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExpertConfirm expertConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertConfirm.setCreateBy(user);
		}
        Page<ExpertConfirm> page = expertConfirmService.find(new Page<ExpertConfirm>(request, response), expertConfirm); 
        model.addAttribute("page", page);
		return "experts/expertConfirmList";
	}

	@RequiresPermissions("experts:expertConfirm:view")
	@RequestMapping(value = "form")
	public String form(ExpertConfirm expertConfirm, Model model) {
		model.addAttribute("expertConfirm", expertConfirm);
		return "experts/expertConfirmForm";
	}

	@RequiresPermissions("experts:expertConfirm:edit")
	@RequestMapping(value = "save")
	public String save(ExpertConfirm expertConfirm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertConfirm)){
			return form(expertConfirm, model);
		}
		expertConfirmService.save(expertConfirm);
		addMessage(redirectAttributes, "保存专家'" + expertConfirm.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/experts/expertConfirm/?repage";
	}
	
	@RequiresPermissions("experts:expertConfirm:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		expertConfirmService.delete(id);
		addMessage(redirectAttributes, "删除专家成功");
		return "redirect:"+Global.getAdminPath()+"/experts/expertConfirm/?repage";
	}

}
