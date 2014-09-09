/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.freebirdweij.cloudroom.modules.experts.web;

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

import com.freebirdweij.cloudroom.common.config.Global;
import com.freebirdweij.cloudroom.common.persistence.Page;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.common.web.BaseController;
import com.freebirdweij.cloudroom.modules.experts.entity.ExpertAttach;
import com.freebirdweij.cloudroom.modules.experts.service.ExpertAttachService;
import com.freebirdweij.cloudroom.modules.sys.entity.User;
import com.freebirdweij.cloudroom.modules.sys.utils.UserUtils;

/**
 * 专家Controller
 * @author Cloudman
 * @version 2014-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/experts/expertAttach")
public class ExpertAttachController extends BaseController {

	@Autowired
	private ExpertAttachService expertAttachService;
	
	@ModelAttribute
	public ExpertAttach get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return expertAttachService.get(id);
		}else{
			return new ExpertAttach();
		}
	}
	
	@RequiresPermissions("experts:expertAttach:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExpertAttach expertAttach, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertAttach.setCreateBy(user);
		}
        Page<ExpertAttach> page = expertAttachService.find(new Page<ExpertAttach>(request, response), expertAttach); 
        model.addAttribute("page", page);
		return "experts/expertAttachList";
	}

	@RequiresPermissions("experts:expertAttach:view")
	@RequestMapping(value = "form")
	public String form(ExpertAttach expertAttach, Model model) {
		model.addAttribute("expertAttach", expertAttach);
		return "experts/expertAttachForm";
	}

	@RequiresPermissions("experts:expertAttach:edit")
	@RequestMapping(value = "save")
	public String save(ExpertAttach expertAttach, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertAttach)){
			return form(expertAttach, model);
		}
		expertAttachService.save(expertAttach);
		addMessage(redirectAttributes, "保存专家'" + expertAttach.getAttachName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/experts/expertAttach/?repage";
	}
	
	@RequiresPermissions("experts:expertAttach:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		expertAttachService.delete(id);
		addMessage(redirectAttributes, "删除专家成功");
		return "redirect:"+Global.getAdminPath()+"/experts/expertAttach/?repage";
	}

}
