package com.thinkgem.jeesite.modules.project.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.IdEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
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
	
	private ProjectInfo parent;		// 编号
	
	private List<ProjectInfo> childList = Lists.newArrayList();// 拥有子项目列表
	
	private String prjCode;		// 项目编号

	@Transient
	public String getPrjCode() {
		return prjCode;
	}

	public void setPrjCode(String prjCode) {
		this.prjCode = prjCode;
	}

	@Id
	@ExcelField(title="项目编号",align=2, sort=1)
	public String getId() {
		// TODO 自动生成的方法存根
		return id;
	}

	public void setId(String id) {
		// TODO 自动生成的方法存根
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public ProjectInfo getParent() {
		return parent;
	}

	public void setParent(ProjectInfo parent) {
		this.parent = parent;
	}

	public ProjectInfo(String id){
		this();
		this.id = id;
	}
	
	@OneToMany(mappedBy = "parent", fetch=FetchType.LAZY)
	@Where(clause="del_flag='"+DEL_FLAG_NORMAL+"'")
	@OrderBy(value="id") @Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<ProjectInfo> getChildList() {
		return childList;
	}

	public void setChildList(List<ProjectInfo> childList) {
		this.childList = childList;
	}

	@Transient
	public static void sortList(List<ProjectInfo> list, List<ProjectInfo> sourcelist, String parentId){
		for (int i=0; i<sourcelist.size(); i++){
			ProjectInfo e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<sourcelist.size(); j++){
					ProjectInfo child = sourcelist.get(j);
					if (child.getParent()!=null && child.getParent().getId()!=null
							&& child.getParent().getId().equals(e.getId())){
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}

	@Transient
	public boolean isRoot(){
		return isRoot(this.id);
	}
	
	@Transient
	public static boolean isRoot(String id){
		return id != null && id.equals("1");
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
	private BigDecimal prjMoney;

	/** 项目级别. */
	private String prjLevel;

	/** 项目地址. */
	private String prjAddress;

	/** 项目说明. */
	private String prjNotes;

	/** 项目状态. */
	private String prjStatus;

	/** 项目年度. */
	private String prjYear;

	@ExcelField(title="归属年度", align=2, sort=7)
	public String getPrjYear() {
		return prjYear;
	}

	public void setPrjYear(String prjYear) {
		this.prjYear = prjYear;
	}

	/** 项目开始时间. */
	private Date prjBegin;

	/** 项目结束时间. */
	private Date prjEnd;

	private Office unit;	// 主体单位
	
	@ManyToOne
	@JoinColumn(name="prjUnit")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@ExcelField(title="建设单位", value="unit.name",align=2, sort=3)
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
	@ExcelField(title="项目名称",align=2, sort=2)
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
	@ExcelField(title="项目类型", align=2, sort=4, dictType="sys_prjtype_type")
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
	public void setPrjMoney(BigDecimal prjMoney) {
		this.prjMoney = prjMoney;
	}

	/**
	 * Get the 投资金额.
	 * 
	 * @return 投资金额
	 */
	@Digits(fraction = 4, integer = 15)
	@ExcelField(title="投资金额", align=2, sort=5)
	public BigDecimal getPrjMoney() {
		//DecimalFormat df=new DecimalFormat( "#,##0.00");
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
	@ExcelField(title="项目状态", align=2, sort=6, dictType="sys_prjstatus_type")
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
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
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
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
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
