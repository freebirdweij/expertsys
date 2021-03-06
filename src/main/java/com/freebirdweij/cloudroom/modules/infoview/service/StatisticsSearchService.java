/**
 * There are <a href="https://github.com/freebirdweij/cloudroom">CloudRoom</a> code generation
 */
package com.freebirdweij.cloudroom.modules.infoview.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.freebirdweij.cloudroom.common.persistence.Page;
import com.freebirdweij.cloudroom.common.service.BaseService;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.modules.infoview.dao.StatisticsSearchDao;
import com.freebirdweij.cloudroom.modules.infoview.entity.StatisticsSearch;

/**
 * 统计查询模块Service
 * @author Cloudman
 * @version 2014-08-03
 */
@Component
@Transactional(readOnly = true)
public class StatisticsSearchService extends BaseService {

	@Autowired
	private StatisticsSearchDao statisticsSearchDao;
	
	public StatisticsSearch get(String id) {
		return statisticsSearchDao.get(id);
	}
	
	public Page<StatisticsSearch> find(Page<StatisticsSearch> page, StatisticsSearch statisticsSearch) {
		DetachedCriteria dc = statisticsSearchDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(statisticsSearch.getName())){
			dc.add(Restrictions.like("name", "%"+statisticsSearch.getName()+"%"));
		}
		dc.add(Restrictions.eq(StatisticsSearch.FIELD_DEL_FLAG, StatisticsSearch.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return statisticsSearchDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(StatisticsSearch statisticsSearch) {
		statisticsSearchDao.save(statisticsSearch);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		statisticsSearchDao.deleteById(id);
	}
	
}
