/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/freebirdweij/cloudroom">CloudRoom</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.freebirdweij.cloudroom.modules.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.freebirdweij.cloudroom.common.persistence.BaseDao;
import com.freebirdweij.cloudroom.common.persistence.Parameter;
import com.freebirdweij.cloudroom.modules.cms.entity.Article;

/**
 * 文章DAO接口
 * @author CloudRoom
 * @version 2013-8-23
 */
@Repository
public class ArticleDao extends BaseDao<Article> {
	
	public List<Article> findByIdIn(String[] ids){
		return find("from Article where id in (:p1)", new Parameter(new Object[]{ids}));
	}
	
	public int updateHitsAddOne(String id){
		return update("update Article set hits=hits+1 where id = :p1", new Parameter(id));
	}
	
	public int updateExpiredWeight(){
		return update("update Article set weight=0 where weight > 0 and weightDate < current_timestamp()");
	}
	
}
