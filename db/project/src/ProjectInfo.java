package com.thinkgem.jeesite.modules.project.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;

/**
 * 项目信息Entity
 * @author Cloudman
 * @version 2014-07-08
 */
@Entity
@Table(name = "project_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectInfo extends DataEntity<ProjectInfo> {

	public ProjectInfo() {
		super();
	}

	public ProjectInfo(String id){
		this();
		this.id = id;
	}
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 项目ID. */
	@Id
	private String id;

	/** 项目名称. */
	private String prjName;

	/** 项目类别. */
	private String prjType;

	/** 项目负责人. */
	private String prjDuty;

	/** 投资金额. */
	private Double prjMoney;

	/** 项目级别. */
	private String prjLevel;

	/** 项目说明. */
	private String prjNotes;

	/** 项目状态. */
	private String prjStatus;

	/** 项目开始时间. */
	private Date prjBegin;

	/** 项目结束时间. */
	private Date prjEnd;

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

	/** The set of 项目评委表. */
	private Set<ProjectCommittee> projectCommitteeSet;

	/** The set of 专家附件. */
	private Set<ProjectAttach> projectAttachSet;

	/** The set of 项目专家表. */
	private Set<ProjectExpert> projectExpertSet;

	/**
	 * Constructor.
	 */
	public ProjectInfo() {
		this.projectAttachSet = new HashSet<ProjectAttach>();
		this.projectCommitteeSet = new HashSet<ProjectCommittee>();
		this.projectExpertSet = new HashSet<ProjectExpert>();
	}

	/**
	 * Set the 项目ID.
	 * 
	 * @param id
	 *            项目ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the 项目ID.
	 * 
	 * @return 项目ID
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Set the 项目名称.
	 * 
	 * @param prjName
	 *            项目名称
	 */
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	/**
	 * Get the 项目名称.
	 * 
	 * @return 项目名称
	 */
	public String getPrjName() {
		return this.prjName;
	}

	/**
	 * Set the 项目类别.
	 * 
	 * @param prjType
	 *            项目类别
	 */
	public void setPrjType(String prjType) {
		this.prjType = prjType;
	}

	/**
	 * Get the 项目类别.
	 * 
	 * @return 项目类别
	 */
	public String getPrjType() {
		return this.prjType;
	}

	/**
	 * Set the 项目负责人.
	 * 
	 * @param prjDuty
	 *            项目负责人
	 */
	public void setPrjDuty(String prjDuty) {
		this.prjDuty = prjDuty;
	}

	/**
	 * Get the 项目负责人.
	 * 
	 * @return 项目负责人
	 */
	public String getPrjDuty() {
		return this.prjDuty;
	}

	/**
	 * Set the 投资金额.
	 * 
	 * @param prjMoney
	 *            投资金额
	 */
	public void setPrjMoney(Double prjMoney) {
		this.prjMoney = prjMoney;
	}

	/**
	 * Get the 投资金额.
	 * 
	 * @return 投资金额
	 */
	public Double getPrjMoney() {
		return this.prjMoney;
	}

	/**
	 * Set the 项目级别.
	 * 
	 * @param prjLevel
	 *            项目级别
	 */
	public void setPrjLevel(String prjLevel) {
		this.prjLevel = prjLevel;
	}

	/**
	 * Get the 项目级别.
	 * 
	 * @return 项目级别
	 */
	public String getPrjLevel() {
		return this.prjLevel;
	}

	/**
	 * Set the 项目说明.
	 * 
	 * @param prjNotes
	 *            项目说明
	 */
	public void setPrjNotes(String prjNotes) {
		this.prjNotes = prjNotes;
	}

	/**
	 * Get the 项目说明.
	 * 
	 * @return 项目说明
	 */
	public String getPrjNotes() {
		return this.prjNotes;
	}

	/**
	 * Set the 项目状态.
	 * 
	 * @param prjStatus
	 *            项目状态
	 */
	public void setPrjStatus(String prjStatus) {
		this.prjStatus = prjStatus;
	}

	/**
	 * Get the 项目状态.
	 * 
	 * @return 项目状态
	 */
	public String getPrjStatus() {
		return this.prjStatus;
	}

	/**
	 * Set the 项目开始时间.
	 * 
	 * @param prjBegin
	 *            项目开始时间
	 */
	public void setPrjBegin(Date prjBegin) {
		this.prjBegin = prjBegin;
	}

	/**
	 * Get the 项目开始时间.
	 * 
	 * @return 项目开始时间
	 */
	public Date getPrjBegin() {
		return this.prjBegin;
	}

	/**
	 * Set the 项目结束时间.
	 * 
	 * @param prjEnd
	 *            项目结束时间
	 */
	public void setPrjEnd(Date prjEnd) {
		this.prjEnd = prjEnd;
	}

	/**
	 * Get the 项目结束时间.
	 * 
	 * @return 项目结束时间
	 */
	public Date getPrjEnd() {
		return this.prjEnd;
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
	 * Set the set of the 项目评委表.
	 * 
	 * @param projectCommitteeSet
	 *            The set of 项目评委表
	 */
	public void setProjectCommitteeSet(Set<ProjectCommittee> projectCommitteeSet) {
		this.projectCommitteeSet = projectCommitteeSet;
	}

	/**
	 * Add the 项目评委表.
	 * 
	 * @param projectCommittee
	 *            项目评委表
	 */
	public void addProjectCommittee(ProjectCommittee projectCommittee) {
		this.projectCommitteeSet.add(projectCommittee);
	}

	/**
	 * Get the set of the 项目评委表.
	 * 
	 * @return The set of 项目评委表
	 */
	public Set<ProjectCommittee> getProjectCommitteeSet() {
		return this.projectCommitteeSet;
	}

	/**
	 * Set the set of the 专家附件.
	 * 
	 * @param projectAttachSet
	 *            The set of 专家附件
	 */
	public void setProjectAttachSet(Set<ProjectAttach> projectAttachSet) {
		this.projectAttachSet = projectAttachSet;
	}

	/**
	 * Add the 专家附件.
	 * 
	 * @param projectAttach
	 *            专家附件
	 */
	public void addProjectAttach(ProjectAttach projectAttach) {
		this.projectAttachSet.add(projectAttach);
	}

	/**
	 * Get the set of the 专家附件.
	 * 
	 * @return The set of 专家附件
	 */
	public Set<ProjectAttach> getProjectAttachSet() {
		return this.projectAttachSet;
	}

	/**
	 * Set the set of the 项目专家表.
	 * 
	 * @param projectExpertSet
	 *            The set of 项目专家表
	 */
	public void setProjectExpertSet(Set<ProjectExpert> projectExpertSet) {
		this.projectExpertSet = projectExpertSet;
	}

	/**
	 * Add the 项目专家表.
	 * 
	 * @param projectExpert
	 *            项目专家表
	 */
	public void addProjectExpert(ProjectExpert projectExpert) {
		this.projectExpertSet.add(projectExpert);
	}

	/**
	 * Get the set of the 项目专家表.
	 * 
	 * @return The set of 项目专家表
	 */
	public Set<ProjectExpert> getProjectExpertSet() {
		return this.projectExpertSet;
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
		ProjectInfo other = (ProjectInfo) obj;
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
