import java.io.Serializable;

/**
 * Model class of 委员会专家（评委）表.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class CommitteeExpert implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 评委会信息表. */
	private CommitteeInfo committeeInfo;

	/** 专家确认表. */
	private ExpertConfirm expertConfirm;

	/** 专家担任评委的类别. */
	private String raterType;

	/**
	 * Constructor.
	 */
	public CommitteeExpert() {
	}

	/**
	 * Set the 评委会信息表.
	 * 
	 * @param committeeInfo
	 *            评委会信息表
	 */
	public void setCommitteeInfo(CommitteeInfo committeeInfo) {
		this.committeeInfo = committeeInfo;
	}

	/**
	 * Get the 评委会信息表.
	 * 
	 * @return 评委会信息表
	 */
	public CommitteeInfo getCommitteeInfo() {
		return this.committeeInfo;
	}

	/**
	 * Set the 专家确认表.
	 * 
	 * @param expertConfirm
	 *            专家确认表
	 */
	public void setExpertConfirm(ExpertConfirm expertConfirm) {
		this.expertConfirm = expertConfirm;
	}

	/**
	 * Get the 专家确认表.
	 * 
	 * @return 专家确认表
	 */
	public ExpertConfirm getExpertConfirm() {
		return this.expertConfirm;
	}

	/**
	 * Set the 专家担任评委的类别.
	 * 
	 * @param raterType
	 *            专家担任评委的类别
	 */
	public void setRaterType(String raterType) {
		this.raterType = raterType;
	}

	/**
	 * Get the 专家担任评委的类别.
	 * 
	 * @return 专家担任评委的类别
	 */
	public String getRaterType() {
		return this.raterType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((committeeInfo == null) ? 0 : committeeInfo.hashCode());
		result = prime * result + ((expertConfirm == null) ? 0 : expertConfirm.hashCode());
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
		CommitteeExpert other = (CommitteeExpert) obj;
		if (committeeInfo == null) {
			if (other.committeeInfo != null) {
				return false;
			}
		} else if (!committeeInfo.equals(other.committeeInfo)) {
			return false;
		}
		if (expertConfirm == null) {
			if (other.expertConfirm != null) {
				return false;
			}
		} else if (!expertConfirm.equals(other.expertConfirm)) {
			return false;
		}
		return true;
	}

}
