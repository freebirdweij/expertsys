package com.thinkgem.jeesite.modules.loginfo.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 日志处理模块Entity
 * @author Cloudman
 * @version 2014-08-25
 */
@Entity
@Table(name = "expertdb_log")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExpertdbLog extends DataEntity<ExpertdbLog> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 日志ID. */
	private Integer id;

	/** 日志关联的对象ID（专家、项目...）. */
	private String objectId;
	
	/** 日志关联的对象ID（专家、项目...）. */
	private String objectName;

	/** 对象的操作者（对专家、项目等进行操作的系统用户）. */
	private User objectUser;

	/** 被操作的对象类型（专家、项目等）. */
	private String objectType;

	/** 操作内容. */
	private String operation;
	
	private Date logBegin;
	
	private Date logEnd;

	@Transient
	public Date getLogBegin() {
		return logBegin;
	}

	public void setLogBegin(Date logBegin) {
		this.logBegin = logBegin;
	}

	@Transient
	public Date getLogEnd() {
		return logEnd;
	}

	public void setLogEnd(Date logEnd) {
		this.logEnd = logEnd;
	}

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
	@Id
    @GenericGenerator(name = "idGenerator", strategy = "increment")
    @GeneratedValue(generator = "idGenerator")	
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

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * Set the 对象的操作者（对专家、项目等进行操作的系统用户）.
	 * 
	 * @param objectUser
	 *            对象的操作者（对专家、项目等进行操作的系统用户）
	 */
	public void setObjectUser(User objectUser) {
		this.objectUser = objectUser;
	}

	/**
	 * Get the 对象的操作者（对专家、项目等进行操作的系统用户）.
	 * 
	 * @return 对象的操作者（对专家、项目等进行操作的系统用户）
	 */
	@ManyToOne
	@JoinColumn(name="objectUser")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public User getObjectUser() {
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
