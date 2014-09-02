/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.loginfo.web;

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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.loginfo.entity.ExpertdbLog;
import com.thinkgem.jeesite.modules.loginfo.service.ExpertdbLogService;

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

	@RequiresPermissions("loginfo:expertdbLog:view")
	@RequestMapping(value = {"expertLog", ""})
	public String expertLog(ExpertdbLog expertdbLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertdbLog.setCreateBy(user);
		}
		if(expertdbLog!=null||expertdbLog.getLogBegin()==null){
			expertdbLog.setLogBegin(new Timestamp((new Date()).getTime()));
			expertdbLog.setLogEnd(new Timestamp((new Date()).getTime()));			
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
		if(expertdbLog!=null||expertdbLog.getLogBegin()==null){
			expertdbLog.setLogBegin(new Timestamp((new Date()).getTime()));
			expertdbLog.setLogEnd(new Timestamp((new Date()).getTime()));			
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
