/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.project.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;
import com.thinkgem.jeesite.modules.project.dao.ProjectInfoDao;

/**
 * 项目信息Service
 * @author Cloudman
 * @version 2014-07-08
 */
@Component
@Transactional(readOnly = true)
public class ProjectInfoService extends BaseService {
	
	private final String PROJECT_STATUS_DISCUSS = "1";//讨论阶段的项目状态

	private final String PROJECT_STATUS_BEFORE_REVIEW = "3";//评审前的项目状态

	private final String PROJECT_STATUS_REVIEW_PASSED = "4";//评审通过

	private final String PROJECT_STATUS_DONE = "7";//完工的项目

	private final String PROJECT_STATUS_ACCEPTED = "8";//验收通过的项目

	@Autowired
	private ProjectInfoDao projectInfoDao;
	
	public ProjectInfo get(String id) {
		return projectInfoDao.get(id);
	}
	
	public Page<ProjectInfo> find(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(projectInfo.getId())){
			dc.add(Restrictions.eq("id", projectInfo.getId()));
		}
		if (StringUtils.isNotEmpty(projectInfo.getPrjName())){
			dc.add(Restrictions.like("prjName", "%"+projectInfo.getPrjName()+"%"));
		}
		if (StringUtils.isNotEmpty(projectInfo.getPrjDuty())){
			dc.add(Restrictions.like("prjDuty", "%"+projectInfo.getPrjDuty()+"%"));
		}
		if (StringUtils.isNotEmpty(projectInfo.getPrjStatus())){
			dc.add(Restrictions.eq("prjStatus", projectInfo.getPrjStatus()));
		}
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public Page<ProjectInfo> findReviewing(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		dc.add(Restrictions.between("prjStatus", PROJECT_STATUS_DISCUSS,PROJECT_STATUS_BEFORE_REVIEW));
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
	}
	
	public Page<ProjectInfo> findAccepting(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		DetachedCriteria dc = projectInfoDao.createDetachedCriteria();
		dc.add(Restrictions.eq("prjStatus", PROJECT_STATUS_DONE));
		dc.add(Restrictions.eq(ProjectInfo.FIELD_DEL_FLAG, ProjectInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectInfoDao.find(page, dc);
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
