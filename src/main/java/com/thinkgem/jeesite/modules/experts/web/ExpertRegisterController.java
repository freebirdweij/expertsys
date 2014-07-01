/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.experts.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

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
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.HibernateSessionFactory;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.experts.service.ExpertInfoService;

/**
 * 专家Controller
 * @author Cloudman
 * @version 2014-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/experts")
public class ExpertRegisterController extends BaseController {

	@Autowired
	private ExpertInfoService expertInfoService;
	
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
		expertInfo.setName(user.getName());
		model.addAttribute("expertInfo", expertInfo);
		return "modules/experts/stepOne";
	}

	@RequiresPermissions("experts:expertInfo:view")
	@RequestMapping(value = "form")
	public String form(ExpertInfo expertInfo, Model model) {
		model.addAttribute("expertInfo", expertInfo);
		return "experts/expertInfoForm";
	}

	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "save1", method=RequestMethod.POST)
	public String save(ExpertInfo expertInfo, Model model, RedirectAttributes redirectAttributes,@RequestParam("picture0") MultipartFile file) {
		if (!beanValidator(model, expertInfo)){
			return form(expertInfo, model);
		}
		
		//专家信息表的user_id与用户标id保持一致。
		User user = UserUtils.getUser();
		expertInfo.setUserId(user.getId());
		//Session session = sessionFactory.openSession();
		//Blob blob = null;
		try {
			expertInfo.setPicture(file.getBytes());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//expertInfo.setPicture(blob);
		
		expertInfoService.save(expertInfo);
		addMessage(redirectAttributes, "保存专家'" + expertInfo.getName() + "'成功");
		return "modules/experts/stepTwo";
	}
	
	@RequiresPermissions("experts:expertInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		expertInfoService.delete(id);
		addMessage(redirectAttributes, "删除专家成功");
		return "redirect:"+Global.getAdminPath()+"/experts/expertInfo/?repage";
	}

}
