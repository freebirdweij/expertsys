/**
 * There are <a href="https://github.com/freebirdweij/cloudroom">CloudRoom</a> code generation
 */
package com.freebirdweij.cloudroom.modules.expmanage.service;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.freebirdweij.cloudroom.common.persistence.Page;
import com.freebirdweij.cloudroom.common.service.BaseService;
import com.freebirdweij.cloudroom.common.utils.StringUtils;
import com.freebirdweij.cloudroom.modules.expfetch.entity.ProjectExpert;
import com.freebirdweij.cloudroom.modules.expmanage.dao.ExpertConfirmDao;
import com.freebirdweij.cloudroom.modules.expmanage.dao.ExpertImportDao;
import com.freebirdweij.cloudroom.modules.expmanage.entity.ExpertConfirm;
import com.freebirdweij.cloudroom.modules.expmanage.entity.ExpertImport;

/**
 * 专家确认Service
 * @author Cloudman
 * @version 2014-07-08
 */
@Component
@Transactional(readOnly = true)
public class ExpertConfirmService extends BaseService {

	@Autowired
	private ExpertConfirmDao expertConfirmDao;
	
	@Autowired
	private ExpertImportDao expertImportDao;
	
	public ExpertConfirm get(String id) {
		return expertConfirmDao.get(id);
	}
	
	public BigDecimal selectExpertSequence(){
		return expertConfirmDao.selectExpertSequence();
	}
	
	public Page<ExpertImport> findTemplate(Page<ExpertImport> page, ExpertImport expertConfirm) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertImport.class, "e");

		if (expertConfirm.getExpertInfo()!=null){
			if(StringUtils.isNotEmpty(expertConfirm.getExpertInfo().getName())){
				dc.createAlias("e.expertInfo", "i");
			dc.add(Restrictions.like("i.name", "%"+expertConfirm.getExpertInfo().getName()+"%"));
			}
			if(StringUtils.isNotEmpty(expertConfirm.getExpertCompany().getId())){
				dc.createAlias("e.expertCompany", "c");
			dc.add(Restrictions.eq("c.id", expertConfirm.getExpertCompany().getId()));
			}
			if(StringUtils.isNotEmpty(expertConfirm.getExpertArea().getId())){
				dc.createAlias("e.expertArea", "a");
			dc.add(Restrictions.eq("a.id", expertConfirm.getExpertArea().getId()));
			}
		}
		dc.add(Restrictions.eq("e.delFlag", ExpertImport.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("e.id"));
		return expertImportDao.find(page, dc);
	}
	
	public Page<ExpertConfirm> find(Page<ExpertConfirm> page, ExpertConfirm expertConfirm) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");

		if (expertConfirm.getExpertInfo()!=null){
			if(StringUtils.isNotEmpty(expertConfirm.getExpertInfo().getName())){
				dc.createAlias("e.expertInfo", "i");
			dc.add(Restrictions.like("i.name", "%"+expertConfirm.getExpertInfo().getName()+"%"));
			}
			if(StringUtils.isNotEmpty(expertConfirm.getExpertCompany().getId())){
				dc.createAlias("e.expertCompany", "c");
			dc.add(Restrictions.eq("c.id", expertConfirm.getExpertCompany().getId()));
			}
			if(expertConfirm.getExpertArea()!=null&&StringUtils.isNotEmpty(expertConfirm.getExpertArea().getId())){
				dc.createAlias("e.expertArea", "a");
			dc.add(Restrictions.eq("a.id", expertConfirm.getExpertArea().getId()));
			}
		}
		dc.add(Restrictions.eq("e.delFlag", ExpertConfirm.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("e.id"));
		return expertConfirmDao.find(page, dc);
	}
	
	public List<ExpertConfirm> findAExpert(String userid) {
		DetachedCriteria dc = expertConfirmDao.createDetachedCriteria();
			if(StringUtils.isNotEmpty(userid)){
			dc.add(Restrictions.eq("expertInfo.userId", userid));
			}
		dc.add(Restrictions.eq(ExpertConfirm.FIELD_DEL_FLAG, ExpertConfirm.DEL_FLAG_NORMAL));
		//dc.addOrder(Order.desc("id"));
		return expertConfirmDao.find(dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpertConfirm expertConfirm) {
		expertConfirmDao.save(expertConfirm);
	}
	
	@Transactional(readOnly = false)
	public void saveImport(ExpertImport expertConfirm) {
		expertImportDao.save(expertConfirm);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		expertConfirmDao.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public void updateExpertLevel(String explevel,String userId) {
		expertConfirmDao.updateExpertLevel(explevel,userId);
	}
	
	public Page<ExpertConfirm> findSuperviseExperts(Page<ExpertConfirm> page, ExpertConfirm expertConfirm) {
		DetachedCriteria dc = DetachedCriteria.forClass(ExpertConfirm.class, "e");
		dc.createAlias("e.expertInfo", "i").createAlias("e.expertCompany", "c").createAlias("e.expertArea", "a");
		if (StringUtils.isNotEmpty(expertConfirm.getId())){
			dc.add(Restrictions.eq("id", expertConfirm.getId()));
		}
		if (expertConfirm.getExpertInfo()!=null&&StringUtils.isNotEmpty(expertConfirm.getExpertInfo().getName())){
			dc.add(Restrictions.like("i.name", "%"+expertConfirm.getExpertInfo().getName()+"%"));
		}
		if (expertConfirm.getExpertCompany()!=null&&StringUtils.isNotEmpty(expertConfirm.getExpertCompany().getId())){
			dc.add(Restrictions.eq("c.id", expertConfirm.getExpertCompany().getId()));
		}
		if (expertConfirm.getExpertInfo()!=null&&StringUtils.isNotEmpty(expertConfirm.getExpertInfo().getMobile())){
			dc.add(Restrictions.eq("i.mobile", expertConfirm.getExpertInfo().getMobile()));
		}
		if (expertConfirm.getExpertInfo()!=null&&StringUtils.isNotEmpty(expertConfirm.getExpertInfo().getCollage())){
			dc.add(Restrictions.eq("i.collage", expertConfirm.getExpertInfo().getCollage()));
		}
		if (StringUtils.isNotEmpty(expertConfirm.getExpertSpecial())){
			dc.add(Restrictions.eq("expertSpecial", expertConfirm.getExpertSpecial()));
		}
		if (expertConfirm.getExpertArea()!=null&&StringUtils.isNotEmpty(expertConfirm.getExpertArea().getId())){
			dc.add(Restrictions.eq("a.id", expertConfirm.getExpertArea().getId()));
		}
		if (StringUtils.isNotEmpty(expertConfirm.getExpertTechnical())){
			dc.add(Restrictions.eq("expertTechnical", expertConfirm.getExpertTechnical()));
		}
		if (expertConfirm.getExpertInfo()!=null&&StringUtils.isNotEmpty(expertConfirm.getExpertInfo().getEducation())){
			dc.add(Restrictions.eq("i.education", expertConfirm.getExpertInfo().getEducation()));
		}
		dc.add(Restrictions.eq(ExpertConfirm.FIELD_DEL_FLAG, ExpertConfirm.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return expertConfirmDao.find(page, dc);
	}
	
}
