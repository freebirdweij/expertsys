/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.project.dao;

import org.springframework.stereotype.Repository;

import com.thinkgem.jeesite.common.persistence.BaseDao;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;

/**
 * 项目信息DAO接口
 * @author Cloudman
 * @version 2014-07-08
 */
@Repository
public class ProjectInfoDao extends BaseDao<ProjectInfo> {
	
	public int updateRecordTwo(ProjectInfo projectInfo){
		return update("update ProjectInfo set prjBegin=:p1, prjEnd=:p2, prjNotes=:p3, prjStatus=:p4 where id = :p5", 
				new Parameter(projectInfo.getPrjBegin(), projectInfo.getPrjEnd(), projectInfo.getPrjNotes(),
				projectInfo.getPrjStatus(),projectInfo.getId()));
	}
	
	public int updateProjectStatus(String prjStatus,String prjid){
		return update("update ProjectInfo set prjStatus=:p1 where id = :p2", 
				new Parameter(prjStatus,prjid));
	}
	
}
