/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.expfetch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.expfetch.entity.ProjectExpert;
import com.thinkgem.jeesite.modules.expfetch.dao.ProjectExpertDao;
import com.thinkgem.jeesite.modules.expmanage.dao.ExpertConfirmDao;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
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
	
	public ProjectExpert get(String id) {
		return projectExpertDao.get(id);
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
	
	public List<ProjectExpert> findMyJob(Page<ProjectExpert> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(ProjectExpert.class, "e");
		if (StringUtils.isNotEmpty(projectExpert.getExpertExpertConfirm().getExpertInfo().getUserId())){
			dc.add(Restrictions.eq("e.expertExpertConfirm.expertInfo.userId", projectExpert.getExpertExpertConfirm().getExpertInfo().getUserId()));
		}
		dc.add(Restrictions.eq("e.fetchStatus", "1"));
		dc.add(Restrictions.eq("e.prjProjectInfo.prjStatus", "3"));
		dc.add(Restrictions.ge("e.reviewBegin", DateUtils.getDateTime()));
		
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return projectExpertDao.find(dc);
	}
	
	public Page<ExpertConfirm> findExperts(Page<ExpertConfirm> page, ProjectExpert projectExpert) {
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.add(Restrictions.in("e.expertArea.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("e.expertCompany.id", unitids));
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
			subdc.add(Restrictions.not(Restrictions.in("e.expertArea.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertCompany.id", unitids)));
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
				subdc.add(Restrictions.in("e.expertArea.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("e.expertCompany.id", unitids));
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
			subdc.add(Restrictions.not(Restrictions.in("e.expertArea.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertCompany.id", unitids)));
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
		
		String resIds = projectExpert.getResIds();
		if(resIds!=null&&!resIds.equalsIgnoreCase("")){
			String[] resids = StringUtils.split(resIds, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.id", resids)));
	    }
		

		subdc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		subdc.addOrder(Order.desc("id"));
		List<ExpertConfirm> res = expertConfirmDao.find(subdc);
		int resSize =res.size(); 
        Random r=new Random();   
        int n = resSize - projectExpert.getExpertCount().intValue();  
        int ri = r.nextInt(n);
		return res.subList(ri,ri+projectExpert.getExpertCount().intValue()); 
	}
	
	public Page<Office> findExpertUnits(Page<Office> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(Office.class, "o");
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		subdc.add(Restrictions.eqProperty("e.expertCompany.id","o.id"));
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.add(Restrictions.in("e.expertArea.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("e.expertCompany.id", unitids));
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
			subdc.add(Restrictions.not(Restrictions.in("e.expertArea.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertCompany.id", unitids)));
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
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return officeDao.find(page, dc);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpertConfirm> findUnitExpertByCount(Page<ExpertConfirm> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(Office.class, "o");
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		subdc.add(Restrictions.eqProperty("e.expertCompany.id","o.id"));
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.add(Restrictions.in("e.expertArea.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("e.expertCompany.id", unitids));
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
			subdc.add(Restrictions.not(Restrictions.in("e.expertArea.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertCompany.id", unitids)));
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
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		
		List<ExpertConfirm> res = expertConfirmDao.find(dc);
		int resSize =res.size(); 
        Random r=new Random();   
        int n = resSize - projectExpert.getExpertCount().intValue();  
        int ri = r.nextInt(n);
		return res.subList(ri,ri+projectExpert.getExpertCount().intValue()); 
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpertConfirm> findUnitExpertByCountRest(Page<ExpertConfirm> page, ProjectExpert projectExpert) {
		DetachedCriteria dc = DetachedCriteria.forClass(Office.class, "o");
		DetachedCriteria subdc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		subdc.add(Restrictions.eqProperty("e.expertCompany.id","o.id"));
		
		String areaIdsYes = projectExpert.getAreaIdsYes();
		if(areaIdsYes!=null&&!areaIdsYes.equalsIgnoreCase("")){
				String[] areaids = StringUtils.split(areaIdsYes, ",");
				subdc.add(Restrictions.in("e.expertArea.id", areaids));
		}

		String unitIdsYes = projectExpert.getUnitIdsYes();
		if(unitIdsYes!=null&&!unitIdsYes.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsYes, ",");
			subdc.add(Restrictions.in("e.expertCompany.id", unitids));
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
			subdc.add(Restrictions.not(Restrictions.in("e.expertArea.id", areaids)));
	    }

		String unitIdsNo = projectExpert.getUnitIdsNo();
		if(unitIdsNo!=null&&!unitIdsNo.equalsIgnoreCase("")){
			String[] unitids = StringUtils.split(unitIdsNo, ",");
			subdc.add(Restrictions.not(Restrictions.in("e.expertCompany.id", unitids)));
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
		
		dc.add(Subqueries.exists(subdc));
		dc.add(Restrictions.eq(ProjectExpert.FIELD_DEL_FLAG, ProjectExpert.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		
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