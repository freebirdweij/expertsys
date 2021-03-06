/**
 * There are <a href="https://github.com/freebirdweij/cloudroom">CloudRoom</a> code generation
 */
package com.freebirdweij.cloudroom.modules.infoview.web;

import java.sql.Timestamp;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.freebirdweij.cloudroom.common.config.Global;
import com.freebirdweij.cloudroom.common.persistence.Page;
import com.freebirdweij.cloudroom.common.utils.Constants;
import com.freebirdweij.cloudroom.common.utils.DateUtils;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.common.utils.excel.ExportExcel;
import com.freebirdweij.cloudroom.common.web.BaseController;
import com.freebirdweij.cloudroom.modules.experts.entity.ExpertInfo;
import com.freebirdweij.cloudroom.modules.expfetch.entity.ProjectExpert;
import com.freebirdweij.cloudroom.modules.expfetch.service.ProjectExpertService;
import com.freebirdweij.cloudroom.modules.expmanage.entity.ExpertConfirm;
import com.freebirdweij.cloudroom.modules.expmanage.service.ExpertConfirmService;
import com.freebirdweij.cloudroom.modules.infoview.entity.StatisticsSearch;
import com.freebirdweij.cloudroom.modules.infoview.service.StatisticsSearchService;
import com.freebirdweij.cloudroom.modules.project.entity.ProjectInfo;
import com.freebirdweij.cloudroom.modules.project.service.ProjectInfoService;
import com.freebirdweij.cloudroom.modules.supervise.entity.FetchSupervise;
import com.freebirdweij.cloudroom.modules.supervise.service.FetchSuperviseService;
import com.freebirdweij.cloudroom.modules.sys.entity.User;
import com.freebirdweij.cloudroom.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

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
        
		//获取交工验收中首抽有效的专家
		String status4[] = {Constants.Fetch_Accept_Sussess};
        Page<ExpertConfirm> page4 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status4); 
        List<ExpertConfirm> acptlist = page4.getList();
		//获取交工验收中补抽有效的专家
		String status5[] = {Constants.Fetch_AcceptRedraw_Sussess};
        Page<ExpertConfirm> page5 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status5); 
        List<ExpertConfirm> acptrdlist = page5.getList();
		//获取交工验收中抽取无效的专家
		String status6[] = {Constants.Fetch_Accept_Failure,Constants.Fetch_AcceptRedraw_Failure};
        Page<ExpertConfirm> page6 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status6); 
        List<ExpertConfirm> acptnlist = page6.getList();
        
		//获取竣工验收中首抽有效的专家
		String status7[] = {Constants.Fetch_Accepted_Sussess};
        Page<ExpertConfirm> page7 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status7); 
        List<ExpertConfirm> acptdlist = page7.getList();
		//获取竣工验收中补抽有效的专家
		String status8[] = {Constants.Fetch_AcceptedRedraw_Sussess};
        Page<ExpertConfirm> page8 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status8); 
        List<ExpertConfirm> acptdrdlist = page8.getList();
		//获取竣工验收中抽取无效的专家
		String status9[] = {Constants.Fetch_Accepted_Failure,Constants.Fetch_AcceptedRedraw_Failure};
        Page<ExpertConfirm> page9 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status9); 
        List<ExpertConfirm> acptdnlist = page9.getList();
        
        model.addAttribute("rewlist", rewlist);
        model.addAttribute("rewrdlist", rewrdlist);
        model.addAttribute("rewnlist", rewnlist);
        model.addAttribute("acptlist", acptlist);
        model.addAttribute("acptrdlist", acptrdlist);
        model.addAttribute("acptnlist",acptnlist);
        model.addAttribute("acptdlist", acptdlist);
        model.addAttribute("acptdrdlist", acptdrdlist);
        model.addAttribute("acptdnlist",acptdnlist);
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
        
		//获取交工验收中首抽有效的项目
		String status4[] = {Constants.Fetch_Accept_Sussess};
        Page<ProjectInfo> page4 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status4); 
        List<ProjectInfo> acptlist = page4.getList();
		//获取交工验收中补抽有效的项目
		String status5[] = {Constants.Fetch_AcceptRedraw_Sussess};
        Page<ProjectInfo> page5 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status5); 
        List<ProjectInfo> acptrdlist = page5.getList();
		//获取交工验收中抽取无效的项目
		String status6[] = {Constants.Fetch_Accept_Failure,Constants.Fetch_AcceptRedraw_Failure};
        Page<ProjectInfo> page6 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status6); 
        List<ProjectInfo> acptnlist = page6.getList();
        
        
		//获取竣工验收中首抽有效的项目
		String status7[] = {Constants.Fetch_Accepted_Sussess};
        Page<ProjectInfo> page7 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status7); 
        List<ProjectInfo> acptdlist = page7.getList();
		//获取竣工验收中补抽有效的项目
		String status8[] = {Constants.Fetch_AcceptedRedraw_Sussess};
        Page<ProjectInfo> page8 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status8); 
        List<ProjectInfo> acptdrdlist = page8.getList();
		//获取竣工验收中抽取无效的项目
		String status9[] = {Constants.Fetch_Accepted_Failure,Constants.Fetch_AcceptedRedraw_Failure};
        Page<ProjectInfo> page9 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status9); 
        List<ProjectInfo> acptdnlist = page9.getList();
        
        model.addAttribute("rewlist", rewlist);
        model.addAttribute("rewrdlist", rewrdlist);
        model.addAttribute("rewnlist", rewnlist);
        model.addAttribute("acptlist", acptlist);
        model.addAttribute("acptrdlist", acptrdlist);
        model.addAttribute("acptnlist",acptnlist);
        model.addAttribute("acptdlist", acptdlist);
        model.addAttribute("acptdrdlist", acptdrdlist);
        model.addAttribute("acptdnlist",acptdnlist);
        model.addAttribute("expid",expid);
		return "modules/infoview/expertFetchExport";
	}

	@RequestMapping(value = "statistics")
	public String statistics(FetchSupervise fetchSupervise, Model model) {
		fetchSupervise.setExpertBegin(new Timestamp((new Date()).getTime()));
		fetchSupervise.setExpertEnd(new Timestamp((new Date()).getTime()));
		fetchSupervise.setUnitBegin(new Timestamp((new Date()).getTime()));
		fetchSupervise.setUnitEnd(new Timestamp((new Date()).getTime()));
		fetchSupervise.setFetchBegin(new Timestamp((new Date()).getTime()));
		fetchSupervise.setFetchEnd(new Timestamp((new Date()).getTime()));
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
		/*if (StringUtils.isNotEmpty(projectInfo.getPrjYear())){
			projectInfo.setPrjYear("2014");
		}*/
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
        
		//获取交工验收中首抽有效的专家
		String status4[] = {Constants.Fetch_Accept_Sussess};
        Page<ExpertConfirm> page4 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status4); 
        List<ExpertConfirm> acptlist = page4.getList();
		//获取交工验收中补抽有效的专家
		String status5[] = {Constants.Fetch_AcceptRedraw_Sussess};
        Page<ExpertConfirm> page5 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status5); 
        List<ExpertConfirm> acptrdlist = page5.getList();
		//获取交工验收中抽取无效的专家
		String status6[] = {Constants.Fetch_Accept_Failure,Constants.Fetch_AcceptRedraw_Failure};
        Page<ExpertConfirm> page6 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status6); 
        List<ExpertConfirm> acptnlist = page6.getList();
        
		//获取竣工验收中首抽有效的专家
		String status7[] = {Constants.Fetch_Accepted_Sussess};
        Page<ExpertConfirm> page7 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status7); 
        List<ExpertConfirm> acptdlist = page7.getList();
		//获取竣工验收中补抽有效的专家
		String status8[] = {Constants.Fetch_AcceptedRedraw_Sussess};
        Page<ExpertConfirm> page8 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status8); 
        List<ExpertConfirm> acptdrdlist = page8.getList();
		//获取竣工验收中抽取无效的专家
		String status9[] = {Constants.Fetch_Accepted_Failure,Constants.Fetch_AcceptedRedraw_Failure};
        Page<ExpertConfirm> page9 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status9); 
        List<ExpertConfirm> acptdnlist = page9.getList();
        
        model.addAttribute("rewlist", rewlist);
        model.addAttribute("rewrdlist", rewrdlist);
        model.addAttribute("rewnlist", rewnlist);
        model.addAttribute("acptlist", acptlist);
        model.addAttribute("acptrdlist", acptrdlist);
        model.addAttribute("acptnlist",acptnlist);
        model.addAttribute("acptdlist", acptdlist);
        model.addAttribute("acptdrdlist", acptdrdlist);
        model.addAttribute("acptdnlist",acptdnlist);
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
        
		//获取交工验收中首抽有效的项目
		String status4[] = {Constants.Fetch_Accept_Sussess};
        Page<ProjectInfo> page4 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status4); 
        List<ProjectInfo> acptlist = page4.getList();
		//获取交工验收中补抽有效的项目
		String status5[] = {Constants.Fetch_AcceptRedraw_Sussess};
        Page<ProjectInfo> page5 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status5); 
        List<ProjectInfo> acptrdlist = page5.getList();
		//获取交工验收中抽取无效的项目
		String status6[] = {Constants.Fetch_Accept_Failure,Constants.Fetch_AcceptRedraw_Failure};
        Page<ProjectInfo> page6 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status6); 
        List<ProjectInfo> acptnlist = page6.getList();
        
		//获取竣工验收中首抽有效的项目
		String status7[] = {Constants.Fetch_Accepted_Sussess};
        Page<ProjectInfo> page7 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status7); 
        List<ProjectInfo> acptdlist = page7.getList();
		//获取竣工验收中补抽有效的项目
		String status8[] = {Constants.Fetch_AcceptedRedraw_Sussess};
        Page<ProjectInfo> page8 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status8); 
        List<ProjectInfo> acptdrdlist = page8.getList();
		//获取竣工验收中抽取无效的项目
		String status9[] = {Constants.Fetch_Accepted_Failure,Constants.Fetch_AcceptedRedraw_Failure};
        Page<ProjectInfo> page9 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status9); 
        List<ProjectInfo> acptdnlist = page9.getList();
        
        model.addAttribute("rewlist", rewlist);
        model.addAttribute("rewrdlist", rewrdlist);
        model.addAttribute("rewnlist", rewnlist);
        model.addAttribute("acptlist", acptlist);
        model.addAttribute("acptrdlist", acptrdlist);
        model.addAttribute("acptnlist",acptnlist);
        model.addAttribute("acptdlist", acptdlist);
        model.addAttribute("acptdrdlist", acptdrdlist);
        model.addAttribute("acptdnlist",acptdnlist);
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
        
		//获取交工验收中首抽有效的项目
		String status4[] = {Constants.Fetch_Accept_Sussess};
        Page<ProjectInfo> page4 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status4); 
        List<ProjectInfo> acptlist = page4.getList();
		//获取交工验收中补抽有效的项目
		String status5[] = {Constants.Fetch_AcceptRedraw_Sussess};
        Page<ProjectInfo> page5 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status5); 
        List<ProjectInfo> acptrdlist = page5.getList();
		//获取交工验收中抽取无效的项目
		String status6[] = {Constants.Fetch_Accept_Failure,Constants.Fetch_AcceptRedraw_Failure};
        Page<ProjectInfo> page6 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status6); 
        List<ProjectInfo> acptnlist = page6.getList();
        
		//获取竣工验收中首抽有效的项目
		String status7[] = {Constants.Fetch_Accepted_Sussess};
        Page<ProjectInfo> page7 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status7); 
        List<ProjectInfo> acptdlist = page7.getList();
		//获取竣工验收中补抽有效的项目
		String status8[] = {Constants.Fetch_AcceptedRedraw_Sussess};
        Page<ProjectInfo> page8 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status8); 
        List<ProjectInfo> acptdrdlist = page8.getList();
		//获取竣工验收中抽取无效的项目
		String status9[] = {Constants.Fetch_Accepted_Failure,Constants.Fetch_AcceptedRedraw_Failure};
        Page<ProjectInfo> page9 = projectExpertService.findFetchProjectsByExpertAndStatus(new Page<ProjectInfo>(request, response), expid,status9); 
        List<ProjectInfo> acptdnlist = page9.getList();
        
        
		try {
            String fileName = "项目列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
            List<ProjectInfo> alist = Lists.newArrayList();
            ExportExcel ee = new ExportExcel("项目列表", ProjectInfo.class);
            if(rewlist!=null&&rewlist.size()>0){
            ProjectInfo pi1 = new ProjectInfo();
            pi1.setId("评审中首抽有效的项目");
            alist.add(pi1);
            alist.addAll(rewlist);
            }
            if(rewrdlist!=null&&rewrdlist.size()>0){
            ProjectInfo pi2 = new ProjectInfo();
            pi2.setId("评审中补抽有效的项目");
            alist.add(pi2);
            alist.addAll(rewrdlist);
            }
            if(rewnlist!=null&&rewnlist.size()>0){
            ProjectInfo pi3 = new ProjectInfo();
            pi3.setId("评审中抽取无效的项目");
            alist.add(pi3);
            alist.addAll(rewnlist);
            }
            if(acptlist!=null&&acptlist.size()>0){
            ProjectInfo pi4 = new ProjectInfo();
            pi4.setId("交工验收中首抽有效的项目");
            alist.add(pi4);
            alist.addAll(acptlist);
            }
            if(acptrdlist!=null&&acptrdlist.size()>0){
            ProjectInfo pi5 = new ProjectInfo();
            pi5.setId("交工验收中补抽有效的项目");
            alist.add(pi5);
            alist.addAll(acptrdlist);
            }
            if(acptnlist!=null&&acptnlist.size()>0){
            ProjectInfo pi6 = new ProjectInfo();
            pi6.setId("交工验收中补抽无效的项目");
            alist.add(pi6);
            alist.addAll(acptnlist);
            }
            if(acptdlist!=null&&acptdlist.size()>0){
            ProjectInfo pi7 = new ProjectInfo();
            pi7.setId("竣工验收中首抽有效的项目");
            alist.add(pi7);
            alist.addAll(acptdlist);
            }
            if(acptdrdlist!=null&&acptdrdlist.size()>0){
            ProjectInfo pi8 = new ProjectInfo();
            pi8.setId("竣工验收中补抽有效的项目");
            alist.add(pi8);
            alist.addAll(acptdrdlist);
            }
            if(acptdnlist!=null&&acptdnlist.size()>0){
            ProjectInfo pi9 = new ProjectInfo();
            pi9.setId("竣工验收中补抽无效的项目");
            alist.add(pi9);
            alist.addAll(acptdnlist);
            }
            
            
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
        
		//获取竣工验收中首抽有效的专家
		String status7[] = {Constants.Fetch_Accepted_Sussess};
        Page<ExpertConfirm> page7 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status7); 
        List<ExpertConfirm> acptdlist = page7.getList();
		//获取竣工验收中补抽有效的专家
		String status8[] = {Constants.Fetch_AcceptedRedraw_Sussess};
        Page<ExpertConfirm> page8 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status8); 
        List<ExpertConfirm> acptdrdlist = page8.getList();
		//获取竣工验收中抽取无效的专家
		String status9[] = {Constants.Fetch_Accepted_Failure,Constants.Fetch_AcceptedRedraw_Failure};
        Page<ExpertConfirm> page9 = projectExpertService.findFetchExpertsByProjectAndStatus(new Page<ExpertConfirm>(request, response), prjid,status9); 
        List<ExpertConfirm> acptdnlist = page9.getList();
        
		try {
            String fileName = "专家列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
            List<ExpertConfirm> alist = Lists.newArrayList();
            ExportExcel ee = new ExportExcel("专家列表", ExpertConfirm.class);            
            if(rewlist!=null&&rewlist.size()>0){
            	ExpertConfirm pi1 = new ExpertConfirm();
            	ExpertInfo ei = new ExpertInfo();
            	ei.setName("评审中首抽有效的专家");
                pi1.setExpertInfo(ei);
            alist.add(pi1);
            alist.addAll(rewlist);
            }
            if(rewrdlist!=null&&rewrdlist.size()>0){
            	ExpertConfirm pi2 = new ExpertConfirm();
            	ExpertInfo ei = new ExpertInfo();
            	ei.setName("评审中补抽有效的专家");
            pi2.setExpertInfo(ei);
            alist.add(pi2);
            alist.addAll(rewrdlist);
            }
            if(rewnlist!=null&&rewnlist.size()>0){
            	ExpertConfirm pi3 = new ExpertConfirm();
            	ExpertInfo ei = new ExpertInfo();
            	ei.setName("评审中抽取无效的专家");
            pi3.setExpertInfo(ei);
            alist.add(pi3);
            alist.addAll(rewnlist);
            }
            if(acptlist!=null&&acptlist.size()>0){
            	ExpertConfirm pi4 = new ExpertConfirm();
            	ExpertInfo ei = new ExpertInfo();
            	ei.setName("交工验收中首抽有效的专家");
            pi4.setExpertInfo(ei);
            alist.add(pi4);
            alist.addAll(acptlist);
            }
            if(acptrdlist!=null&&acptrdlist.size()>0){
            	ExpertConfirm pi5 = new ExpertConfirm();
            	ExpertInfo ei = new ExpertInfo();
            	ei.setName("交工验收中补抽有效的专家");
            pi5.setExpertInfo(ei);
            alist.add(pi5);
            alist.addAll(acptrdlist);
            }
            if(acptnlist!=null&&acptnlist.size()>0){
            	ExpertConfirm pi6 = new ExpertConfirm();
            	ExpertInfo ei = new ExpertInfo();
            	ei.setName("交工验收中补抽无效的专家");
            pi6.setExpertInfo(ei);
            alist.add(pi6);
            alist.addAll(acptnlist);
            }
            if(acptdlist!=null&&acptdlist.size()>0){
            	ExpertConfirm pi7 = new ExpertConfirm();
            	ExpertInfo ei = new ExpertInfo();
            	ei.setName("竣工验收中首抽有效的专家");
            pi7.setExpertInfo(ei);
            alist.add(pi7);
            alist.addAll(acptdlist);
            }
            if(acptdrdlist!=null&&acptdrdlist.size()>0){
            	ExpertConfirm pi8 = new ExpertConfirm();
            	ExpertInfo ei = new ExpertInfo();
            	ei.setName("竣工验收中补抽有效的专家");
            pi8.setExpertInfo(ei);
            alist.add(pi8);
            alist.addAll(acptdrdlist);
            }
            if(acptdnlist!=null&&acptdnlist.size()>0){
            	ExpertConfirm pi9 = new ExpertConfirm();
            	ExpertInfo ei = new ExpertInfo();
            	ei.setName("竣工验收中补抽无效的专家");
            pi9.setExpertInfo(ei);
            alist.add(pi9);
            alist.addAll(acptdnlist);
            }
            
            ee.setDataList(alist).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出专家失败！失败信息："+e.getMessage());
		}
		return "modules/infoview/projectFetchExport";
    }

}
