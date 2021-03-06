/**
 * There are <a href="https://github.com/freebirdweij/cloudroom">CloudRoom</a> code generation
 */
package com.freebirdweij.cloudroom.modules.supervise.service;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.freebirdweij.cloudroom.common.persistence.Page;
import com.freebirdweij.cloudroom.common.service.BaseService;
import com.freebirdweij.cloudroom.common.utils.Constants;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.modules.expfetch.dao.ProjectExpertDao;
import com.freebirdweij.cloudroom.modules.expfetch.entity.ProjectExpert;
import com.freebirdweij.cloudroom.modules.expmanage.entity.ExpertConfirm;
import com.freebirdweij.cloudroom.modules.supervise.dao.FetchSuperviseDao;
import com.freebirdweij.cloudroom.modules.supervise.entity.FetchSupervise;

/**
 * 对项目抽取进行监督Service
 * @author Cloudman
 * @version 2014-08-03
 */
@Component
@Transactional(readOnly = true)
public class FetchSuperviseService extends BaseService {

	@Autowired
	private ProjectExpertDao projectExpertDao;
	
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
		Criteria dc = fetchSuperviseDao.getSession().createCriteria(ProjectExpert.class, "o");
		dc.createAlias("o.expertExpertConfirm", "e").createAlias("e.expertInfo", "i");
		if (fetchSupervise.getExpertBegin()!=null&&fetchSupervise.getExpertEnd()!=null){
			dc.add(Restrictions.between("o.createDate", fetchSupervise.getExpertBegin(), fetchSupervise.getExpertEnd()));
			String sts[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,Constants.Fetch_AcceptRedraw_Sussess,Constants.Fetch_Accepted_Sussess,Constants.Fetch_AcceptedRedraw_Sussess};
			dc.add(Restrictions.in("o.fetchStatus", sts));
		}else{
			return null;
		}
		dc.add(Restrictions.eq(FetchSupervise.FIELD_DEL_FLAG, FetchSupervise.DEL_FLAG_NORMAL));
		ProjectionList projList = Projections.projectionList(); 
		projList.add(Projections.property("i.name").as("name")); 
		projList.add(Projections.count("e.id").as("count")); 
		projList.add(Projections.groupProperty("e.id")); 
		projList.add(Projections.groupProperty("i.name")); 
		dc.setProjection(projList);		
		dc.addOrder(Order.desc("count"));
		
