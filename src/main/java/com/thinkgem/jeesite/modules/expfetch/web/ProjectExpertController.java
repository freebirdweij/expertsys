/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.expfetch.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ExportFetchExcel;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;
import com.thinkgem.jeesite.modules.project.service.ProjectInfoService;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.service.LogService;
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
	private LogService logService;

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private ProjectInfoService projectInfoService;
	
	@Autowired
	public ProjectExpertService projectExpertService;
	
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
		
		//这一段用于翻页的处理
		String repage = request.getParameter("repage");
		if(repage!=null&&repage.equalsIgnoreCase("1")){
			projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
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
		
		//这一段用于翻页的处理
		String repage = request.getParameter("repage");
		if(repage!=null&&repage.equalsIgnoreCase("1")){
			projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
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
				return "modules/expfetch/expMethodForm";
		    }
	    }
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，单位选择矛盾！");
				return "modules/expfetch/expMethodForm";
		    }
	    }
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，专家类别选择矛盾！");
				return "modules/expfetch/expMethodForm";
		    }
	    }
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，专业选择矛盾！");
				return "modules/expfetch/expMethodForm";
		    }
	    }
		if(seriesIdsYes!=null&&!seriesIdsYes.equalsIgnoreCase("")){
			if(seriesIdsNo!=null&&!seriesIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，行业选择矛盾！");
				return "modules/expfetch/expMethodForm";
		    }
	    }
		if(techIdsYes!=null&&!techIdsYes.equalsIgnoreCase("")){
			if(techIdsNo!=null&&!techIdsNo.equalsIgnoreCase("")){
				addMessage(redirectAttributes, "您选择的条件存在矛盾，相同类型不能既选择符合性，又选择拒绝性条件，职称选择矛盾！");
				return "modules/expfetch/expMethodForm";
		    }
	    }
        Page<ExpertConfirm> page = projectExpertService.findExperts(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("page", page);
        request.getSession().setAttribute("projectExpert", projectExpert);
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

	@SuppressWarnings("unchecked")
	@RequiresPermissions("expfetch:projectExpert:view")
	@RequestMapping(value = {"directdrawunit", ""})
	public String directdrawunit(ProjectExpert projectExpert, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectExpert.setCreateBy(user);
		}
		//先取得项目主体单位
		String prjid = projectExpert.getPrjid();
		//可以有多个项目同时抽取，先判断有几个项目
		String prjs[] = StringUtils.split(prjid, ",");
		Office prjunit = projectInfoService.get(prjs[0]).getUnit();
		
		//需要获取的专家数
		Byte techcnt = projectExpert.getTechcnt();//技术类
		if(techcnt==null) techcnt=0;
		Byte ecomcnt = projectExpert.getEcomcnt();//经济类
		if(ecomcnt==null) ecomcnt=0;
		Integer expertCount = techcnt+ecomcnt;
		
		//如果两个类型都没选，需要处理
		if(expertCount==0){
			addMessage(model, "您未选择抽取的专家数！");
	        projectExpert.setReviewBegin(new Timestamp(projectExpert.getReviewBegin().getTime()));
	        projectExpert.setReviewEnd(new Timestamp(projectExpert.getReviewEnd().getTime()));
	        model.addAttribute("projectExpert", projectExpert);
			return "modules/expfetch/unitFetchResult";
		}
		//屏蔽近期已抽选
		Byte discnt = projectExpert.getDiscnt();
		//监督人
		String supervise = projectExpert.getSupervise();
		
		//存储需屏蔽的单位集合
		List<String> uidslist = Lists.newArrayList();
		uidslist.add(prjunit.getId());//主体单位
		if(discnt!=null){
			uidslist.addAll(projectExpertService.findUnitRecentByCount(projectExpert));	
		}
		
		//只有大于两个数量才进行交投特定抽取
		ExpertConfirm jec = null;
		if(expertCount>2){
			HashMap<String,Office> officeMap = (HashMap<String,Office>) UserUtils.getJiaoTouMap();
			Map<String,Office> om = (Map<String, Office>) officeMap.clone();

			if(uidslist.size()>0){
				for(String id:uidslist){
					if(om.containsKey(id)){
						om.remove(id);
					}
				}
			}

			//先抽取交投的专家
			if(om.size()>0){
				jec = getAExpertByJiaoTouMap(techcnt, ecomcnt, om);
			}
			//如果交投的抽中了。
			if(jec!=null){
				uidslist.addAll(om.keySet());
				if(jec.getExpertKind().equals(Constants.Expert_Kind_Technical)){
					techcnt--;
				}else if(jec.getExpertKind().equals(Constants.Expert_Kind_Economic)){
					ecomcnt--;
				}else{
					techcnt--;
				}
			}
		}
		
		//存储结果集
        List<ExpertConfirm> erclist =  Lists.newArrayList();
		projectExpert.setExpertCount(expertCount.byteValue());
		if(techcnt>0){
			projectExpert.setUnitIdsNo(StringUtils.join(uidslist, ","));
			projectExpert.setKindIdsYes(Constants.Expert_Kind_Technical);
        List<Office> tlist = projectExpertService.findUnitExpertByCondition(new Page<Office>(request, response), projectExpert); 
        
        //以下进行随机选取计算
		int resSize =tlist.size(); 
		if(techcnt<resSize){
	        Random r=new Random();   
	        int n = resSize - techcnt+1;  
	        int ri = r.nextInt(n);
	        tlist = tlist.subList(ri,ri+techcnt);
		}else if(techcnt>resSize){
			//待抽取单位不足，需要改变条件
			addMessage(model, "条件限制过多，库中专家不足！");
	        projectExpert.setReviewBegin(new Timestamp(projectExpert.getReviewBegin().getTime()));
	        projectExpert.setReviewEnd(new Timestamp(projectExpert.getReviewEnd().getTime()));
	        model.addAttribute("projectExpert", projectExpert);
			return "modules/expfetch/unitFetchResult";
		}
        
        for(Office ec : tlist){
        	erclist.add(projectExpertService.findAExpertByUnitAndKind(ec, Constants.Expert_Kind_Technical));
        	uidslist.add(ec.getId());
        }
		}
        
        if(jec!=null) erclist.add(jec);
        
		if(ecomcnt>0){
			projectExpert.setUnitIdsNo(StringUtils.join(uidslist, ","));
			projectExpert.setKindIdsYes(Constants.Expert_Kind_Economic);
        List<Office> elist = projectExpertService.findUnitExpertByCondition(new Page<Office>(request, response), projectExpert); 
        
        //以下进行随机选取计算
		int resSize =elist.size(); 
		if(ecomcnt<resSize){
	        Random r=new Random();   
	        int n = resSize - ecomcnt+1;  
	        int ri = r.nextInt(n);
	        elist = elist.subList(ri,ri+ecomcnt);
		}else if(ecomcnt>resSize){
			//待抽取单位不足，需要改变条件
			addMessage(model, "条件限制过多，库中专家不足！");
	        projectExpert.setReviewBegin(new Timestamp(projectExpert.getReviewBegin().getTime()));
	        projectExpert.setReviewEnd(new Timestamp(projectExpert.getReviewEnd().getTime()));
	        model.addAttribute("projectExpert", projectExpert);
			return "modules/expfetch/unitFetchResult";
		}
        
        for(Office ec : elist){
        	erclist.add(projectExpertService.findAExpertByUnitAndKind(ec, Constants.Expert_Kind_Economic));
        	uidslist.add(ec.getId());
        }
		}
        
		if(erclist.size()==0){
			//待抽取单位不足，需要改变条件
			addMessage(model, "条件限制过多，库中专家不足！");
	        projectExpert.setReviewBegin(new Timestamp(projectExpert.getReviewBegin().getTime()));
	        projectExpert.setReviewEnd(new Timestamp(projectExpert.getReviewEnd().getTime()));
	        model.addAttribute("projectExpert", projectExpert);
			return "modules/expfetch/unitFetchResult";
			
		}
		
		//需先把抽取结果保留
		int fcount = 0;
			fcount = projectExpertService.selectMaxFetchTime()+1;
    	//本次抽取记录。重要
		for(String prj:prjs){//对每个项目都需单独记录
	    for (ExpertConfirm ec : erclist) {
	    	ProjectExpert pExpert = new ProjectExpert();
	    	pExpert.setFetchTime(fcount);
	    	pExpert.setExpertCount(expertCount.byteValue());
	    	pExpert.setPrjProjectInfo(new ProjectInfo(prj));
	    	pExpert.setFetchMethod(Constants.Fetch_Method_Unit);
	    	pExpert.setFetchStatus(Constants.Fetch_Review_Failure);
	    	pExpert.setExpertExpertConfirm(ec);
	    	pExpert.setReviewBegin(projectExpert.getReviewBegin());
	    	pExpert.setReviewEnd(projectExpert.getReviewEnd());
	    	pExpert.setSupervise(supervise);
	    	pExpert.setDiscnt(discnt);
			StringBuffer strb = new StringBuffer();
			strb.append(Constants.Log_Function_FetchReview).append("评审前抽取专家,").append(Constants.Log_Expert_Name).append(ec.getExpertInfo().getName()).append(",")
			.append(Constants.Log_Operater_Name).append(user.getName()).append(".");
			pExpert.setRemarks(strb.toString());
			projectExpertService.save(pExpert);
	    }
		}
	    //request.getSession().removeAttribute("projectExpert");
		addMessage(model, "进行专家抽取成功.");
		
        
        model.addAttribute("rlist", erclist);
        
        //保留抽取的结果到页面,若采纳需要使用到
        List<String> eclist =  Lists.newArrayList();
        for(ExpertConfirm ec : erclist){
        	eclist.add(ec.getId());
        }
        projectExpert.setReviewBegin(new Timestamp(projectExpert.getReviewBegin().getTime()));
        projectExpert.setReviewEnd(new Timestamp(projectExpert.getReviewEnd().getTime()));
        projectExpert.setResIds(StringUtils.join(eclist, ","));
        projectExpert.setFetchTime(fcount);
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
		//需要获取的专家数
		Byte expertCount = projectExpert.getExpertCount();
		//从页面得到的屏蔽的专家id
		String discIds = projectExpert.getDiscIds();
		//屏蔽主体单位标志
		String rejectUnit = projectExpert.getRejectUnit();
		//冲突屏蔽方式
		String timeClash = projectExpert.getTimeClash();
		//String resIds = projectExpert.getResIds();
		
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
		
        List<String> tlist =  Lists.newArrayList();
        //冲突屏蔽方式处理
		if(timeClash!=null&&!timeClash.equalsIgnoreCase("")){
			if(timeClash.equalsIgnoreCase(Constants.Time_Clash_OneDay)||timeClash.equalsIgnoreCase(Constants.Time_Clash_HalfDay)){
				tlist.addAll(projectExpertService.findExpertsByTimeClash(projectExpert));
			}
		}
		
		if(discIds!=null&&!discIds.equalsIgnoreCase("")){
			String[] dids = StringUtils.split(discIds, ",");
			tlist.addAll(Arrays.asList(dids));
		}
		if(tlist.size()>0){
		   projectExpert.setDiscIds(StringUtils.join(tlist, ","));
		}
		projectExpert.setExpertCount(expertCount);
        List<ExpertConfirm> rlist = projectExpertService.findExpertsByCountRest(new Page<ExpertConfirm>(request, response), projectExpert); 
		
        //以下进行随机选取计算
		int resSize =rlist.size(); 
		if(expertCount<resSize){
	        Random r=new Random();   
	        int n = resSize - expertCount+1;  
	        int ri = r.nextInt(n);
	        rlist = rlist.subList(ri,ri+expertCount);
		}
        model.addAttribute("rlist", rlist);
        
        Page<ExpertConfirm> page = projectExpertService.findExperts(new Page<ExpertConfirm>(request, response), projectExpert); 
        model.addAttribute("page", page);
        
        List<String> eclist =  Lists.newArrayList();
        for(ExpertConfirm ec : rlist){
        	eclist.add(ec.getId());
        }
        projectExpert.setResIds(StringUtils.join(eclist, ","));
        model.addAttribute("projectExpert", projectExpert);
        //request.getSession().setAttribute("projectExpert",projectExpert);       
        
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
	@RequestMapping(value = {"saveinglist", ""})
	public String saveinglist(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			projectInfo.setCreateBy(user);
		}
        Page<ProjectInfo> page = projectInfoService.findSaveing(new Page<ProjectInfo>(request, response), projectInfo); 
        model.addAttribute("page", page);
		return "modules/expfetch/saveingList";
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
		projectExpert.setReviewBegin(new Timestamp((new Date()).getTime()));
		projectExpert.setReviewEnd(new Timestamp((new Date()).getTime()));
		model.addAttribute("projectExpert", projectExpert);
		return "modules/expfetch/unitFetchResult";
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
	public String receiveunitresult(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		int fcount = 0;
		if(projectExpert.getFetchTime()==null){
			fcount = projectExpertService.selectMaxFetchTime();
		}else{
		    fcount = projectExpert.getFetchTime();
		}
		String prjid = projectExpert.getPrjid();
		String resIds = projectExpert.getResIds();
		String[] ids = StringUtils.split(resIds, ",");
		
    	//本次抽取状态标志。重要
		//可以有多个项目同时抽取，先判断有几个项目
		String prjs[] = StringUtils.split(prjid, ",");
		//须建立项目的父子关系，补抽时用到
		String pid = "0";
		if(prjs.length>1){
			pid = prjs[0];
		}
		for(String prj:prjs){
			for (String id : ids) {
				projectExpertService.updateProjectExpertStatus(Constants.Fetch_Review_Sussess,fcount,prj,id);
			}
			if(prj.equals(pid)){
			projectInfoService.updateProjectStatusAndParent(Constants.Project_Status_Apply,"0", prj);
			}else{
				projectInfoService.updateProjectStatusAndParent(Constants.Project_Status_Apply,pid, prj);
				
			}
		}
	    //request.getSession().removeAttribute("projectExpert");
		addMessage(model, "确认对项目进行专家抽取成功.");
		
		List<ProjectInfo> plist = projectInfoService.findProjectsByIds(new Page<ProjectInfo>(), prjs);
        model.addAttribute("plist", plist);
		projectExpert.setResIds(resIds);
        List<ExpertConfirm> rlist = projectExpertService.findExpertsByIds(new Page<ExpertConfirm>(request, response), projectExpert);
        model.addAttribute("rlist", rlist);
		User user = UserUtils.getUser();
		//记录系统日志
		Log log = new Log();
		log.setCreateBy(user);
		log.setCreateDate( DateUtils.parseDate(DateUtils.getDateTime()));
		log.setCurrentUser(user);
		log.setType(Log.TYPE_ACCESS);
		log.setRemoteAddr(request.getRemoteAddr());
		log.setRequestUri(request.getRequestURI());
		log.setMethod(request.getMethod());
		logService.save(log);
		
		model.addAttribute("userName", user.getName());
		model.addAttribute("fetchDate", DateUtils.getDateTime());
        projectExpert.setReviewBegin(new Timestamp(projectExpert.getReviewBegin().getTime()));
        projectExpert.setReviewEnd(new Timestamp(projectExpert.getReviewEnd().getTime()));
		model.addAttribute("projectExpert", projectExpert);
		return "modules/expfetch/unitReceiveNote";
	}
	
	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "receiveexpertresult")
	public String receiveexpertresult(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		int fcount = 0;
		if(pExpert.getFetchTime()==null){
			fcount = projectExpertService.selectMaxFetchTime()+1;
		}else{
		    fcount = pExpert.getFetchTime()+1;
		}
		String resIds = projectExpert.getResIds();
		String[] ids = StringUtils.split(resIds, ",");
    	//本次抽取状态标志。重要
	    for (String id : ids) {
	    	projectExpert = new ProjectExpert();
			projectExpert.setFetchTime(fcount);
		    projectExpert.setPrjProjectInfo(new ProjectInfo(pExpert.getPrjid()));
	    	projectExpert.setFetchMethod(Constants.Fetch_Method_Expert);
	    	projectExpert.setFetchStatus(Constants.Fetch_Review_Sussess);
	    	projectExpert.setExpertExpertConfirm(new ExpertConfirm(id));
	    	projectExpert.setReviewBegin(pExpert.getReviewBegin());
	    	projectExpert.setReviewEnd(pExpert.getReviewEnd());
			projectExpertService.save(projectExpert);
	    }
	    projectInfoService.updateProjectStatus(Constants.Project_Status_Apply,pExpert.getPrjid());
	    //request.getSession().removeAttribute("projectExpert");
		addMessage(redirectAttributes, "保存对项目进行专家抽取成功.");
		
		ProjectInfo projectInfo = projectInfoService.get(pExpert.getPrjid());
		projectExpert.setPrjProjectInfo(projectInfo);
		projectExpert.setResIds(resIds);
		model.addAttribute("projectExpert", projectExpert);
        List<ExpertConfirm> rlist = projectExpertService.findExpertsByIds(new Page<ExpertConfirm>(request, response), projectExpert);
        model.addAttribute("rlist", rlist);
		User user = UserUtils.getUser();
		//记录系统日志
		Log log = new Log();
		log.setCreateBy(user);
		log.setCreateDate( DateUtils.parseDate(DateUtils.getDateTime()));
		log.setCurrentUser(user);
		log.setType(Log.TYPE_ACCESS);
		log.setRemoteAddr(request.getRemoteAddr());
		log.setRequestUri(request.getRequestURI());
		log.setMethod(request.getMethod());
		logService.save(log);
		return "modules/expfetch/unitReceiveNote";
	}
	
	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "cancelunitresult")
	public String cancelunitresult(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		return "redirect:"+Global.getAdminPath()+"/expfetch/reviewinglist/?repage";
	}
	
	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "backunitmethod")
	public String backunitmethod(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		String prjid = pExpert.getPrjid();
		int fcount = 0;
		if(pExpert.getFetchTime()==null){
			fcount = projectExpertService.selectMaxFetchTime()+1;
		}else{
		    fcount = pExpert.getFetchTime()+1;
		}
		String resIds = projectExpert.getResIds();
		String[] ids = StringUtils.split(resIds, ",");
    	//本次抽取状态标志。重要
	    for (String id : ids) {
	    	projectExpert = new ProjectExpert();
			projectExpert.setFetchTime(fcount);
		    projectExpert.setPrjProjectInfo(new ProjectInfo(pExpert.getPrjid()));
	    	projectExpert.setFetchMethod(Constants.Fetch_Method_Unit);
	    	projectExpert.setFetchStatus(Constants.Fetch_Review_Failure);
	    	projectExpert.setExpertExpertConfirm(new ExpertConfirm(id));
	    	projectExpert.setReviewBegin(pExpert.getReviewBegin());
	    	projectExpert.setReviewEnd(pExpert.getReviewEnd());
			projectExpertService.save(projectExpert);
	    }
	    //request.getSession().removeAttribute("projectExpert");
		addMessage(redirectAttributes, "保存对项目进行专家抽取成功.");
		
	    request.getSession().removeAttribute("projectExpert");
	    request.getSession().removeAttribute("projectExpertBak");
	    projectExpert = new ProjectExpert();
		//addMessage(redirectAttributes, "保存对项目进行专家抽取'" + projectExpert.getPrjProjectInfo().getPrjName() + "'成功");
		//projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpertBak");
		return unitmethod(projectExpert,model, prjid);
	}
	
	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "backexpmethod")
	public String backexpmethod(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		String prjid = pExpert.getPrjid();
		int fcount = 0;
		if(pExpert.getFetchTime()==null){
			fcount = projectExpertService.selectMaxFetchTime()+1;
		}else{
		    fcount = pExpert.getFetchTime()+1;
		}
		String resIds = projectExpert.getResIds();
		String[] ids = StringUtils.split(resIds, ",");
    	//本次抽取状态标志。重要
	    for (String id : ids) {
	    	projectExpert = new ProjectExpert();
			projectExpert.setFetchTime(fcount);
		    projectExpert.setPrjProjectInfo(new ProjectInfo(pExpert.getPrjid()));
	    	projectExpert.setFetchMethod(Constants.Fetch_Method_Expert);
	    	projectExpert.setFetchStatus(Constants.Fetch_Review_Failure);
	    	projectExpert.setExpertExpertConfirm(new ExpertConfirm(id));
	    	projectExpert.setReviewBegin(pExpert.getReviewBegin());
	    	projectExpert.setReviewEnd(pExpert.getReviewEnd());
			projectExpertService.save(projectExpert);
	    }
	    //request.getSession().removeAttribute("projectExpert");
		addMessage(redirectAttributes, "保存对项目进行专家抽取成功.");
		
	    request.getSession().removeAttribute("projectExpert");
	    request.getSession().removeAttribute("projectExpertBak");
	    projectExpert = new ProjectExpert();
		//addMessage(redirectAttributes, "保存对项目进行专家抽取'" + projectExpert.getPrjProjectInfo().getPrjName() + "'成功");
		//projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpertBak");
		return expertmethod(projectExpert,model, prjid);
	}
	
	@RequiresPermissions("expfetch:projectExpert:edit")
	@RequestMapping(value = "cancelexpertresult")
	public String cancelexpertresult(ProjectExpert projectExpert, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		if (!beanValidator(model, projectExpert)){
			return form(projectExpert, model);
		}
		ProjectExpert pExpert = (ProjectExpert) request.getSession().getAttribute("projectExpert");
		int fcount = 0;
		if(pExpert.getFetchTime()==null){
			fcount = projectExpertService.selectMaxFetchTime()+1;
		}else{
		    fcount = pExpert.getFetchTime()+1;
		}
		String resIds = projectExpert.getResIds();
		String[] ids = StringUtils.split(resIds, ",");
    	//本次抽取状态标志。重要
	    for (String id : ids) {
	    	projectExpert = new ProjectExpert();
			projectExpert.setFetchTime(fcount);
		    projectExpert.setPrjProjectInfo(new ProjectInfo(pExpert.getPrjid()));
	    	projectExpert.setFetchMethod(Constants.Fetch_Method_Expert);
	    	projectExpert.setFetchStatus(Constants.Fetch_Review_Failure);
	    	projectExpert.setExpertExpertConfirm(new ExpertConfirm(id));
	    	projectExpert.setReviewBegin(pExpert.getReviewBegin());
	    	projectExpert.setReviewEnd(pExpert.getReviewEnd());
			projectExpertService.save(projectExpert);
	    }
	    //request.getSession().removeAttribute("projectExpert");
		addMessage(redirectAttributes, "保存对项目进行专家抽取成功.");
		
	    request.getSession().removeAttribute("projectExpert");
		//addMessage(redirectAttributes, "保存对项目进行专家抽取'" + projectExpert.getPrjProjectInfo().getPrjName() + "'成功");
		projectExpert = (ProjectExpert) request.getSession().getAttribute("projectExpertBak");
	    //projectExpert.setResIds(null);
        model.addAttribute("projectExpert", projectExpert);
		return expertfetch(projectExpert, request, response, model, redirectAttributes);
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

    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ProjectExpert projectExpert, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
            String fileName = "项目评审专家抽取确认表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
            List<ExpertConfirm> rlist = projectExpertService.findExpertsByIds(new Page<ExpertConfirm>(request, response), projectExpert);
    		//ProjectInfo projectInfo = projectInfoService.get(projectExpert.getPrjid());
    		//Office un = officeService.get(projectInfo.getUnit().getId());
    		//projectInfo.setUnit(un);
    		//projectExpert.setPrjProjectInfo(projectInfo);
    		try {
				new ExportFetchExcel("项目评审专家抽取确认表", ExpertConfirm.class,projectExpert,projectInfoService).setDataList(rlist).write(response, fileName).dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
		return reviewinglist(new ProjectInfo(), request, response, model);
    }

}
