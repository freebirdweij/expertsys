/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.expfetch.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.expfetch.entity.ProjectExpert;
import com.thinkgem.jeesite.modules.expfetch.dao.ProjectExpertDao;
import com.thinkgem.jeesite.modules.expmanage.dao.ExpertConfirmDao;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
import com.thinkgem.jeesite.modules.project.dao.ProjectInfoDao;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 对项目进行专家抽取Service
 * @author Cloudman
 * @version 2014-07-12
 */
@Component
@Transactional(readOnly = true)
public class ProjectExpertService extends BaseService {

	@Autowired
	private ProjectExpertDao projectExpertDao;
	
	@Autowired
	private OfficeDao officeDao;
		
	@Autowired
	private ExpertConfirmDao expertConfirmDao;
	
	@Autowired
	private ProjectInfoDao projectInfoDao;
	
	public ProjectExpert get(String id) {
		return projectExpertDao.get(id);
	}
	
	public Integer selectMaxFetchTime(){
		return projectExpertDao.selectMaxFetchTime();
	}
	
	public int updateProjectExpertStatus(String fetchStatus,Integer fetchTime,String prjid,String expid){
		return projectExpertDao.updateProjectExpertStatus(fetchStatus, fetchTime, prjid, expid);
	}
	
	public int updateProjectExpertReviewDate(String fetchStatus,String prjid,Date reviewBegin,Date reviewEnd){
		return projectExpertDao.updateProjectExpertReviewDate(fetchStatus,prjid,reviewBegin,reviewEnd);
	}
	
	public int updateProjectExpertAbsence(String fetchStatus,String absenceReson,String prjid,String expid){
		return projectExpertDao.updateProjectExpertAbsence(fetchStatus, absenceReson, prjid, expid);
	}
	
