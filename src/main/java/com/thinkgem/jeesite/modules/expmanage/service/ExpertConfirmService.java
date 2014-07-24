/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.expmanage.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
import com.thinkgem.jeesite.modules.expmanage.dao.ExpertConfirmDao;

/**
 * 专家确认Service
 * @author Cloudman
 * @version 2014-07-08
 */
@Component
@Transactional(readOnly = true)
public class ExpertConfirmService extends BaseService {

	@Autowired
	private ExpertConfirmDao expertConfirmDao;
	
	public ExpertConfirm get(String id) {
		return expertConfirmDao.get(id);
	}
	
	public Page<ExpertConfirm> find(Page<ExpertConfirm> page, ExpertConfirm expertConfirm) {
		DetachedCriteria dc = expertConfirmDao.createDetachedCriteria();
		if (expertConfirm.getExpertInfo()!=null){
			if(StringUtils.isNotEmpty(expertConfirm.getExpertInfo().getName())){
			dc.add(Restrictions.like("name", "%"+expertConfirm.getExpertInfo().getName()+"%"));
			}
		}
		dc.add(Restrictions.eq(ExpertConfirm.FIELD_DEL_FLAG, ExpertConfirm.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return expertConfirmDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpertConfirm expertConfirm) {
		expertConfirmDao.save(expertConfirm);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		expertConfirmDao.deleteById(id);
	}
	
}
