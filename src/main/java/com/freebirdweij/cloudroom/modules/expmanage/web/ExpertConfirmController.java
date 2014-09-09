/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.freebirdweij.cloudroom.modules.expmanage.web;

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
import com.freebirdweij.cloudroom.common.mapper.BeanMapper;
import com.freebirdweij.cloudroom.common.persistence.Page;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.common.web.BaseController;
import com.freebirdweij.cloudroom.modules.experts.entity.ExpertInfo;
import com.freebirdweij.cloudroom.modules.experts.service.ExpertInfoService;
import com.freebirdweij.cloudroom.modules.expmanage.entity.ExpertConfirm;
import com.freebirdweij.cloudroom.modules.expmanage.service.ExpertConfirmService;
import com.freebirdweij.cloudroom.modules.sys.entity.User;
import com.freebirdweij.cloudroom.modules.sys.utils.UserUtils;

/**
 * 专家确认Controller
 * @author Cloudman
 * @version 2014-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/expmanage")
public class ExpertConfirmController extends BaseController {

	@Autowired
	private ExpertConfirmService expertConfirmService;
	
	@Autowired
	private ExpertInfoService expertInfoService;
	
	@ModelAttribute
	public ExpertConfirm get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return expertConfirmService.get(id);
		}else{
			return new ExpertConfirm();
		}
	}
	
	@RequiresPermissions("expmanage:expertConfirm:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExpertConfirm expertConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertConfirm.setCreateBy(user);
		}
        Page<ExpertConfirm> page = expertConfirmService.find(new Page<ExpertConfirm>(request, response), expertConfirm); 
        model.addAttribute("page", page);
		return "expmanage/expertConfirmList";
	}

	@RequiresPermissions("expmanage:expertConfirm:view")
	@RequestMapping(value = "formm")
	public String formm(ExpertConfirm expertConfirm, Model model) {
		model.addAttribute("expertConfirm", expertConfirm);
		return "expmanage/expertConfirmForm";
	}

	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "verifyc")
	public String verifyc(ExpertConfirm expertConfirm, Model model,@ModelAttribute("expertInfo") ExpertInfo expertInfo, RedirectAttributes redirectAttributes) {
		if(expertInfo!=null){
			BeanMapper.copy(expertInfo, expertConfirm);
			expertConfirm.setExpertInfo(expertInfo);
		}
		model.addAttribute("expertConfirm", expertConfirm);
		return "modules/expmanage/expertVerify";
	}
	
	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "confirmc")
	public String confirmc(ExpertConfirm expertConfirm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertConfirm)){
			return formm(expertConfirm, model);
		}
		if(expertConfirm.getKindOne()!=null&&expertConfirm.getSpecialOne()!=null){
			expertConfirm.setId(expertConfirm.getExpertCode());
			expertConfirm.setExpertKind(expertConfirm.getKindOne());
			expertConfirm.setExpertSpecial(expertConfirm.getSpecialOne());
			expertConfirm.setExpertSeries(expertConfirm.getSeriesOne());
			expertConfirmService.save(expertConfirm);			
		}
		
		if(expertConfirm.getKindTwo()!=null&&expertConfirm.getSpecialTwo()!=null){
			expertConfirm.setId(expertConfirm.getExpertCode()+"-2");
			expertConfirm.setExpertKind(expertConfirm.getKindTwo());
			expertConfirm.setExpertSpecial(expertConfirm.getSpecialTwo());
			expertConfirm.setExpertSeries(expertConfirm.getSeriesTwo());
			expertConfirmService.save(expertConfirm);			
		}
		
		//已审核通过的专家记为4
		expertInfoService.updateRegStep("4", expertConfirm.getExpertInfo().getUserId());
		addMessage(redirectAttributes, "保存专家确认'" + expertConfirm.getExpertInfo().getName() + "'成功");
		return "modules/expmanage/confirmNote";
	}
	
	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "savec")
	public String savec(ExpertConfirm expertConfirm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertConfirm)){
			return formm(expertConfirm, model);
		}
		expertConfirmService.save(expertConfirm);
		addMessage(redirectAttributes, "保存专家确认'" + expertConfirm.getExpertInfo().getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/expmanage/expertConfirm/?repage";
	}
	
	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "deletec")
	public String deletec(String id, RedirectAttributes redirectAttributes) {
		expertConfirmService.delete(id);
		addMessage(redirectAttributes, "删除专家确认成功");
		return "redirect:"+Global.getAdminPath()+"/expmanage/expertConfirm/?repage";
	}

}
