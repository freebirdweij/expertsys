import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class of 专家信息表.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class ExpertInfo implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 用户编号. */
	private String userId;

	/** 姓名. */
	private String name;

	/** 性别. */
	private String sex;

	/** 出生年月. */
	private Date birthdate;

	/** 政治面貌. */
	private String politics;

	/** 民族. */
	private String nation;

	/** 身份证号. */
	private String identifyCode;

	/** 工作单位. */
	private String company;

	/** 职务. */
	private String job;

	/** 职称. */
	private String technical;

	/** 职称评定时间. */
	private Date techGettime;

	/** 执业资格. */
	private String workCert;

	/** 取得时间. */
	private Date certGettime;

	/** 现从事专业. */
	private String specialist;

	/** 专业从事时间-从. */
	private Date specialFrom;

	/** 专业从事时间-到. */
	private Date specialTo;

	/** 单位地址. */
	private String companyAddr;

	/** 单位电话. */
	private String companyPhone;

	/** 单位邮编. */
	private String companyMailcode;

	/** 家庭地址. */
	private String homeAddr;

	/** 家庭电话. */
	private String homePhone;

	/** 家庭邮编. */
	private String homeMailcode;

	/** 手机. */
	private String mobile;

	/** 电子邮箱. */
	private String email;

	/** 毕业学校. */
	private String collage;

	/** 毕业时间. */
	private Date graduateTime;

	/** 学历. */
	private String education;

	/** 所学专业. */
	private String studySpecial;

	/** 负责或参与评审的重大项目及学术论著. */
	private String hardProjectsArticals;

	/** 本人的专业特长. */
	private String mySpecials;

	/** 本人认为在项目评审中需回避的单位（项目）. */
	private String selfAvoid;

	/** 推荐单位意见. */
	private String pushAdvice;

	/** 本人所在单位组织人事部门意见. */
	private String companyAdvice;

	/** 财政部门意见. */
	private String accountAdvice;

	/** 照片. */
	private Blob picture;

	/** 学位. */
	private String myDegree;

	/** 参加工作时间. */
	private Date startworkTime;

	/** 是否所属单位组长人员. */
	private String ifTeamleader;

	/** 健康状况. */
	private String health;

	/** 现任评审资格. */
	private String nowcertKind;

	/** 资格级别. */
	private String certLevel;

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

	/** 通信地址. */
	private String address;

	/** 申报类别1. */
	private String specialKind1;

	/** 申报类别1的申报专业1. */
	private String kind1Special1;

	/** 申报类别1的申报专业2. */
	private String kind1Special2;

	/** 申报类别2. */
	private String specialKind2;

	/** 申报类别2的申报专业1. */
	private String kind2Special1;

	/** 申报类别2的申报专业2. */
	private String kind2Special2;

	/** 工作经历. */
	private String workThrough;

	/** 主要业绩. */
	private String achievement;

	/** 行业部门〈或管理单位)初审意见. */
	private String deptormanageAdvice;

	/** 注册资料完成状态. */
	private String regStep;

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

	/** The set of 专家确认表. */
	private Set<ExpertConfirm> expertConfirmSet;

	/** The set of 专家附件. */
	private Set<ExpertAttach> expertAttachSet;

	/**
	 * Constructor.
	 */
	public ExpertInfo() {
		this.expertAttachSet = new HashSet<ExpertAttach>();
		this.expertConfirmSet = new HashSet<ExpertConfirm>();
	}

	/**
	 * Set the 用户编号.
	 * 
	 * @param userId
	 *            用户编号
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Get the 用户编号.
	 * 
	 * @return 用户编号
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * Set the 姓名.
	 * 
	 * @param name
	 *            姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the 姓名.
	 * 
	 * @return 姓名
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the 性别.
	 * 
	 * @param sex
	 *            性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * Get the 性别.
	 * 
	 * @return 性别
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * Set the 出生年月.
	 * 
	 * @param birthdate
	 *            出生年月
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * Get the 出生年月.
	 * 
	 * @return 出生年月
	 */
	public Date getBirthdate() {
		return this.birthdate;
	}

	/**
	 * Set the 政治面貌.
	 * 
	 * @param politics
	 *            政治面貌
	 */
	public void setPolitics(String politics) {
		this.politics = politics;
	}

	/**
	 * Get the 政治面貌.
	 * 
	 * @return 政治面貌
	 */
	public String getPolitics() {
		return this.politics;
	}

	/**
	 * Set the 民族.
	 * 
	 * @param nation
	 *            民族
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}

	/**
	 * Get the 民族.
	 * 
	 * @return 民族
	 */
	public String getNation() {
		return this.nation;
	}

	/**
	 * Set the 身份证号.
	 * 
	 * @param identifyCode
	 *            身份证号
	 */
	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	/**
	 * Get the 身份证号.
	 * 
	 * @return 身份证号
	 */
	public String getIdentifyCode() {
		return this.identifyCode;
	}

	/**
	 * Set the 工作单位.
	 * 
	 * @param company
	 *            工作单位
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * Get the 工作单位.
	 * 
	 * @return 工作单位
	 */
	public String getCompany() {
		return this.company;
	}

	/**
	 * Set the 职务.
	 * 
	 * @param job
	 *            职务
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * Get the 职务.
	 * 
	 * @return 职务
	 */
	public String getJob() {
		return this.job;
	}

	/**
	 * Set the 职称.
	 * 
	 * @param technical
	 *            职称
	 */
	public void setTechnical(String technical) {
		this.technical = technical;
	}

	/**
	 * Get the 职称.
	 * 
	 * @return 职称
	 */
	public String getTechnical() {
		return this.technical;
	}

	/**
	 * Set the 职称评定时间.
	 * 
	 * @param techGettime
	 *            职称评定时间
	 */
	public void setTechGettime(Date techGettime) {
		this.techGettime = techGettime;
	}

	/**
	 * Get the 职称评定时间.
	 * 
	 * @return 职称评定时间
	 */
	public Date getTechGettime() {
		return this.techGettime;
	}

	/**
	 * Set the 执业资格.
	 * 
	 * @param workCert
	 *            执业资格
	 */
	public void setWorkCert(String workCert) {
		this.workCert = workCert;
	}

	/**
	 * Get the 执业资格.
	 * 
	 * @return 执业资格
	 */
	public String getWorkCert() {
		return this.workCert;
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
	public Date getCertGettime() {
		return this.certGettime;
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
	public Date getSpecialTo() {
		return this.specialTo;
	}

	/**
	 * Set the 单位地址.
	 * 
	 * @param companyAddr
	 *            单位地址
	 */
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	/**
	 * Get the 单位地址.
	 * 
	 * @return 单位地址
	 */
	public String getCompanyAddr() {
		return this.companyAddr;
	}

	/**
	 * Set the 单位电话.
	 * 
	 * @param companyPhone
	 *            单位电话
	 */
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	/**
	 * Get the 单位电话.
	 * 
	 * @return 单位电话
	 */
	public String getCompanyPhone() {
		return this.companyPhone;
	}

	/**
	 * Set the 单位邮编.
	 * 
	 * @param companyMailcode
	 *            单位邮编
	 */
	public void setCompanyMailcode(String companyMailcode) {
		this.companyMailcode = companyMailcode;
	}

	/**
	 * Get the 单位邮编.
	 * 
	 * @return 单位邮编
	 */
	public String getCompanyMailcode() {
		return this.companyMailcode;
	}

	/**
	 * Set the 家庭地址.
	 * 
	 * @param homeAddr
	 *            家庭地址
	 */
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	/**
	 * Get the 家庭地址.
	 * 
	 * @return 家庭地址
	 */
	public String getHomeAddr() {
		return this.homeAddr;
	}

	/**
	 * Set the 家庭电话.
	 * 
	 * @param homePhone
	 *            家庭电话
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	/**
	 * Get the 家庭电话.
	 * 
	 * @return 家庭电话
	 */
	public String getHomePhone() {
		return this.homePhone;
	}

	/**
	 * Set the 家庭邮编.
	 * 
	 * @param homeMailcode
	 *            家庭邮编
	 */
	public void setHomeMailcode(String homeMailcode) {
		this.homeMailcode = homeMailcode;
	}

	/**
	 * Get the 家庭邮编.
	 * 
	 * @return 家庭邮编
	 */
	public String getHomeMailcode() {
		return this.homeMailcode;
	}

	/**
	 * Set the 手机.
	 * 
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Get the 手机.
	 * 
	 * @return 手机
	 */
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * Set the 电子邮箱.
	 * 
	 * @param email
	 *            电子邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the 电子邮箱.
	 * 
	 * @return 电子邮箱
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Set the 毕业学校.
	 * 
	 * @param collage
	 *            毕业学校
	 */
	public void setCollage(String collage) {
		this.collage = collage;
	}

	/**
	 * Get the 毕业学校.
	 * 
	 * @return 毕业学校
	 */
	public String getCollage() {
		return this.collage;
	}

	/**
	 * Set the 毕业时间.
	 * 
	 * @param graduateTime
	 *            毕业时间
	 */
	public void setGraduateTime(Date graduateTime) {
		this.graduateTime = graduateTime;
	}

	/**
	 * Get the 毕业时间.
	 * 
	 * @return 毕业时间
	 */
	public Date getGraduateTime() {
		return this.graduateTime;
	}

	/**
	 * Set the 学历.
	 * 
	 * @param education
	 *            学历
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * Get the 学历.
	 * 
	 * @return 学历
	 */
	public String getEducation() {
		return this.education;
	}

	/**
	 * Set the 所学专业.
	 * 
	 * @param studySpecial
	 *            所学专业
	 */
	public void setStudySpecial(String studySpecial) {
		this.studySpecial = studySpecial;
	}

	/**
	 * Get the 所学专业.
	 * 
	 * @return 所学专业
	 */
	public String getStudySpecial() {
		return this.studySpecial;
	}

	/**
	 * Set the 负责或参与评审的重大项目及学术论著.
	 * 
	 * @param hardProjectsArticals
	 *            负责或参与评审的重大项目及学术论著
	 */
	public void setHardProjectsArticals(String hardProjectsArticals) {
		this.hardProjectsArticals = hardProjectsArticals;
	}

	/**
	 * Get the 负责或参与评审的重大项目及学术论著.
	 * 
	 * @return 负责或参与评审的重大项目及学术论著
	 */
	public String getHardProjectsArticals() {
		return this.hardProjectsArticals;
	}

	/**
	 * Set the 本人的专业特长.
	 * 
	 * @param mySpecials
	 *            本人的专业特长
	 */
	public void setMySpecials(String mySpecials) {
		this.mySpecials = mySpecials;
	}

	/**
	 * Get the 本人的专业特长.
	 * 
	 * @return 本人的专业特长
	 */
	public String getMySpecials() {
		return this.mySpecials;
	}

	/**
	 * Set the 本人认为在项目评审中需回避的单位（项目）.
	 * 
	 * @param selfAvoid
	 *            本人认为在项目评审中需回避的单位（项目）
	 */
	public void setSelfAvoid(String selfAvoid) {
		this.selfAvoid = selfAvoid;
	}

	/**
	 * Get the 本人认为在项目评审中需回避的单位（项目）.
	 * 
	 * @return 本人认为在项目评审中需回避的单位（项目）
	 */
	public String getSelfAvoid() {
		return this.selfAvoid;
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
	 * Set the 本人所在单位组织人事部门意见.
	 * 
	 * @param companyAdvice
	 *            本人所在单位组织人事部门意见
	 */
	public void setCompanyAdvice(String companyAdvice) {
		this.companyAdvice = companyAdvice;
	}

	/**
	 * Get the 本人所在单位组织人事部门意见.
	 * 
	 * @return 本人所在单位组织人事部门意见
	 */
	public String getCompanyAdvice() {
		return this.companyAdvice;
	}

	/**
	 * Set the 财政部门意见.
	 * 
	 * @param accountAdvice
	 *            财政部门意见
	 */
	public void setAccountAdvice(String accountAdvice) {
		this.accountAdvice = accountAdvice;
	}

	/**
	 * Get the 财政部门意见.
	 * 
	 * @return 财政部门意见
	 */
	public String getAccountAdvice() {
		return this.accountAdvice;
	}

	/**
	 * Set the 照片.
	 * 
	 * @param picture
	 *            照片
	 */
	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	/**
	 * Get the 照片.
	 * 
	 * @return 照片
	 */
	public Blob getPicture() {
		return this.picture;
	}

	/**
	 * Set the 学位.
	 * 
	 * @param myDegree
	 *            学位
	 */
	public void setMyDegree(String myDegree) {
		this.myDegree = myDegree;
	}

	/**
	 * Get the 学位.
	 * 
	 * @return 学位
	 */
	public String getMyDegree() {
		return this.myDegree;
	}

	/**
	 * Set the 参加工作时间.
	 * 
	 * @param startworkTime
	 *            参加工作时间
	 */
	public void setStartworkTime(Date startworkTime) {
		this.startworkTime = startworkTime;
	}

	/**
	 * Get the 参加工作时间.
	 * 
	 * @return 参加工作时间
	 */
	public Date getStartworkTime() {
		return this.startworkTime;
	}

	/**
	 * Set the 是否所属单位组长人员.
	 * 
	 * @param ifTeamleader
	 *            是否所属单位组长人员
	 */
	public void setIfTeamleader(String ifTeamleader) {
		this.ifTeamleader = ifTeamleader;
	}

	/**
	 * Get the 是否所属单位组长人员.
	 * 
	 * @return 是否所属单位组长人员
	 */
	public String getIfTeamleader() {
		return this.ifTeamleader;
	}

	/**
	 * Set the 健康状况.
	 * 
	 * @param health
	 *            健康状况
	 */
	public void setHealth(String health) {
		this.health = health;
	}

	/**
	 * Get the 健康状况.
	 * 
	 * @return 健康状况
	 */
	public String getHealth() {
		return this.health;
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
	public Date getEverRaterTime() {
		return this.everRaterTime;
	}

	/**
	 * Set the 通信地址.
	 * 
	 * @param address
	 *            通信地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get the 通信地址.
	 * 
	 * @return 通信地址
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set the 申报类别1.
	 * 
	 * @param specialKind1
	 *            申报类别1
	 */
	public void setSpecialKind1(String specialKind1) {
		this.specialKind1 = specialKind1;
	}

	/**
	 * Get the 申报类别1.
	 * 
	 * @return 申报类别1
	 */
	public String getSpecialKind1() {
		return this.specialKind1;
	}

	/**
	 * Set the 申报类别1的申报专业1.
	 * 
	 * @param kind1Special1
	 *            申报类别1的申报专业1
	 */
	public void setKind1Special1(String kind1Special1) {
		this.kind1Special1 = kind1Special1;
	}

	/**
	 * Get the 申报类别1的申报专业1.
	 * 
	 * @return 申报类别1的申报专业1
	 */
	public String getKind1Special1() {
		return this.kind1Special1;
	}

	/**
	 * Set the 申报类别1的申报专业2.
	 * 
	 * @param kind1Special2
	 *            申报类别1的申报专业2
	 */
	public void setKind1Special2(String kind1Special2) {
		this.kind1Special2 = kind1Special2;
	}

	/**
	 * Get the 申报类别1的申报专业2.
	 * 
	 * @return 申报类别1的申报专业2
	 */
	public String getKind1Special2() {
		return this.kind1Special2;
	}

	/**
	 * Set the 申报类别2.
	 * 
	 * @param specialKind2
	 *            申报类别2
	 */
	public void setSpecialKind2(String specialKind2) {
		this.specialKind2 = specialKind2;
	}

	/**
	 * Get the 申报类别2.
	 * 
	 * @return 申报类别2
	 */
	public String getSpecialKind2() {
		return this.specialKind2;
	}

	/**
	 * Set the 申报类别2的申报专业1.
	 * 
	 * @param kind2Special1
	 *            申报类别2的申报专业1
	 */
	public void setKind2Special1(String kind2Special1) {
		this.kind2Special1 = kind2Special1;
	}

	/**
	 * Get the 申报类别2的申报专业1.
	 * 
	 * @return 申报类别2的申报专业1
	 */
	public String getKind2Special1() {
		return this.kind2Special1;
	}

	/**
	 * Set the 申报类别2的申报专业2.
	 * 
	 * @param kind2Special2
	 *            申报类别2的申报专业2
	 */
	public void setKind2Special2(String kind2Special2) {
		this.kind2Special2 = kind2Special2;
	}

	/**
	 * Get the 申报类别2的申报专业2.
	 * 
	 * @return 申报类别2的申报专业2
	 */
	public String getKind2Special2() {
		return this.kind2Special2;
	}

	/**
	 * Set the 工作经历.
	 * 
	 * @param workThrough
	 *            工作经历
	 */
	public void setWorkThrough(String workThrough) {
		this.workThrough = workThrough;
	}

	/**
	 * Get the 工作经历.
	 * 
	 * @return 工作经历
	 */
	public String getWorkThrough() {
		return this.workThrough;
	}

	/**
	 * Set the 主要业绩.
	 * 
	 * @param achievement
	 *            主要业绩
	 */
	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	/**
	 * Get the 主要业绩.
	 * 
	 * @return 主要业绩
	 */
	public String getAchievement() {
		return this.achievement;
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
	 * Set the 注册资料完成状态.
	 * 
	 * @param regStep
	 *            注册资料完成状态
	 */
	public void setRegStep(String regStep) {
		this.regStep = regStep;
	}

	/**
	 * Get the 注册资料完成状态.
	 * 
	 * @return 注册资料完成状态
	 */
	public String getRegStep() {
		return this.regStep;
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
	 * Set the set of the 专家确认表.
	 * 
	 * @param expertConfirmSet
	 *            The set of 专家确认表
	 */
	public void setExpertConfirmSet(Set<ExpertConfirm> expertConfirmSet) {
		this.expertConfirmSet = expertConfirmSet;
	}

	/**
	 * Add the 专家确认表.
	 * 
	 * @param expertConfirm
	 *            专家确认表
	 */
	public void addExpertConfirm(ExpertConfirm expertConfirm) {
		this.expertConfirmSet.add(expertConfirm);
	}

	/**
	 * Get the set of the 专家确认表.
	 * 
	 * @return The set of 专家确认表
	 */
	public Set<ExpertConfirm> getExpertConfirmSet() {
		return this.expertConfirmSet;
	}

	/**
	 * Set the set of the 专家附件.
	 * 
	 * @param expertAttachSet
	 *            The set of 专家附件
	 */
	public void setExpertAttachSet(Set<ExpertAttach> expertAttachSet) {
		this.expertAttachSet = expertAttachSet;
	}

	/**
	 * Add the 专家附件.
	 * 
	 * @param expertAttach
	 *            专家附件
	 */
	public void addExpertAttach(ExpertAttach expertAttach) {
		this.expertAttachSet.add(expertAttach);
	}

	/**
	 * Get the set of the 专家附件.
	 * 
	 * @return The set of 专家附件
	 */
	public Set<ExpertAttach> getExpertAttachSet() {
		return this.expertAttachSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		ExpertInfo other = (ExpertInfo) obj;
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

}
