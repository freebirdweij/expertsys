/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.freebirdweij.cloudroom.modules.supervise.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

import com.freebirdweij.cloudroom.common.config.Global;
import com.freebirdweij.cloudroom.common.persistence.Page;
import com.freebirdweij.cloudroom.common.utils.Constants;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.common.web.BaseController;
import com.freebirdweij.cloudroom.modules.expfetch.entity.ProjectExpert;
import com.freebirdweij.cloudroom.modules.expfetch.service.ProjectExpertService;
import com.freebirdweij.cloudroom.modules.expmanage.entity.ExpertConfirm;
import com.freebirdweij.cloudroom.modules.expmanage.service.ExpertConfirmService;
import com.freebirdweij.cloudroom.modules.project.entity.ProjectInfo;
import com.freebirdweij.cloudroom.modules.project.service.ProjectInfoService;
import com.freebirdweij.cloudroom.modules.supervise.entity.FetchSupervise;
import com.freebirdweij.cloudroom.modules.supervise.service.FetchSuperviseService;
import com.freebirdweij.cloudroom.modules.sys.entity.User;
import com.freebirdweij.cloudroom.modules.sys.utils.UserUtils;

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
	
	@Autowired
	private ExpertConfirmService expertConfirmService;
	
	@ModelAttribute
	public FetchSupervise get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return fetchSuperviseService.get(id);
		}else{
			return new FetchSupervise();
		}
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
		addMessage(redirectAttributes, "保存对项目抽取进行监督成功");
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
        Page<ProjectInfo> page = projectInfoService.findSuperviseProjects(new Page<ProjectInfo>(request, response), projectInfo); 
        model.addAttribute("page", page);
		return "modules/supervise/projectSearch";
	}

	@RequestMapping(value = {"expertsearch", ""})
	public String expertsearch(ExpertConfirm expertConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertConfirm.setCreateBy(user);
		}
        Page<ExpertConfirm> page = expertConfirmService.findSuperviseExperts(new Page<ExpertConfirm>(request, response), expertConfirm); 
        model.addAttribute("page", page);
		return "modules/supervise/expertSearch";
	}

	@RequestMapping(value = {"checkprojectfetch", ""})
	public String checkprojectfetch(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		String prjid =  request.getParameter("prjid");
		//获取评审中首抽有效的专家
		String status1[] = {Constants.Fetch_Review_Sussess};
        Page<ExpertConfirm> page1 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status1); 
        List<ExpertConfirm> rewlist = page1.getList();
		//获取评审中补抽有效的专家
		String status2[] = {Constants.Fetch_ReviewRedraw_Sussess};
        Page<ExpertConfirm> page2 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status2); 
        List<ExpertConfirm> rewrdlist = page2.getList();
		//获取评审中抽取无效的专家
		String status3[] = {Constants.Fetch_Review_Failure,Constants.Fetch_ReviewRedraw_Failure};
        Page<ExpertConfirm> page3 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status3); 
        List<ExpertConfirm> rewnlist = page3.getList();
		//获取验收中首抽有效的专家
		String status4[] = {Constants.Fetch_Accept_Sussess};
        Page<ExpertConfirm> page4 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status4); 
        List<ExpertConfirm> acptlist = page4.getList();
		//获取验收中补抽有效的专家
		String status5[] = {Constants.Fetch_AcceptRedraw_Sussess};
        Page<ExpertConfirm> page5 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status5); 
        List<ExpertConfirm> acptrdlist = page5.getList();
		//获取验收中抽取无效的专家
		String status6[] = {Constants.Fetch_Accept_Failure,Constants.Fetch_AcceptRedraw_Failure};
        Page<ExpertConfirm> page6 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status6); 
        List<ExpertConfirm> acptnlist = page6.getList();
        
        model.addAttribute("rewlist", rewlist);
        model.addAttribute("rewrdlist", rewrdlist);
        model.addAttribute("rewnlist", rewnlist);
        model.addAttribute("acptlist", acptlist);
        model.addAttribute("acptrdlist", acptrdlist);
        model.addAttribute("acptnlist",acptnlist);
		return "modules/supervise/projectFetchList";
	}

	@RequestMapping(value = {"checkexpertfetch", ""})
	public String checkexpertfetch(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		String expid =  request.getParameter("expid");
		//获取评审中首抽有效的项目
		String status1[] = {Constants.Fetch_Review_Sussess};
        Page<ProjectInfo> page1 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status1); 
        List<ProjectInfo> rewlist = page1.getList();
		//获取评审中补抽有效的项目
		String status2[] = {Constants.Fetch_ReviewRedraw_Sussess};
        Page<ProjectInfo> page2 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status2); 
        List<ProjectInfo> rewrdlist = page2.getList();
		//获取评审中抽取无效的项目
		String status3[] = {Constants.Fetch_Review_Failure,Constants.Fetch_ReviewRedraw_Failure};
        Page<ProjectInfo> page3 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status3); 
        List<ProjectInfo> rewnlist = page3.getList();
		//获取验收中首抽有效的项目
		String status4[] = {Constants.Fetch_Accept_Sussess};
        Page<ProjectInfo> page4 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status4); 
        List<ProjectInfo> acptlist = page4.getList();
		//获取验收中补抽有效的项目
		String status5[] = {Constants.Fetch_AcceptRedraw_Sussess};
        Page<ProjectInfo> page5 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status5); 
        List<ProjectInfo> acptrdlist = page5.getList();
		//获取验收中抽取无效的项目
		String status6[] = {Constants.Fetch_Accept_Failure,Constants.Fetch_AcceptRedraw_Failure};
        Page<ProjectInfo> page6 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status6); 
        List<ProjectInfo> acptnlist = page6.getList();
        
        model.addAttribute("rewlist", rewlist);
        model.addAttribute("rewrdlist", rewrdlist);
        model.addAttribute("rewnlist", rewnlist);
        model.addAttribute("acptlist", acptlist);
        model.addAttribute("acptrdlist", acptrdlist);
        model.addAttribute("acptnlist",acptnlist);
		return "modules/supervise/expertFetchList";
	}

	@RequestMapping(value = "statistics")
	public String statistics(FetchSupervise fetchSupervise, Model model) {
		model.addAttribute("fetchSupervise", fetchSupervise);
		return "modules/supervise/statisticsForm";
	}

	@RequestMapping(value = "statisticsexpert")
	public String statisticsexpert(FetchSupervise fetchSupervise, Model model) {
		Page<Object> page = fetchSuperviseService.findStatisticsExperts(new Page<Object>(), fetchSupervise);
        model.addAttribute("page", page);
		return "modules/supervise/statisticsList";
	}

	@RequestMapping(value = "statisticsunit")
	public String statisticsunit(FetchSupervise fetchSupervise, Model model) {
		Page<Object> page = fetchSuperviseService.findStatisticsUnits(new Page<Object>(), fetchSupervise);
        model.addAttribute("page", page);
		return "modules/supervise/statisticsList";
	}

	@RequestMapping(value = "statisticskind")
	public String statisticskind(FetchSupervise fetchSupervise, Model model) {
		Page<Object> page = fetchSuperviseService.findStatisticsKinds(new Page<Object>(), fetchSupervise);
        model.addAttribute("page", page);
        FetchSupervise fSupervise = new FetchSupervise();
        fSupervise.setSticsKind(fetchSupervise.getSticsKind());
        model.addAttribute("fetchSupervise", fSupervise);
		return "modules/supervise/statisticsList";
	}

	@RequestMapping(value = "statisticsfetch")
	public String statisticsfetch(FetchSupervise fetchSupervise, Model model) {
		Page<Object> page = fetchSuperviseService.findStatisticsFetchs(new Page<Object>(), fetchSupervise);
        model.addAttribute("page", page);
        FetchSupervise fSupervise = new FetchSupervise();
        fSupervise.setFetchKind(fetchSupervise.getFetchKind());
        model.addAttribute("fetchSupervise", fSupervise);
		return "modules/supervise/statisticsList";
	}

}
