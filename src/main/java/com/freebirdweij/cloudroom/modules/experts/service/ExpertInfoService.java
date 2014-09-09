/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.freebirdweij.cloudroom.modules.experts.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.freebirdweij.cloudroom.common.persistence.Page;
import com.freebirdweij.cloudroom.common.persistence.Parameter;
import com.freebirdweij.cloudroom.common.service.BaseService;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.modules.experts.dao.ExpertInfoDao;
import com.freebirdweij.cloudroom.modules.experts.entity.ExpertInfo;

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
	public int updateExpertInfo(ExpertInfo expertInfo){
		return expertInfoDao.updateExpertInfo(expertInfo);
	}
	
	public int updateRegStep(String regStep,String userId){
		return expertInfoDao.updateRegStep(regStep, userId);
	}
	
	public Page<ExpertInfo> find(Page<ExpertInfo> page, ExpertInfo expertInfo) {
		DetachedCriteria dc = expertInfoDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(expertInfo.getName())){
			dc.add(Restrictions.like("name", "%"+expertInfo.getName()+"%"));
		}
		if (StringUtils.isNotEmpty(expertInfo.getRegStep())){
			dc.add(Restrictions.eq("regStep", expertInfo.getRegStep()));
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
    
	public int saveStepOne(ExpertInfo expertInfo){
		return expertInfoDao.saveStepOne(expertInfo);
	}
	
	public int saveStepTwo(ExpertInfo expertInfo){
		return expertInfoDao.saveStepTwo(expertInfo);
	}
	
    public int saveStepThree(ExpertInfo expertInfo){
	return expertInfoDao.saveStepThree(expertInfo);
    }
}
