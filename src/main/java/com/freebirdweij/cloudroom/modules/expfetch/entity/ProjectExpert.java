package com.freebirdweij.cloudroom.modules.expfetch.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freebirdweij.cloudroom.common.persistence.DataEntity;
import com.freebirdweij.cloudroom.modules.expmanage.entity.ExpertConfirm;
import com.freebirdweij.cloudroom.modules.project.entity.ProjectInfo;

/**
 * Model class of 项目专家表.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
@Entity
@Table(name = "project_expert")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectExpert extends DataEntity<ProjectExpert>  implements Serializable{

	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 主键. */
    private Integer id;

    @Id 
    @GenericGenerator(name = "idGenerator", strategy = "increment")
    @GeneratedValue(generator = "idGenerator")	
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/** 项目信息表. */
    private ProjectInfo prjProjectInfo;

	/** 专家确认表. */
	private ExpertConfirm expertExpertConfirm;

	/** 第几次抽取. */
	private Integer fetchTime;

	/** 本次所需专家数. */
	private Byte expertCount;

	/** 专家在本次小组中的身份. */
	private String expertRate;

	/** 抽取有效标志. */
	private String fetchStatus;

	/** 专家获取的方式：单位、个人、选取、随机抽取。. */
	private String fetchMethod;

	/** 本次抽取组成的评委会名称. */
	private String committeeName;

	/** 执行评审开始时间. */
	private Date reviewBegin;

	/** 执行评审结束时间. */
	private Date reviewEnd;

	/** 专家接受任务标志。. */
	private String expertAccept;
	
	/** 技术类专家数. */
	private Byte techcnt;
	
	/** 经济类专家数. */
	private Byte ecomcnt;
	
	/** 屏蔽近期已抽选次数. */
	private Byte discnt;

	/** 监督人. */
	private String supervise;
	
	private Byte halfday;
	
	@Transient
	public Byte getHalfday() {
		/*long hour = DateUtils.getFragmentInHours(reviewEnd, Calendar.HOUR_OF_DAY)-DateUtils.getFragmentInHours(reviewBegin, Calendar.HOUR_OF_DAY);
		if(hour==0){
			halfday = 1;
		}else if(hour==6){
			halfday = 2;
		}else if(hour==12){
			halfday = 0;
		}*/
		return halfday;
	}

	public void setHalfday(Byte halfday) {
		this.halfday = halfday;
	}

	@Transient
	public Byte getTechcnt() {
		return techcnt;
	}

	public void setTechcnt(Byte techcnt) {
		this.techcnt = techcnt;
	}

	@Transient
	public Byte getEcomcnt() {
		return ecomcnt;
	}

	public void setEcomcnt(Byte ecomcnt) {
		this.ecomcnt = ecomcnt;
	}

	public Byte getDiscnt() {
		return discnt;
	}

	public void setDiscnt(Byte discnt) {
		this.discnt = discnt;
	}

	public String getSupervise() {
		return supervise;
	}

	public void setSupervise(String supervise) {
		this.supervise = supervise;
	}

	@Transient
	public String getRejectUnit() {
		return rejectUnit;
	}

	public void setRejectUnit(String rejectUnit) {
		this.rejectUnit = rejectUnit;
	}

	@Transient
	public String getRejectRecent() {
		return rejectRecent;
	}

	public void setRejectRecent(String rejectRecent) {
		this.rejectRecent = rejectRecent;
	}

	@Transient
	public String getTimeClash() {
		return timeClash;
	}

	public void setTimeClash(String timeClash) {
		this.timeClash = timeClash;
	}

	private String rejectUnit;
	
	private String rejectRecent;
	
	private String timeClash;
		
	private String prjid;
	
	@Transient
	public String getPrjid() {
		return prjid;
	}

	public void setPrjid(String prjid) {
		this.prjid = prjid;
	}

	private String resIds;
	
	@Transient
	public String getResIds() {
		return resIds;
	}

	public void setResIds(String resIds) {
		this.resIds = resIds;
	}

	private String discIds;

	@Transient
	public String getDiscIds() {
		return discIds;
	}

	public void setDiscIds(String discIds) {
		this.discIds = discIds;
	}

	private String areaIdsYes;

	private String unitIdsYes;

	private String kindIdsYes;

	private String specialIdsYes;

	private String seriesIdsYes;

	private String areaIdsNo;

	private String unitIdsNo;

	@Transient
	public String getAreaIdsYes() {
		return areaIdsYes;
	}

	public void setAreaIdsYes(String areaIdsYes) {
		this.areaIdsYes = areaIdsYes;
	}

	@Transient
	public String getUnitIdsYes() {
		return unitIdsYes;
	}

	public void setUnitIdsYes(String unitIdsYes) {
		this.unitIdsYes = unitIdsYes;
	}

	@Transient
	public String getKindIdsYes() {
		return kindIdsYes;
	}

	public void setKindIdsYes(String kindIdsYes) {
		this.kindIdsYes = kindIdsYes;
	}

	@Transient
	public String getSpecialIdsYes() {
		return specialIdsYes;
	}

	public void setSpecialIdsYes(String specialIdsYes) {
		this.specialIdsYes = specialIdsYes;
	}

	@Transient
	public String getSeriesIdsYes() {
		return seriesIdsYes;
	}

	public void setSeriesIdsYes(String seriesIdsYes) {
		this.seriesIdsYes = seriesIdsYes;
	}

	@Transient
	public String getAreaIdsNo() {
		return areaIdsNo;
	}

	public void setAreaIdsNo(String areaIdsNo) {
		this.areaIdsNo = areaIdsNo;
	}

	@Transient
	public String getUnitIdsNo() {
		return unitIdsNo;
	}

	public void setUnitIdsNo(String unitIdsNo) {
		this.unitIdsNo = unitIdsNo;
	}

	@Transient
	public String getKindIdsNo() {
		return kindIdsNo;
	}

	public void setKindIdsNo(String kindIdsNo) {
		this.kindIdsNo = kindIdsNo;
	}

	@Transient
	public String getSpecialIdsNo() {
		return specialIdsNo;
	}

	public void setSpecialIdsNo(String specialIdsNo) {
		this.specialIdsNo = specialIdsNo;
	}

	@Transient
	public String getSeriesIdsNo() {
		return seriesIdsNo;
	}

	public void setSeriesIdsNo(String seriesIdsNo) {
		this.seriesIdsNo = seriesIdsNo;
	}

	@Transient
	public String getTechIdsYes() {
		return techIdsYes;
	}

	public void setTechIdsYes(String techIdsYes) {
		this.techIdsYes = techIdsYes;
	}

	@Transient
	public String getTechIdsNo() {
		return techIdsNo;
	}

	public void setTechIdsNo(String techIdsNo) {
		this.techIdsNo = techIdsNo;
	}

	private String kindIdsNo;

	private String specialIdsNo;

	private String seriesIdsNo;

	private String techIdsYes;

	private String techIdsNo;


	public ProjectExpert(ProjectInfo prjProjectInfo,
			ExpertConfirm expertExpertConfirm, Integer fetchTime) {
		super();
		this.prjProjectInfo = prjProjectInfo;
		this.expertExpertConfirm = expertExpertConfirm;
		this.fetchTime = fetchTime;
	}

	/**
	 * Constructor.
	 */
	public ProjectExpert() {
	}

	/**
	 * Set the 项目信息表.
	 * 
	 * @param prjProjectInfo
	 *            项目信息表
	 */
	public void setPrjProjectInfo(ProjectInfo prjProjectInfo) {
		this.prjProjectInfo = prjProjectInfo;
	}

	/**
	 * Get the 项目信息表.
	 * 
	 * @return 项目信息表
	 */
	@ManyToOne
	@JoinColumn(name="prjId")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public ProjectInfo getPrjProjectInfo() {
		return this.prjProjectInfo;
	}

	/**
	 * Set the 专家确认表.
	 * 
	 * @param expertExpertConfirm
	 *            专家确认表
	 */
	public void setExpertExpertConfirm(ExpertConfirm expertExpertConfirm) {
		this.expertExpertConfirm = expertExpertConfirm;
	}

	/**
	 * Get the 专家确认表.
	 * 
	 * @return 专家确认表
	 */
	@ManyToOne
	@JoinColumn(name="expertId")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public ExpertConfirm getExpertExpertConfirm() {
		return this.expertExpertConfirm;
	}

	/**
	 * Set the 第几次抽取.
	 * 
	 * @param fetchTime
	 *            第几次抽取
	 */
	public void setFetchTime(Integer fetchTime) {
		this.fetchTime = fetchTime;
	}

	/**
	 * Get the 第几次抽取.
	 * 
	 * @return 第几次抽取
	 */
	public Integer getFetchTime() {
		return this.fetchTime;
	}

	/**
	 * Set the 本次所需专家数.
	 * 
	 * @param expertCount
	 *            本次所需专家数
	 */
	public void setExpertCount(Byte expertCount) {
		this.expertCount = expertCount;
	}

	/**
	 * Get the 本次所需专家数.
	 * 
	 * @return 本次所需专家数
	 */
	public Byte getExpertCount() {
		return this.expertCount;
	}

	/**
	 * Set the 专家在本次小组中的身份.
	 * 
	 * @param expertRate
	 *            专家在本次小组中的身份
	 */
	public void setExpertRate(String expertRate) {
		this.expertRate = expertRate;
	}

	/**
	 * Get the 专家在本次小组中的身份.
	 * 
	 * @return 专家在本次小组中的身份
	 */
	public String getExpertRate() {
		return this.expertRate;
	}

	/**
	 * Set the 抽取有效标志.
	 * 
	 * @param fetchStatus
	 *            抽取有效标志
	 */
	public void setFetchStatus(String fetchStatus) {
		this.fetchStatus = fetchStatus;
	}

	/**
	 * Get the 抽取有效标志.
	 * 
	 * @return 抽取有效标志
	 */
	public String getFetchStatus() {
		return this.fetchStatus;
	}

	/**
	 * Set the 专家获取的方式：单位、个人、选取、随机抽取。.
	 * 
	 * @param fetchMethod
	 *            专家获取的方式：单位、个人、选取、随机抽取。
	 */
	public void setFetchMethod(String fetchMethod) {
		this.fetchMethod = fetchMethod;
	}

	/**
	 * Get the 专家获取的方式：单位、个人、选取、随机抽取。.
	 * 
	 * @return 专家获取的方式：单位、个人、选取、随机抽取。
	 */
	public String getFetchMethod() {
		return this.fetchMethod;
	}

	/**
	 * Set the 本次抽取组成的评委会名称.
	 * 
	 * @param committeeName
	 *            本次抽取组成的评委会名称
	 */
	public void setCommitteeName(String committeeName) {
		this.committeeName = committeeName;
	}

	/**
	 * Get the 本次抽取组成的评委会名称.
	 * 
	 * @return 本次抽取组成的评委会名称
	 */
	public String getCommitteeName() {
		return this.committeeName;
	}

	/**
	 * Set the 执行评审开始时间.
	 * 
	 * @param reviewBegin
	 *            执行评审开始时间
	 */
	public void setReviewBegin(Date reviewBegin) {
		this.reviewBegin = reviewBegin;
	}

	/**
	 * Get the 执行评审开始时间.
	 * 
	 * @return 执行评审开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReviewBegin() {
		return this.reviewBegin;
	}

	/**
	 * Set the 执行评审结束时间.
	 * 
	 * @param reviewEnd
	 *            执行评审结束时间
	 */
	public void setReviewEnd(Date reviewEnd) {
		this.reviewEnd = reviewEnd;
	}

	/**
	 * Get the 执行评审结束时间.
	 * 
	 * @return 执行评审结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReviewEnd() {
		return this.reviewEnd;
	}

	/**
	 * Set the 专家接受任务标志。.
	 * 
	 * @param expertAccept
	 *            专家接受任务标志。
	 */
	public void setExpertAccept(String expertAccept) {
		this.expertAccept = expertAccept;
	}

	/**
	 * Get the 专家接受任务标志。.
	 * 
	 * @return 专家接受任务标志。
	 */
	public String getExpertAccept() {
		return this.expertAccept;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prjProjectInfo == null) ? 0 : prjProjectInfo.hashCode());
		result = prime * result + ((expertExpertConfirm == null) ? 0 : expertExpertConfirm.hashCode());
		result = prime * result + ((fetchTime == null) ? 0 : fetchTime.hashCode());
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
		ProjectExpert other = (ProjectExpert) obj;
		if (prjProjectInfo == null) {
			if (other.prjProjectInfo != null) {
				return false;
			}
		} else if (!prjProjectInfo.equals(other.prjProjectInfo)) {
			return false;
		}
		if (expertExpertConfirm == null) {
			if (other.expertExpertConfirm != null) {
				return false;
			}
		} else if (!expertExpertConfirm.equals(other.expertExpertConfirm)) {
			return false;
		}
		if (fetchTime == null) {
			if (other.fetchTime != null) {
				return false;
			}
		} else if (!fetchTime.equals(other.fetchTime)) {
			return false;
		}
		return true;
	}

}
