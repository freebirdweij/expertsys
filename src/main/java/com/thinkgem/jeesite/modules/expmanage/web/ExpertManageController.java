/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.expmanage.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.experts.service.ExpertInfoService;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
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
		model.addAttribute("user", expertConfirm.getExpertInfo().getUser());
		model.addAttribute("id", id);
		return "modules/expmanage/userForm";
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
	public String confirm(ExpertConfirm expertConfirm, Model model, RedirectAttributes redirectAttributes) {
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
			expertConfirmService.save(expertConfirm);			
		}
		
		if(expertConfirm.getKindTwo()!=null&&!expertConfirm.getKindTwo().equalsIgnoreCase("")&&expertConfirm.getSpecialTwo()!=null&&!expertConfirm.getSpecialTwo().equalsIgnoreCase("")){
			expertConfirm = new ExpertConfirm();
			expertConfirm.setId(expertConfirm.getExpertCode()+"-2");
			expertConfirm.setExpertKind(expertConfirm.getKindTwo());
			expertConfirm.setExpertSpecial(expertConfirm.getSpecialTwo());
			expertConfirm.setExpertSeries(expertConfirm.getSeriesTwo());
			expertConfirm.setExpertInfo(expertInfo);
			expertConfirmService.save(expertConfirm);			
		}
		
		//已审核通过的专家记为4
		expertInfoService.updateRegStep("4", expertConfirm.getExpertInfo().getUserId());
		addMessage(redirectAttributes, "保存专家确认'" + expertConfirm.getExpertInfo().getName() + "'成功");
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
		return expedit(request.getParameter("expid"), model);
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "savebase", method=RequestMethod.POST)
	public String savebase(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes,@RequestParam("picture0") MultipartFile file, HttpServletRequest request) {
		User user = UserUtils.getUser();
		expertInfo.setUnit(user.getCompany());
		if (!beanValidator(model, expertInfo)){
			return baseform(request.getParameter("expid"), model);
		}
		
		try {
			expertInfo.setPicture(file.getBytes());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//保留注册状态
		expertInfo.setRegStep("4");
		expertInfoService.updateStepOne(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return baseform(request.getParameter("expid"), model);
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "savework", method=RequestMethod.POST)
	public String savework(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, expertInfo)){
			return workform(request.getParameter("expid"), model);
		}
		
		
		//保留注册状态
		expertInfo.setRegStep("4");
		
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
		expertInfo.setRegStep("4");
				
		expertInfoService.updateStepThree(expertInfo);
		expertConfirmService.save(expertConfirm);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
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

	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
    }
	
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/user/?repage";
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
	public String delete(String id, RedirectAttributes redirectAttributes) {
		expertConfirmService.delete(id);
		addMessage(redirectAttributes, "删除专家确认成功");
		return "redirect:"+Global.getAdminPath()+"/expmanage/expertConfirm/?repage";
	}

	@RequiresPermissions("experts:expertInfo:reg")
	@RequestMapping(value = "expnew")
	public String expnew(ExpertInfo expertInfo, Model model) {
		User user = UserUtils.getUser();
		expertInfo = expertInfoService.get(user.getId());
		if(expertInfo==null){
			expertInfo = new ExpertInfo();
			expertInfo.setName(user.getName());
			expertInfo.setUserId(user.getId());
			model.addAttribute("expertInfo", expertInfo);
			return "modules/expmanage/stepOne";
		}else if(expertInfo.getUserId()==null||expertInfo.getUserId().equalsIgnoreCase("")){
			expertInfo = new ExpertInfo();
			expertInfo.setName(user.getName());
			expertInfo.setUserId(user.getId());
			model.addAttribute("expertInfo", expertInfo);
			return "modules/experts/stepOne";
		}else if(expertInfo.getRegStep()!=null&&expertInfo.getRegStep().equalsIgnoreCase("1")){
			model.addAttribute("expertInfo", expertInfo);
			return "modules/experts/stepTwo";
		}else if(expertInfo.getRegStep()!=null&&expertInfo.getRegStep().equalsIgnoreCase("2")){
			model.addAttribute("expertInfo", expertInfo);
			return "modules/experts/stepThree";
		}else if(expertInfo.getRegStep()!=null&&expertInfo.getRegStep().equalsIgnoreCase("3")){
			model.addAttribute("expertInfo", expertInfo);
			return "modules/experts/regNotice";
		}
		model.addAttribute("user", new User());
		return "modules/expmanage/userNew";
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
	public String addbase(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes,@RequestParam("picture0") MultipartFile file) {
		User user = UserUtils.getUser();
		expertInfo.setUnit(user.getCompany());
		if (!beanValidator(model, expertInfo)){
			return formi(expertInfo, model);
		}
		
		try {
			expertInfo.setPicture(file.getBytes());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		//表示已作过第一步录入
		expertInfo.setRegStep("1");
		
		expertInfoService.save(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "modules/expmanage/workNew";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "addwork", method=RequestMethod.POST)
	public String addwork(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertInfo)){
			return formi(expertInfo, model);
		}
		
		
		//表示已作过第二步录入
		expertInfo.setRegStep("2");
		
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
		expertInfo.setRegStep("3");
		
		expertInfoService.updateStepThree(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "modules/experts/regNotice";
	}
	
}
