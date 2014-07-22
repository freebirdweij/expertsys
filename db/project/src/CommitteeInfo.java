import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class of 评委会信息表.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class CommitteeInfo implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 评委会ID. */
	private String id;

	/** 评委会名称. */
	private String committee;

	/** 组长. */
	private String teamLeader;

	/** 委员数. */
	private Byte raterCount;

	/** 组成时间. */
	private Date commitTime;

	/** 对成立本评委会的说明. */
	private String commiteeRemark;

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

	/**
	 * Constructor.
	 */
	public CommitteeInfo() {
		this.projectCommitteeSet = new HashSet<ProjectCommittee>();
	}

	/**
	 * Set the 评委会ID.
	 * 
	 * @param id
	 *            评委会ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the 评委会ID.
	 * 
	 * @return 评委会ID
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Set the 评委会名称.
	 * 
	 * @param committee
	 *            评委会名称
	 */
	public void setCommittee(String committee) {
		this.committee = committee;
	}

	/**
	 * Get the 评委会名称.
	 * 
	 * @return 评委会名称
	 */
	public String getCommittee() {
		return this.committee;
	}

	/**
	 * Set the 组长.
	 * 
	 * @param teamLeader
	 *            组长
	 */
	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	/**
	 * Get the 组长.
	 * 
	 * @return 组长
	 */
	public String getTeamLeader() {
		return this.teamLeader;
	}

	/**
	 * Set the 委员数.
	 * 
	 * @param raterCount
	 *            委员数
	 */
	public void setRaterCount(Byte raterCount) {
		this.raterCount = raterCount;
	}

	/**
	 * Get the 委员数.
	 * 
	 * @return 委员数
	 */
	public Byte getRaterCount() {
		return this.raterCount;
	}

	/**
	 * Set the 组成时间.
	 * 
	 * @param commitTime
	 *            组成时间
	 */
	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	/**
	 * Get the 组成时间.
	 * 
	 * @return 组成时间
	 */
	public Date getCommitTime() {
		return this.commitTime;
	}

	/**
	 * Set the 对成立本评委会的说明.
	 * 
	 * @param commiteeRemark
	 *            对成立本评委会的说明
	 */
	public void setCommiteeRemark(String commiteeRemark) {
		this.commiteeRemark = commiteeRemark;
	}

	/**
	 * Get the 对成立本评委会的说明.
	 * 
	 * @return 对成立本评委会的说明
	 */
	public String getCommiteeRemark() {
		return this.commiteeRemark;
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
		CommitteeInfo other = (CommitteeInfo) obj;
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