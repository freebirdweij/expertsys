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
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.experts.service.ExpertInfoService;

/**
 * 专家Controller
 * @author Cloudman
 * @version 2014-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/experts")
public class ExpertRegisterController extends BaseController {

	@Autowired
	private ExpertInfoService expertInfoService;
	
	@ModelAttribute
	public ExpertInfo get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return expertInfoService.get(id);
		}else{
			return new ExpertInfo();
		}
	}
	
	@RequiresPermissions("experts:expertInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExpertInfo expertInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertInfo.setCreateBy(user);
		}
        Page<ExpertInfo> page = expertInfoService.find(new Page<ExpertInfo>(request, response), expertInfo); 
        model.addAttribute("page", page);
		return "experts/expertInfoList";
	}

	@RequiresPermissions("experts:expertInfo:reg")
	@RequestMapping(value = "register")
	public String register(ExpertInfo expertInfo, Model model) {
		model.addAttribute("expertInfo", expertInfo);
		return "modules/experts/stepOne";
	}

	@RequiresPermissions("experts:expertInfo:view")
	@RequestMapping(value = "form")
	public String form(ExpertInfo expertInfo, Model model) {
		model.addAttribute("expertInfo", expertInfo);
		return "experts/expertInfoForm";
	}

	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "save")
	public String save(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertInfo)){
			return form(expertInfo, model);
		}
		expertInfoService.save(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/experts/expertInfo/?repage";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		expertInfoService.delete(id);
		addMessage(redirectAttributes, "删除专家成功");
		return "redirect:"+Global.getAdminPath()+"/experts/expertInfo/?repage";
	}

}
