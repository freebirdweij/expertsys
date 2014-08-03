/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.supervise.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import com.thinkgem.jeesite.modules.expfetch.entity.ProjectExpert;
import com.thinkgem.jeesite.modules.expfetch.service.ProjectExpertService;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;
import com.thinkgem.jeesite.modules.project.service.ProjectInfoService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.supervise.entity.FetchSupervise;
import com.thinkgem.jeesite.modules.supervise.service.FetchSuperviseService;

/**
 * 对项目抽取进行监督Controller
 * @author Cloudman
 * @version 2014-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/supervise")
public class FetchSuperviseController extends BaseController {

	@Autowired
	private FetchSuperviseService fetchSuperviseService;
	
	@Autowired
	private ProjectInfoService projectInfoService;

	@Autowired
	private ProjectExpertService projectExpertService;
	
	@ModelAttribute
	public FetchSupervise get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return fetchSuperviseService.get(id);
		}else{
			return new FetchSupervise();
		}
	}
	
	@RequiresPermissions("supervise:fetchSupervise:view")
	@RequestMapping(value = {"list", ""})
	public String list(FetchSupervise fetchSupervise, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			fetchSupervise.setCreateBy(user);
		}
        Page<FetchSupervise> page = fetchSuperviseService.find(new Page<FetchSupervise>(request, response), fetchSupervise); 
        model.addAttribute("page", page);
		return "supervise/fetchSuperviseList";
	}

	@RequiresPermissions("supervise:fetchSupervise:view")
	@RequestMapping(value = "form")
	public String form(FetchSupervise fetchSupervise, Model model) {
		model.addAttribute("fetchSupervise", fetchSupervise);
		return "supervise/fetchSuperviseForm";
	}

	@RequiresPermissions("supervise:fetchSupervise:edit")
	@RequestMapping(value = "save")
	public String save(FetchSupervise fetchSupervise, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fetchSupervise)){
			return form(fetchSupervise, model);
		}
		fetchSuperviseService.save(fetchSupervise);
		addMessage(redirectAttributes, "保存对项目抽取进行监督'" + fetchSupervise.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/supervise/fetchSupervise/?repage";
	}
	
	@RequiresPermissions("supervise:fetchSupervise:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		fetchSuperviseService.delete(id);
		addMessage(redirectAttributes, "删除对项目抽取进行监督成功");
		return "redirect:"+Global.getAdminPath()+"/supervise/fetchSupervise/?repage";
	}

	@RequestMapping(value = {"reviewinglist", ""})
	public String reviewinglist(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectInfo.setCreateBy(user);
		}
        Page<ProjectInfo> page = projectInfoService.findSuperviseReviewing(new Page<ProjectInfo>(request, response), projectInfo); 
        model.addAttribute("page", page);
		return "modules/supervise/reviewingList";
	}

	@RequestMapping(value = {"acceptinglist", ""})
	public String acceptinglist(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectInfo.setCreateBy(user);
		}
        Page<ProjectInfo> page = projectInfoService.findSuperviseAccepting(new Page<ProjectInfo>(request, response), projectInfo); 
        model.addAttribute("page", page);
		return "modules/supervise/acceptingList";
	}

	@RequestMapping(value = {"checkreviewfetch", ""})
	public String checkreviewfetch(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		String prjid =  request.getParameter("prjid");
        Page<ExpertConfirm> page = projectExpertService.findReviewingExpertByProject(new Page<ExpertConfirm>(request, response), prjid); 
        model.addAttribute("rlist", page.getList());
		return "modules/supervise/checkExpertsList";
	}

	@RequestMapping(value = {"checkacceptfetch", ""})
	public String checkacceptfetch(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		String prjid =  request.getParameter("prjid");
        Page<ExpertConfirm> page = projectExpertService.findAcceptingExpertByProject(new Page<ExpertConfirm>(request, response), prjid); 
        model.addAttribute("rlist", page.getList());
		return "modules/supervise/checkExpertsList";
	}

	@RequestMapping(value = {"projectsearch", ""})
	public String projectsearch(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectInfo.setCreateBy(user);
		}
        Page<ProjectInfo> page = projectInfoService.findSuperviseReviewing(new Page<ProjectInfo>(request, response), projectInfo); 
        model.addAttribute("page", page);
		return "modules/supervise/reviewingList";
	}

}
