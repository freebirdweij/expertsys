/**
 * There are <a href="https://github.com/freebirdweij/cloudroom">CloudRoom</a> code generation
 */
package com.freebirdweij.cloudroom.modules.supervise.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import com.freebirdweij.cloudroom.common.persistence.DataEntity;
import com.freebirdweij.cloudroom.modules.sys.entity.User;

/**
 * 对项目抽取进行监督Entity
 * @author Cloudman
 * @version 2014-08-03
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FetchSupervise extends DataEntity {
	
	private static final long serialVersionUID = 1L;
	private Date expertBegin;
	private Date expertEnd;
	private Date unitBegin;
	private Date unitEnd;
	private Date fetchBegin;
	private Date fetchEnd;
	private String sticsKind; 		// 编号
	private String fetchKind; 	// 名称

	public FetchSupervise() {
		super();
	}

	public Date getExpertBegin() {
		return expertBegin;
	}

	public void setExpertBegin(Date expertBegin) {
		this.expertBegin = expertBegin;
	}

	public Date getExpertEnd() {
		return expertEnd;
	}

	public void setExpertEnd(Date expertEnd) {
		this.expertEnd = expertEnd;
	}

	public Date getUnitBegin() {
		return unitBegin;
	}

	public void setUnitBegin(Date unitBegin) {
		this.unitBegin = unitBegin;
	}

	public Date getUnitEnd() {
		return unitEnd;
	}

	public void setUnitEnd(Date unitEnd) {
		this.unitEnd = unitEnd;
	}

	public Date getFetchBegin() {
		return fetchBegin;
	}

	public void setFetchBegin(Date fetchBegin) {
		this.fetchBegin = fetchBegin;
	}

	public Date getFetchEnd() {
		return fetchEnd;
	}

	public void setFetchEnd(Date fetchEnd) {
		this.fetchEnd = fetchEnd;
	}

	public String getSticsKind() {
		return sticsKind;
	}

	public void setSticsKind(String sticsKind) {
		this.sticsKind = sticsKind;
	}

	public String getFetchKind() {
		return fetchKind;
	}

	public void setFetchKind(String fetchKind) {
		this.fetchKind = fetchKind;
	}

	
	
}


