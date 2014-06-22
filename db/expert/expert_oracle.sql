
/* Drop Indexes */

DROP INDEX expert_company_index;
DROP INDEX expert_name_index;



/* Drop Tables */

DROP TABLE expert_info CASCADE CONSTRAINTS;




/* Create Tables */

CREATE TABLE expert_info
(
	user_id nvarchar2(64) NOT NULL,
	name varchar2(100) NOT NULL,
	-- 性别
	sex char DEFAULT '' NOT NULL,
	-- 出生年月
	birthdate timestamp with local time zone,
	politics char,
	-- 工作单位
	company varchar2(64),
	job char(3),
	technical char(2),
	-- 职称评定时间
	tech_gettime timestamp with local time zone,
	work_cert char(2),
	cert_gettime timestamp with local time zone,
	specialist char(3),
	-- 专业从事时间-从
	special_from timestamp with local time zone,
	-- 专业从事时间-到
	special_to timestamp with local time zone,
	company_addr varchar2(500),
	company_phone varchar2(100),
	company_mailcode varchar2(50),
	home_addr varchar2(500),
	home_phone varchar2(100),
	home_mailcode varchar2(50),
	mobile varchar2(100),
	email varchar2(200),
	collage char(3),
	graduate_time timestamp with local time zone,
	education char(2),
	study_special char(3),
	-- 负责或参与评审的重大项目及学术论著
	hard_projects_articals varchar2(2000),
	-- 本人的专业特长
	my_specials varchar2(2000),
	-- 本人认为在项目评审中需回避的单位（项目）
	self_avoid varchar2(500),
	-- 本人所在单位组织人事部门意见
	company_advice varchar2(2000),
	-- 财政部门意见
	account_advice varchar2(300),
	picture varchar2(500),
	attachment varchar2(500),
	my_degree char,
	startwork_time timestamp with local time zone,
	if_teamleader char,
	health char,
	nowcert_kind char,
	cert_level char,
	cert_series char(2),
	work_status char,
	ever_rater nvarchar2(300),
	ever_rater_job char,
	ever_rater_time timestamp with local time zone,
	address varchar2(500),
	special_kind1 char(3),
	kind1_special1 char(3),
	kind1_special2 char(3),
	special_kind2 char(3),
	kind2_special1 char(3),
	kind2_special2 char(3),
	work_through varchar2(1000),
	achievement varchar2(3000),
	deptormanage_advice varchar2(2000),
	-- 创建者
	create_by varchar2(64),
	-- 创建时间
	create_date timestamp,
	-- 更新者
	update_by varchar2(64),
	-- 更新时间
	update_date timestamp,
	-- 备注信息
	remarks varchar2(255),
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (user_id)
);



/* Create Indexes */

CREATE INDEX expert_company_index ON expert_info (company);
CREATE INDEX expert_name_index ON expert_info (name);



/* Comments */

COMMENT ON TABLE expert_info IS '专家信息表';
COMMENT ON COLUMN expert_info.user_id IS '用户编号';
COMMENT ON COLUMN expert_info.name IS '姓名';
COMMENT ON COLUMN expert_info.sex IS '性别';
COMMENT ON COLUMN expert_info.birthdate IS '出生年月';
COMMENT ON COLUMN expert_info.politics IS '政治面貌';
COMMENT ON COLUMN expert_info.company IS '工作单位';
COMMENT ON COLUMN expert_info.job IS '职务';
COMMENT ON COLUMN expert_info.technical IS '职称';
COMMENT ON COLUMN expert_info.tech_gettime IS '职称评定时间';
COMMENT ON COLUMN expert_info.work_cert IS '执业资格';
COMMENT ON COLUMN expert_info.cert_gettime IS '取得时间';
COMMENT ON COLUMN expert_info.specialist IS '现从事专业';
COMMENT ON COLUMN expert_info.special_from IS '专业从事时间-从';
COMMENT ON COLUMN expert_info.special_to IS '专业从事时间-到';
COMMENT ON COLUMN expert_info.company_addr IS '单位地址';
COMMENT ON COLUMN expert_info.company_phone IS '单位电话';
COMMENT ON COLUMN expert_info.company_mailcode IS '单位邮编';
COMMENT ON COLUMN expert_info.home_addr IS '家庭地址';
COMMENT ON COLUMN expert_info.home_phone IS '家庭电话';
COMMENT ON COLUMN expert_info.home_mailcode IS '家庭邮编';
COMMENT ON COLUMN expert_info.mobile IS '手机';
COMMENT ON COLUMN expert_info.email IS '电子邮箱';
COMMENT ON COLUMN expert_info.collage IS '毕业学校';
COMMENT ON COLUMN expert_info.graduate_time IS '毕业时间';
COMMENT ON COLUMN expert_info.education IS '学历';
COMMENT ON COLUMN expert_info.study_special IS '所学专业';
COMMENT ON COLUMN expert_info.hard_projects_articals IS '负责或参与评审的重大项目及学术论著';
COMMENT ON COLUMN expert_info.my_specials IS '本人的专业特长';
COMMENT ON COLUMN expert_info.self_avoid IS '本人认为在项目评审中需回避的单位（项目）';
COMMENT ON COLUMN expert_info.company_advice IS '本人所在单位组织人事部门意见';
COMMENT ON COLUMN expert_info.account_advice IS '财政部门意见';
COMMENT ON COLUMN expert_info.picture IS '照片';
COMMENT ON COLUMN expert_info.attachment IS '附件';
COMMENT ON COLUMN expert_info.my_degree IS '学位';
COMMENT ON COLUMN expert_info.startwork_time IS '参加工作时间';
COMMENT ON COLUMN expert_info.if_teamleader IS '是否所属单位组长人员';
COMMENT ON COLUMN expert_info.health IS '健康状况';
COMMENT ON COLUMN expert_info.nowcert_kind IS '现任评审资格';
COMMENT ON COLUMN expert_info.cert_level IS '资格级别';
COMMENT ON COLUMN expert_info.cert_series IS '所属系列';
COMMENT ON COLUMN expert_info.work_status IS '聘任情况';
COMMENT ON COLUMN expert_info.ever_rater IS '曾任何评委';
COMMENT ON COLUMN expert_info.ever_rater_job IS '曾任评委职务';
COMMENT ON COLUMN expert_info.ever_rater_time IS '曾任评委时间';
COMMENT ON COLUMN expert_info.address IS '通信地址';
COMMENT ON COLUMN expert_info.special_kind1 IS '申报类别1';
COMMENT ON COLUMN expert_info.kind1_special1 IS '申报类别1的申报专业1';
COMMENT ON COLUMN expert_info.kind1_special2 IS '申报类别1的申报专业2';
COMMENT ON COLUMN expert_info.special_kind2 IS '申报类别2';
COMMENT ON COLUMN expert_info.kind2_special1 IS '申报类别2的申报专业1';
COMMENT ON COLUMN expert_info.kind2_special2 IS '申报类别2的申报专业2';
COMMENT ON COLUMN expert_info.work_through IS '工作经历';
COMMENT ON COLUMN expert_info.achievement IS '主要业绩';
COMMENT ON COLUMN expert_info.deptormanage_advice IS '行业部门〈或管理单位)初审意见';
COMMENT ON COLUMN expert_info.create_by IS '创建者';
COMMENT ON COLUMN expert_info.create_date IS '创建时间';
COMMENT ON COLUMN expert_info.update_by IS '更新者';
COMMENT ON COLUMN expert_info.update_date IS '更新时间';
COMMENT ON COLUMN expert_info.remarks IS '备注信息';
COMMENT ON COLUMN expert_info.del_flag IS '删除标记';



