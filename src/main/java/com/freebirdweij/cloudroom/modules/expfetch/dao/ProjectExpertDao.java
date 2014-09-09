/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.freebirdweij.cloudroom.modules.expfetch.dao;

import java.util.Date;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.freebirdweij.cloudroom.common.persistence.BaseDao;
import com.freebirdweij.cloudroom.common.persistence.Parameter;
import com.freebirdweij.cloudroom.common.utils.Constants;
import com.freebirdweij.cloudroom.modules.expfetch.entity.ProjectExpert;

/**
 * 对项目进行专家抽取DAO接口
 * @author Cloudman
 * @version 2014-07-12
 */
@Repository
public class ProjectExpertDao extends BaseDao<ProjectExpert> {
	
	public Integer selectMaxFetchTime(){
		Query query = createQuery("select max(p.fetchTime) from ProjectExpert p",null); 
		Object result = query.uniqueResult(); 
		
		if(result==null) return new Integer(0);
		
		return (Integer)result;
	}
	
	public int updateProjectExpertStatus(String fetchStatus,Integer fetchTime,String prjid,String expid){
		return update("update ProjectExpert set fetchStatus=:p1 where fetchTime = :p2 and prjProjectInfo.id = :p3 and expertExpertConfirm.id = :p4", 
				new Parameter(fetchStatus,fetchTime,prjid,expid));
	}
	
	public int updateProjectExpertReviewDate(String fetchStatus,String prjid,Date reviewBegin,Date reviewEnd){
		if(fetchStatus.equals(Constants.Fetch_Review_Sussess)){
		return update("update ProjectExpert set reviewBegin=:p1,reviewEnd=:p2 where prjProjectInfo.id = :p3 and fetchStatus in ("
				+Constants.Fetch_Review_Sussess+","+Constants.Fetch_ReviewRedraw_Sussess+")",
				new Parameter(reviewBegin,reviewEnd,prjid));
		}
		if(fetchStatus.equals(Constants.Fetch_Accept_Sussess)){
		return update("update ProjectExpert set reviewBegin=:p1,reviewEnd=:p2 where prjProjectInfo.id = :p3 and fetchStatus in ("
				+Constants.Fetch_Accept_Sussess+","+Constants.Fetch_AcceptRedraw_Sussess+")",
				new Parameter(reviewBegin,reviewEnd,prjid));
		}
		if(fetchStatus.equals(Constants.Fetch_Accepted_Sussess)){
		return update("update ProjectExpert set reviewBegin=:p1,reviewEnd=:p2 where prjProjectInfo.id = :p3 and fetchStatus in ("
				+Constants.Fetch_Accepted_Sussess+","+Constants.Fetch_AcceptedRedraw_Sussess+")",
				new Parameter(reviewBegin,reviewEnd,prjid));
		}
		return 0;
	}
	
	public int updateProjectExpertAbsence(String fetchStatus,String absenceReson,String prjid,String expid){
		if(fetchStatus.equals(Constants.Fetch_Review_Sussess)){
		return update("update ProjectExpert set expertAccept=:p1,committeeName=:p2 where prjProjectInfo.id = :p3 and expertExpertConfirm.id = :p4 and fetchStatus in ("
				+Constants.Fetch_Review_Sussess+","+Constants.Fetch_ReviewRedraw_Sussess+")", 
				new Parameter(Constants.Expert_Apply_Absence,absenceReson,prjid,expid));
		}
		if(fetchStatus.equals(Constants.Fetch_Accept_Sussess)){
		return update("update ProjectExpert set expertAccept=:p1,committeeName=:p2 where prjProjectInfo.id = :p3 and expertExpertConfirm.id = :p4 and fetchStatus in ("
				+Constants.Fetch_Accept_Sussess+","+Constants.Fetch_AcceptRedraw_Sussess+")", 
				new Parameter(Constants.Expert_Apply_Absence,absenceReson,prjid,expid));
		}
		if(fetchStatus.equals(Constants.Fetch_Accepted_Sussess)){
		return update("update ProjectExpert set expertAccept=:p1,committeeName=:p2 where prjProjectInfo.id = :p3 and expertExpertConfirm.id = :p4 and fetchStatus in ("
				+Constants.Fetch_Accepted_Sussess+","+Constants.Fetch_AcceptedRedraw_Sussess+")", 
				new Parameter(Constants.Expert_Apply_Absence,absenceReson,prjid,expid));
		}
		return 0;
	}
	
}
