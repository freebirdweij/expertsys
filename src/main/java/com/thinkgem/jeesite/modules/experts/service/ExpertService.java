/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.experts.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.experts.entity.Expert;
import com.thinkgem.jeesite.modules.experts.dao.ExpertDao;

/**
 * 专家Service
 * @author Cloudman
 * @version 2014-06-22
 */
@Component
@Transactional(readOnly = true)
public class ExpertService extends BaseService {

	@Autowired
	private ExpertDao expertDao;
	
	public Expert get(String id) {
		return expertDao.get(id);
	}
	
	public Page<Expert> find(Page<Expert> page, Expert expert) {
		DetachedCriteria dc = expertDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(expert.getName())){
			dc.add(Restrictions.like("name", "%"+expert.getName()+"%"));
		}
		dc.add(Restrictions.eq(Expert.FIELD_DEL_FLAG, Expert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return expertDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Expert expert) {
		expertDao.save(expert);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		expertDao.deleteById(id);
	}
	
}
