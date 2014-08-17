/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.experts.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.experts.service.ExpertInfoService;

/**
 * 专家信息维护Controller
 * @author Cloudman
 * @version 2014-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/experts")
public class ExpertInfoController extends BaseController {

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
	
	@RequestMapping(value = "baseinfo")
	public String baseinfo(ExpertInfo expertInfo, Model model) {
		User user = UserUtils.getUser();
			expertInfo = expertInfoService.get(user.getId());			
		if(expertInfo==null||expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_First)||expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_Second)){
			return "modules/experts/editNote";
		}
			model.addAttribute("expertInfo", expertInfo);
		return "modules/experts/baseInfo";
	}
	
	@RequestMapping(value = "binfo")
	public String binfo(ExpertInfo expertInfo, HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
			expertInfo = expertInfoService.get(id);			
		if(expertInfo==null||expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_First)||expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_Second)){
			return "modules/experts/editNote";
		}
		model.addAttribute("expertInfo", expertInfo);
		model.addAttribute("id", id);
		return "modules/experts/bInfo";
	}
	
	@RequestMapping(value = "workinfo")
	public String workinfo(ExpertInfo expertInfo, Model model) {
		User user = UserUtils.getUser();
		expertInfo = expertInfoService.get(user.getId());
		if(expertInfo==null||expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_First)||expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_Second)){
			return "modules/experts/editNote";
		}
			model.addAttribute("expertInfo", expertInfo);
		return "modules/experts/workInfo";
	}
	
	@RequestMapping(value = "winfo")
	public String winfo(ExpertInfo expertInfo, HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		expertInfo = expertInfoService.get(id);			
			model.addAttribute("expertInfo", expertInfo);
			model.addAttribute("id", id);
		return "modules/experts/wInfo";
	}
	
	@RequestMapping(value = "applyinfo")
	public String applyinfo(ExpertInfo expertInfo, Model model) {
		User user = UserUtils.getUser();
		expertInfo = expertInfoService.get(user.getId());
		if(expertInfo==null||expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_First)||expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_Second)){
			return "modules/experts/editNote";
		}
			model.addAttribute("expertInfo", expertInfo);
		return "modules/experts/applyInfo";
	}
	
	@RequestMapping(value = "ainfo")
	public String ainfo(ExpertInfo expertInfo, HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		expertInfo = expertInfoService.get(id);			
			model.addAttribute("expertInfo", expertInfo);
			model.addAttribute("id", id);
		return "modules/experts/aInfo";
	}
	
	@RequestMapping(value = "baseform")
	public String baseform(ExpertInfo expertInfo, Model model) {
		User user = UserUtils.getUser();
		expertInfo = expertInfoService.get(user.getId());
		if(expertInfo==null){
			return "modules/experts/editNote";
		}
			model.addAttribute("expertInfo", expertInfo);
		return "modules/experts/baseForm";
	}
	
	@RequestMapping(value = "workform")
	public String workform(ExpertInfo expertInfo, Model model) {
		User user = UserUtils.getUser();
		expertInfo = expertInfoService.get(user.getId());
			model.addAttribute("expertInfo", expertInfo);
		return "modules/experts/workForm";
	}
	
	@RequestMapping(value = "applyform")
	public String applyform(ExpertInfo expertInfo, Model model) {
		User user = UserUtils.getUser();
		expertInfo = expertInfoService.get(user.getId());
			model.addAttribute("expertInfo", expertInfo);
		return "modules/experts/applyForm";
	}
	
	@RequiresPermissions("experts:expertInfo:view")
	@RequestMapping(value = "formm")
	public String formm(ExpertInfo expertInfo, Model model) {
		model.addAttribute("expertInfo", expertInfo);
		return "experts/expertInfoForm";
	}

	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "savebase", method=RequestMethod.POST)
	public String savebase(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes/*,@RequestParam("picture0") MultipartFile file*/) {
		User user = UserUtils.getUser();
		expertInfo.setUnit(user.getCompany());
		if (!beanValidator(model, expertInfo)){
			return formm(expertInfo, model);
		}
		
		/*try {
			expertInfo.setPicture(file.getBytes());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
		//保留注册状态
		//expertInfo.setRegStep("3");
		expertInfoService.save(expertInfo);
		expertInfo.setMobile(user.getMobile());
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/experts/baseform/?repage";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "savework", method=RequestMethod.POST)
	public String savework(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertInfo)){
			return formm(expertInfo, model);
		}
		
		
		//保留注册状态
		//expertInfo.setRegStep("3");
		
		expertInfoService.updateStepTwo(expertInfo);
		addMessage(redirectAttributes, "保存专家信息成功");
		return "redirect:"+Global.getAdminPath()+"/experts/workinfo/?repage";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "saveapply", method=RequestMethod.POST)
	public String saveapply(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertInfo)){
			return formm(expertInfo, model);
		}
		
		//保留注册状态
		//expertInfo.setRegStep("3");
				
		expertInfoService.updateStepThree(expertInfo);
		addMessage(redirectAttributes, "保存专家信息成功");
		return "modules/experts/applyInfo";
	}
	
	
}
