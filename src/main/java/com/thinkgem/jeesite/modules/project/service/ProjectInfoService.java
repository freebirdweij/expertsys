/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.project.service;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.expfetch.entity.ProjectExpert;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;
import com.thinkgem.jeesite.modules.project.dao.ProjectInfoDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 项目信息Service
 * @author Cloudman
 * @version 2014-07-08
 */
@Component
@Transactional(readOnly = true)
public class ProjectInfoService extends BaseService {
	
	//private final String PROJECT_STATUS_DISCUSS = "1";//讨论阶段的项目状态

	//private final String PROJECT_STATUS_BEFORE_REVIEW = "3";//评审前的项目状态

	//private final String PROJECT_STATUS_REVIEW_PASSED = "4";//评审通过

	//private final String PROJECT_STATUS_DONE = "7";//完工的项目

	//private final String PROJECT_STATUS_ACCEPTED = "8";//验收通过的项目

	@Autowired
	private ProjectInfoDao projectInfoDao;
	
	public ProjectInfo get(String id) {
		return projectInfoDao.get(id);
	}
	
	public BigDecimal selectProjectSequence(){
		return projectInfoDao.selectProjectSequence();
	}
	
	public int updateProjectStatus(String prjStatus,String prjid){
		return projectInfoDao.updateProjectStatus(prjStatus,prjid);
	}
	
	public int updateProjectStatusAndParent(String prjStatus,String parentid,String prjid){
		return projectInfoDao.updateProjectStatusAndParent(prjStatus,parentid,prjid);
	}
	
	public Page<ProjectInfo> find(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(projectInfo.getId())){
			dc.add(Restrictions.eq("id", projectInfo.getId()));
		}
		if (StringUtils.isNotEmpty(projectInfo.getPrjName())){
			dc.add(Restrictions.like("prjName", "%"+projectInfo.getPrjName()+"%"));
		}
		if (projectInfo.getUnit()!=null&&StringUtils.isNotEmpty(projectInfo.getUnit().getId())){
			dc.add(Restrictions.eq("prjDuty", projectInfo.getUnit().getId()));
		}
		if (StringUtils.isNotEmpty(projectInfo.getPrjStatus())){
			dc.add(Restrictions.eq("prjStatus", projectInfo.getPrjStatus()));
		}
		
