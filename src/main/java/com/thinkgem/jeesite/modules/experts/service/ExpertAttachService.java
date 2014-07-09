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
import com.thinkgem.jeesite.modules.experts.entity.ExpertAttach;
import com.thinkgem.jeesite.modules.experts.dao.ExpertAttachDao;

/**
 * 专家Service
 * @author Cloudman
 * @version 2014-06-23
 */
@Component
@Transactional(readOnly = true)
public class ExpertAttachService extends BaseService {

	@Autowired
	private ExpertAttachDao expertAttachDao;
	
	public ExpertAttach get(String id) {
		return expertAttachDao.get(id);
	}
	
	public Page<ExpertAttach> find(Page<ExpertAttach> page, ExpertAttach expertAttach) {
		DetachedCriteria dc = expertAttachDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(expertAttach.getAttachName())){
			dc.add(Restrictions.like("name", "%"+expertAttach.getAttachName()+"%"));
		}
		dc.add(Restrictions.eq(ExpertAttach.FIELD_DEL_FLAG, ExpertAttach.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return expertAttachDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpertAttach expertAttach) {
		expertAttachDao.save(expertAttach);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		expertAttachDao.deleteById(id);
	}
	
}
