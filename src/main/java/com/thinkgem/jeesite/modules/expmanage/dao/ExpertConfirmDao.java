/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.expmanage.dao;

import org.springframework.stereotype.Repository;

import com.thinkgem.jeesite.common.persistence.BaseDao;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;

/**
 * 专家确认DAO接口
 * @author Cloudman
 * @version 2014-07-08
 */
@Repository
public class ExpertConfirmDao extends BaseDao<ExpertConfirm> {
	
	public int updateExpertLevel(String explevel,String userId){
		return update("update ExpertConfirm set expertLevel=:p1 where expertInfo.userId = :p2", 
				new Parameter(explevel,userId));
	}
	
}
