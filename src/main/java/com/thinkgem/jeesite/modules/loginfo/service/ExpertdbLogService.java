/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.loginfo.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.loginfo.entity.ExpertdbLog;
import com.thinkgem.jeesite.modules.loginfo.dao.ExpertdbLogDao;

/**
 * 日志处理模块Service
 * @author Cloudman
 * @version 2014-08-25
 */
@Component
@Transactional(readOnly = true)
public class ExpertdbLogService extends BaseService {

	@Autowired
	private ExpertdbLogDao expertdbLogDao;
	
	public ExpertdbLog get(String id) {
		return expertdbLogDao.get(id);
	}
	
	public Page<ExpertdbLog> find(Page<ExpertdbLog> page, ExpertdbLog expertdbLog) {
		DetachedCriteria dc = expertdbLogDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(expertdbLog.getOperation())){
			dc.add(Restrictions.like("name", "%"+expertdbLog.getOperation()+"%"));
		}
		dc.add(Restrictions.eq(ExpertdbLog.FIELD_DEL_FLAG, ExpertdbLog.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return expertdbLogDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpertdbLog expertdbLog) {
		expertdbLogDao.save(expertdbLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		expertdbLogDao.deleteById(id);
	}
	
}
