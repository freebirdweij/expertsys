
/* Drop Indexes */

DROP INDEX attach_name_index;
DROP INDEX attach_type_index;
DROP INDEX expert_company_index;
DROP INDEX expert_name_index;
DROP INDEX expert_tech_index;
DROP INDEX expert_special_index;
DROP INDEX confirm_special_index;
DROP INDEX confirm_nowcert_index;
DROP INDEX confirm_series_index;



/* Drop Tables */

DROP TABLE committee_expert CASCADE CONSTRAINTS;
DROP TABLE expert_leave CASCADE CONSTRAINTS;
DROP TABLE expert_attach CASCADE CONSTRAINTS;
DROP TABLE committee_info CASCADE CONSTRAINTS;
DROP TABLE expert_confirm CASCADE CONSTRAINTS;
DROP TABLE expert_info CASCADE CONSTRAINTS;




/* Create Tables */

CREATE TABLE committee_expert
(
	comittee_id nvarchar2(64) NOT NULL,
	expert_id nvarchar2(64) NOT NULL,
	rater_type char(2),
	PRIMARY KEY (comittee_id, expert_id)
);


CREATE TABLE expert_leave
(
	id nvarchar2(64) NOT NULL,
	expert_id nvarchar2(64) NOT NULL,
	reason varchar2(200),
	leave_start timestamp,
	leave_end timestamp,
	leave_confirm char,
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
	PRIMARY KEY (id)
);


CREATE TABLE expert_attach
(
	id nvarchar2(64) NOT NULL,
	user_id nvarchar2(64) NOT NULL,
	attach_name varchar2(100) NOT NULL,
	attach_type char(3),
	attach_link varchar2(300),
	attach_store blob,
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
	PRIMARY KEY (id)
);


CREATE TABLE committee_info
(
	id nvarchar2(64) NOT NULL,
	committee varchar2(500),
	team_leader nvarchar2(64) NOT NULL,
	rater_count number(3,0),
	commit_time timestamp,
	commitee_remark varchar2(1000),
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
	PRIMARY KEY (id)
);


