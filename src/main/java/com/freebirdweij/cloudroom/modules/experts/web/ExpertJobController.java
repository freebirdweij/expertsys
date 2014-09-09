/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.freebirdweij.cloudroom.modules.experts.web;

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

import com.freebirdweij.cloudroom.common.config.Global;
import com.freebirdweij.cloudroom.common.persistence.Page;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.common.web.BaseController;
import com.freebirdweij.cloudroom.modules.expfetch.entity.ProjectExpert;
import com.freebirdweij.cloudroom.modules.expfetch.service.ProjectExpertService;
import com.freebirdweij.cloudroom.modules.expmanage.entity.ExpertConfirm;
import com.freebirdweij.cloudroom.modules.expmanage.service.ExpertConfirmService;
import com.freebirdweij.cloudroom.modules.project.entity.ProjectInfo;
import com.freebirdweij.cloudroom.modules.project.service.ProjectInfoService;
import com.freebirdweij.cloudroom.modules.sys.entity.Menu;
import com.freebirdweij.cloudroom.modules.sys.entity.Office;
import com.freebirdweij.cloudroom.modules.sys.entity.User;
import com.freebirdweij.cloudroom.modules.sys.service.AreaService;
import com.freebirdweij.cloudroom.modules.sys.service.OfficeService;
import com.freebirdweij.cloudroom.modules.sys.utils.DictUtils;
import com.freebirdweij.cloudroom.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

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
        List<ProjectExpert> list = projectExpertService.findMyJob(new Page<ProjectExpert>(request, response), user.getId()); 
        model.addAttribute("list", list);
		return "modules/experts/myNewJob";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"myHistory", ""})
	public String myHistory(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		Page<ProjectExpert> page = projectExpertService.findMyHistory(new Page<ProjectExpert>(request, response), user.getId()); 
        model.addAttribute("page", page);
		return "modules/experts/myHistoryJob";
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
	        	expertConfirm.setExpertLevel(list.get(0).getExpertLevel()==null?"0":list.get(0).getExpertLevel());
	        }
		}else if(expertConfirm.getExpertLevel().equalsIgnoreCase("0")){
			expertConfirmService.updateExpertLevel(expertConfirm.getExpertLevel(),user.getId());
		}else if(expertConfirm.getExpertLevel().equalsIgnoreCase("1")){
			expertConfirmService.updateExpertLevel(expertConfirm.getExpertLevel(),user.getId());
		}else{
			expertConfirm.setExpertLevel("0");
		}
        
        model.addAttribute("expertConfirm", expertConfirm);
		return "modules/experts/myLeave";
	}

	@RequiresPermissions("project:projectInfo:edit")
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,ProjectInfo projectInfo, Model model) {
		String id = request.getParameter("id");
		projectInfo = projectInfoService.get(id);
		model.addAttribute("projectInfo", projectInfo);
		return "modules/experts/projectForm";
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
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		projectExpertService.delete(id);
		addMessage(redirectAttributes, "删除对项目进行专家抽取成功");
		return "redirect:"+Global.getAdminPath()+"/expfetch/projectExpert/?repage";
	}

}
