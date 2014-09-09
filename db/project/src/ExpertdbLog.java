package com.freebirdweij.cloudroom.modules.loginfo.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.freebirdweij.cloudroom.common.persistence.DataEntity;

/**
 * 日志处理模块Entity
 * @author Cloudman
 * @version 2014-08-25
 */
@Entity
@Table(name = "expertdb_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExpertdbLog extends DataEntity {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 日志ID. */
	private Integer id;

	/** 日志关联的对象ID（专家、项目...）. */
	private String objectId;

	/** 对象的操作者（对专家、项目等进行操作的系统用户）. */
	private String objectUser;

	/** 被操作的对象类型（专家、项目等）. */
	private String objectType;

	/** 操作内容. */
	private String operation;

	/** 创建者. */
	private String createBy;

	/** 创建时间. */
	private Date createDate;

	/** 更新者. */
	private String updateBy;

	/** 更新时间. */
	private Date updateDate;

	/** 备注信息. */
	private String remarks;

	/** 删除标记. */
	private String delFlag;

	/**
	 * Constructor.
	 */
	public ExpertdbLog() {
	}

	/**
	 * Set the 日志ID.
	 * 
	 * @param id
	 *            日志ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Get the 日志ID.
	 * 
	 * @return 日志ID
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Set the 日志关联的对象ID（专家、项目...）.
	 * 
	 * @param objectId
	 *            日志关联的对象ID（专家、项目...）
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * Get the 日志关联的对象ID（专家、项目...）.
	 * 
	 * @return 日志关联的对象ID（专家、项目...）
	 */
	public String getObjectId() {
		return this.objectId;
	}

	/**
	 * Set the 对象的操作者（对专家、项目等进行操作的系统用户）.
	 * 
	 * @param objectUser
	 *            对象的操作者（对专家、项目等进行操作的系统用户）
	 */
	public void setObjectUser(String objectUser) {
		this.objectUser = objectUser;
	}

	/**
	 * Get the 对象的操作者（对专家、项目等进行操作的系统用户）.
	 * 
	 * @return 对象的操作者（对专家、项目等进行操作的系统用户）
	 */
	public String getObjectUser() {
		return this.objectUser;
	}

	/**
	 * Set the 被操作的对象类型（专家、项目等）.
	 * 
	 * @param objectType
	 *            被操作的对象类型（专家、项目等）
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * Get the 被操作的对象类型（专家、项目等）.
	 * 
	 * @return 被操作的对象类型（专家、项目等）
	 */
	public String getObjectType() {
		return this.objectType;
	}

	/**
	 * Set the 操作内容.
	 * 
	 * @param operation
	 *            操作内容
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * Get the 操作内容.
	 * 
	 * @return 操作内容
	 */
	public String getOperation() {
		return this.operation;
	}

	/**
	 * Set the 创建者.
	 * 
	 * @param createBy
	 *            创建者
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * Get the 创建者.
	 * 
	 * @return 创建者
	 */
	public String getCreateBy() {
		return this.createBy;
	}

	/**
	 * Set the 创建时间.
	 * 
	 * @param createDate
	 *            创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Get the 创建时间.
	 * 
	 * @return 创建时间
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * Set the 更新者.
	 * 
	 * @param updateBy
	 *            更新者
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * Get the 更新者.
	 * 
	 * @return 更新者
	 */
	public String getUpdateBy() {
		return this.updateBy;
	}

	/**
	 * Set the 更新时间.
	 * 
	 * @param updateDate
	 *            更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Get the 更新时间.
	 * 
	 * @return 更新时间
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * Set the 备注信息.
	 * 
	 * @param remarks
	 *            备注信息
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Get the 备注信息.
	 * 
	 * @return 备注信息
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * Set the 删除标记.
	 * 
	 * @param delFlag
	 *            删除标记
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * Get the 删除标记.
	 * 
	 * @return 删除标记
	 */
	public String getDelFlag() {
		return this.delFlag;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExpertdbLog other = (ExpertdbLog) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
