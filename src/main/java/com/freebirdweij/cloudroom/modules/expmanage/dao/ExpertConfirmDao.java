/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.freebirdweij.cloudroom.modules.expmanage.dao;

import java.math.BigDecimal;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.freebirdweij.cloudroom.common.persistence.BaseDao;
import com.freebirdweij.cloudroom.common.persistence.Parameter;
import com.freebirdweij.cloudroom.modules.expmanage.entity.ExpertConfirm;

/**
 * 专家确认DAO接口
 * @author Cloudman
 * @version 2014-07-08
 */
@Repository
public class ExpertConfirmDao extends BaseDao<ExpertConfirm> {
	
	public BigDecimal selectExpertSequence(){
		Query query = createSqlQuery("select expert_seq.nextval from dual",null); 
		Object result = query.uniqueResult(); 
		
		if(result==null) return new BigDecimal(0);
		
		return (BigDecimal)result;
	}
	
	public int updateExpertLevel(String explevel,String userId){
		return update("update ExpertConfirm set expertLevel=:p1 where expertInfo.userId = :p2", 
				new Parameter(explevel,userId));
	}
	
}
