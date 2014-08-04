/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.supervise.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.expfetch.entity.ProjectExpert;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
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
		return (FetchSupervise) fetchSuperviseDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public void save(FetchSupervise fetchSupervise) {
		fetchSuperviseDao.save(fetchSupervise);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		fetchSuperviseDao.deleteById(id);
	}
	
	public Page<Object> findStatisticsExperts(Page<Object> page, FetchSupervise fetchSupervise) {
		DetachedCriteria dc = DetachedCriteria.forClass(ProjectExpert.class, "o");
		ProjectionList projList = Projections.projectionList(); 
		projList.add(Projections.property("o.expertExpertConfirm.expertInfo.name").as("name")); 
		projList.add(Projections.count("o.id").as("count")); 
		projList.add(Projections.groupProperty("o.expertExpertConfirm.id")); 
		dc.setProjection(projList);		
		
		if (fetchSupervise.getExpertBegin()!=null&&fetchSupervise.getExpertEnd()!=null){
			dc.add(Restrictions.between("o.createDate", fetchSupervise.getExpertBegin(), fetchSupervise.getExpertEnd()));
			String sts[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,Constants.Fetch_AcceptRedraw_Sussess};
			dc.add(Restrictions.in("o.fetchStatus", sts));
		}else{
			return null;
		}
		dc.add(Restrictions.eq(FetchSupervise.FIELD_DEL_FLAG, FetchSupervise.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return fetchSuperviseDao.find(page,dc);
	}
	
	public Page<Object> findStatisticsUnits(Page<Object> page, FetchSupervise fetchSupervise) {
		DetachedCriteria dc = DetachedCriteria.forClass(ProjectExpert.class, "o");
		ProjectionList projList = Projections.projectionList(); 
		projList.add(Projections.property("o.expertExpertConfirm.expertCompany.name").as("name")); 
		projList.add(Projections.count("o.id").as("count")); 
		projList.add(Projections.groupProperty("o.expertExpertConfirm.expertCompany")); 
		dc.setProjection(projList);		
		
		if (fetchSupervise.getUnitBegin()!=null&&fetchSupervise.getUnitEnd()!=null){
			dc.add(Restrictions.between("o.createDate", fetchSupervise.getUnitBegin(), fetchSupervise.getUnitEnd()));
			String sts[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,Constants.Fetch_AcceptRedraw_Sussess};
			dc.add(Restrictions.in("o.fetchStatus", sts));
		}else{
			return null;
		}
		dc.add(Restrictions.eq(FetchSupervise.FIELD_DEL_FLAG, FetchSupervise.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return fetchSuperviseDao.find(page,dc);
	}
	
	public Page<Object> findStatisticsKinds(Page<Object> page, FetchSupervise fetchSupervise) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		ProjectionList projList = Projections.projectionList();
		
		if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Area)){
			projList.add(Projections.property("e.expertArea.name").as("name")); 
			projList.add(Projections.count("e.id").as("count")); 
			projList.add(Projections.groupProperty("e.expertArea")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Unit)){
			projList.add(Projections.property("e.expertCompany.name").as("name")); 
			projList.add(Projections.count("e.id").as("count")); 
			projList.add(Projections.groupProperty("e.expertCompany")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Type)){
			projList.add(Projections.property("e.expertKind").as("name")); 
			projList.add(Projections.count("e.id").as("count")); 
			projList.add(Projections.groupProperty("e.expertKind")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Special)){
			projList.add(Projections.property("e.expertSpecial").as("name")); 
			projList.add(Projections.count("e.id").as("count")); 
			projList.add(Projections.groupProperty("e.expertSpecial")); 
		}
		dc.setProjection(projList);		
		
		dc.add(Restrictions.eq(FetchSupervise.FIELD_DEL_FLAG, FetchSupervise.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return fetchSuperviseDao.find(page,dc);
	}
	
	public Page<Object> findStatisticsFetchs(Page<Object> page, FetchSupervise fetchSupervise) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "o");
		ProjectionList projList = Projections.projectionList();
		
		if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Area)){
			projList.add(Projections.property("o.expertExpertConfirm.expertArea.name").as("name")); 
			projList.add(Projections.count("o.id").as("count")); 
			projList.add(Projections.groupProperty("o.expertExpertConfirm.expertArea")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Unit)){
			projList.add(Projections.property("o.expertExpertConfirm.expertCompany.name").as("name")); 
			projList.add(Projections.count("o.id").as("count")); 
			projList.add(Projections.groupProperty("o.expertExpertConfirm.expertCompany")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Type)){
			projList.add(Projections.property("o.expertExpertConfirm.expertKind").as("name")); 
			projList.add(Projections.count("o.id").as("count")); 
			projList.add(Projections.groupProperty("o.expertExpertConfirm.expertKind")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Special)){
			projList.add(Projections.property("o.expertExpertConfirm.expertSpecial").as("name")); 
			projList.add(Projections.count("o.id").as("count")); 
			projList.add(Projections.groupProperty("o.expertExpertConfirm.expertSpecial")); 
		}
		dc.setProjection(projList);		
		
		if (fetchSupervise.getFetchBegin()!=null&&fetchSupervise.getFetchEnd()!=null){
			dc.add(Restrictions.between("o.createDate", fetchSupervise.getFetchBegin(), fetchSupervise.getFetchEnd()));
			String sts[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,Constants.Fetch_AcceptRedraw_Sussess};
			dc.add(Restrictions.in("o.fetchStatus", sts));
		}else{
			return null;
		}
		dc.add(Restrictions.eq(FetchSupervise.FIELD_DEL_FLAG, FetchSupervise.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return fetchSuperviseDao.find(page,dc);
	}
	
}
