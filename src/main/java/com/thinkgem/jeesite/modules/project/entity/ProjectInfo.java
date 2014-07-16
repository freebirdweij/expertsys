package com.thinkgem.jeesite.modules.project.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.IdEntity;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 项目信息Entity
 * @author Cloudman
 * @version 2014-07-08
 */
@Entity
@Table(name = "project_info")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectInfo extends DataEntity<ProjectInfo> {

	private String id;		// 编号
	
	private String prjCode;		// 项目编号

	@Transient
	public String getPrjCode() {
		return prjCode;
	}

	public void setPrjCode(String prjCode) {
		this.prjCode = prjCode;
	}

	@Id
	public String getId() {
		// TODO 自动生成的方法存根
		return id;
	}

	public void setId(String id) {
		// TODO 自动生成的方法存根
		this.id = id;
	}

	public ProjectInfo(String id){
		this();
		this.id = id;
	}
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

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

	/** 项目地址. */
	private String prjAddress;

	/** 项目说明. */
	private String prjNotes;

	/** 项目状态. */
	private String prjStatus;

	/** 项目开始时间. */
	private Date prjBegin;

	/** 项目结束时间. */
	private Date prjEnd;

	private Office unit;	// 主体单位
	
	@ManyToOne
	@JoinColumn(name="prjUnit")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public Office getUnit() {
		return unit;
	}

	public void setUnit(Office unit) {
		this.unit = unit;
	}


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
		/** The set of 项目评委表. */
		this.projectCommitteeSet = new HashSet<ProjectCommittee>();

		/** The set of 专家附件. */
		this.projectAttachSet = new HashSet<ProjectAttach>();

		/** The set of 项目专家表. */
		this.projectExpertSet = new HashSet<ProjectExpert>();
	}

	public String getPrjAddress() {
		return prjAddress;
	}

	public void setPrjAddress(String prjAddress) {
		this.prjAddress = prjAddress;
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
	@JsonIgnore
	@XmlTransient
	@Transient
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
	@JsonIgnore
	@XmlTransient
	@Transient
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
	@JsonIgnore
	@XmlTransient
	@Transient
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
