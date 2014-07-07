package com.thinkgem.jeesite.modules.project.entity;
import java.io.Serializable;

import com.thinkgem.jeesite.modules.experts.entity.CommitteeInfo;

/**
 * Model class of 项目评委表.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class ProjectCommittee implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 项目信息表. */
	private ProjectInfo prjProjectInfo;

	/** 评委会信息表. */
	private CommitteeInfo committeeCommitteeInfo;

	/**
	 * Constructor.
	 */
	public ProjectCommittee() {
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
	public ProjectInfo getPrjProjectInfo() {
		return this.prjProjectInfo;
	}

	/**
	 * Set the 评委会信息表.
	 * 
	 * @param committeeCommitteeInfo
	 *            评委会信息表
	 */
	public void setCommitteeCommitteeInfo(CommitteeInfo committeeCommitteeInfo) {
		this.committeeCommitteeInfo = committeeCommitteeInfo;
	}

	/**
	 * Get the 评委会信息表.
	 * 
	 * @return 评委会信息表
	 */
	public CommitteeInfo getCommitteeCommitteeInfo() {
		return this.committeeCommitteeInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prjProjectInfo == null) ? 0 : prjProjectInfo.hashCode());
		result = prime * result + ((committeeCommitteeInfo == null) ? 0 : committeeCommitteeInfo.hashCode());
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
		ProjectCommittee other = (ProjectCommittee) obj;
		if (prjProjectInfo == null) {
			if (other.prjProjectInfo != null) {
				return false;
			}
		} else if (!prjProjectInfo.equals(other.prjProjectInfo)) {
			return false;
		}
		if (committeeCommitteeInfo == null) {
			if (other.committeeCommitteeInfo != null) {
				return false;
			}
		} else if (!committeeCommitteeInfo.equals(other.committeeCommitteeInfo)) {
			return false;
		}
		return true;
	}

}
