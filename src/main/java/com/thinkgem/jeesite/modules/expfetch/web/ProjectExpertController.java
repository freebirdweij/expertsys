/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.expfetch.web;

import java.lang.reflect.InvocationTargetException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import com.thinkgem.jeesite.common.utils.Constants;
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

/**
 * 对项目进行专家抽取Controller
 * @author Cloudman
 * @version 2014-07-12
 */
@Controller
@RequestMapping(value = "${adminPath}/expfetch")
public class ProjectExpertController extends BaseController {

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private ProjectInfoService projectInfoService;
	
	@Autowired
	private ProjectExpertService projectExpertService;
	
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
	@RequestMapping(value = {"unitfetch", ""})
	public String unitfetch(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		String unitIdsYes = projectExpert.getUnitIdsYes();
		String kindIdsYes = projectExpert.getKindIdsYes();
		String specialIdsYes = projectExpert.getSpecialIdsYes();
		String seriesIdsYes = projectExpert.getSeriesIdsYes();
		String areaIdsNo = projectExpert.getAreaIdsNo();
		String unitIdsNo = projectExpert.getUnitIdsNo();
		String kindIdsNo = projectExpert.getKindIdsNo();
		String specialIdsNo = projectExpert.getSpecialIdsNo();
		String seriesIdsNo = projectExpert.getSeriesIdsNo();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
			if(areaIdsNo!=null&&!areaIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，区域选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/unitMethodForm?repage";
		    }
	    }
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，单位选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/unitMethodForm?repage";
		    }
	    }
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，专家类别选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/unitMethodForm?repage";
		    }
	    }
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，专业选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/unitMethodForm?repage";
		    }
	    }
		if(seriesIdsYes!=null&&!seriesIdsYes.equalsIgnoreCase("")){
			if(seriesIdsNo!=null&&!seriesIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，行业选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/unitMethodForm?repage";
		    }
	    }
        Page<Office> page = projectExpertService.findExpertUnits(new Page<Office>(request, response), projectExpert); 
        model.addAttribute("page", page);
        request.getSession().setAttribute("projectExpert", projectExpert);
        //ProjectExpert pExpert = new ProjectExpert();
        
        try {
			request.getSession().setAttribute("projectExpertBak", BeanUtils.cloneBean(projectExpert));
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "modules/expfetch/unitFetchResult";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"expertfetch", ""})
	public String expertfetch(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		String areaIdsYes = projectExpert.getAreaIdsYes();
		String unitIdsYes = projectExpert.getUnitIdsYes();
		String kindIdsYes = projectExpert.getKindIdsYes();
		String specialIdsYes = projectExpert.getSpecialIdsYes();
		String seriesIdsYes = projectExpert.getSeriesIdsYes();
		String areaIdsNo = projectExpert.getAreaIdsNo();
		String unitIdsNo = projectExpert.getUnitIdsNo();
		String kindIdsNo = projectExpert.getKindIdsNo();
		String specialIdsNo = projectExpert.getSpecialIdsNo();
		String seriesIdsNo = projectExpert.getSeriesIdsNo();
		String techIdsYes = projectExpert.getTechIdsYes();
		String techIdsNo = projectExpert.getTechIdsNo();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
			if(areaIdsNo!=null&&!areaIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，区域选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/expMethodForm?repage";
		    }
	    }
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，单位选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/expMethodForm?repage";
		    }
	    }
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，专家类别选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/expMethodForm?repage";
		    }
	    }
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，专业选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/expMethodForm?repage";
		    }
	    }
		if(seriesIdsYes!=null&&!seriesIdsYes.equalsIgnoreCase("")){
			if(seriesIdsNo!=null&&!seriesIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，行业选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/expMethodForm?repage";
		    }
	    }
		if(techIdsYes!=null&&!techIdsYes.equalsIgnoreCase("")){
			if(techIdsNo!=null&&!techIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，职称选择矛盾！");
				return "redirect:"+Global.getAdminPath()+"modules/expfetch/expMethodForm?repage";
		    }
	    }
        Page<ExpertConfirm> page = projectExpertService.findExperts(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("page", page);
        request.getSession().setAttribute("projectExpert", projectExpert);
		return "modules/expfetch/expFetchResult";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"unitexp", ""})
	public String unitexp(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		String unitid = request.getParameter("unitid");
		String resIds = request.getParameter("resIds");
		if(unitid!=null&&!unitid.equalsIgnoreCase("")){
			projectExpert.setUnitIdsYes(unitid);
			projectExpert.setUnitIdsNo(null);
		}
        List<ExpertConfirm> rlist = null; 
		if(resIds!=null&&!resIds.equalsIgnoreCase("")){
			projectExpert.setResIds(resIds);
	        rlist = projectExpertService.findExpertsByIds(new Page<ExpertConfirm>(request, response), projectExpert); 
		}
        Page<ExpertConfirm> page = projectExpertService.findExperts(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("page", page);
        model.addAttribute("projectExpert", projectExpert);
        model.addAttribute("rlist", rlist);
		return "modules/expfetch/unitExpertList";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"conditionexp", ""})
	public String conditionexp(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		String unitid = request.getParameter("unitid");
		String kind = request.getParameter("kind");
		String special = request.getParameter("special");
		String technical = request.getParameter("technical");
		String resIds = request.getParameter("resIds");
		if(unitid!=null&&!unitid.equalsIgnoreCase("")){
			projectExpert.setUnitIdsYes(unitid);
			projectExpert.setUnitIdsNo(null);
		}
		if(kind!=null&&!kind.equalsIgnoreCase("")){
			projectExpert.setKindIdsYes(kind);
			projectExpert.setKindIdsNo(null);
		}
		if(special!=null&&!special.equalsIgnoreCase("")){
			projectExpert.setSeriesIdsYes(special);
			projectExpert.setSpecialIdsNo(null);
		}
		if(technical!=null&&!technical.equalsIgnoreCase("")){
			projectExpert.setTechIdsYes(technical);
			projectExpert.setTechIdsNo(null);
		}
        List<ExpertConfirm> rlist = null; 
		if(resIds!=null&&!resIds.equalsIgnoreCase("")){
			projectExpert.setResIds(resIds);
	        rlist = projectExpertService.findExpertsByIds(new Page<ExpertConfirm>(request, response), projectExpert); 
		}
        Page<ExpertConfirm> page = projectExpertService.findExperts(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("page", page);
        model.addAttribute("projectExpert", projectExpert);
        model.addAttribute("rlist", rlist);
		return "modules/expfetch/expExpertList";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"directdrawunit", ""})
	public String directdrawunit(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		//需要获取的专家数
		Byte expertCount = projectExpert.getExpertCount();
		//从页面得到的屏蔽的单位id
		String discIds = projectExpert.getDiscIds();
		//屏蔽主体单位标志
		String rejectUnit = projectExpert.getRejectUnit();
		//屏蔽最近三次抽取标志
		String rejectRecent = projectExpert.getRejectRecent();
		
		projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		String unitIdsYes = projectExpert.getUnitIdsYes();
		String unitIdsNo = projectExpert.getUnitIdsNo();
		//构造最终需要的单位集合
		List<String> unitList = Lists.newArrayList();
		
		//存储需屏蔽的单位集合
		List<String> uidslist = Lists.newArrayList();
		
		if(rejectUnit.equalsIgnoreCase(Constants.Reject_Main_Unit)){
			String prjid = projectExpert.getPrjid();
			rejectUnit = projectInfoService.get(prjid).getUnit().getId();
			uidslist.add(rejectUnit);
		}
		
		if(rejectRecent.equalsIgnoreCase(Constants.Reject_Recent_Three)){
			uidslist.addAll(projectExpertService.findUnitRecentThree(projectExpert));
		}
		
		if(discIds!=null&&!discIds.equalsIgnoreCase("")){
			  String[] dids = StringUtils.split(discIds, ",");
			  uidslist.addAll(Arrays.asList(dids));
		}
		
		if(uidslist.size()>0){
			if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
				String[] ids = StringUtils.split(unitIdsYes, ",");
				for (String id : ids) {
					unitList.add(id);
					for (String discId : uidslist) {
						if(discId.equalsIgnoreCase(id)){	
							unitList.remove(id);
						}
					}
				}
				projectExpert.setUnitIdsYes(StringUtils.join(unitList, ","));
				projectExpert.setUnitIdsNo(null);
			}else if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
				String[] ids = StringUtils.split(unitIdsNo, ",");
				unitList.addAll(uidslist);
				unitList.addAll(Arrays.asList(ids));

				projectExpert.setUnitIdsYes(null);
				projectExpert.setUnitIdsNo(StringUtils.join(unitList, ","));
			}else{
				unitList.addAll(uidslist);
				projectExpert.setUnitIdsYes(null);
				projectExpert.setUnitIdsNo(StringUtils.join(unitList, ","));
			}
		}
		projectExpert.setExpertCount(expertCount);
        List<Office> rlist = projectExpertService.findUnitExpertByCount(new Page<Office>(request, response), projectExpert); 
        
        //以下进行随机选取计算
		int resSize =rlist.size(); 
		if(expertCount<resSize){
	        Random r=new Random();   
	        int n = resSize - expertCount+1;  
	        int ri = r.nextInt(n);
	        rlist = rlist.subList(ri,ri+expertCount);
		}
        
        List<ExpertConfirm> eclist =  Lists.newArrayList();
        for(Office ec : rlist){
        	eclist.add(projectExpertService.findAExpertByUnit(ec, projectExpert));
        }
        
        model.addAttribute("rlist", eclist);
        
        Page<Office> page = projectExpertService.findExpertUnits(new Page<Office>(request, response), projectExpert); 
        model.addAttribute("page", page);
        
        List<String> dclist =  Lists.newArrayList();
        for(ExpertConfirm ec : eclist){
        	dclist.add(ec.getId());
        }
        projectExpert.setResIds(StringUtils.join(dclist, ","));
        model.addAttribute("projectExpert", projectExpert);
        
		return "modules/expfetch/unitFetchResult";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"directdrawexpert", ""})
	public String directdrawexpert(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		Byte expertCount = projectExpert.getExpertCount();
		String discIds = projectExpert.getDiscIds();
		String resIds = projectExpert.getResIds();
		projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		List<String> eList = Lists.newArrayList();
		
		if(discIds!=null&&!discIds.equalsIgnoreCase("")){
			
				  String[] dids = StringUtils.split(discIds, ",");
				  String[] ids = StringUtils.split(resIds, ",");
				  eList.addAll(Arrays.asList(dids));
				  eList.addAll(Arrays.asList(ids));
				projectExpert.setResIds(StringUtils.join(eList, ","));
			
		}
		projectExpert.setExpertCount(expertCount);
        List<ExpertConfirm> rlist = projectExpertService.findExpertsByCountRest(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("rlist", rlist);
        
        ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
        pExpert.setExpertCount(expertCount);
        Page<ExpertConfirm> page = projectExpertService.findExperts(new Page<ExpertConfirm>(request, response), pExpert); 
        model.addAttribute("page", page);
        
        List<String> eclist =  Lists.newArrayList();
        for(ExpertConfirm ec : rlist){
        	eclist.add(ec.getId());
        }
        pExpert.setResIds(StringUtils.join(eclist, ","));
        model.addAttribute("projectExpert", pExpert);
        request.getSession().setAttribute("projectExpert",pExpert);       
        
		return "modules/expfetch/expFetchResult";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"backunitresult", ""})
	public String backunitresult(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		String discIds = projectExpert.getDiscIds();
		projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		String unitIdsYes = projectExpert.getUnitIdsYes();
		String unitIdsNo = projectExpert.getUnitIdsNo();
		List<String> unitList = Lists.newArrayList();
		
		if(discIds!=null&&!discIds.equalsIgnoreCase("")){
			
			if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
				  String[] dids = StringUtils.split(discIds, ",");
				  String[] ids = StringUtils.split(unitIdsYes, ",");
			  for (String id : ids) {
				  unitList.add(id);
				  for (String discId : dids) {
					  if(discId.equalsIgnoreCase(id)){	
						  unitList.remove(id);
					  }
				  }
			  }
				projectExpert.setUnitIdsYes(StringUtils.join(unitList, ","));
				projectExpert.setUnitIdsNo(null);
			}
			
			if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
				  String[] dids = StringUtils.split(discIds, ",");
				  String[] ids = StringUtils.split(unitIdsYes, ",");
				  unitList.addAll(Arrays.asList(dids));
				  unitList.addAll(Arrays.asList(ids));
				  
					projectExpert.setUnitIdsYes(null);
					projectExpert.setUnitIdsNo(StringUtils.join(unitList, ","));
			}
		}
        List<ExpertConfirm> rlist = projectExpertService.findExpertsByIds(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("rlist", rlist);
        
        ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
        Page<Office> page = projectExpertService.findExpertUnits(new Page<Office>(request, response), pExpert); 
        model.addAttribute("page", page);
        
        List<String> eclist =  Lists.newArrayList();
        for(ExpertConfirm ec : rlist){
        	eclist.add(ec.getId());
        }
        pExpert.setResIds(StringUtils.join(eclist, ","));
        model.addAttribute("projectExpert", pExpert);
        
		return "modules/expfetch/unitFetchResult";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"backexpresult", ""})
	public String backexpresult(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		String resIds = projectExpert.getResIds();
		projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		if(resIds!=null&&!resIds.equalsIgnoreCase("")){
			projectExpert.setResIds(resIds);
		}
        List<ExpertConfirm> rlist = projectExpertService.findExpertsByIds(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("rlist", rlist);
        
        Page<ExpertConfirm> page = projectExpertService.findExperts(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("page", page);
        
        model.addAttribute("projectExpert", projectExpert);
        request.getSession().setAttribute("projectExpert", projectExpert);
        
		return "modules/expfetch/expFetchResult";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"drawunitexpert", ""})
	public String drawunitexpert(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		Byte expertCount = projectExpert.getExpertCount();
		String unitIdsYes = projectExpert.getUnitIdsYes();
		String resIds = projectExpert.getResIds();
		projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		
			
			if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
				projectExpert.setUnitIdsYes(unitIdsYes);
				projectExpert.setUnitIdsNo(null);
			}
			
		projectExpert.setExpertCount(expertCount);
        projectExpert.setResIds(resIds);
        List<ExpertConfirm> dlist = projectExpertService.findUnitExpertByCountRest(new Page<ExpertConfirm>(request, response), projectExpert); 
        List<ExpertConfirm> rlist = projectExpertService.findExpertsByIds(new Page<ExpertConfirm>(request, response), projectExpert);
        rlist.addAll(dlist);
        model.addAttribute("rlist", rlist);
        
        Page<ExpertConfirm> page = projectExpertService.findExperts(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("page", page);
        
        model.addAttribute("projectExpert", projectExpert);
        
		return "modules/expfetch/unitExpertList";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"drawexpert", ""})
	public String drawexpert(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		Byte expertCount = projectExpert.getExpertCount();
		String unitid = projectExpert.getUnitIdsYes();
		String kind = projectExpert.getKindIdsYes();
		String special = projectExpert.getSpecialIdsYes();
		String technical = projectExpert.getTechIdsYes();
		String resIds = projectExpert.getResIds();
		projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		
			
		if(unitid!=null&&!unitid.equalsIgnoreCase("")){
			projectExpert.setUnitIdsYes(unitid);
			projectExpert.setUnitIdsNo(null);
		}
		if(kind!=null&&!kind.equalsIgnoreCase("")){
			projectExpert.setKindIdsYes(kind);
			projectExpert.setKindIdsNo(null);
		}
		if(special!=null&&!special.equalsIgnoreCase("")){
			projectExpert.setSeriesIdsYes(special);
			projectExpert.setSpecialIdsNo(null);
		}
		if(technical!=null&&!technical.equalsIgnoreCase("")){
			projectExpert.setTechIdsYes(technical);
			projectExpert.setTechIdsNo(null);
		}
			
		projectExpert.setExpertCount(expertCount);
        projectExpert.setResIds(resIds);
        List<ExpertConfirm> dlist = projectExpertService.findUnitExpertByCountRest(new Page<ExpertConfirm>(request, response), projectExpert); 
        List<ExpertConfirm> rlist = projectExpertService.findExpertsByIds(new Page<ExpertConfirm>(request, response), projectExpert);
        rlist.addAll(dlist);
        model.addAttribute("rlist", rlist);
        
        Page<ExpertConfirm> page = projectExpertService.findExperts(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("page", page);
        
        model.addAttribute("projectExpert", projectExpert);
        
		return "modules/expfetch/expExpertList";
	}

	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"reviewinglist", ""})
	public String reviewinglist(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectInfo.setCreateBy(user);
		}
        Page<ProjectInfo> page = projectInfoService.findReviewing(new Page<ProjectInfo>(request, response), projectInfo); 
        model.addAttribute("page", page);
		return "modules/expfetch/reviewingList";
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
	public String unitmethod(ProjectExpert projectExpert, Model model,@RequestParam("prjid") String prjid) {
		projectExpert.setPrjid(prjid);
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
	public String expertmethod(ProjectExpert projectExpert, Model model,@RequestParam("prjid") String prjid) {
		projectExpert.setPrjid(prjid);
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
	public String cancelunitresult(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		if(pExpert.getFetchTime()==null){
			
		}
		int fcount = pExpert.getFetchTime()+1;
		projectExpert.setFetchTime(fcount);
		String resIds = projectExpert.getResIds();
		String[] ids = StringUtils.split(resIds, ",");
	    projectExpert.setPrjProjectInfo(pExpert.getPrjProjectInfo());
    	projectExpert.setFetchMethod(Constants.Fetch_Method_Unit);
    	//本次抽取状态标志。重要
    	projectExpert.setFetchStatus(Constants.Fetch_Status_Failure);
	    for (String id : ids) {
	    	projectExpert.getExpertExpertConfirm().setId(id);
			projectExpertService.save(projectExpert);
	    }
	    request.getSession().removeAttribute("projectExpert");
		//addMessage(redirectAttributes, "保存对项目进行专家抽取'" + projectExpert.getPrjProjectInfo().getPrjName() + "'成功");
		projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpertBak");
		return unitfetch(projectExpert, request, response, model, redirectAttributes);
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
