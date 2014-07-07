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
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.experts.dao.ExpertInfoDao;

/**
 * 专家Service
 * @author Cloudman
 * @version 2014-06-23
 */
@Component
@Transactional(readOnly = true)
public class ExpertInfoService extends BaseService {

	@Autowired
	private ExpertInfoDao expertInfoDao;
	
	public ExpertInfo get(String id) {
		return expertInfoDao.get(id);
	}
	
	public Page<ExpertInfo> find(Page<ExpertInfo> page, ExpertInfo expertInfo) {
		DetachedCriteria dc = expertInfoDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(expertInfo.getName())){
			dc.add(Restrictions.like("name", "%"+expertInfo.getName()+"%"));
		}
		dc.add(Restrictions.eq(ExpertInfo.FIELD_DEL_FLAG, ExpertInfo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return expertInfoDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpertInfo expertInfo) {
		expertInfoDao.save(expertInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		expertInfoDao.deleteById(id);
	}
	
	public int updateStepOne(ExpertInfo expertInfo){
		return expertInfoDao.updateStepOne(expertInfo);
	}
	
	public int updateStepTwo(ExpertInfo expertInfo){
		return expertInfoDao.updateStepTwo(expertInfo);
	}
	
    public int updateStepThree(ExpertInfo expertInfo){
	return expertInfoDao.updateStepThree(expertInfo);
    }
}
