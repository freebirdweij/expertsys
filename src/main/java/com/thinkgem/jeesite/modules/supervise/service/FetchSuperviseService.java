/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.supervise.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.supervise.entity.FetchSupervise;
import com.thinkgem.jeesite.modules.supervise.dao.FetchSuperviseDao;

/**
 * 对项目抽取进行监督Service
 * @author Cloudman
 * @version 2014-08-03
 */
@Component
@Transactional(readOnly = true)
public class FetchSuperviseService extends BaseService {

	@Autowired
	private FetchSuperviseDao fetchSuperviseDao;
	
	public FetchSupervise get(String id) {
		return fetchSuperviseDao.get(id);
	}
	
	public Page<FetchSupervise> find(Page<FetchSupervise> page, FetchSupervise fetchSupervise) {
		DetachedCriteria dc = fetchSuperviseDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(fetchSupervise.getName())){
			dc.add(Restrictions.like("name", "%"+fetchSupervise.getName()+"%"));
		}
		dc.add(Restrictions.eq(FetchSupervise.FIELD_DEL_FLAG, FetchSupervise.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return fetchSuperviseDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(FetchSupervise fetchSupervise) {
		fetchSuperviseDao.save(fetchSupervise);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		fetchSuperviseDao.deleteById(id);
	}
	
}
