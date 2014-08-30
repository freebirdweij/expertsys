package com.thinkgem.jeesite.modules.expmanage.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * Model class of 专家确认表.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
@Entity
@Table(name = "expert_confirm")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExpertConfirm extends DataEntity<ExpertConfirm>{

	public ExpertConfirm(String id) {
		super();
		this.id = id;
	}

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String kindOne;
	private String specialOne;
	private String seriesOne;
	private String kindTwo;
	private String specialTwo;
	private String seriesTwo;
	private String expertCode;
	private String expertPhone;
	private String expertSex;
	private Date expertBirthdate;
	private String expertNation;
	private String expertCollage;
	private Date expertGraduate;
	private String expertDegree;
	private String expertStudy;
	private String expertJob;
	private String expertEmail;

	@Transient
	public String getExpertSex() {
		return expertSex;
	}

	public void setExpertSex(String expertSex) {
		this.expertSex = expertSex;
	}

	@Transient
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getExpertBirthdate() {
		return expertBirthdate;
	}

	public void setExpertBirthdate(Date expertBirthdate) {
		this.expertBirthdate = expertBirthdate;
	}

	@Transient
	public String getExpertNation() {
		return expertNation;
	}

	public void setExpertNation(String expertNation) {
		this.expertNation = expertNation;
	}

	@Transient
	public String getExpertCollage() {
		return expertCollage;
	}

	public void setExpertCollage(String expertCollage) {
		this.expertCollage = expertCollage;
	}

	@Transient
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getExpertGraduate() {
		return expertGraduate;
	}

	public void setExpertGraduate(Date expertGraduate) {
		this.expertGraduate = expertGraduate;
	}

	@Transient
	@ExcelField(title="学历", value="expertInfo.education",align=2, sort=6)
	public String getExpertDegree() {
		return expertDegree;
	}

	public void setExpertDegree(String expertDegree) {
		this.expertDegree = expertDegree;
	}

	@Transient
	public String getExpertStudy() {
		return expertStudy;
	}

	public void setExpertStudy(String expertStudy) {
		this.expertStudy = expertStudy;
	}

	@Transient
	public String getExpertJob() {
		return expertJob;
	}

	public void setExpertJob(String expertJob) {
		this.expertJob = expertJob;
	}

	@Transient
	public String getExpertEmail() {
		return expertEmail;
	}

	public void setExpertEmail(String expertEmail) {
		this.expertEmail = expertEmail;
	}

	@Transient
	@ExcelField(title="电话", value="expertInfo.mobile",align=2, sort=7)
	public String getExpertPhone() {
		return expertPhone;
	}

	public void setExpertPhone(String expertPhone) {
		this.expertPhone = expertPhone;
	}

	@Transient
	public String getExpertCode() {
		return expertCode;
	}

	public void setExpertCode(String expertCode) {
		this.expertCode = expertCode;
	}

	/** 专家确认ID. */
	private String id;

	@Transient
	public String getKindOne() {
		return kindOne;
	}

	public void setKindOne(String kindOne) {
		this.kindOne = kindOne;
	}

	@Transient
	public String getSpecialOne() {
		return specialOne;
	}

	public void setSpecialOne(String specialOne) {
		this.specialOne = specialOne;
	}

	@Transient
	public String getSeriesOne() {
		return seriesOne;
	}

	public void setSeriesOne(String seriesOne) {
		this.seriesOne = seriesOne;
	}

	@Transient
	public String getKindTwo() {
		return kindTwo;
	}

	public void setKindTwo(String kindTwo) {
		this.kindTwo = kindTwo;
	}

	@Transient
	public String getSpecialTwo() {
		return specialTwo;
	}

	public void setSpecialTwo(String specialTwo) {
		this.specialTwo = specialTwo;
	}

	@Transient
	public String getSeriesTwo() {
		return seriesTwo;
	}

	public void setSeriesTwo(String seriesTwo) {
		this.seriesTwo = seriesTwo;
	}

	private String uid;

	@Transient
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	/** 专家信息表. */
	private ExpertInfo expertInfo;
	
	/** 专家所属区域. */
	private Area expertArea;

	/** 专家所属单位. */
	private Office expertCompany;
	
	/** 专家类别. */
	private String expertKind;

	/** 专家专业. */
	private String expertSpecial;

	/** 专家所属行业. */
	private String expertSeries;

	/** 专家职称. */
	private String expertTechnical;

	/** 专家级别. */
	private String expertLevel;

	/** 照片. */
	private byte[] photo;

	/** 现从事专业. */
	private String specialist;

	/** 专业从事时间-从. */
	private Date specialFrom;

	/** 专业从事时间-到. */
	private Date specialTo;

	/** 现任评审资格. */
	private String nowcertKind;

	/** 资格级别. */
	private String certLevel;

	/** 取得时间. */
	private Date certGettime;

	/** 所属系列. */
	private String certSeries;

	/** 聘任情况. */
	private String workStatus;

	/** 曾任何评委. */
	private String everRater;

	/** 曾任评委职务. */
	private String everRaterJob;

	/** 曾任评委时间. */
	private Date everRaterTime;

	/** 推荐单位意见. */
	private String pushAdvice;

	/** 行业部门〈或管理单位)初审意见. */
	private String deptormanageAdvice;

	/** The set of 评委会信息表. */
	private Set<CommitteeInfo> committeeInfoSet;

	/** The set of 委员会专家（评委）表. */
	private Set<CommitteeExpert> committeeExpertSet;

	/** The set of 专家请假表. */
	private Set<ExpertLeave> expertLeaveSet;

	/**
	 * Constructor.
	 */
	public ExpertConfirm() {
		this.committeeExpertSet = new HashSet<CommitteeExpert>();
		this.committeeInfoSet = new HashSet<CommitteeInfo>();
		this.expertLeaveSet = new HashSet<ExpertLeave>();
	}

	@ManyToOne
	@JoinColumn(name="expertArea")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public Area getExpertArea() {
		return expertArea;
	}

	public void setExpertArea(Area expertArea) {
		this.expertArea = expertArea;
	}

	@ManyToOne
	@JoinColumn(name="expertCompany")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@ExcelField(title="单位", value="expertCompany.name", align=2, sort=2)
	public Office getExpertCompany() {
		return expertCompany;
	}

	public void setExpertCompany(Office expertCompany) {
		this.expertCompany = expertCompany;
	}

	@ExcelField(title="职称", align=2, sort=5, dictType="sys_tech_type")
	public String getExpertTechnical() {
		return this.expertTechnical;
	}

	public void setExpertTechnical(String expertTechnical) {
		this.expertTechnical = expertTechnical;
	}

	/**
	 * Set the 专家确认ID.
	 * 
	 * @param id
	 *            专家确认ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the 专家确认ID.
	 * 
	 * @return 专家确认ID
	 */
	@Id
	public String getId() {
		return this.id;
	}

	/**
	 * Set the 专家信息表.
	 * 
	 * @param expertInfo
	 *            专家信息表
	 */
	public void setExpertInfo(ExpertInfo expertInfo) {
		this.expertInfo = expertInfo;
	}

	/**
	 * Get the 专家信息表.
	 * 
	 * @return 专家信息表
	 */
	@ManyToOne
	@JoinColumn(name="userId")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@ExcelField(title="专家姓名", value="expertInfo.name", align=2, sort=1)
	public ExpertInfo getExpertInfo() {
		return this.expertInfo;
	}

	/**
	 * Set the 专家类别.
	 * 
	 * @param expertKind
	 *            专家类别
	 */
	public void setExpertKind(String expertKind) {
		this.expertKind = expertKind;
	}

	/**
	 * Get the 专家类别.
	 * 
	 * @return 专家类别
	 */
	@ExcelField(title="专家类别", align=2, sort=3, dictType="sys_specialkind_type")
	public String getExpertKind() {
		return this.expertKind;
	}

	/**
	 * Set the 专家专业.
	 * 
	 * @param expertSpecial
	 *            专家专业
	 */
	public void setExpertSpecial(String expertSpecial) {
		this.expertSpecial = expertSpecial;
	}

	/**
	 * Get the 专家专业.
	 * 
	 * @return 专家专业
	 */
	@ExcelField(title="专业", align=2, sort=4, dictType="sys_special_type")
	public String getExpertSpecial() {
		return this.expertSpecial;
	}

	/**
	 * Set the 专家所属系列.
	 * 
	 * @param expertSeries
	 *            专家所属系列
	 */
	public void setExpertSeries(String expertSeries) {
		this.expertSeries = expertSeries;
	}

	/**
	 * Get the 专家所属系列.
	 * 
	 * @return 专家所属系列
	 */
	public String getExpertSeries() {
		return this.expertSeries;
	}

	/**
	 * Set the 专家级别.
	 * 
	 * @param expertLevel
	 *            专家级别
	 */
	public void setExpertLevel(String expertLevel) {
		this.expertLevel = expertLevel;
	}

	/**
	 * Get the 专家级别.
	 * 
	 * @return 专家级别
	 */
	public String getExpertLevel() {
		return this.expertLevel;
	}

	/**
	 * Set the 照片.
	 * 
	 * @param photo
	 *            照片
	 */
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	/**
	 * Get the 照片.
	 * 
	 * @return 照片
	 */
	public byte[] getPhoto() {
		return this.photo;
	}

	/**
	 * Set the 现从事专业.
	 * 
	 * @param specialist
	 *            现从事专业
	 */
	public void setSpecialist(String specialist) {
		this.specialist = specialist;
	}

	/**
	 * Get the 现从事专业.
	 * 
	 * @return 现从事专业
	 */
	public String getSpecialist() {
		return this.specialist;
	}

	/**
	 * Set the 专业从事时间-从.
	 * 
	 * @param specialFrom
	 *            专业从事时间-从
	 */
	public void setSpecialFrom(Date specialFrom) {
		this.specialFrom = specialFrom;
	}

	/**
	 * Get the 专业从事时间-从.
	 * 
	 * @return 专业从事时间-从
	 */
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSpecialFrom() {
		return this.specialFrom;
	}

	/**
	 * Set the 专业从事时间-到.
	 * 
	 * @param specialTo
	 *            专业从事时间-到
	 */
	public void setSpecialTo(Date specialTo) {
		this.specialTo = specialTo;
	}

	/**
	 * Get the 专业从事时间-到.
	 * 
	 * @return 专业从事时间-到
	 */
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSpecialTo() {
		return this.specialTo;
	}

	/**
	 * Set the 现任评审资格.
	 * 
	 * @param nowcertKind
	 *            现任评审资格
	 */
	public void setNowcertKind(String nowcertKind) {
		this.nowcertKind = nowcertKind;
	}

	/**
	 * Get the 现任评审资格.
	 * 
	 * @return 现任评审资格
	 */
	public String getNowcertKind() {
		return this.nowcertKind;
	}

	/**
	 * Set the 资格级别.
	 * 
	 * @param certLevel
	 *            资格级别
	 */
	public void setCertLevel(String certLevel) {
		this.certLevel = certLevel;
	}

	/**
	 * Get the 资格级别.
	 * 
	 * @return 资格级别
	 */
	public String getCertLevel() {
		return this.certLevel;
	}

	/**
	 * Set the 取得时间.
	 * 
	 * @param certGettime
	 *            取得时间
	 */
	public void setCertGettime(Date certGettime) {
		this.certGettime = certGettime;
	}

	/**
	 * Get the 取得时间.
	 * 
	 * @return 取得时间
	 */
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCertGettime() {
		return this.certGettime;
	}

	/**
	 * Set the 所属系列.
	 * 
	 * @param certSeries
	 *            所属系列
	 */
	public void setCertSeries(String certSeries) {
		this.certSeries = certSeries;
	}

	/**
	 * Get the 所属系列.
	 * 
	 * @return 所属系列
	 */
	public String getCertSeries() {
		return this.certSeries;
	}

	/**
	 * Set the 聘任情况.
	 * 
	 * @param workStatus
	 *            聘任情况
	 */
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	/**
	 * Get the 聘任情况.
	 * 
	 * @return 聘任情况
	 */
	public String getWorkStatus() {
		return this.workStatus;
	}

	/**
	 * Set the 曾任何评委.
	 * 
	 * @param everRater
	 *            曾任何评委
	 */
	public void setEverRater(String everRater) {
		this.everRater = everRater;
	}

	/**
	 * Get the 曾任何评委.
	 * 
	 * @return 曾任何评委
	 */
	public String getEverRater() {
		return this.everRater;
	}

	/**
	 * Set the 曾任评委职务.
	 * 
	 * @param everRaterJob
	 *            曾任评委职务
	 */
	public void setEverRaterJob(String everRaterJob) {
		this.everRaterJob = everRaterJob;
	}

	/**
	 * Get the 曾任评委职务.
	 * 
	 * @return 曾任评委职务
	 */
	public String getEverRaterJob() {
		return this.everRaterJob;
	}

	/**
	 * Set the 曾任评委时间.
	 * 
	 * @param everRaterTime
	 *            曾任评委时间
	 */
	public void setEverRaterTime(Date everRaterTime) {
		this.everRaterTime = everRaterTime;
	}

	/**
	 * Get the 曾任评委时间.
	 * 
	 * @return 曾任评委时间
	 */
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEverRaterTime() {
		return this.everRaterTime;
	}

	/**
	 * Set the 推荐单位意见.
	 * 
	 * @param pushAdvice
	 *            推荐单位意见
	 */
	public void setPushAdvice(String pushAdvice) {
		this.pushAdvice = pushAdvice;
	}

	/**
	 * Get the 推荐单位意见.
	 * 
	 * @return 推荐单位意见
	 */
	public String getPushAdvice() {
		return this.pushAdvice;
	}

	/**
	 * Set the 行业部门〈或管理单位)初审意见.
	 * 
	 * @param deptormanageAdvice
	 *            行业部门〈或管理单位)初审意见
	 */
	public void setDeptormanageAdvice(String deptormanageAdvice) {
		this.deptormanageAdvice = deptormanageAdvice;
	}

	/**
	 * Get the 行业部门〈或管理单位)初审意见.
	 * 
	 * @return 行业部门〈或管理单位)初审意见
	 */
	public String getDeptormanageAdvice() {
		return this.deptormanageAdvice;
	}

	/**
	 * Set the set of the 评委会信息表.
	 * 
	 * @param committeeInfoSet
	 *            The set of 评委会信息表
	 */
	public void setCommitteeInfoSet(Set<CommitteeInfo> committeeInfoSet) {
		this.committeeInfoSet = committeeInfoSet;
	}

	/**
	 * Add the 评委会信息表.
	 * 
	 * @param committeeInfo
	 *            评委会信息表
	 */
	public void addCommitteeInfo(CommitteeInfo committeeInfo) {
		this.committeeInfoSet.add(committeeInfo);
	}

	/**
	 * Get the set of the 评委会信息表.
	 * 
	 * @return The set of 评委会信息表
	 */
	@Transient
	public Set<CommitteeInfo> getCommitteeInfoSet() {
		return this.committeeInfoSet;
	}

	/**
	 * Set the set of the 委员会专家（评委）表.
	 * 
	 * @param committeeExpertSet
	 *            The set of 委员会专家（评委）表
	 */
	public void setCommitteeExpertSet(Set<CommitteeExpert> committeeExpertSet) {
		this.committeeExpertSet = committeeExpertSet;
	}

	/**
	 * Add the 委员会专家（评委）表.
	 * 
	 * @param committeeExpert
	 *            委员会专家（评委）表
	 */
	public void addCommitteeExpert(CommitteeExpert committeeExpert) {
		this.committeeExpertSet.add(committeeExpert);
	}

	/**
	 * Get the set of the 委员会专家（评委）表.
	 * 
	 * @return The set of 委员会专家（评委）表
	 */
	@Transient
	public Set<CommitteeExpert> getCommitteeExpertSet() {
		return this.committeeExpertSet;
	}

	/**
	 * Set the set of the 专家请假表.
	 * 
	 * @param expertLeaveSet
	 *            The set of 专家请假表
	 */
	public void setExpertLeaveSet(Set<ExpertLeave> expertLeaveSet) {
		this.expertLeaveSet = expertLeaveSet;
	}

	/**
	 * Add the 专家请假表.
	 * 
	 * @param expertLeave
	 *            专家请假表
	 */
	public void addExpertLeave(ExpertLeave expertLeave) {
		this.expertLeaveSet.add(expertLeave);
	}

	/**
	 * Get the set of the 专家请假表.
	 * 
	 * @return The set of 专家请假表
	 */
	@Transient
	public Set<ExpertLeave> getExpertLeaveSet() {
		return this.expertLeaveSet;
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
		ExpertConfirm other = (ExpertConfirm) obj;
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
