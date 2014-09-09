/**
 * There are <a href="https://github.com/freebirdweij/cloudroom">CloudRoom</a> code generation
 */
package com.freebirdweij.cloudroom.modules.oa.dao;

import org.springframework.stereotype.Repository;

import com.freebirdweij.cloudroom.common.persistence.BaseDao;
import com.freebirdweij.cloudroom.common.persistence.Parameter;
import com.freebirdweij.cloudroom.modules.oa.entity.Leave;

/**
 * 请假DAO接口
 * @author liuj
 * @version 2013-8-23
 */
@Repository
public class LeaveDao extends BaseDao<Leave> {
	
	public int updateProcessInstanceId(String id,String processInstanceId){
		return update("update Leave set processInstanceId=:p1 where id = :p2", new Parameter(processInstanceId, id));
	}
	
}
