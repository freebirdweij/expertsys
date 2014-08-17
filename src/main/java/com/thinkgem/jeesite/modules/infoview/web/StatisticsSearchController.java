/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.infoview.web;

import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;
import com.thinkgem.jeesite.modules.project.service.ProjectInfoService;
import com.thinkgem.jeesite.modules.supervise.entity.FetchSupervise;
import com.thinkgem.jeesite.modules.supervise.service.FetchSuperviseService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.expfetch.entity.ProjectExpert;
import com.thinkgem.jeesite.modules.expfetch.service.ProjectExpertService;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
import com.thinkgem.jeesite.modules.expmanage.service.ExpertConfirmService;
import com.thinkgem.jeesite.modules.infoview.entity.StatisticsSearch;
import com.thinkgem.jeesite.modules.infoview.service.StatisticsSearchService;

/**
 * 统计查询模块Controller
 * @author Cloudman
 * @version 2014-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/infoview")
public class StatisticsSearchController extends BaseController {

	@Autowired
	private StatisticsSearchService statisticsSearchService;
	
	@Autowired
	private FetchSuperviseService fetchSuperviseService;
	
	@Autowired
	private ProjectInfoService projectInfoService;

	@Autowired
	private ProjectExpertService projectExpertService;
	
	@Autowired
	private ExpertConfirmService expertConfirmService;
	@ModelAttribute
	public StatisticsSearch get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return statisticsSearchService.get(id);
		}else{
			return new StatisticsSearch();
		}
	}
	
	@RequiresPermissions("infoview:statisticsSearch:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsSearch statisticsSearch, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			statisticsSearch.setCreateBy(user);
		}
        Page<StatisticsSearch> page = statisticsSearchService.find(new Page<StatisticsSearch>(request, response), statisticsSearch); 
        model.addAttribute("page", page);
		return "infoview/statisticsSearchList";
	}

	@RequiresPermissions("infoview:statisticsSearch:view")
	@RequestMapping(value = "form")
	public String form(StatisticsSearch statisticsSearch, Model model) {
		model.addAttribute("statisticsSearch", statisticsSearch);
		return "infoview/statisticsSearchForm";
	}

	@RequiresPermissions("infoview:statisticsSearch:edit")
	@RequestMapping(value = "save")
	public String save(StatisticsSearch statisticsSearch, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, statisticsSearch)){
			return form(statisticsSearch, model);
		}
		statisticsSearchService.save(statisticsSearch);
		addMessage(redirectAttributes, "保存统计查询模块'" + statisticsSearch.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/infoview/statisticsSearch/?repage";
	}
	
	@RequiresPermissions("infoview:statisticsSearch:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		statisticsSearchService.delete(id);
		addMessage(redirectAttributes, "删除统计查询模块成功");
		return "redirect:"+Global.getAdminPath()+"/infoview/statisticsSearch/?repage";
	}

	@RequestMapping(value = {"projectsearchexp", ""})
	public String projectsearchexp(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectInfo.setCreateBy(user);
		}
        Page<ProjectInfo> page = projectInfoService.findSuperviseProjects(new Page<ProjectInfo>(request, response), projectInfo); 
        model.addAttribute("page", page);
		return "modules/infoview/projectSearchExport";
	}

	@RequestMapping(value = {"expertsearchexp", ""})
	public String expertsearchexp(ExpertConfirm expertConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertConfirm.setCreateBy(user);
		}
        Page<ExpertConfirm> page = expertConfirmService.findSuperviseExperts(new Page<ExpertConfirm>(request, response), expertConfirm); 
        model.addAttribute("page", page);
		return "modules/infoview/expertSearchExport";
	}

	@RequestMapping(value = {"checkprojectfetchexp", ""})
	public String checkprojectfetchexp(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
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
        model.addAttribute("prjid",prjid);
		return "modules/infoview/projectFetchExport";
	}

	@RequestMapping(value = {"checkexpertfetchexp", ""})
	public String checkexpertfetchexp(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
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
        model.addAttribute("expid",expid);
		return "modules/infoview/expertFetchExport";
	}

	@RequestMapping(value = "statistics")
	public String statistics(FetchSupervise fetchSupervise, Model model) {
		model.addAttribute("fetchSupervise", fetchSupervise);
		return "modules/infoview/statisticsForm";
	}

	@RequestMapping(value = "statisticsexpert")
	public String statisticsexpert(FetchSupervise fetchSupervise, Model model) {
		Page<Object> page = fetchSuperviseService.findStatisticsExperts(new Page<Object>(), fetchSupervise);
        model.addAttribute("page", page);
		return "modules/infoview/statisticsList";
	}

	@RequestMapping(value = "statisticsunit")
	public String statisticsunit(FetchSupervise fetchSupervise, Model model) {
		Page<Object> page = fetchSuperviseService.findStatisticsUnits(new Page<Object>(), fetchSupervise);
        model.addAttribute("page", page);
		return "modules/infoview/statisticsList";
	}

	@RequestMapping(value = "statisticskind")
	public String statisticskind(FetchSupervise fetchSupervise, Model model) {
		Page<Object> page = fetchSuperviseService.findStatisticsKinds(new Page<Object>(), fetchSupervise);
        model.addAttribute("page", page);
        FetchSupervise fSupervise = new FetchSupervise();
        fSupervise.setSticsKind(fetchSupervise.getSticsKind());
        model.addAttribute("fetchSupervise", fSupervise);
		return "modules/infoview/statisticsList";
	}

	@RequestMapping(value = "statisticsfetch")
	public String statisticsfetch(FetchSupervise fetchSupervise, Model model) {
		Page<Object> page = fetchSuperviseService.findStatisticsFetchs(new Page<Object>(), fetchSupervise);
        model.addAttribute("page", page);
        FetchSupervise fSupervise = new FetchSupervise();
        fSupervise.setFetchKind(fetchSupervise.getFetchKind());
        model.addAttribute("fetchSupervise", fSupervise);
		return "modules/infoview/statisticsList";
	}

	@RequestMapping(value = {"projectsearch", ""})
	public String projectsearch(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectInfo.setCreateBy(user);
		}
        Page<ProjectInfo> page = projectInfoService.findSuperviseProjects(new Page<ProjectInfo>(request, response), projectInfo); 
        model.addAttribute("page", page);
		return "modules/infoview/projectSearch";
	}

	@RequestMapping(value = {"expertsearch", ""})
	public String expertsearch(ExpertConfirm expertConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertConfirm.setCreateBy(user);
		}
        Page<ExpertConfirm> page = expertConfirmService.findSuperviseExperts(new Page<ExpertConfirm>(request, response), expertConfirm); 
        model.addAttribute("page", page);
		return "modules/infoview/expertSearch";
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
        model.addAttribute("prjid",prjid);
		return "modules/infoview/projectFetchList";
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
        model.addAttribute("expid",expid);
		return "modules/infoview/expertFetchList";
	}

    @RequestMapping(value = "exportExpertSearch", method=RequestMethod.POST)
    public String exportExpertSearch(ExpertConfirm expertConfirm, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "专家列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
            List<ExpertConfirm> rlist = expertConfirmService.findSuperviseExperts(new Page<ExpertConfirm>(request, response), expertConfirm).getList();
    		new ExportExcel("专家列表", ExpertConfirm.class).setDataList(rlist).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出专家失败！失败信息："+e.getMessage());
		}
		return "modules/infoview/expertSearch";
    }

    @RequestMapping(value = "exportProjectSearch", method=RequestMethod.POST)
    public String exportProjectSearch(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
            List<ProjectInfo> rlist = projectInfoService.findSuperviseProjects(new Page<ProjectInfo>(request, response), projectInfo).getList();
    		new ExportExcel("项目列表", ProjectInfo.class).setDataList(rlist).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出项目失败！失败信息："+e.getMessage());
		}
		return "modules/infoview/projectSearch";
    }

    @RequestMapping(value = "exportExpertFetch", method=RequestMethod.POST)
    public String exportExpertFetch(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
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
        
		try {
            String fileName = "项目列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
            List<ProjectInfo> alist = Lists.newArrayList();
            ExportExcel ee = new ExportExcel("项目列表", ProjectInfo.class);
            ProjectInfo pi1 = new ProjectInfo();
            pi1.setId("评审中首抽有效的项目");
            alist.add(pi1);
            alist.addAll(rewlist);
            ProjectInfo pi2 = new ProjectInfo();
            pi2.setId("评审中补抽有效的项目");
            alist.add(pi2);
            alist.addAll(rewrdlist);
            ProjectInfo pi3 = new ProjectInfo();
            pi3.setId("评审中抽取无效的项目");
            alist.add(pi3);
            alist.addAll(rewnlist);
            ProjectInfo pi4 = new ProjectInfo();
            pi4.setId("验收中首抽有效的项目");
            alist.add(pi4);
            alist.addAll(acptlist);
            ProjectInfo pi5 = new ProjectInfo();
            pi5.setId("评审中补抽有效的项目");
            alist.add(pi5);
            alist.addAll(acptrdlist);
            ProjectInfo pi6 = new ProjectInfo();
            pi6.setId("评审中补抽有效的项目");
            alist.add(pi6);
            alist.addAll(acptnlist);
            
            
            ee.setDataList(alist).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出项目失败！失败信息："+e.getMessage());
		}
		return "modules/infoview/expertFetchExport";
    }

    @RequestMapping(value = "exportProjectFetch", method=RequestMethod.POST)
    public String exportProjectFetch(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
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
        
		try {
            String fileName = "专家列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
            List<ExpertConfirm> alist = Lists.newArrayList();
            ExportExcel ee = new ExportExcel("专家列表", ExpertConfirm.class);
            ExpertConfirm pi1 = new ExpertConfirm();
            pi1.setId("评审中首抽有效的专家");
            alist.add(pi1);
            alist.addAll(rewlist);
            ExpertConfirm pi2 = new ExpertConfirm();
            pi2.setId("评审中补抽有效的专家");
            alist.add(pi2);
            alist.addAll(rewrdlist);
            ExpertConfirm pi3 = new ExpertConfirm();
            pi3.setId("评审中抽取无效的专家");
            alist.add(pi3);
            alist.addAll(rewnlist);
            ExpertConfirm pi4 = new ExpertConfirm();
            pi4.setId("验收中首抽有效的专家");
            alist.add(pi4);
            alist.addAll(acptlist);
            ExpertConfirm pi5 = new ExpertConfirm();
            pi5.setId("评审中补抽有效的专家");
            alist.add(pi5);
            alist.addAll(acptrdlist);
            ExpertConfirm pi6 = new ExpertConfirm();
            pi6.setId("评审中补抽有效的专家");
            alist.add(pi6);
            alist.addAll(acptnlist);
            
            
            ee.setDataList(alist).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出专家失败！失败信息："+e.getMessage());
		}
		return "modules/infoview/projectFetchExport";
    }

}