	public Page<ProjectExpert> find(Page<ProjectExpert> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = projectExpertDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(projectExpert.getPrjProjectInfo().getPrjName())){
			dc.add(Restrictions.like("name", "%"+projectExpert.getPrjProjectInfo().getPrjName()+"%"));
		}
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectExpertDao.find(page, dc);
	}
	
	public ProjectExpert findProjectExpertByPrjAndStatus(String prjid, String status) {
		DetachedCriteria dc = projectExpertDao.createDetachedCriteria();
		dc.createAlias("prjProjectInfo", "p");
		if (StringUtils.isNotEmpty(prjid)){
			dc.add(Restrictions.eq("p.id", prjid));
		}
		if (StringUtils.isNotEmpty(status)){
			dc.add(Restrictions.eq("fetchStatus", status));
		}
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		List<ProjectExpert> list = projectExpertDao.find(dc);
		if(list==null||list.size()==0){
			return null;
		}
		return list.get(0);
	}
	
	public List<ProjectExpert> findMutiProjectExpertByPrjAndStatus(String prjid, String status[]) {
		DetachedCriteria dc = projectExpertDao.createDetachedCriteria();
		dc.createAlias("prjProjectInfo", "p");
		if (StringUtils.isNotEmpty(prjid)){
			dc.add(Restrictions.eq("p.id", prjid));
		}
		if (status!=null){
			dc.add(Restrictions.in("fetchStatus", status));
		}
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		List<ProjectExpert> list = projectExpertDao.find(dc);
		if(list==null||list.size()==0){
			return null;
		}
		return list;
	}
	
	public List<ProjectExpert> findMyJob(Page<ProjectExpert> page, String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(ProjectExpert.class, "e");
		if (userId!=null){
			if (StringUtils.isNotEmpty(userId)){
				dc.createAlias("e.expertExpertConfirm", "c");
				dc.createAlias("c.expertInfo", "i");
				dc.add(Restrictions.eq("i.userId", userId));
			}
		}
		dc.createAlias("e.prjProjectInfo", "p");
		String fts[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,Constants.Fetch_AcceptRedraw_Sussess};
		dc.add(Restrictions.in("e.fetchStatus", fts));
		String sts[] = {Constants.Project_Status_Apply,Constants.Project_Status_Receive};
		dc.add(Restrictions.in("p.prjStatus", sts));
		dc.add(Restrictions.ge("e.reviewBegin", DateUtils.parseDate(DateUtils.getDateTime())));
		
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		//dc.addOrder(Order.desc("id"));
		return projectExpertDao.find(dc);
	}
	
	public Page<ProjectExpert> findMyHistory(Page<ProjectExpert> page, String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(ProjectExpert.class, "e");
		if (userId!=null){
			if (StringUtils.isNotEmpty(userId)){
				dc.createAlias("e.expertExpertConfirm", "c");
				dc.createAlias("c.expertInfo", "i");
				dc.add(Restrictions.eq("i.userId", userId));
			}
		}
		dc.createAlias("e.prjProjectInfo", "p");
		String fts[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,Constants.Fetch_AcceptRedraw_Sussess};
		dc.add(Restrictions.in("e.fetchStatus", fts));
		String sts[] = {Constants.Project_Status_Work,Constants.Project_Status_Receive,Constants.Project_Status_Save};
		dc.add(Restrictions.in("p.prjStatus", sts));
		dc.add(Restrictions.le("e.reviewEnd", DateUtils.parseDate(DateUtils.getDateTime())));
		
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		//dc.addOrder(Order.desc("id"));
		return projectExpertDao.find(page,dc);
	}
	
	public ExpertConfirm findAExpertByUnit(Office office, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		if (office!=null){
			if (StringUtils.isNotEmpty(office.getId())){
				dc.add(Restrictions.eq("e.expertCompany.id", office.getId()));
				dc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
			}
		}
		String kindIdsYes = projectExpert.getKindIdsYes();
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsYes, ",");
			dc.add(Restrictions.in("e.expertKind", kindids));
	    }

		String specialIdsYes = projectExpert.getSpecialIdsYes();
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsYes, ",");
			dc.add(Restrictions.in("e.expertSpecial", specialids));
	    }

		String kindIdsNo = projectExpert.getKindIdsNo();
		if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsNo, ",");
			dc.add(Restrictions.not(Restrictions.in("e.expertKind", kindids)));
	    }

		String specialIdsNo = projectExpert.getSpecialIdsNo();
		if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsNo, ",");
			dc.add(Restrictions.not(Restrictions.in("e.expertSpecial", specialids)));
	    }
		dc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		
		List<ExpertConfirm> list = expertConfirmDao.find(dc);
		
        //以下进行随机选取计算
		int resSize =list.size(); 
		int ri = 0;
		if(1<resSize){
	        Random r=new Random();   
	        int n = resSize;  
	         ri = r.nextInt(n);
		}
        


		return list.get(ri);
	}
	
	public ExpertConfirm findAExpertByUnitAndKind(Office office, String kind) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		if (office!=null){
			if (StringUtils.isNotEmpty(office.getId())){
				dc.add(Restrictions.eq("e.expertCompany.id", office.getId()));
				if(kind!=null){
				dc.add(Restrictions.eq("e.expertKind", kind));
				}
			}
		}
		dc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		
		List<ExpertConfirm> list = expertConfirmDao.find(dc);
		
        //以下进行随机选取计算
		int resSize =list.size(); 
		int ri = 0;
		if(1<resSize){
	        Random r=new Random();   
	        int n = resSize;  
	         ri = r.nextInt(n);
		}
        


		return list.get(ri);
	}
	
	public ExpertConfirm findAExpertByUnitAndKindRemoveSomeExperts(Office office, String kind,String resIds) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		if (office!=null){
			if (StringUtils.isNotEmpty(office.getId())){
				dc.add(Restrictions.eq("e.expertCompany.id", office.getId()));
				if(kind!=null){
				dc.add(Restrictions.eq("e.expertKind", kind));
				}
			}
		}
		if(resIds!=null&&!resIds.equalsIgnoreCase("")){
			String[] resids = StringUtils.split(resIds, ",");
			dc.add(Restrictions.not(Restrictions.in("e.id", resids)));
	    }
		dc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		
		List<ExpertConfirm> list = expertConfirmDao.find(dc);
		
        //以下进行随机选取计算
		int resSize =list.size(); 
		int ri = 0;
		if(1<resSize){
	        Random r=new Random();   
	        int n = resSize;  
	         ri = r.nextInt(n);
		}
        


		return list.get(ri);
	}
	
	public Page<ExpertConfirm> findExperts(Page<ExpertConfirm> page, ProjectExpert projectExpert) {
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.createAlias("e.expertArea", "a");
				subdc.add(Restrictions.in("a.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.createAlias("e.expertCompany", "c");
			subdc.add(Restrictions.in("c.id", unitids));
	    }

		String kindIdsYes = projectExpert.getKindIdsYes();
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsYes, ",");
			subdc.add(Restrictions.in("e.expertKind", kindids));
	    }

		String specialIdsYes = projectExpert.getSpecialIdsYes();
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSpecial", specialids));
	    }

		String seriesIdsYes = projectExpert.getSeriesIdsYes();
		if(seriesIdsYes!=null&&!seriesIdsYes.equalsIgnoreCase("")){
			String[] seriesids = StringUtils.split(seriesIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSeries", seriesids));
	    }

		String techIdsYes = projectExpert.getTechIdsYes();
		if(techIdsYes!=null&&!techIdsYes.equalsIgnoreCase("")){
			String[] techids = StringUtils.split(techIdsYes, ",");
			subdc.add(Restrictions.in("e.expertTechnical", techids));
	    }

		String areaIdsNo = projectExpert.getAreaIdsNo();
		if(areaIdsNo!=null&&!areaIdsNo.equalsIgnoreCase("")){
			String[] areaids = StringUtils.split(areaIdsNo, ",");
			subdc.createAlias("e.expertArea", "a");
			subdc.add(Restrictions.not(Restrictions.in("a.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.createAlias("e.expertCompany", "c");
			subdc.add(Restrictions.not(Restrictions.in("c.id", unitids)));
	    }
		
		String kindIdsNo = projectExpert.getKindIdsNo();
		if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertKind", kindids)));
	    }

		String specialIdsNo = projectExpert.getSpecialIdsNo();
		if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSpecial", specialids)));
	    }

		String seriesIdsNo = projectExpert.getSeriesIdsNo();
		if(seriesIdsNo!=null&&!seriesIdsNo.equalsIgnoreCase("")){
			String[] seriesids = StringUtils.split(seriesIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSeries", seriesids)));
	    }
		
		String techIdsNo = projectExpert.getTechIdsNo();
		if(techIdsNo!=null&&!techIdsNo.equalsIgnoreCase("")){
			String[] techids = StringUtils.split(techIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertTechnical", techids)));
	    }
		
		String discIds = projectExpert.getDiscIds();
		if(discIds!=null&&!discIds.equalsIgnoreCase("")){
			String[] resids = StringUtils.split(discIds, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.id", resids)));
	    }
		subdc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		

		subdc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		subdc.addOrder(Order.desc("id"));
		return expertConfirmDao.find(page, subdc);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpertConfirm> findExpertsByCountRest(Page<ExpertConfirm> page, ProjectExpert projectExpert) {
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.createAlias("e.expertArea", "a");
				subdc.add(Restrictions.in("a.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.createAlias("e.expertCompany", "c");
			subdc.add(Restrictions.in("c.id", unitids));
	    }

		String kindIdsYes = projectExpert.getKindIdsYes();
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsYes, ",");
			subdc.add(Restrictions.in("e.expertKind", kindids));
	    }

		String specialIdsYes = projectExpert.getSpecialIdsYes();
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSpecial", specialids));
	    }

		String seriesIdsYes = projectExpert.getSeriesIdsYes();
		if(seriesIdsYes!=null&&!seriesIdsYes.equalsIgnoreCase("")){
			String[] seriesids = StringUtils.split(seriesIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSeries", seriesids));
	    }

		String techIdsYes = projectExpert.getTechIdsYes();
		if(techIdsYes!=null&&!techIdsYes.equalsIgnoreCase("")){
			String[] techids = StringUtils.split(techIdsYes, ",");
			subdc.add(Restrictions.in("e.expertTechnical", techids));
	    }

		String areaIdsNo = projectExpert.getAreaIdsNo();
		if(areaIdsNo!=null&&!areaIdsNo.equalsIgnoreCase("")){
			String[] areaids = StringUtils.split(areaIdsNo, ",");
			subdc.createAlias("e.expertArea", "a");
			subdc.add(Restrictions.not(Restrictions.in("a.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.createAlias("e.expertCompany", "c");
			subdc.add(Restrictions.not(Restrictions.in("c.id", unitids)));
	    }
		
		String kindIdsNo = projectExpert.getKindIdsNo();
		if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertKind", kindids)));
	    }

		String specialIdsNo = projectExpert.getSpecialIdsNo();
		if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSpecial", specialids)));
	    }

		String seriesIdsNo = projectExpert.getSeriesIdsNo();
		if(seriesIdsNo!=null&&!seriesIdsNo.equalsIgnoreCase("")){
			String[] seriesids = StringUtils.split(seriesIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSeries", seriesids)));
	    }
		
		String techIdsNo = projectExpert.getTechIdsYes();
		if(techIdsNo!=null&&!techIdsNo.equalsIgnoreCase("")){
			String[] techids = StringUtils.split(techIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertTechnical", techids)));
	    }
		
		String discIds = projectExpert.getDiscIds();
		if(discIds!=null&&!discIds.equalsIgnoreCase("")){
			String[] resids = StringUtils.split(discIds, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.id", resids)));
	    }
		subdc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		

		subdc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		subdc.addOrder(Order.desc("id"));
		List<ExpertConfirm> res = expertConfirmDao.find(subdc);
		return res; 
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findExpertsByTimeClash(ProjectExpert projectExpert) {
		DetachedCriteria subdc = DetachedCriteria.forClass(ProjectExpert.class, "e");
		
		Date reviewBegin = projectExpert.getReviewBegin();
		Date reviewEnd = projectExpert.getReviewEnd();
		if(reviewBegin!=null&&reviewEnd!=null){
			subdc.add(Restrictions.between("e.reviewBegin", reviewBegin, reviewEnd));
			subdc.add(Restrictions.or(Restrictions.between("e.reviewEnd", reviewBegin, reviewEnd)));
		}

		subdc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		subdc.addOrder(Order.desc("fetchTime"));
		List<ProjectExpert> res = projectExpertDao.find(subdc);
        List<String> eclist =  Lists.newArrayList();
        for(ProjectExpert pe:res){
        	eclist.add(pe.getExpertExpertConfirm().getId());
        }
		return eclist; 
	}
	
	public Page<Office> findExpertUnits(Page<Office> page, ProjectExpert projectExpert) {
		Criteria dc = officeDao.getSession().createCriteria(Office.class, "o");
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		subdc.createAlias("e.expertCompany", "c");
		subdc.add(Restrictions.eqProperty("o.id","c.id")).setProjection(Projections.id());
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.createAlias("e.expertArea", "a");
				subdc.add(Restrictions.in("e.expertArea.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("c.id", unitids));
	    }

		String kindIdsYes = projectExpert.getKindIdsYes();
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsYes, ",");
			subdc.add(Restrictions.in("e.expertKind", kindids));
	    }

		String specialIdsYes = projectExpert.getSpecialIdsYes();
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSpecial", specialids));
	    }

		String seriesIdsYes = projectExpert.getSeriesIdsYes();
		if(seriesIdsYes!=null&&!seriesIdsYes.equalsIgnoreCase("")){
			String[] seriesids = StringUtils.split(seriesIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSeries", seriesids));
	    }

		String areaIdsNo = projectExpert.getAreaIdsNo();
		if(areaIdsNo!=null&&!areaIdsNo.equalsIgnoreCase("")){
			String[] areaids = StringUtils.split(areaIdsNo, ",");
			subdc.createAlias("e.expertArea", "a");
			subdc.add(Restrictions.not(Restrictions.in("a.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("c.id", unitids)));
	    }
		
		String kindIdsNo = projectExpert.getKindIdsNo();
		if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertKind", kindids)));
	    }

		String specialIdsNo = projectExpert.getSpecialIdsNo();
		if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSpecial", specialids)));
	    }

		String seriesIdsNo = projectExpert.getSeriesIdsNo();
		if(seriesIdsNo!=null&&!seriesIdsNo.equalsIgnoreCase("")){
			String[] seriesids = StringUtils.split(seriesIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSeries", seriesids)));
	    }
		subdc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("o.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("o.id"));
		return page.setList(dc.list());
	}
	
	@SuppressWarnings("unchecked")
	public List<Office> findUnitExpertByCount(Page<Office> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(Office.class, "o");
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		subdc.createAlias("e.expertCompany", "c");
		subdc.add(Restrictions.eqProperty("c.id","o.id")).setProjection(Projections.id());
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.createAlias("e.expertArea", "a");
				subdc.add(Restrictions.in("a.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("c.id", unitids));
	    }

		String kindIdsYes = projectExpert.getKindIdsYes();
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsYes, ",");
			subdc.add(Restrictions.in("e.expertKind", kindids));
	    }

		String specialIdsYes = projectExpert.getSpecialIdsYes();
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSpecial", specialids));
	    }

		String seriesIdsYes = projectExpert.getSeriesIdsYes();
		if(seriesIdsYes!=null&&!seriesIdsYes.equalsIgnoreCase("")){
			String[] seriesids = StringUtils.split(seriesIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSeries", seriesids));
	    }

		String areaIdsNo = projectExpert.getAreaIdsNo();
		if(areaIdsNo!=null&&!areaIdsNo.equalsIgnoreCase("")){
			String[] areaids = StringUtils.split(areaIdsNo, ",");
			subdc.createAlias("e.expertArea", "a");
			subdc.add(Restrictions.not(Restrictions.in("a.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("c.id", unitids)));
	    }
		
		String kindIdsNo = projectExpert.getKindIdsNo();
		if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertKind", kindids)));
	    }

		String specialIdsNo = projectExpert.getSpecialIdsNo();
		if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSpecial", specialids)));
	    }

		String seriesIdsNo = projectExpert.getSeriesIdsNo();
		if(seriesIdsNo!=null&&!seriesIdsNo.equalsIgnoreCase("")){
			String[] seriesids = StringUtils.split(seriesIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSeries", seriesids)));
	    }
		subdc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("o.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("o.id"));
		
		List<Office> res = officeDao.find(dc);
		return res; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Office> findUnitExpertByCondition(Page<Office> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(Office.class, "o");
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		subdc.createAlias("e.expertCompany", "c");
		subdc.add(Restrictions.eqProperty("c.id","o.id")).setProjection(Projections.id());
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.createAlias("e.expertArea", "a");
				subdc.add(Restrictions.in("a.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("c.id", unitids));
	    }

		String kindIdsYes = projectExpert.getKindIdsYes();
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsYes, ",");
			subdc.add(Restrictions.in("e.expertKind", kindids));
	    }

		String specialIdsYes = projectExpert.getSpecialIdsYes();
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSpecial", specialids));
	    }

		String areaIdsNo = projectExpert.getAreaIdsNo();
		if(areaIdsNo!=null&&!areaIdsNo.equalsIgnoreCase("")){
			String[] areaids = StringUtils.split(areaIdsNo, ",");
			subdc.createAlias("e.expertArea", "a");
			subdc.add(Restrictions.not(Restrictions.in("a.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("c.id", unitids)));
	    }
		
		String kindIdsNo = projectExpert.getKindIdsNo();
		if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertKind", kindids)));
	    }

		String specialIdsNo = projectExpert.getSpecialIdsNo();
		if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSpecial", specialids)));
	    }

		subdc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("o.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("o.id"));
		
		List<Office> res = officeDao.find(dc);
		return res; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Office> findUnitExpertByConditionRemoveDiscIds(Page<Office> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(Office.class, "o");
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		subdc.createAlias("e.expertCompany", "c");
		subdc.add(Restrictions.eqProperty("c.id","o.id")).setProjection(Projections.id());
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.createAlias("e.expertArea", "a");
				subdc.add(Restrictions.in("a.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("c.id", unitids));
	    }

		String kindIdsYes = projectExpert.getKindIdsYes();
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsYes, ",");
			subdc.add(Restrictions.in("e.expertKind", kindids));
	    }

		String specialIdsYes = projectExpert.getSpecialIdsYes();
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSpecial", specialids));
	    }

		String areaIdsNo = projectExpert.getAreaIdsNo();
		if(areaIdsNo!=null&&!areaIdsNo.equalsIgnoreCase("")){
			String[] areaids = StringUtils.split(areaIdsNo, ",");
			subdc.createAlias("e.expertArea", "a");
			subdc.add(Restrictions.not(Restrictions.in("a.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("c.id", unitids)));
	    }
		
		String kindIdsNo = projectExpert.getKindIdsNo();
		if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertKind", kindids)));
	    }

		String specialIdsNo = projectExpert.getSpecialIdsNo();
		if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSpecial", specialids)));
	    }

		String discIds = projectExpert.getDiscIds();
		if(discIds!=null&&!discIds.equalsIgnoreCase("")){
			String[] disids = StringUtils.split(discIds, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.id", disids)));
	    }

		subdc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("o.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("o.id"));
		
		List<Office> res = officeDao.find(dc);
		return res; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Office> findUnitExpertByConditionRedrawRemove(Page<Office> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(Office.class, "o");
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		subdc.createAlias("e.expertCompany", "c");
		subdc.add(Restrictions.eqProperty("c.id","o.id")).setProjection(Projections.id());
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.createAlias("e.expertArea", "a");
				subdc.add(Restrictions.in("a.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("c.id", unitids));
	    }

		String kindIdsYes = projectExpert.getKindIdsYes();
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsYes, ",");
			subdc.add(Restrictions.in("e.expertKind", kindids));
	    }

		String specialIdsYes = projectExpert.getSpecialIdsYes();
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSpecial", specialids));
	    }

		String areaIdsNo = projectExpert.getAreaIdsNo();
		if(areaIdsNo!=null&&!areaIdsNo.equalsIgnoreCase("")){
			String[] areaids = StringUtils.split(areaIdsNo, ",");
			subdc.createAlias("e.expertArea", "a");
			subdc.add(Restrictions.not(Restrictions.in("a.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("c.id", unitids)));
	    }
		
		String kindIdsNo = projectExpert.getKindIdsNo();
		if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertKind", kindids)));
	    }

		String specialIdsNo = projectExpert.getSpecialIdsNo();
		if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSpecial", specialids)));
	    }

		String discIds = projectExpert.getTechIdsNo();
		if(discIds!=null&&!discIds.equalsIgnoreCase("")){
			String[] disids = StringUtils.split(discIds, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.id", disids)));
	    }

		subdc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("o.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("o.id"));
		
		List<Office> res = officeDao.find(dc);
		return res; 
	}
	
	@SuppressWarnings("unchecked")
	public Page<ExpertConfirm> findReviewAndAcceptExpertByProject(Page<ExpertConfirm> page, String prjid) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		DetachedCriteria subdc = DetachedCriteria.forClass(ProjectExpert.class, "o");
		subdc.add(Restrictions.eqProperty("e.id","o.expertExpertConfirm.id")).setProjection(Projections.id());
		
		subdc.add(Restrictions.eq("o.prjProjectInfo.id",prjid));
		String st[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,Constants.Fetch_AcceptRedraw_Sussess};
		subdc.add(Restrictions.in("o.fetchStatus", st));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("e.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("e.id"));
		
		Page<ExpertConfirm> res = expertConfirmDao.find(page,dc);
		return res; 
	}
	
	@SuppressWarnings("unchecked")
	public Page<ExpertConfirm> findReviewingExpertByProject(Page<ExpertConfirm> page, String prjid) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		DetachedCriteria subdc = DetachedCriteria.forClass(ProjectExpert.class, "o");
		subdc.add(Restrictions.eqProperty("e.id","o.expertExpertConfirm.id")).setProjection(Projections.id());
		
		subdc.add(Restrictions.eq("o.prjProjectInfo.id",prjid));
		String st[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess};
		subdc.add(Restrictions.in("o.fetchStatus", st));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("e.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("e.id"));
		
		Page<ExpertConfirm> res = expertConfirmDao.find(page,dc);
		return res; 
	}
	
	@SuppressWarnings("unchecked")
	public Page<ExpertConfirm> findFetchExpertsByProjectAndStatus(Page<ExpertConfirm> page, String prjid,String status[]) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		DetachedCriteria subdc = DetachedCriteria.forClass(ProjectExpert.class, "o");
		subdc.add(Restrictions.eqProperty("e.id","o.expertExpertConfirm.id")).setProjection(Projections.id());
		
		subdc.add(Restrictions.eq("o.prjProjectInfo.id",prjid));
		//String st[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess};
		subdc.add(Restrictions.in("o.fetchStatus", status));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("e.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("e.id"));
		
		Page<ExpertConfirm> res = expertConfirmDao.find(page,dc);
		return res; 
	}
	
	@SuppressWarnings("unchecked")
	public Page<ProjectInfo> findFetchProjectsByExpertAndStatus(Page<ProjectInfo> page, String expid,String status[]) {
		DetachedCriteria dc = DetachedCriteria.forClass(ProjectInfo.class, "e");
		DetachedCriteria subdc = DetachedCriteria.forClass(ProjectExpert.class, "o");
		subdc.add(Restrictions.eqProperty("e.id","o.prjProjectInfo.id")).setProjection(Projections.id());
		
		subdc.add(Restrictions.eq("o.expertExpertConfirm.id",expid));
		//String st[] = {Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess};
		subdc.add(Restrictions.in("o.fetchStatus", status));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("e.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("e.id"));
		
		Page<ProjectInfo> res = projectInfoDao.find(page,dc);
		return res; 
	}
	
	@SuppressWarnings("unchecked")
	public Page<ExpertConfirm> findAcceptingExpertByProject(Page<ExpertConfirm> page, String prjid) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		DetachedCriteria subdc = DetachedCriteria.forClass(ProjectExpert.class, "o");
		subdc.add(Restrictions.eqProperty("e.id","o.expertExpertConfirm.id")).setProjection(Projections.id());
		
		subdc.add(Restrictions.eq("o.prjProjectInfo.id",prjid));
		String st[] = {Constants.Fetch_Accept_Sussess,Constants.Fetch_AcceptRedraw_Sussess};
		subdc.add(Restrictions.in("o.fetchStatus", st));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("e.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("e.id"));
		
		Page<ExpertConfirm> res = expertConfirmDao.find(page,dc);
		return res; 
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findUnitRecentThree(ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(ProjectExpert.class, "o");
		
		String ts[] = { Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,
				Constants.Fetch_AcceptRedraw_Sussess,Constants.Fetch_Accepted_Sussess,Constants.Fetch_AcceptedRedraw_Sussess};
				dc.add(Restrictions.in("o.fetchStatus", ts));

		dc.addOrder(Order.desc("o.fetchTime"));
		
		List<ProjectExpert> res = projectExpertDao.find(dc);

		List<String> ulist =  Lists.newArrayList();
		int t=0,tmp = 0;
		for(ProjectExpert pe:res){
			if(tmp<pe.getFetchTime()){
			  tmp = pe.getFetchTime();
			  ulist.add(pe.getExpertExpertConfirm().getExpertCompany().getId());
			}else if(tmp==pe.getFetchTime()){
				  ulist.add(pe.getExpertExpertConfirm().getExpertCompany().getId());				
			}else{
				  tmp = pe.getFetchTime();
				  ulist.add(pe.getExpertExpertConfirm().getExpertCompany().getId());
				  t++;
		    }
			
			if(t>2) break;
		}
		return ulist; 
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findUnitRecentByCount(ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(ProjectExpert.class, "o");
		
		Byte discnt = projectExpert.getDiscnt();
		if(discnt==null) return null;
		
		String ts[] = { Constants.Fetch_Review_Sussess,Constants.Fetch_ReviewRedraw_Sussess,Constants.Fetch_Accept_Sussess,
				Constants.Fetch_AcceptRedraw_Sussess,Constants.Fetch_Accepted_Sussess,Constants.Fetch_AcceptedRedraw_Sussess};
				dc.add(Restrictions.in("o.fetchStatus", ts));

		dc.addOrder(Order.desc("o.fetchTime"));
		
		List<ProjectExpert> res = projectExpertDao.find(dc);

		List<String> ulist =  Lists.newArrayList();
		int t=0,tmp = 0;
		for(ProjectExpert pe:res){
			if(tmp<pe.getFetchTime()){
			  tmp = pe.getFetchTime();
			  ulist.add(pe.getExpertExpertConfirm().getExpertCompany().getId());
			}else if(tmp==pe.getFetchTime()){
				  ulist.add(pe.getExpertExpertConfirm().getExpertCompany().getId());				
			}else{
				  t++;
				  if(t>=discnt) break;
				  tmp = pe.getFetchTime();
				  ulist.add(pe.getExpertExpertConfirm().getExpertCompany().getId());
		    }
			
		}
		return ulist; 
	}
	
	public List<ExpertConfirm> findUnitExpertByCountRest(Page<ExpertConfirm> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(Office.class, "o");
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		subdc.createAlias("e.expertCompany", "c");
		subdc.add(Restrictions.eqProperty("c.id","o.id"));
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.createAlias("e.expertArea", "a");
				subdc.add(Restrictions.in("e.expertArea.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("c.id", unitids));
	    }

		String kindIdsYes = projectExpert.getKindIdsYes();
		if(kindIdsYes!=null&&!kindIdsYes.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsYes, ",");
			subdc.add(Restrictions.in("e.expertKind", kindids));
	    }

		String specialIdsYes = projectExpert.getSpecialIdsYes();
		if(specialIdsYes!=null&&!specialIdsYes.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSpecial", specialids));
	    }

		String seriesIdsYes = projectExpert.getSeriesIdsYes();
		if(seriesIdsYes!=null&&!seriesIdsYes.equalsIgnoreCase("")){
			String[] seriesids = StringUtils.split(seriesIdsYes, ",");
			subdc.add(Restrictions.in("e.expertSeries", seriesids));
	    }

		String areaIdsNo = projectExpert.getAreaIdsNo();
		if(areaIdsNo!=null&&!areaIdsNo.equalsIgnoreCase("")){
			String[] areaids = StringUtils.split(areaIdsNo, ",");
			subdc.createAlias("e.expertArea", "a");
			subdc.add(Restrictions.not(Restrictions.in("a.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("c.id", unitids)));
	    }
		
		String kindIdsNo = projectExpert.getKindIdsNo();
		if(kindIdsNo!=null&&!kindIdsNo.equalsIgnoreCase("")){
			String[] kindids = StringUtils.split(kindIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertKind", kindids)));
	    }

		String specialIdsNo = projectExpert.getSpecialIdsNo();
		if(specialIdsNo!=null&&!specialIdsNo.equalsIgnoreCase("")){
			String[] specialids = StringUtils.split(specialIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSpecial", specialids)));
	    }

		String seriesIdsNo = projectExpert.getSeriesIdsNo();
		if(seriesIdsNo!=null&&!seriesIdsNo.equalsIgnoreCase("")){
			String[] seriesids = StringUtils.split(seriesIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertSeries", seriesids)));
	    }
		
		String resIds = projectExpert.getResIds();
		if(resIds!=null&&!resIds.equalsIgnoreCase("")){
			String[] resids = StringUtils.split(resIds, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.id", resids)));
	    }
		subdc.add(Restrictions.eq("e.expertLevel", Constants.Expert_Status_Work));
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq("o.delFlag", ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("o.id"));
		
		List<ExpertConfirm> res = expertConfirmDao.find(dc);
		int resSize =res.size(); 
        Random r=new Random();   
        int n = resSize - projectExpert.getExpertCount().intValue();  
        int ri = r.nextInt(n);
		return res.subList(ri,ri+projectExpert.getExpertCount().intValue()); 
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpertConfirm> findExpertsByIds(Page<ExpertConfirm> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		
		String resIds = projectExpert.getResIds();
		if(resIds!=null&&!resIds.equalsIgnoreCase("")){
			String[] resids = StringUtils.split(resIds, ",");
			dc.add(Restrictions.in("e.id", resids));
	    }else{
	    	return Lists.newArrayList();
	    }
		
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		
		List<ExpertConfirm> res = expertConfirmDao.find(dc);
		return res; 
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectExpert projectExpert) {
		projectExpertDao.save(projectExpert);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		projectExpertDao.deleteById(id);
	}
	
}
