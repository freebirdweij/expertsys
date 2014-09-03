/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.project.web;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.loginfo.entity.ExpertdbLog;
import com.thinkgem.jeesite.modules.loginfo.service.ExpertdbLogService;
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
	
	@Autowired
	private ExpertdbLogService expertdbLogService;
	
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
		return "modules/project/projectList";
	}

	@RequiresPermissions("project:projectInfo:view")
	@RequestMapping(value = "record")
	public String record(ProjectInfo projectInfo, Model model) {
		User user = UserUtils.getUser();
		projectInfo.setUnit(user.getCompany());
		model.addAttribute("projectInfo", projectInfo);
		return "modules/project/recordOne";
	}

	@RequiresPermissions("project:projectInfo:edit")
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,ProjectInfo projectInfo, Model model) {
		String id = request.getParameter("id");
		projectInfo = get(id);
		if(projectInfo.getPrjStatus()==null||projectInfo.getPrjStatus().equalsIgnoreCase("")){
			projectInfo.setPrjStatus(Constants.Project_Status_Start);
			projectInfoService.save(projectInfo);
			
		}
		ProjectInfo pinfo = null;
		try {
			pinfo = (ProjectInfo) BeanUtils.cloneBean(projectInfo);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("pinfo", pinfo);//修改比较用
		model.addAttribute("projectInfo", projectInfo);
		return "modules/project/projectForm";
	}

	@RequiresPermissions("project:projectInfo:edit")
	@RequestMapping(value = "info")
	public String info(HttpServletRequest request,ProjectInfo projectInfo, Model model) {
		String id = request.getParameter("id");
		projectInfo = get(id);
		if(projectInfo.getPrjStatus()==null||projectInfo.getPrjStatus().equalsIgnoreCase("")){
			projectInfo.setPrjStatus(Constants.Project_Status_Start);
			projectInfoService.save(projectInfo);
			
		}
		
		model.addAttribute("projectInfo", projectInfo);
		return "modules/project/projectInfo";
	}

	@RequiresPermissions("project:projectInfo:edit")
	@RequestMapping(value = "save", method=RequestMethod.POST)
	public String save(HttpServletRequest request,ProjectInfo projectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectInfo)){
			return record(projectInfo, model);
		}
		User user = UserUtils.getUser();
		//日志处理
		ProjectInfo pinfo = (ProjectInfo) request.getSession().getAttribute("pinfo");//修改比较用
		ExpertdbLog expertdbLog = LogUtils.getLogByCompareProject(pinfo, projectInfo, user);
		//expertdbLog.setObjectId(expertConfirm.getId());
		expertdbLogService.save(expertdbLog);
		request.getSession().removeAttribute("pinfo");
		projectInfoService.save(projectInfo);
		addMessage(redirectAttributes, "保存项目信息'" + projectInfo.getPrjName() + "'成功");
		return "modules/project/recordNote";
	}
	
	@RequiresPermissions("project:projectInfo:edit")
	@RequestMapping(value = "saveone", method=RequestMethod.POST)
	public String saveone(ProjectInfo projectInfo, Model model, RedirectAttributes redirectAttributes) {
		ProjectInfo pi = projectInfo;
		if (!beanValidator(model, projectInfo)){
			return record(projectInfo, model);
		}
		User user = UserUtils.getUser();
		//系统生成项目编号
		BigDecimal seq = projectInfoService.selectProjectSequence();
		String pcode = user.getCompany().getCode()+"［"+projectInfo.getPrjYear()+"］"+seq;
		projectInfo.setId(pcode);
		projectInfo.setPrjStatus(Constants.Project_Status_Start);
		projectInfoService.save(projectInfo);
		addMessage(redirectAttributes, "保存项目信息'" + projectInfo.getPrjName() + "'成功");
		
		ExpertdbLog expertdbLog = LogUtils.getLogByProject(projectInfo,user);
		if(expertdbLog!=null){
			//expertdbLog.setObjectId(ecode);
			StringBuffer strb = new StringBuffer();
			strb.append(Constants.Log_Function_ProjectAdd).append("新增了一个项目,").append(Constants.Log_Project_Name).append(projectInfo.getPrjName()).append(",")
			.append(Constants.Log_Operater_Name).append(user.getName()).append(".");
			String operation = strb.toString();
			expertdbLog.setOperation(operation);
			expertdbLogService.save(expertdbLog);
		}
		return "modules/project/recordNote";
	}
	
	@RequiresPermissions("project:projectInfo:edit")
	@RequestMapping(value = "savetwo", method=RequestMethod.POST)
	public String savetwo(ProjectInfo projectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectInfo)){
			return record(projectInfo, model);
		}
		projectInfoService.updateRecordTwo(projectInfo);
		addMessage(redirectAttributes, "保存项目信息'" + projectInfo.getPrjName() + "'成功");
		return "modules/project/recordNote";
	}
	
	@RequiresPermissions("project:projectInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		ProjectInfo projectInfo = projectInfoService.get(id);
		projectInfoService.delete(id);
		addMessage(redirectAttributes, "删除项目信息成功");
		User user = UserUtils.getUser();
		
		ExpertdbLog expertdbLog = LogUtils.getLogByProject(projectInfo,user);
		if(expertdbLog!=null){
			//expertdbLog.setObjectId(ecode);
			StringBuffer strb = new StringBuffer();
			strb.append(Constants.Log_Function_ProjectDel).append("删除了一个项目,").append(Constants.Log_Project_Name).append(projectInfo.getPrjName()).append(",")
			.append(Constants.Log_Operater_Name).append(user.getName()).append(".");
			String operation = strb.toString();
			expertdbLog.setOperation(operation);
			expertdbLogService.save(expertdbLog);
		}
		return "redirect:"+Global.getAdminPath()+"/project/list/?repage";
	}

	@ResponseBody
	@RequiresPermissions("project:projectInfo:edit")
	@RequestMapping(value = "checkProjectID")
	public String checkProjectID(String oldProjectId, String prjCode) {
		if (prjCode !=null && prjCode.equals(oldProjectId)) {
			return "true";
		} else if (prjCode !=null && projectInfoService.get(prjCode) == null) {
			return "true";
		}
		return "false";
	}

}
