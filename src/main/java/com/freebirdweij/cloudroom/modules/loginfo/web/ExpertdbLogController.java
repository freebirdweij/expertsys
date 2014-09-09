/**
 * There are <a href="https://github.com/freebirdweij/cloudroom">CloudRoom</a> code generation
 */
package com.freebirdweij.cloudroom.modules.loginfo.web;

import java.sql.Timestamp;
import java.util.Date;

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
import com.freebirdweij.cloudroom.common.utils.Constants;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.common.web.BaseController;
import com.freebirdweij.cloudroom.modules.expfetch.entity.ProjectExpert;
import com.freebirdweij.cloudroom.modules.loginfo.entity.ExpertdbLog;
import com.freebirdweij.cloudroom.modules.loginfo.service.ExpertdbLogService;
import com.freebirdweij.cloudroom.modules.sys.entity.User;
import com.freebirdweij.cloudroom.modules.sys.utils.UserUtils;

/**
 * 日志处理模块Controller
 * @author Cloudman
 * @version 2014-08-25
 */
@Controller
@RequestMapping(value = "${adminPath}/loginfo")
public class ExpertdbLogController extends BaseController {

	@Autowired
	private ExpertdbLogService expertdbLogService;
	
	@ModelAttribute
	public ExpertdbLog get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return expertdbLogService.get(id);
		}else{
			return new ExpertdbLog();
		}
	}
	
	@RequiresPermissions("loginfo:expertdbLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExpertdbLog expertdbLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertdbLog.setCreateBy(user);
		}
        Page<ExpertdbLog> page = expertdbLogService.find(new Page<ExpertdbLog>(request, response), expertdbLog); 
        model.addAttribute("page", page);
		return "loginfo/expertdbLogList";
	}

	@RequestMapping(value = {"fetchLog", ""})
	public String fetchLog(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		/*if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}*/
		if(projectExpert!=null&&projectExpert.getReviewBegin()==null){
			projectExpert.setReviewBegin(new Timestamp((new Date()).getTime()));
			projectExpert.setReviewEnd(new Timestamp((new Date()).getTime()));			
		}else{
	        projectExpert.setReviewBegin(new Timestamp(projectExpert.getReviewBegin().getTime()));
	        projectExpert.setReviewEnd(new Timestamp(projectExpert.getReviewEnd().getTime()));
		}
        Page<ProjectExpert> page = projectExpertService.find(new Page<ProjectExpert>(request, response), projectExpert); 
        model.addAttribute("page", page);
        model.addAttribute("projectExpert", projectExpert);
		return "modules/loginfo/fetchLogList";
	}

	@RequiresPermissions("loginfo:expertdbLog:view")
	@RequestMapping(value = {"expertLog", ""})
	public String expertLog(ExpertdbLog expertdbLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertdbLog.setCreateBy(user);
		}
		if(expertdbLog!=null&&expertdbLog.getLogBegin()==null){
			expertdbLog.setLogBegin(new Timestamp((new Date()).getTime()));
			expertdbLog.setLogEnd(new Timestamp((new Date()).getTime()));			
		}else{
			expertdbLog.setLogBegin(new Timestamp(expertdbLog.getLogBegin().getTime()));
			expertdbLog.setLogEnd(new Timestamp(expertdbLog.getLogEnd().getTime()));
		}
		expertdbLog.setObjectType(Constants.Log_Type_Expert);
        Page<ExpertdbLog> page = expertdbLogService.find(new Page<ExpertdbLog>(request, response), expertdbLog); 
        model.addAttribute("page", page);
        model.addAttribute("expertdbLog", expertdbLog);
		return "modules/loginfo/expertLogList";
	}

	@RequiresPermissions("loginfo:expertdbLog:view")
	@RequestMapping(value = {"projectLog", ""})
	public String projectLog(ExpertdbLog expertdbLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertdbLog.setCreateBy(user);
		}
		if(expertdbLog!=null&&expertdbLog.getLogBegin()==null){
			expertdbLog.setLogBegin(new Timestamp((new Date()).getTime()));
			expertdbLog.setLogEnd(new Timestamp((new Date()).getTime()));			
		}else{
			expertdbLog.setLogBegin(new Timestamp(expertdbLog.getLogBegin().getTime()));
			expertdbLog.setLogEnd(new Timestamp(expertdbLog.getLogEnd().getTime()));
		}
		expertdbLog.setObjectType(Constants.Log_Type_Project);
        Page<ExpertdbLog> page = expertdbLogService.find(new Page<ExpertdbLog>(request, response), expertdbLog); 
        model.addAttribute("page", page);
        model.addAttribute("expertdbLog", expertdbLog);
		return "modules/loginfo/projectLogList";
	}

	@RequiresPermissions("loginfo:expertdbLog:view")
	@RequestMapping(value = "form")
	public String form(ExpertdbLog expertdbLog, Model model) {
		model.addAttribute("expertdbLog", expertdbLog);
		return "loginfo/expertdbLogForm";
	}

	@RequiresPermissions("loginfo:expertdbLog:edit")
	@RequestMapping(value = "save")
	public String save(ExpertdbLog expertdbLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertdbLog)){
			return form(expertdbLog, model);
		}
		expertdbLogService.save(expertdbLog);
		addMessage(redirectAttributes, "保存日志处理模块'" + expertdbLog.getOperation() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/loginfo/expertdbLog/?repage";
	}
	
	@RequiresPermissions("loginfo:expertdbLog:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		expertdbLogService.delete(id);
		addMessage(redirectAttributes, "删除日志处理模块成功");
		return "redirect:"+Global.getAdminPath()+"/loginfo/expertdbLog/?repage";
	}

}
