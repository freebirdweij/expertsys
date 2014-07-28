/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.experts.web;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;
import com.thinkgem.jeesite.modules.project.service.ProjectInfoService;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.expfetch.entity.ProjectExpert;
import com.thinkgem.jeesite.modules.expfetch.service.ProjectExpertService;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
import com.thinkgem.jeesite.modules.expmanage.service.ExpertConfirmService;

/**
 * 对项目进行专家抽取Controller
 * @author Cloudman
 * @version 2014-07-12
 */
@Controller
@RequestMapping(value = "${adminPath}/experts/job")
public class ExpertJobController extends BaseController {

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private ProjectInfoService projectInfoService;
	
	@Autowired
	private ProjectExpertService projectExpertService;
	
	@Autowired
	private ExpertConfirmService expertConfirmService;
	
	@ModelAttribute
	public ProjectExpert get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return projectExpertService.get(id);
		}else{
			return new ProjectExpert();
		}
	}
	
	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
        Page<ProjectExpert> page = projectExpertService.find(new Page<ProjectExpert>(request, response), projectExpert); 
        model.addAttribute("page", page);
		return "expfetch/projectExpertList";
	}


	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"myjoblist", ""})
	public String myjoblist(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
        List<ProjectExpert> list = projectExpertService.findMyJob(new Page<ProjectExpert>(request, response), projectExpert); 
        model.addAttribute("list", list);
		return "modules/expfetch/reviewingList";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"myleave", ""})
	public String myLeave(ExpertConfirm expertConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertConfirm.setCreateBy(user);
		}
		if(expertConfirm==null||expertConfirm.getExpertLevel()==null){
			expertConfirm = new ExpertConfirm();
	        List<ExpertConfirm> list = expertConfirmService.findAExpert(user.getId());
	        if(list==null||list.size()==0){
	        	expertConfirm.setExpertLevel("2");//表示不存在
	        }else{
	        	expertConfirm.setExpertLevel(list.get(0).getExpertLevel());
	        }
		}else if(expertConfirm.getExpertLevel().equalsIgnoreCase("0")){
			expertConfirmService.updateExpertLevel(expertConfirm.getExpertLevel(),user.getId());
		}else if(expertConfirm.getExpertLevel().equalsIgnoreCase("1")){
			expertConfirmService.updateExpertLevel(expertConfirm.getExpertLevel(),user.getId());
		}
        
        model.addAttribute("expertConfirm", expertConfirm);
		return "modules/experts/myLeave";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"acceptinglist", ""})
	public String acceptinglist(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectInfo.setCreateBy(user);
		}
        Page<ProjectInfo> page = projectInfoService.findAccepting(new Page<ProjectInfo>(request, response), projectInfo); 
        model.addAttribute("page", page);
		return "modules/expfetch/acceptingList";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = "form")
	public String form(ProjectExpert projectExpert, Model model) {
		model.addAttribute("projectExpert", projectExpert);
		return "expfetch/projectExpertForm";
	}

	
	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = "unitmethod")
	public String unitmethod(ProjectExpert projectExpert, Model model) {
		model.addAttribute("projectExpert", projectExpert);
		model.addAttribute("areaList", areaService.findAll());
		model.addAttribute("unitList", officeService.findAll());
		model.addAttribute("kindList", DictUtils.getDictList("sys_specialkind_type"));
		model.addAttribute("specialList",  DictUtils.getDictList("sys_special_type"));
		model.addAttribute("seriesList",  DictUtils.getDictList("sys_series_type"));
		return "modules/expfetch/unitMethodForm";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = "expertmethod")
	public String expertmethod(ProjectExpert projectExpert, Model model) {
		model.addAttribute("projectExpert", projectExpert);
		model.addAttribute("areaList", areaService.findAll());
		model.addAttribute("unitList", officeService.findAll());
		model.addAttribute("kindList",  DictUtils.getDictList("sys_specialkind_type"));
		model.addAttribute("specialList",  DictUtils.getDictList("sys_special_type"));
		model.addAttribute("seriesList",  DictUtils.getDictList("sys_series_type"));
		model.addAttribute("techList",  DictUtils.getDictList("sys_tech_type"));
		return "modules/expfetch/expMethodForm";
	}

	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "receiveunitresult")
	public String receiveunitresult(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		int fcount = pExpert.getFetchTime()+1;
		projectExpert.setFetchTime(fcount);
		String resIds = projectExpert.getResIds();
		  String[] ids = StringUtils.split(resIds, ",");
	    for (String id : ids) {
	    	projectExpert.getExpertExpertConfirm().setId(id);
	    	projectExpert.setFetchMethod("1");
	    	//本次抽取状态标志。重要
	    	projectExpert.setFetchStatus("1");
			projectExpertService.save(projectExpert);
	    }
	    request.getSession().removeAttribute("projectExpert");
		addMessage(redirectAttributes, "保存对项目进行专家抽取'" + projectExpert.getPrjProjectInfo().getPrjName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/expfetch/reviewinglist/?repage";
	}
	
	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "receiveexpertresult")
	public String receiveexpertresult(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		int fcount = pExpert.getFetchTime()+1;
		projectExpert.setFetchTime(fcount);
		String resIds = projectExpert.getResIds();
		  String[] ids = StringUtils.split(resIds, ",");
	    for (String id : ids) {
	    	projectExpert.getExpertExpertConfirm().setId(id);
	    	projectExpert.setFetchMethod("1");
	    	//本次抽取状态标志。重要
	    	projectExpert.setFetchStatus("1");
			projectExpertService.save(projectExpert);
	    }
	    request.getSession().removeAttribute("projectExpert");
		addMessage(redirectAttributes, "保存对项目进行专家抽取'" + projectExpert.getPrjProjectInfo().getPrjName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/expfetch/reviewinglist/?repage";
	}
	
	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "cancelunitresult")
	public String cancelunitresult(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		int fcount = pExpert.getFetchTime()+1;
		projectExpert.setFetchTime(fcount);
		String resIds = projectExpert.getResIds();
		  String[] ids = StringUtils.split(resIds, ",");
	    for (String id : ids) {
	    	projectExpert.getExpertExpertConfirm().setId(id);
	    	projectExpert.setFetchMethod("1");
	    	//本次抽取状态标志。重要
	    	projectExpert.setFetchStatus("0");
			projectExpertService.save(projectExpert);
	    }
	    request.getSession().removeAttribute("projectExpert");
		addMessage(redirectAttributes, "保存对项目进行专家抽取'" + projectExpert.getPrjProjectInfo().getPrjName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/expfetch/unitfetch/?repage";
	}
	
	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "cancelexpertresult")
	public String cancelexpertresult(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		int fcount = pExpert.getFetchTime()+1;
		projectExpert.setFetchTime(fcount);
		String resIds = projectExpert.getResIds();
		  String[] ids = StringUtils.split(resIds, ",");
	    for (String id : ids) {
	    	projectExpert.getExpertExpertConfirm().setId(id);
	    	projectExpert.setFetchMethod("1");
	    	//本次抽取状态标志。重要
	    	projectExpert.setFetchStatus("0");
			projectExpertService.save(projectExpert);
	    }
	    request.getSession().removeAttribute("projectExpert");
		addMessage(redirectAttributes, "保存对项目进行专家抽取'" + projectExpert.getPrjProjectInfo().getPrjName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/expfetch/expertfetch/?repage";
	}
	
	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "save")
	public String save(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		projectExpertService.save(projectExpert);
		addMessage(redirectAttributes, "保存对项目进行专家抽取'" + projectExpert.getPrjProjectInfo().getPrjName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/expfetch/projectExpert/?repage";
	}
	
	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		projectExpertService.delete(id);
		addMessage(redirectAttributes, "删除对项目进行专家抽取成功");
		return "redirect:"+Global.getAdminPath()+"/expfetch/projectExpert/?repage";
	}

}
