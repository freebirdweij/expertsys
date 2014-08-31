/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.expmanage.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.LogService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.experts.service.ExpertInfoService;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertImport;
import com.thinkgem.jeesite.modules.expmanage.service.ExpertConfirmService;

/**
 * 专家确认Controller
 * @author Cloudman
 * @version 2014-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/expmanage")
public class ExpertManageController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private ExpertConfirmService expertConfirmService;
	
	@Autowired
	private ExpertInfoService expertInfoService;
	
	@ModelAttribute
	public ExpertConfirm get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return expertConfirmService.get(id);
		}else{
			return new ExpertConfirm();
		}
	}
	
	@RequiresPermissions("expmanage:expertConfirm:view")
	@RequestMapping(value = {"explist", ""})
	public String explist(ExpertConfirm expertConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertConfirm.setCreateBy(user);
		}
        Page<ExpertConfirm> page = expertConfirmService.find(new Page<ExpertConfirm>(request, response), expertConfirm); 
        model.addAttribute("page", page);
        //model.addAttribute("expertConfirm", expertConfirm);
		return "modules/expmanage/expertList";
	}

	@RequiresPermissions("expmanage:expertConfirm:view")
	@RequestMapping(value = "form")
	public String form(ExpertConfirm expertConfirm, Model model) {
		model.addAttribute("expertConfirm", expertConfirm);
		return "expmanage/expertConfirmForm";
	}

	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "expedit")
	public String expedit(@RequestParam("id") String id, Model model) {
		ExpertConfirm expertConfirm = expertConfirmService.get(id);
		model.addAttribute("expertInfo", expertConfirm.getExpertInfo());
		model.addAttribute("expid", id);
		//model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/expmanage/baseForm";
	}

	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "baseform")
	public String baseform(@RequestParam("id") String id, Model model) {
		ExpertConfirm expertConfirm = expertConfirmService.get(id);
		model.addAttribute("expertInfo", expertConfirm.getExpertInfo());
		model.addAttribute("id", id);
		return "modules/expmanage/baseForm";
	}

	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "workform")
	public String workform(@RequestParam("id") String id, Model model) {
		ExpertConfirm expertConfirm = expertConfirmService.get(id);
		model.addAttribute("expertInfo", expertConfirm.getExpertInfo());
		model.addAttribute("id", id);
		return "modules/expmanage/workForm";
	}

	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "applyform")
	public String applyform(@RequestParam("id") String id, Model model) {
		ExpertConfirm expertConfirm = expertConfirmService.get(id);
		expertConfirm.getExpertInfo().setSpecialKind1(expertConfirm.getExpertKind());
		expertConfirm.getExpertInfo().setKind1Special1(expertConfirm.getExpertSpecial());
		expertConfirm.getExpertInfo().setCertSeries(expertConfirm.getCertSeries());
		model.addAttribute("expertInfo", expertConfirm.getExpertInfo());
		model.addAttribute("id", id);
		model.addAttribute("deptormanageAdvice", expertConfirm.getDeptormanageAdvice());
		return "modules/expmanage/applyForm";
	}

	@RequiresPermissions("expmanage:expertConfirm:view")
	@RequestMapping(value = "uinfo")
	public String uinfo(@RequestParam("id") String id, Model model) {
		ExpertConfirm expertConfirm = expertConfirmService.get(id);
		model.addAttribute("user", expertConfirm.getExpertInfo().getUser());
		model.addAttribute("id", id);
		return "modules/expmanage/uInfo";
	}

	@RequiresPermissions("expmanage:expertConfirm:view")
	@RequestMapping(value = "binfo")
	public String binfo(@RequestParam("id") String id, Model model) {
		ExpertConfirm expertConfirm = expertConfirmService.get(id);
		model.addAttribute("expertInfo", expertConfirm.getExpertInfo());
		model.addAttribute("id", id);
		return "modules/expmanage/bInfo";
	}

	@RequiresPermissions("expmanage:expertConfirm:view")
	@RequestMapping(value = "winfo")
	public String winfo(@RequestParam("id") String id, Model model) {
		ExpertConfirm expertConfirm = expertConfirmService.get(id);
		model.addAttribute("expertInfo", expertConfirm.getExpertInfo());
		model.addAttribute("id", id);
		return "modules/expmanage/wInfo";
	}

	@RequiresPermissions("expmanage:expertConfirm:view")
	@RequestMapping(value = "ainfo")
	public String ainfo(@RequestParam("id") String id, Model model) {
		ExpertConfirm expertConfirm = expertConfirmService.get(id);
		model.addAttribute("expertConfirm", expertConfirm);
		model.addAttribute("id", id);
		return "modules/expmanage/aInfo";
	}

	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "verify")
	public String verify(ExpertConfirm expertConfirm, Model model,@ModelAttribute("expertInfo") ExpertInfo expertInfo, RedirectAttributes redirectAttributes) {
		String userId = expertInfo.getUserId();
		if(userId!=null){
			expertInfo = expertInfoService.get(userId);
			expertConfirm.setExpertInfo(expertInfo);
			expertConfirm.setExpertArea(expertInfo.getUnit().getArea());
			expertConfirm.setExpertCompany(expertInfo.getUnit());
			expertConfirm.setExpertTechnical(expertInfo.getTechnical());
			expertConfirm.setKindOne(expertInfo.getSpecialKind1());
			expertConfirm.setSpecialOne(expertInfo.getKind1Special1());
			expertConfirm.setKindTwo(expertInfo.getSpecialKind2());
			expertConfirm.setSpecialTwo(expertInfo.getKind2Special1());
			try {
				ConvertUtils.register(new DateConverter(null), java.util.Date.class); 
				BeanUtils.copyProperties(expertConfirm, expertInfo);
			} catch (IllegalAccessException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			expertConfirm.setUid(userId);
		}
		model.addAttribute("expertConfirm", expertConfirm);
		return "modules/expmanage/expertVerify";
	}
	
	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "confirm")
	public String confirm(ExpertConfirm expertConfirm, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertConfirm)){
			return form(expertConfirm, model);
		}
		ExpertInfo expertInfo = expertInfoService.get(expertConfirm.getUid());
		if(expertConfirm.getKindOne()!=null&&!expertConfirm.getKindOne().equalsIgnoreCase("")&&expertConfirm.getSpecialOne()!=null&&!expertConfirm.getSpecialOne().equalsIgnoreCase("")){
			expertConfirm.setId(expertConfirm.getExpertCode());
			expertConfirm.setExpertKind(expertConfirm.getKindOne());
			expertConfirm.setExpertSpecial(expertConfirm.getSpecialOne());
			expertConfirm.setExpertSeries(expertConfirm.getSeriesOne());
			expertConfirm.setExpertInfo(expertInfo);
			expertConfirm.setExpertCompany(expertInfo.getUnit());
			expertConfirm.setExpertArea(expertInfo.getUnit().getArea());
			expertConfirm.setExpertLevel(Constants.Expert_Status_Work);
			expertConfirmService.save(expertConfirm);			
		}
		
		/*if(expertConfirm.getKindTwo()!=null&&!expertConfirm.getKindTwo().equalsIgnoreCase("")&&expertConfirm.getSpecialTwo()!=null&&!expertConfirm.getSpecialTwo().equalsIgnoreCase("")){
			expertConfirm = new ExpertConfirm();
			expertConfirm.setId(expertConfirm.getExpertCode()+"-2");
			expertConfirm.setExpertKind(expertConfirm.getKindTwo());
			expertConfirm.setExpertSpecial(expertConfirm.getSpecialTwo());
			expertConfirm.setExpertSeries(expertConfirm.getSeriesTwo());
			expertConfirm.setExpertInfo(expertInfo);
			expertConfirmService.save(expertConfirm);			
		}*/
		
		//已审核通过的专家记为4
		expertInfoService.updateRegStep(Constants.Register_Status_Accept, expertConfirm.getExpertInfo().getUserId());
		addMessage(redirectAttributes, "保存专家确认'" + expertConfirm.getExpertInfo().getName() + "'成功");
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
		return "modules/expmanage/confirmNote";
	}
	
	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "save")
	public String save(ExpertConfirm expertConfirm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertConfirm)){
			return form(expertConfirm, model);
		}
		expertConfirmService.save(expertConfirm);
		addMessage(redirectAttributes, "保存专家确认'" + expertConfirm.getExpertInfo().getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/expmanage/expertConfirm/?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "saveuser")
	public String saveuser(@ModelAttribute("user") User user, String oldLoginName, String newPassword, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			user.setPassword(SystemService.entryptPassword(newPassword));
		}else{
			user.setPassword(UserUtils.getUserById(user.getId()).getPassword());
		}
		if (!beanValidator(model, user)){
			return expedit(request.getParameter("expid"), model);
		}
		if (!"true".equals(checkLoginName(oldLoginName, user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return expedit(request.getParameter("expid"), model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		//model.addAttribute("allRoles", systemService.findAllRole());
		return "redirect:"+Global.getAdminPath()+"/expmanage/expedit/?repage";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "savebase", method=RequestMethod.POST)
	public String savebase(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes/*,@RequestParam("picture0") MultipartFile file*/, HttpServletRequest request,HttpServletResponse response) {
		User user = UserUtils.getUser();
		expertInfo.setUnit(user.getCompany());
		if (!beanValidator(model, expertInfo)){
			return baseform(request.getParameter("expid"), model);
		}
		ExpertConfirm expertConfirm = get(request.getParameter("expid"));
		expertConfirm.setExpertKind(expertInfo.getSpecialKind1());
		expertConfirm.setExpertSpecial(expertInfo.getKind1Special1());
		expertConfirm.setDeptormanageAdvice(request.getParameter("deptormanageAdvice"));
		//保留注册状态
		expertInfo.setRegStep(Constants.Register_Status_Accept);
				
		
		/*try {
			expertInfo.setPicture(file.getBytes());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
		//保留注册状态
		//expertInfo.setRegStep("4");
		expertInfoService.save(expertInfo);
		expertConfirmService.save(expertConfirm);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return explist(expertConfirm,request,response, model);
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "savework", method=RequestMethod.POST)
	public String savework(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, expertInfo)){
			return workform(request.getParameter("expid"), model);
		}
		
		
		//保留注册状态
		//expertInfo.setRegStep("4");
		
		expertInfoService.updateStepTwo(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
	    return workform(request.getParameter("expid"), model);
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "saveapply", method=RequestMethod.POST)
	public String saveapply(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, expertInfo)){
			return applyform(request.getParameter("expid"), model);
		}
		ExpertConfirm expertConfirm = get(request.getParameter("expid"));
		expertConfirm.setExpertKind(expertInfo.getSpecialKind1());
		expertConfirm.setExpertSpecial(expertInfo.getKind1Special1());
		expertConfirm.setExpertSeries(expertInfo.getCertSeries());
		expertConfirm.setDeptormanageAdvice(request.getParameter("deptormanageAdvice"));
		//保留注册状态
		expertInfo.setRegStep(Constants.Register_Status_Accept);
				
		expertInfoService.saveStepThree(expertInfo);
		expertConfirmService.save(expertConfirm);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
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
		return applyform(request.getParameter("expid"), model);
	}
	
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
    		Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user); 
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
    }

	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "impnote")
	public String impnote(ExpertConfirm expertConfirm, Model model, RedirectAttributes redirectAttributes) {
		expertConfirm.setKindTwo("init");
		model.addAttribute("expertConfirm", expertConfirm);
		return "modules/expmanage/importNote";
	}
	
	@RequiresPermissions("expmanage:expertConfirm:edit")
	//@Transactional(rollbackFor={Exception.class}) 
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(ExpertConfirm expertConfirm, Model model,MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
		}
		int successNum = 0;
		int failureNum = 0;
		try {
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ExpertImport> list = ei.getDataList(ExpertImport.class);
			for (ExpertImport expert : list){
				try{
					User user = new User();
					user.setName(expert.getExpertInfo().getName());
					user.setCompany(expert.getExpertCompany());
					user.setOffice(expert.getExpertCompany());
					user.setLoginName(expert.getId());
					user.setMobile(expert.getExpertPhone());
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						//默认把专家角色分给新用户
						List<Role> roleList = Lists.newArrayList();
						Role role = systemService.getRole(Constants.Sys_Role_Expert);
						if(role!=null){
							roleList.add(role);
							user.setRoleList(roleList);
						}
						
						systemService.saveUser(user);
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
					User ur = null;
					ur = systemService.getUserByLoginName(user.getLoginName());
					
					Office cy = officeService.get(ur.getCompany().getId());
					
					//表示已成为专家
					ExpertInfo expertInfo = new ExpertInfo();
					expertInfo.setRegStep(Constants.Register_Status_Accept);
					
					// 保存专家信息
					Date dt = DateUtils.parseDate("1900-01-01");
					expertInfo.setUserId(ur.getId());
					expertInfo.setName(ur.getName());
					expertInfo.setSex(Constants.Expert_Sex_Boy);
					expertInfo.setUnit(ur.getCompany());
					expertInfo.setSpecialKind1(expert.getExpertKind());
					expertInfo.setKind1Special1(expert.getExpertSpecial());
					expertInfo.setTechnical(expert.getExpertTechnical());
					expertInfo.setMobile(expert.getExpertPhone());
					expertInfo.setBirthdate(dt);
					expertInfo.setSpecialFrom(dt);
					expertInfo.setSpecialTo(dt);
					expertInfo.setStartworkTime(dt);
					expertInfoService.save(expertInfo);
					
					//保存专家审批
					ExpertConfirm eConfirm = new ExpertConfirm();
					expert.setExpertInfo(expertInfo);
					expert.setExpertArea(cy.getArea());
					expert.setExpertLevel(Constants.Expert_Status_Work);
					expert.setSpecialFrom(dt);
					expert.setSpecialTo(dt);
					BeanUtils.copyProperties(eConfirm, expert);
					expertConfirmService.save(eConfirm);			
					
					successNum++;
					
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+expert.getId()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+expert.getId()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		expertConfirm.setKindTwo("import");
		expertConfirm.setSpecialTwo(String.valueOf(successNum));
		expertConfirm.setSpecialist(String.valueOf(failureNum));
		model.addAttribute("expertConfirm", expertConfirm);
		return "modules/expmanage/importNote";
    }
	
	@RequiresPermissions("expmanage:expertConfirm:edit")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(ExpertConfirm expertConfirm, Model model,HttpServletResponse response, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {
            String fileName = "专家数据导入模板.xlsx";
    		List<ExpertConfirm> list = Lists.newArrayList(); list.add(expertConfirmService.find(new Page<ExpertConfirm>(request, response), expertConfirm).getList().get(0));
    		new ExportExcel("专家数据", ExpertConfirm.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		expertConfirm.setKindTwo("init");
		model.addAttribute("expertConfirm", expertConfirm);
		return "modules/expmanage/importNote";
    }

	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	
	@RequiresUser
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
    
	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		ExpertConfirm expertConfirm = get(id);
		expertConfirmService.delete(id);
		expertInfoService.updateRegStep(Constants.Register_Status_Third, expertConfirm.getExpertInfo().getUserId());
		addMessage(redirectAttributes, "删除专家确认成功");
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
		return "redirect:"+Global.getAdminPath()+"/expmanage/explist/?repage";
	}

	@RequiresPermissions("expmanage:expertConfirm:edit")
	@RequestMapping(value = "expdelete")
	public String expdelete(String id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		expertConfirmService.delete(id);
		addMessage(redirectAttributes, "删除专家确认成功");
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
		return "redirect:"+Global.getAdminPath()+"/expmanage/expertConfirm/?repage";
	}

	@RequiresPermissions("experts:expertInfo:reg")
	@RequestMapping(value = "expnew")
	public String expnew(ExpertInfo expertInfo, Model model) {
		model.addAttribute("expertInfo",expertInfo);
		//model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/expmanage/baseNew";
	}

	@RequiresPermissions("experts:expertInfo:view")
	@RequestMapping(value = "formi")
	public String formi(ExpertInfo expertInfo, Model model) {
		model.addAttribute("expertInfo", expertInfo);
		return "experts/expertInfoForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "adduser")
	public String adduser(@ModelAttribute("user") User user, String oldLoginName, String newPassword, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			user.setPassword(SystemService.entryptPassword(newPassword));
		}
		if (!beanValidator(model, user)){
			return expedit(request.getParameter("expid"), model);
		}
		if (!"true".equals(checkLoginName(oldLoginName, user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return expedit(request.getParameter("expid"), model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "modules/expmanage/baseNew";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "addbase", method=RequestMethod.POST)
	public String addbase(ExpertInfo expertInfo, Model model, String oldLoginName, String newPassword, HttpServletRequest request, RedirectAttributes redirectAttributes/*,@RequestParam("picture0") MultipartFile file*/) {
		if (!beanValidator(model, expertInfo)){
			return formi(expertInfo, model);
		}
		
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
		}
		User user = new User();
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		user.setName(expertInfo.getName());
		user.setLoginName(expertInfo.getUser().getLoginName());
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			user.setPassword(SystemService.entryptPassword(newPassword));
		}
		if (!"true".equals(checkLoginName(oldLoginName, user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			model.addAttribute("expertInfo",expertInfo);
			return "modules/expmanage/baseNew";
		}
		//默认把专家角色分给新用户
		List<Role> roleList = Lists.newArrayList();
		Role role = systemService.getRole(Constants.Sys_Role_Expert);
		if(role!=null){
			roleList.add(role);
			user.setRoleList(roleList);
		}
		
		// 保存用户信息
		systemService.saveUser(user);
		User ur = null;
		ur = systemService.getUserByLoginName(user.getLoginName());
		
		Office cy = officeService.get(ur.getCompany().getId());
		
		//表示已成为专家
		expertInfo.setRegStep(Constants.Register_Status_Accept);
		
		// 保存专家信息
		expertInfo.setUserId(ur.getId());
		expertInfo.setUnit(ur.getCompany());
		expertInfo.setMobile(ur.getMobile());
		expertInfoService.save(expertInfo);
		
		//保存专家审批
		//系统生成专家编号
		BigDecimal seq = expertConfirmService.selectExpertSequence();
		ExpertConfirm expertConfirm = new ExpertConfirm();
		String ecode = "GXEWA_"+user.getCompany().getCode()+"_ZJ_"+expertInfo.getSpecialKind1()+"_"+expertInfo.getKind1Special1()+"_"+seq;
		expertConfirm.setId(ecode);
		expertConfirm.setDeptormanageAdvice(request.getParameter("deptormanageAdvice"));
		expertConfirm.setExpertKind(expertInfo.getSpecialKind1());
		expertConfirm.setExpertSpecial(expertInfo.getKind1Special1());
		expertConfirm.setExpertTechnical(expertInfo.getTechnical());
		expertConfirm.setExpertLevel(Constants.Expert_Status_Work);
		expertConfirm.setExpertInfo(expertInfo);
		expertConfirm.setExpertCompany(ur.getCompany());
		expertConfirm.setExpertArea(cy.getArea());
		expertConfirm.setSpecialist(expertInfo.getSpecialist());
		expertConfirm.setSpecialFrom(expertInfo.getSpecialFrom());
		expertConfirm.setSpecialTo(expertInfo.getSpecialTo());
		/*try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class); 
			BeanUtils.copyProperties(expertConfirm, expertInfo);
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
		expertConfirmService.save(expertConfirm);			
		
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/expmanage/expnew/?repage";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "addwork", method=RequestMethod.POST)
	public String addwork(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertInfo)){
			return formi(expertInfo, model);
		}
		
		
		//表示已作过第二步录入
		expertInfo.setRegStep(Constants.Register_Status_Second);
		
		expertInfoService.updateStepTwo(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "modules/expmanage/applyNew";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "addapply", method=RequestMethod.POST)
	public String addapply(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertInfo)){
			return formi(expertInfo, model);
		}
		
		
		//表示已作过第三步录入
		expertInfo.setRegStep(Constants.Register_Status_Apply);
		
		expertInfoService.saveStepThree(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "modules/experts/regNotice";
	}
	
	@ResponseBody
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "checkExpertID")
	public String checkExpertID(String oldExpertId, String expertCode) {
		if (expertCode !=null && expertCode.equals(oldExpertId)) {
			return "true";
		} else if (expertCode !=null && expertConfirmService.get(expertCode) == null) {
			return "true";
		}
		return "false";
	}

}