		//限制本用户单位项目
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			dc.add(Restrictions.eq("unit.id", user.getCompany().getId()));
		}
		
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public List<ProjectInfo> findProjectsByIds(Page<ProjectInfo> page, String prjs[]) {
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		if (prjs!=null){
			dc.add(Restrictions.in("id", prjs));
		}
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc).getList();
	}
	
	public Page<ProjectInfo> findReviewing(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		dc.add(Restrictions.eq("prjStatus", Constants.Project_Status_Start));
		
		//限治本用户单位项目
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			dc.add(Restrictions.eq("unit.id", user.getCompany().getId()));
		}
		
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public Page<ProjectInfo> findSuperviseReviewing(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		updateProjectStatusToWork();
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		dc.add(Restrictions.eq("prjStatus", Constants.Project_Status_Apply));
		
		//限治本用户单位项目
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			dc.add(Restrictions.eq("unit.id", user.getCompany().getId()));
		}
		
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public Page<ProjectInfo> findSuperviseProjects(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		DetachedCriteria dc = DetachedCriteria.forClass(ProjectInfo.class,"p");
		dc.createAlias("p.unit", "u");
		if (StringUtils.isNotEmpty(projectInfo.getId())){
			dc.add(Restrictions.eq("id", projectInfo.getId()));
		}
		if (StringUtils.isNotEmpty(projectInfo.getPrjName())){
			dc.add(Restrictions.like("prjName", "%"+projectInfo.getPrjName()+"%"));
		}
		if (projectInfo.getPrjMoney()!=null&&!projectInfo.getPrjMoney().equals("")){
			dc.add(Restrictions.ge("prjMoney", projectInfo.getPrjMoney()));
		}
		if (projectInfo.getPrjBegin()!=null&&!projectInfo.getPrjBegin().equals("")){
			dc.add(Restrictions.le("prjBegin", projectInfo.getPrjBegin()));
			dc.add(Restrictions.ge("prjEnd", projectInfo.getPrjBegin()));
		}
		if (StringUtils.isNotEmpty(projectInfo.getPrjStatus())){
			dc.add(Restrictions.eq("prjStatus", projectInfo.getPrjStatus()));
		}
		if (projectInfo.getUnit()!=null&&StringUtils.isNotEmpty(projectInfo.getUnit().getId())){
			dc.add(Restrictions.eq("u.id", projectInfo.getUnit().getId()));
		}
		
		//限治本用户单位项目
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			dc.add(Restrictions.eq("unit.id", user.getCompany().getId()));
		}
		
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public Page<ProjectInfo> findRedrawReviewing(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		updateProjectStatusToWork();
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		dc.add(Restrictions.eq("prjStatus", Constants.Project_Status_Apply));
		
		//限治本用户单位项目
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			dc.add(Restrictions.eq("unit.id", user.getCompany().getId()));
		}
		
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public Page<ProjectInfo> findAccepting(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		updateProjectStatusToWork();
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		
		//限治本用户单位项目
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			dc.add(Restrictions.eq("unit.id", user.getCompany().getId()));
		}
		
		dc.add(Restrictions.eq("prjStatus", Constants.Project_Status_Work));
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public Page<ProjectInfo> findSaveing(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		updateProjectStatusToWork();
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		
		//限治本用户单位项目
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			dc.add(Restrictions.eq("unit.id", user.getCompany().getId()));
		}
		
		dc.add(Restrictions.eq("prjStatus", Constants.Project_Status_Received));
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public Page<ProjectInfo> findSuperviseAccepting(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		updateProjectStatusToSave();
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		dc.add(Restrictions.eq("prjStatus", Constants.Project_Status_Receive));
		
		//限治本用户单位项目
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			dc.add(Restrictions.eq("unit.id", user.getCompany().getId()));
		}
		
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public Page<ProjectInfo> findRedrawAccepting(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		updateProjectStatusToSave();
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		dc.add(Restrictions.eq("prjStatus", Constants.Project_Status_Receive));
		
		//限治本用户单位项目
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			dc.add(Restrictions.eq("unit.id", user.getCompany().getId()));
		}
		
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public boolean updateProjectStatusToWork(){
		Criteria dc = projectInfoDao.getSession().createCriteria(ProjectInfo.class, "p");
		DetachedCriteria subdc = DetachedCriteria.forClass(ProjectExpert.class, "e");
		subdc.add(Restrictions.eqProperty("p.id","e.prjProjectInfo.id")).setProjection(Projections.id());
		
		dc.add(Restrictions.eq("p.prjStatus", Constants.Project_Status_Apply));
		subdc.add(Restrictions.eq("e.fetchStatus",Constants.Fetch_Review_Sussess));
		subdc.add(Restrictions.lt("e.reviewEnd", DateUtils.parseDate(DateUtils.getDateTime())));
		dc.add(Restrictions.eq("p.delFlag", ProjectInfo.DEL_FLAG_NORMAL));
		dc.add(Subqueries.exists(subdc));
		@SuppressWarnings("unchecked")
		List<ProjectInfo> plist = dc.list();
		for(ProjectInfo pi:plist){
			updateProjectStatus(Constants.Project_Status_Work,pi.getId());
		}
		return true;
	}
	
	public boolean updateProjectStatusToReceived(){
		Criteria dc = projectInfoDao.getSession().createCriteria(ProjectInfo.class, "p");
		DetachedCriteria subdc = DetachedCriteria.forClass(ProjectExpert.class, "e");
		subdc.add(Restrictions.eqProperty("p.id","e.prjProjectInfo.id")).setProjection(Projections.id());
		
		dc.add(Restrictions.eq("p.prjStatus", Constants.Project_Status_Receive));
		subdc.add(Restrictions.eq("e.fetchStatus",Constants.Fetch_Accept_Sussess));
		subdc.add(Restrictions.lt("e.reviewEnd", DateUtils.parseDate(DateUtils.getDateTime())));
		dc.add(Restrictions.eq("p.delFlag", ProjectInfo.DEL_FLAG_NORMAL));
		dc.add(Subqueries.exists(subdc));
		@SuppressWarnings("unchecked")
		List<ProjectInfo> plist = dc.list();
		for(ProjectInfo pi:plist){
			updateProjectStatus(Constants.Project_Status_Received,pi.getId());
		}
		return true;
	}
	
	public boolean updateProjectStatusToSave(){
		Criteria dc = projectInfoDao.getSession().createCriteria(ProjectInfo.class, "p");
		DetachedCriteria subdc = DetachedCriteria.forClass(ProjectExpert.class, "e");
		subdc.add(Restrictions.eqProperty("p.id","e.prjProjectInfo.id")).setProjection(Projections.id());
		
		dc.add(Restrictions.eq("p.prjStatus", Constants.Project_Status_End));
		subdc.add(Restrictions.eq("e.fetchStatus",Constants.Fetch_Accepted_Sussess));
		subdc.add(Restrictions.lt("e.reviewEnd", DateUtils.parseDate(DateUtils.getDateTime())));
		dc.add(Restrictions.eq("p.delFlag", ProjectInfo.DEL_FLAG_NORMAL));
		dc.add(Subqueries.exists(subdc));
		@SuppressWarnings("unchecked")
		List<ProjectInfo> plist = dc.list();
		for(ProjectInfo pi:plist){
			updateProjectStatus(Constants.Project_Status_Save,pi.getId());
		}
		return true;
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectInfo projectInfo) {
		projectInfoDao.save(projectInfo);
	}
	
	@Transactional(readOnly = false)
	public int updateRecordTwo(ProjectInfo projectInfo){
		return projectInfoDao.updateRecordTwo(projectInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		projectInfoDao.deleteById(id);
	}
	
}
