/**
 * There are <a href="https://github.com/freebirdweij/cloudroom">CloudRoom</a> code generation
 */
package com.freebirdweij.cloudroom.modules.loginfo.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.freebirdweij.cloudroom.common.persistence.Page;
import com.freebirdweij.cloudroom.common.service.BaseService;
import com.freebirdweij.cloudroom.common.utils.DateUtils;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.modules.loginfo.dao.ExpertdbLogDao;
import com.freebirdweij.cloudroom.modules.loginfo.entity.ExpertdbLog;

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
		if (StringUtils.isNotEmpty(expertdbLog.getObjectName())){
			dc.add(Restrictions.like("objectName", "%"+expertdbLog.getObjectName()+"%"));
		}
		if (expertdbLog.getObjectUser()!=null&&StringUtils.isNotEmpty(expertdbLog.getObjectUser().getName())){
			dc.add(Restrictions.like("objectUser.name", "%"+expertdbLog.getObjectUser().getName()+"%"));
		}
		if (StringUtils.isNotEmpty(expertdbLog.getObjectType())){
			dc.add(Restrictions.eq("objectType", expertdbLog.getObjectType()));
		}
		if (expertdbLog.getLogBegin()!=null&&expertdbLog.getLogEnd()!=null){
			dc.add(Restrictions.between("createDate",expertdbLog.getLogBegin(),DateUtils.addHours(expertdbLog.getLogEnd(), 24)));
		}
		dc.add(Restrictions.eq(ExpertdbLog.FIELD_DEL_FLAG, ExpertdbLog.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
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
