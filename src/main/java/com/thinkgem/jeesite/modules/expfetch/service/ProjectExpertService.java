/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.expfetch.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.expfetch.entity.ProjectExpert;
import com.thinkgem.jeesite.modules.expfetch.dao.ProjectExpertDao;

/**
 * 对项目进行专家抽取Service
 * @author Cloudman
 * @version 2014-07-12
 */
@Component
@Transactional(readOnly = true)
public class ProjectExpertService extends BaseService {

	@Autowired
	private ProjectExpertDao projectExpertDao;
	
	public ProjectExpert get(String id) {
		return projectExpertDao.get(id);
	}
	
	public Page<ProjectExpert> find(Page<ProjectExpert> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = projectExpertDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(projectExpert.getPrjProjectInfo().getPrjName())){
			dc.add(Restrictions.like("name", "%"+projectExpert.getPrjProjectInfo().getPrjName()+"%"));
		}
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectExpertDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectExpert projectExpert) {
		projectExpertDao.save(projectExpert);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		projectExpertDao.deleteById(id);
	}
	
}
