/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.experts.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.thinkgem.jeesite.common.persistence.BaseDao;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;

/**
 * 专家DAO接口
 * @author Cloudman
 * @version 2014-06-23
 */
@Repository
public class ExpertInfoDao extends BaseDao<ExpertInfo> {
	
	public int updateRegStep(String regStep,String userId){
		return update("update ExpertInfo set regStep=:p1 where id = :p2", 
				new Parameter(regStep,userId));
	}
	
	public int updateStepOne(ExpertInfo expertInfo){
		return update("update ExpertInfo set name=:p1, sex=:p2, birthdate=:p3, health=:p4, nation=:p5, identifyCode=:p6, " +
				"collage=:p7, graduateTime=:p8, education=:p9, studySpecial=:p10, myDegree=:p11, homeAddr=:p12, homePhone=:p13, " +
				"homeMailcode=:p14,regStep=:p15 where id = :p16", 
				new Parameter(expertInfo.getName(), expertInfo.getSex(), expertInfo.getBirthdate(),expertInfo.getHealth(),
						expertInfo.getNation(),expertInfo.getIdentifyCode(),expertInfo.getCollage(),expertInfo.getGraduateTime(),
						expertInfo.getEducation(),expertInfo.getStudySpecial(),expertInfo.getMyDegree(),expertInfo.getHomeAddr(),
						expertInfo.getHomePhone(),expertInfo.getHomeMailcode(),expertInfo.getRegStep(),expertInfo.getUserId()));
	}
	
	public int updateStepTwo(ExpertInfo expertInfo){
		return update("update ExpertInfo set unit=:p1, ifTeamleader=:p2, job=:p3, specialist=:p4, specialFrom=:p5, specialTo=:p6, " +
				"technical=:p7, techGettime=:p8, startworkTime=:p9, workCert=:p10, certGettime=:p11, politics=:p12, companyAddr=:p13, " +
				"companyPhone=:p14, companyMailcode=:p15, workThrough=:p16,regStep=:p17 where id = :p18", 
				new Parameter(expertInfo.getUnit(), expertInfo.getIfTeamleader(), expertInfo.getJob(),expertInfo.getSpecialist(),
						expertInfo.getSpecialFrom(),expertInfo.getSpecialTo(),expertInfo.getTechnical(),expertInfo.getTechGettime(),
						expertInfo.getStartworkTime(),expertInfo.getWorkCert(),expertInfo.getCertGettime(),expertInfo.getPolitics(),
						expertInfo.getCompanyAddr(),expertInfo.getCompanyPhone(),expertInfo.getCompanyMailcode(),expertInfo.getWorkThrough(),
						expertInfo.getRegStep(),expertInfo.getUserId()));
	}
	
	public int updateStepThree(ExpertInfo expertInfo){
		return update("update ExpertInfo set specialKind1=:p1, kind1Special1=:p2, kind1Special2=:p3, specialKind2=:p4, kind2Special1=:p5, kind2Special2=:p6, " +
				"certSeries=:p7, achievement=:p8, hardProjectsArticals=:p9, mySpecials=:p10,regStep=:p11 where id = :p12", 
				new Parameter(expertInfo.getSpecialKind1(), expertInfo.getKind1Special1(), expertInfo.getKind1Special2(),expertInfo.getSpecialKind2(),
						expertInfo.getKind2Special1(),expertInfo.getKind2Special2(),expertInfo.getCertSeries(),expertInfo.getAchievement(),
						expertInfo.getHardProjectsArticals(),expertInfo.getMySpecials(),expertInfo.getRegStep(),
						expertInfo.getUserId()));
	}
	
}
