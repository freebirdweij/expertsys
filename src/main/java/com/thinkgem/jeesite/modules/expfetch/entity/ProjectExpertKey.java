package com.thinkgem.jeesite.modules.expfetch.entity;

import java.io.Serializable;

import com.thinkgem.jeesite.modules.expmanage.entity.ExpertConfirm;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;

public class ProjectExpertKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2272066117749483590L;

	/** 项目信息表. */
	private ProjectInfo prjProjectInfo;

	/** 专家确认表. */
	private ExpertConfirm expertExpertConfirm;

	/** 第几次抽取. */
	private Integer fetchTime;
	public ProjectExpertKey() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public ProjectInfo getPrjProjectInfo() {
		return prjProjectInfo;
	}

	public void setPrjProjectInfo(ProjectInfo prjProjectInfo) {
		this.prjProjectInfo = prjProjectInfo;
	}

	public ExpertConfirm getExpertExpertConfirm() {
		return expertExpertConfirm;
	}

	public void setExpertExpertConfirm(ExpertConfirm expertExpertConfirm) {
		this.expertExpertConfirm = expertExpertConfirm;
	}

	public Integer getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(Integer fetchTime) {
		this.fetchTime = fetchTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prjProjectInfo == null) ? 0 : prjProjectInfo.hashCode());
		result = prime * result + ((expertExpertConfirm == null) ? 0 : expertExpertConfirm.hashCode());
		result = prime * result + ((fetchTime == null) ? 0 : fetchTime.hashCode());
		return result;
	}

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
		ProjectExpertKey other = (ProjectExpertKey) obj;
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