CREATE TABLE expert_info
(
	user_id nvarchar2(64) NOT NULL,
	name varchar2(100) NOT NULL,
	-- 性别
	sex char DEFAULT '' NOT NULL,
	-- 出生年月
	birthdate timestamp,
	politics char,
	nation char(3),
	identify_code varchar2(100),
	-- 工作单位
	company varchar2(64),
	job varchar2(100),
	technical char(2),
	-- 职称评定时间
	tech_gettime timestamp,
	work_cert varchar2(100),
	cert_gettime timestamp,
	specialist varchar2(200),
	-- 专业从事时间-从
	special_from timestamp,
	-- 专业从事时间-到
	special_to timestamp,
	company_addr varchar2(500),
	company_phone varchar2(100),
	company_mailcode varchar2(50),
	home_addr varchar2(500),
	home_phone varchar2(100),
	home_mailcode varchar2(50),
	mobile varchar2(100),
	email varchar2(200),
	collage varchar2(300),
	graduate_time timestamp,
	education char(2),
	study_special varchar2(200),
	-- 负责或参与评审的重大项目及学术论著
	hard_projects_articals varchar2(2000),
	-- 本人的专业特长
	my_specials varchar2(2000),
	-- 本人认为在项目评审中需回避的单位（项目）
	self_avoid varchar2(500),
	push_advice varchar2(2000),
	-- 本人所在单位组织人事部门意见
	company_advice varchar2(2000),
	-- 财政部门意见
	account_advice varchar2(300),
	picture blob,
	my_degree char,
	startwork_time timestamp,
	if_teamleader char,
	health char,
	nowcert_kind char,
	cert_level char,
	cert_series char(3),
	work_status char,
	ever_rater nvarchar2(300),
	ever_rater_job char,
	ever_rater_time timestamp,
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
	reg_step char,
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


CREATE TABLE expert_confirm
(
	id nvarchar2(64) NOT NULL,
	user_id nvarchar2(64) NOT NULL,
	expert_area nvarchar2(64),
	expert_company nvarchar2(64),
	expert_kind char(3) NOT NULL,
	expert_special char(3) NOT NULL,
	expert_series char(3),
	expert_technical char(3),
	expert_level char,
	photo blob,
	specialist varchar2(200),
	-- 专业从事时间-从
	special_from timestamp,
	-- 专业从事时间-到
	special_to timestamp,
	nowcert_kind char,
	cert_level char,
	cert_gettime timestamp,
	cert_series char(3),
	work_status char,
	ever_rater nvarchar2(300),
	ever_rater_job char,
	ever_rater_time timestamp,
	push_advice varchar2(2000),
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
	PRIMARY KEY (id)
);



/* Create Foreign Keys */

ALTER TABLE committee_expert
	ADD FOREIGN KEY (comittee_id)
	REFERENCES committee_info (id)
;


ALTER TABLE expert_attach
	ADD FOREIGN KEY (user_id)
	REFERENCES expert_info (user_id)
;


ALTER TABLE expert_confirm
	ADD FOREIGN KEY (user_id)
	REFERENCES expert_info (user_id)
;


ALTER TABLE committee_info
	ADD FOREIGN KEY (team_leader)
	REFERENCES expert_confirm (id)
;


ALTER TABLE expert_leave
	ADD FOREIGN KEY (expert_id)
	REFERENCES expert_confirm (id)
;


ALTER TABLE committee_expert
	ADD FOREIGN KEY (expert_id)
	REFERENCES expert_confirm (id)
;



/* Create Indexes */

CREATE INDEX attach_name_index ON expert_attach (attach_name);
CREATE INDEX attach_type_index ON expert_attach (attach_type);
CREATE INDEX expert_company_index ON expert_info (company);
CREATE INDEX expert_name_index ON expert_info (name);
CREATE INDEX expert_tech_index ON expert_info (technical);
CREATE INDEX expert_special_index ON expert_info (specialist);
CREATE INDEX confirm_special_index ON expert_confirm (expert_special);
CREATE INDEX confirm_nowcert_index ON expert_confirm (expert_technical);
CREATE INDEX confirm_series_index ON expert_confirm (expert_series);



/* Comments */

COMMENT ON TABLE committee_expert IS '委员会专家（评委）表';
COMMENT ON COLUMN committee_expert.comittee_id IS '评委会ID';
COMMENT ON COLUMN committee_expert.expert_id IS '专家确认ID';
COMMENT ON COLUMN committee_expert.rater_type IS '专家担任评委的类别';
COMMENT ON TABLE expert_leave IS '专家请假表';
COMMENT ON COLUMN expert_leave.id IS '专家请假ID';
COMMENT ON COLUMN expert_leave.expert_id IS '专家ID';
COMMENT ON COLUMN expert_leave.reason IS '请假事项原因';
COMMENT ON COLUMN expert_leave.leave_start IS '请假开始时间';
COMMENT ON COLUMN expert_leave.leave_end IS '请假结束时间';
COMMENT ON COLUMN expert_leave.leave_confirm IS '请假获批标志';
COMMENT ON COLUMN expert_leave.create_by IS '创建者';
COMMENT ON COLUMN expert_leave.create_date IS '创建时间';
COMMENT ON COLUMN expert_leave.update_by IS '更新者';
COMMENT ON COLUMN expert_leave.update_date IS '更新时间';
COMMENT ON COLUMN expert_leave.remarks IS '备注信息';
COMMENT ON COLUMN expert_leave.del_flag IS '删除标记';
COMMENT ON TABLE expert_attach IS '专家附件';
COMMENT ON COLUMN expert_attach.id IS '附件ID';
COMMENT ON COLUMN expert_attach.user_id IS '用户编号';
COMMENT ON COLUMN expert_attach.attach_name IS '附件名称';
COMMENT ON COLUMN expert_attach.attach_type IS '附件类型';
COMMENT ON COLUMN expert_attach.attach_link IS '附件位置';
COMMENT ON COLUMN expert_attach.attach_store IS '附件存储';
COMMENT ON COLUMN expert_attach.create_by IS '创建者';
COMMENT ON COLUMN expert_attach.create_date IS '创建时间';
COMMENT ON COLUMN expert_attach.update_by IS '更新者';
COMMENT ON COLUMN expert_attach.update_date IS '更新时间';
COMMENT ON COLUMN expert_attach.remarks IS '备注信息';
COMMENT ON COLUMN expert_attach.del_flag IS '删除标记';
COMMENT ON TABLE committee_info IS '评委会信息表';
COMMENT ON COLUMN committee_info.id IS '评委会ID';
COMMENT ON COLUMN committee_info.committee IS '评委会名称';
COMMENT ON COLUMN committee_info.team_leader IS '组长';
COMMENT ON COLUMN committee_info.rater_count IS '委员数';
COMMENT ON COLUMN committee_info.commit_time IS '组成时间';
COMMENT ON COLUMN committee_info.commitee_remark IS '对成立本评委会的说明';
COMMENT ON COLUMN committee_info.create_by IS '创建者';
COMMENT ON COLUMN committee_info.create_date IS '创建时间';
COMMENT ON COLUMN committee_info.update_by IS '更新者';
COMMENT ON COLUMN committee_info.update_date IS '更新时间';
COMMENT ON COLUMN committee_info.remarks IS '备注信息';
COMMENT ON COLUMN committee_info.del_flag IS '删除标记';
COMMENT ON TABLE expert_info IS '专家信息表';
COMMENT ON COLUMN expert_info.user_id IS '用户编号';
COMMENT ON COLUMN expert_info.name IS '姓名';
COMMENT ON COLUMN expert_info.sex IS '性别';
COMMENT ON COLUMN expert_info.birthdate IS '出生年月';
COMMENT ON COLUMN expert_info.politics IS '政治面貌';
COMMENT ON COLUMN expert_info.nation IS '民族';
COMMENT ON COLUMN expert_info.identify_code IS '身份证号';
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
COMMENT ON COLUMN expert_info.push_advice IS '推荐单位意见';
COMMENT ON COLUMN expert_info.company_advice IS '本人所在单位组织人事部门意见';
COMMENT ON COLUMN expert_info.account_advice IS '财政部门意见';
COMMENT ON COLUMN expert_info.picture IS '照片';
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
COMMENT ON COLUMN expert_info.reg_step IS '注册资料完成状态';
COMMENT ON COLUMN expert_info.create_by IS '创建者';
COMMENT ON COLUMN expert_info.create_date IS '创建时间';
COMMENT ON COLUMN expert_info.update_by IS '更新者';
COMMENT ON COLUMN expert_info.update_date IS '更新时间';
COMMENT ON COLUMN expert_info.remarks IS '备注信息';
COMMENT ON COLUMN expert_info.del_flag IS '删除标记';
COMMENT ON TABLE expert_confirm IS '专家确认表';
COMMENT ON COLUMN expert_confirm.id IS '专家确认ID';
COMMENT ON COLUMN expert_confirm.user_id IS '用户编号';
COMMENT ON COLUMN expert_confirm.expert_area IS '专家所属区域';
COMMENT ON COLUMN expert_confirm.expert_company IS '专家所属单位';
COMMENT ON COLUMN expert_confirm.expert_kind IS '专家类别';
COMMENT ON COLUMN expert_confirm.expert_special IS '专家专业';
COMMENT ON COLUMN expert_confirm.expert_series IS '专家所属行业';
COMMENT ON COLUMN expert_confirm.expert_technical IS '专家职称';
COMMENT ON COLUMN expert_confirm.expert_level IS '专家级别';
COMMENT ON COLUMN expert_confirm.photo IS '照片';
COMMENT ON COLUMN expert_confirm.specialist IS '现从事专业';
COMMENT ON COLUMN expert_confirm.special_from IS '专业从事时间-从';
COMMENT ON COLUMN expert_confirm.special_to IS '专业从事时间-到';
COMMENT ON COLUMN expert_confirm.nowcert_kind IS '现任评审资格';
COMMENT ON COLUMN expert_confirm.cert_level IS '资格级别';
COMMENT ON COLUMN expert_confirm.cert_gettime IS '取得时间';
COMMENT ON COLUMN expert_confirm.cert_series IS '所属系列';
COMMENT ON COLUMN expert_confirm.work_status IS '聘任情况';
COMMENT ON COLUMN expert_confirm.ever_rater IS '曾任何评委';
COMMENT ON COLUMN expert_confirm.ever_rater_job IS '曾任评委职务';
COMMENT ON COLUMN expert_confirm.ever_rater_time IS '曾任评委时间';
COMMENT ON COLUMN expert_confirm.push_advice IS '推荐单位意见';
COMMENT ON COLUMN expert_confirm.deptormanage_advice IS '行业部门〈或管理单位)初审意见';
COMMENT ON COLUMN expert_confirm.create_by IS '创建者';
COMMENT ON COLUMN expert_confirm.create_date IS '创建时间';
COMMENT ON COLUMN expert_confirm.update_by IS '更新者';
COMMENT ON COLUMN expert_confirm.update_date IS '更新时间';
COMMENT ON COLUMN expert_confirm.remarks IS '备注信息';
COMMENT ON COLUMN expert_confirm.del_flag IS '删除标记';



