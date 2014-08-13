/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.experts.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.Hibernate;
import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.HibernateSessionFactory;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.LogService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.experts.service.ExpertInfoService;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
import com.thinkgem.jeesite.modules.expmanage.service.ExpertConfirmService;

/**
 * 专家注册Controller
 * @author Cloudman
 * @version 2014-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/experts")
public class ExpertRegisterController extends BaseController {

	@Autowired
	private LogService logService;
	
	@Autowired
	private ExpertInfoService expertInfoService;
	
	@Autowired
	private ExpertConfirmService expertConfirmService;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@ModelAttribute
	public ExpertInfo get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return expertInfoService.get(id);
		}else{
			return new ExpertInfo();
		}
	}
	
	
	@RequiresPermissions("experts:expertInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExpertInfo expertInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertInfo.setCreateBy(user);
		}
        Page<ExpertInfo> page = expertInfoService.find(new Page<ExpertInfo>(request, response), expertInfo); 
        model.addAttribute("page", page);
		return "experts/expertInfoList";
	}

	@RequiresPermissions("experts:expertInfo:view")
	@RequestMapping(value = "applylist")
	public String applylist(ExpertInfo expertInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertInfo.setCreateBy(user);
		}
		expertInfo.setRegStep(Constants.Register_Status_Apply);
        Page<ExpertInfo> page = expertInfoService.find(new Page<ExpertInfo>(request, response), expertInfo); 
        model.addAttribute("page", page);
		return "modules/expmanage/confirmList";
	}

	@RequestMapping(value = "picture", method=RequestMethod.POST)
	public String uploadPicture(ExpertInfo expertInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expertInfo.setCreateBy(user);
		}
		//InputStream in = FileUtils.takeUploadFile(request);
		Session session = sessionFactory.openSession();
		byte[] bts = FileUtils.takeUploadBytes(request);
		if(bts==null){
			System.out.println("bts==null");
		}else{
			System.out.println("bts.length=="+bts.length);
		}
		Blob blob = session.getLobHelper().createBlob(bts);
		//expertInfo.setPicture(blob);
        Page<ExpertInfo> page = expertInfoService.find(new Page<ExpertInfo>(request, response), expertInfo); 
        model.addAttribute("page", page);
		return "modules/experts/stepOne";
	}
	
	
	@RequiresPermissions("experts:expertInfo:reg")
	@RequestMapping(value = "register")
	public String register(ExpertInfo expertInfo, Model model) {
		User user = UserUtils.getUser();
		expertInfo = expertInfoService.get(user.getId());
		if(expertInfo==null){
			expertInfo = new ExpertInfo();
			expertInfo.setName(user.getName());
			expertInfo.setUserId(user.getId());
			/*Date birthdate = expertInfo.getBirthdate();
			if (birthdate == null){
				birthdate = DateUtils.setDays(new Date(), 1);
				expertInfo.setBirthdate(birthdate);
			}*/
			model.addAttribute("expertInfo", expertInfo);
			return "modules/experts/stepOne";
		}else if(expertInfo.getUserId()==null||expertInfo.getUserId().equalsIgnoreCase("")){
			expertInfo = new ExpertInfo();
			expertInfo.setName(user.getName());
			expertInfo.setUserId(user.getId());
			/*Date birthdate = expertInfo.getBirthdate();
			if (birthdate == null){
				birthdate = DateUtils.setDays(new Date(), 1);
				expertInfo.setBirthdate(birthdate);
			}*/
			model.addAttribute("expertInfo", expertInfo);
			return "modules/experts/stepOne";
		}else if(expertInfo.getRegStep()!=null&&expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_First)){
			/*Date birthdate = expertInfo.getBirthdate();
			if (birthdate == null){
				birthdate = DateUtils.setDays(new Date(), 1);
				expertInfo.setBirthdate(birthdate);
			}*/
			model.addAttribute("expertInfo", expertInfo);
			return "modules/experts/stepTwo";
		}else if(expertInfo.getRegStep()!=null&&expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_Second)){
			/*Date birthdate = expertInfo.getBirthdate();
			if (birthdate == null){
				birthdate = DateUtils.setDays(new Date(), 1);
				expertInfo.setBirthdate(birthdate);
			}*/
			model.addAttribute("expertInfo", expertInfo);
			return "modules/experts/stepThree";
		}else if(expertInfo.getRegStep()!=null&&expertInfo.getRegStep().equalsIgnoreCase(Constants.Register_Status_Third)){
			/*Date birthdate = expertInfo.getBirthdate();
			if (birthdate == null){
				birthdate = DateUtils.setDays(new Date(), 1);
				expertInfo.setBirthdate(birthdate);
			}*/
			model.addAttribute("expertInfo", expertInfo);
			return "modules/experts/regNotice";
		}
		model.addAttribute("expertInfo", expertInfo);
		return "modules/experts/regNotice";
	}

	@RequiresPermissions("experts:expertInfo:view")
	@RequestMapping(value = "form")
	public String form(ExpertInfo expertInfo, Model model) {
		model.addAttribute("expertInfo", expertInfo);
		return "experts/expertInfoForm";
	}

	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "saveOne", method=RequestMethod.POST)
	public String saveOne(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes/*,@RequestParam("picture0") MultipartFile file*/) {
		User user = UserUtils.getUser();
		expertInfo.setUnit(user.getCompany());
		if (!beanValidator(model, expertInfo)){
			return form(expertInfo, model);
		}
		
		/*try {
			expertInfo.setPicture(file.getBytes());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
		
		//表示已作过第三步录入
		expertInfo.setRegStep(Constants.Register_Status_Third);
		
		expertInfoService.save(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "modules/experts/regNotice";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "saveTwo", method=RequestMethod.POST)
	public String saveTwo(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertInfo)){
			return form(expertInfo, model);
		}
		
		
		//表示已作过第二步录入
		expertInfo.setRegStep(Constants.Register_Status_Second);
		
		expertInfoService.saveStepTwo(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "modules/experts/stepThree";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "saveThree", method=RequestMethod.POST)
	public String saveThree(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expertInfo)){
			return form(expertInfo, model);
		}
		
		//表示已作过第三步录入
		expertInfo.setRegStep(Constants.Register_Status_Third);
		
		expertInfoService.saveStepThree(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "modules/experts/regNotice";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "applySave", method=RequestMethod.POST)
	public String applySave(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, expertInfo)){
			return form(expertInfo, model);
		}
		
		List<ExpertConfirm> list = expertConfirmService.findAExpert(expertInfo.getUserId());
		if(list!=null&&list.size()>0){
			ExpertConfirm expertConfirm = list.get(0);
			expertConfirmService.delete(expertConfirm.getId());
		}
		
		//表示已提出申请
		expertInfo.setRegStep(Constants.Register_Status_Apply);
		
		expertInfoService.save(expertInfo);
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
		return "modules/experts/regNotice";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		expertInfoService.delete(id);
		addMessage(redirectAttributes, "删除专家成功");
		return "redirect:"+Global.getAdminPath()+"/experts/expertInfo/?repage";
	}

}
