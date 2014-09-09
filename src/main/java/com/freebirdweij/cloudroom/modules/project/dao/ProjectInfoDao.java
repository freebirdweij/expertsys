/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.freebirdweij.cloudroom.modules.project.dao;

import java.math.BigDecimal;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.freebirdweij.cloudroom.common.persistence.BaseDao;
import com.freebirdweij.cloudroom.common.persistence.Parameter;
import com.freebirdweij.cloudroom.modules.experts.entity.ExpertInfo;
import com.freebirdweij.cloudroom.modules.project.entity.ProjectInfo;

/**
 * 项目信息DAO接口
 * @author Cloudman
 * @version 2014-07-08
 */
@Repository
public class ProjectInfoDao extends BaseDao<ProjectInfo> {
	
	public BigDecimal selectProjectSequence(){
		Query query = createSqlQuery("select project_seq.nextval from dual",null); 
		Object result = query.uniqueResult(); 
		
		if(result==null) return new BigDecimal(0);
		
		return (BigDecimal)result;
	}
	
	public int updateRecordTwo(ProjectInfo projectInfo){
		return update("update ProjectInfo set prjBegin=:p1, prjEnd=:p2, prjNotes=:p3, prjStatus=:p4 where id = :p5", 
				new Parameter(projectInfo.getPrjBegin(), projectInfo.getPrjEnd(), projectInfo.getPrjNotes(),
				projectInfo.getPrjStatus(),projectInfo.getId()));
	}
	
	public int updateProjectStatus(String prjStatus,String prjid){
		return update("update ProjectInfo set prjStatus=:p1 where id = :p2", 
				new Parameter(prjStatus,prjid));
	}
	
	public int updateProjectStatusAndParent(String prjStatus,String parentid,String prjid){
		return update("update ProjectInfo set prjStatus=:p1,parent.id=:p2 where id = :p3", 
				new Parameter(prjStatus,parentid,prjid));
	}
	
}