		return page.setList(dc.list());
	}
	
	public Page<Object> findStatisticsUnits(Page<Object> page, FetchSupervise fetchSupervise) {
		Criteria dc = fetchSuperviseDao.getSession().createCriteria(ProjectExpert.class, "o");
		dc.createAlias("o.expertExpertConfirm", "e").createAlias("e.expertCompany", "c");
		if (fetchSupervise.getUnitBegin()!=null&&fetchSupervise.getUnitEnd()!=null){
			dc.add(Restrictions.between("o.createDate", fetchSupervise.getUnitBegin(), fetchSupervise.getUnitEnd()));
			String sts[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,Constants.Fetch_AcceptRedraw_Sussess,Constants.Fetch_Accepted_Sussess,Constants.Fetch_AcceptedRedraw_Sussess};
			dc.add(Restrictions.in("o.fetchStatus", sts));
		}else{
			return null;
		}
		ProjectionList projList = Projections.projectionList(); 
		projList.add(Projections.property("c.name").as("name")); 
		projList.add(Projections.count("c.id").as("count")); 
		projList.add(Projections.groupProperty("c.id")); 
		projList.add(Projections.groupProperty("c.name")); 
		dc.setProjection(projList);		
		
		dc.add(Restrictions.eq(FetchSupervise.FIELD_DEL_FLAG, FetchSupervise.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("count"));
		return page.setList(dc.list());
	}
	
	public Page<Object> findStatisticsKinds(Page<Object> page, FetchSupervise fetchSupervise) {
		Criteria dc = fetchSuperviseDao.getSession().createCriteria(ExpertConfirm.class, "e");
		dc.createAlias("e.expertCompany", "c").createAlias("e.expertArea", "a");
		ProjectionList projList = Projections.projectionList();
		
		if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Area)){
			projList.add(Projections.property("a.name").as("name")); 
			projList.add(Projections.count("a.id").as("count")); 
			projList.add(Projections.groupProperty("a.id")); 
			projList.add(Projections.groupProperty("a.name")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Unit)){
			projList.add(Projections.property("c.name").as("name")); 
			projList.add(Projections.count("c.id").as("count")); 
			projList.add(Projections.groupProperty("c.id")); 
			projList.add(Projections.groupProperty("c.name")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Type)){
			projList.add(Projections.property("e.expertKind").as("name")); 
			projList.add(Projections.count("e.expertKind").as("count")); 
			projList.add(Projections.groupProperty("e.expertKind")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getSticsKind())&&fetchSupervise.getSticsKind().equalsIgnoreCase(Constants.Statistics_Kind_Special)){
			projList.add(Projections.property("e.expertSpecial").as("name")); 
			projList.add(Projections.count("e.expertSpecial").as("count")); 
			projList.add(Projections.groupProperty("e.expertSpecial")); 
		}else{
			return null;
		}
		dc.setProjection(projList);		
		
		dc.add(Restrictions.eq(FetchSupervise.FIELD_DEL_FLAG, FetchSupervise.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("count"));
		return page.setList(dc.list());
	}
	
	public Page<Object> findStatisticsFetchs(Page<Object> page, FetchSupervise fetchSupervise) {
		Criteria dc = fetchSuperviseDao.getSession().createCriteria(ProjectExpert.class, "o");
		dc.createAlias("o.expertExpertConfirm", "e").createAlias("e.expertCompany", "c").createAlias("e.expertArea", "a");
		if (fetchSupervise.getFetchBegin()!=null&&fetchSupervise.getFetchEnd()!=null){
			dc.add(Restrictions.between("o.createDate", fetchSupervise.getFetchBegin(), fetchSupervise.getFetchEnd()));
			String sts[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,Constants.Fetch_AcceptRedraw_Sussess,Constants.Fetch_Accepted_Sussess,Constants.Fetch_AcceptedRedraw_Sussess};
			dc.add(Restrictions.in("o.fetchStatus", sts));
		}else{
			return null;
		}
		dc.add(Restrictions.eq(FetchSupervise.FIELD_DEL_FLAG, FetchSupervise.DEL_FLAG_NORMAL));
		ProjectionList projList = Projections.projectionList();
		
		if(StringUtils.isNotEmpty(fetchSupervise.getFetchKind())&&fetchSupervise.getFetchKind().equalsIgnoreCase(Constants.Statistics_Kind_Area)){
			projList.add(Projections.property("a.name").as("name")); 
			projList.add(Projections.count("a.id").as("count")); 
			projList.add(Projections.groupProperty("a.id")); 
			projList.add(Projections.groupProperty("a.name")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getFetchKind())&&fetchSupervise.getFetchKind().equalsIgnoreCase(Constants.Statistics_Kind_Unit)){
			projList.add(Projections.property("c.name").as("name")); 
			projList.add(Projections.count("c.id").as("count")); 
			projList.add(Projections.groupProperty("c.id")); 
			projList.add(Projections.groupProperty("c.name")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getFetchKind())&&fetchSupervise.getFetchKind().equalsIgnoreCase(Constants.Statistics_Kind_Type)){
			projList.add(Projections.property("e.expertKind").as("name")); 
			projList.add(Projections.count("e.expertKind").as("count")); 
			projList.add(Projections.groupProperty("e.expertKind")); 
		}else if(StringUtils.isNotEmpty(fetchSupervise.getFetchKind())&&fetchSupervise.getFetchKind().equalsIgnoreCase(Constants.Statistics_Kind_Special)){
			projList.add(Projections.property("e.expertSpecial").as("name")); 
			projList.add(Projections.count("e.expertSpecial").as("count")); 
			projList.add(Projections.groupProperty("e.expertSpecial")); 
		}else{
			return null;
		}
		dc.setProjection(projList);		
		
		dc.addOrder(Order.desc("count"));
		return page.setList(dc.list());
	}
	
}
