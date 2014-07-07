/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.project.web;

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
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;
import com.thinkgem.jeesite.modules.project.service.ProjectInfoService;

/**
 * 项目信息Controller
 * @author Cloudman
 * @version 2014-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/project")
public class ProjectInfoController extends BaseController {

	@Autowired
	private ProjectInfoService projectInfoService;
	
	@ModelAttribute
	public ProjectInfo get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return projectInfoService.get(id);
		}else{
			return new ProjectInfo();
		}
	}
	
	@RequiresPermissions("project:projectInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectInfo.setCreateBy(user);
		}
        Page<ProjectInfo> page = projectInfoService.find(new Page<ProjectInfo>(request, response), projectInfo); 
        model.addAttribute("page", page);
		return "project/projectInfoList";
	}

	@RequiresPermissions("project:projectInfo:view")
	@RequestMapping(value = "record")
	public String form(ProjectInfo projectInfo, Model model) {
		model.addAttribute("projectInfo", projectInfo);
		return "modules/project/projectForm";
	}

	@RequiresPermissions("project:projectInfo:edit")
	@RequestMapping(value = "save")
	public String save(ProjectInfo projectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectInfo)){
			return form(projectInfo, model);
		}
		projectInfoService.save(projectInfo);
		addMessage(redirectAttributes, "保存项目信息'" + projectInfo.getPrjName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"modules/project/recordNote";
	}
	
	@RequiresPermissions("project:projectInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		projectInfoService.delete(id);
		addMessage(redirectAttributes, "删除项目信息成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectInfo/?repage";
	}

}
