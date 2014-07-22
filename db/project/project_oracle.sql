
/* Drop Indexes */

DROP INDEX attach_name_index;
DROP INDEX attach_type_index;
DROP INDEX confirm_special_index;
DROP INDEX confirm_nowcert_index;
DROP INDEX confirm_series_index;



/* Drop Tables */

DROP TABLE project_committee CASCADE CONSTRAINTS;
DROP TABLE project_attach CASCADE CONSTRAINTS;
DROP TABLE committee_info CASCADE CONSTRAINTS;
DROP TABLE project_expert CASCADE CONSTRAINTS;
DROP TABLE expert_confirm CASCADE CONSTRAINTS;
DROP TABLE project_info CASCADE CONSTRAINTS;




/* Create Tables */

CREATE TABLE project_committee
(
	prj_id nvarchar2(64) NOT NULL,
	committee_id nvarchar2(64) NOT NULL,
	PRIMARY KEY (prj_id, committee_id)
);


CREATE TABLE project_attach
(
	id nvarchar2(64) NOT NULL,
	prj_id nvarchar2(64) NOT NULL,
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


CREATE TABLE expert_confirm
(
	id nvarchar2(64) NOT NULL,
	user_id nvarchar2(64) NOT NULL,
	photo blob,
	specialist varchar2(200),
	-- 专业从事时间-从
	special_from timestamp,
	-- 专业从事时间-到
	special_to timestamp,
	nowcert_kind char,
	cert_level char,
	cert_gettime timestamp,
	cert_series char(2),
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


CREATE TABLE project_expert
(
	prj_id nvarchar2(64) NOT NULL,
	expert_id nvarchar2(64) NOT NULL,
	fetch_time number NOT NULL,
	expert_count number(3,0),
	expert_rate char,
	fetch_status char(2),
	fetch_method char,
	committee_name varchar2(100),
	review_begin timestamp,
	review_end timestamp,
	expert_accept char,
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
	PRIMARY KEY (prj_id, expert_id, fetch_time)
);


CREATE TABLE project_info
(
	id nvarchar2(64) NOT NULL,
	prj_name varchar2(200) NOT NULL,
	prj_type char(3),
	prj_duty varchar2(100),
	prj_unit nvarchar2(64),
	prj_money number(19,4),
	prj_level char,
	prj_address varchar2(500),
	prj_notes varchar2(1000),
	prj_status char(2),
	prj_begin timestamp,
	prj_end timestamp,
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

ALTER TABLE project_committee
	ADD FOREIGN KEY (committee_id)
	REFERENCES committee_info (id)
;


ALTER TABLE project_expert
	ADD FOREIGN KEY (expert_id)
	REFERENCES expert_confirm (id)
;


ALTER TABLE project_attach
	ADD FOREIGN KEY (prj_id)
	REFERENCES project_info (id)
;


ALTER TABLE project_expert
	ADD FOREIGN KEY (prj_id)
	REFERENCES project_info (id)
;


ALTER TABLE project_committee
	ADD FOREIGN KEY (prj_id)
	REFERENCES project_info (id)
;



/* Create Indexes */

CREATE INDEX attach_name_index ON project_attach ();
CREATE INDEX attach_type_index ON project_attach ();
CREATE INDEX confirm_special_index ON expert_confirm ();
CREATE INDEX confirm_nowcert_index ON expert_confirm ();
CREATE INDEX confirm_series_index ON expert_confirm ();



/* Comments */

COMMENT ON TABLE project_committee IS '项目评委表';
COMMENT ON COLUMN project_committee.prj_id IS '项目ID';
COMMENT ON COLUMN project_committee.committee_id IS '评委会ID';
COMMENT ON TABLE project_attach IS '专家附件';
COMMENT ON COLUMN project_attach.id IS '附件ID';
COMMENT ON COLUMN project_attach.prj_id IS '项目编号';
COMMENT ON COLUMN project_attach.attach_name IS '附件名称';
COMMENT ON COLUMN project_attach.attach_type IS '附件类型';
COMMENT ON COLUMN project_attach.attach_link IS '附件位置';
COMMENT ON COLUMN project_attach.attach_store IS '附件存储';
COMMENT ON COLUMN project_attach.create_by IS '创建者';
COMMENT ON COLUMN project_attach.create_date IS '创建时间';
COMMENT ON COLUMN project_attach.update_by IS '更新者';
COMMENT ON COLUMN project_attach.update_date IS '更新时间';
COMMENT ON COLUMN project_attach.remarks IS '备注信息';
COMMENT ON COLUMN project_attach.del_flag IS '删除标记';
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
COMMENT ON TABLE expert_confirm IS '专家确认表';
COMMENT ON COLUMN expert_confirm.id IS '专家确认ID';
COMMENT ON COLUMN expert_confirm.user_id IS '用户编号';
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
COMMENT ON TABLE project_expert IS '项目专家表';
COMMENT ON COLUMN project_expert.prj_id IS '项目ID';
COMMENT ON COLUMN project_expert.expert_id IS '专家确认ID';
COMMENT ON COLUMN project_expert.fetch_time IS '第几次抽取';
COMMENT ON COLUMN project_expert.expert_count IS '本次所需专家数';
COMMENT ON COLUMN project_expert.expert_rate IS '专家在本次小组中的身份';
COMMENT ON COLUMN project_expert.fetch_status IS '抽取有效标志';
COMMENT ON COLUMN project_expert.fetch_method IS '专家获取的方式：单位、个人、选取、随机抽取。';
COMMENT ON COLUMN project_expert.committee_name IS '本次抽取组成的评委会名称';
COMMENT ON COLUMN project_expert.review_begin IS '执行评审开始时间';
COMMENT ON COLUMN project_expert.review_end IS '执行评审结束时间';
COMMENT ON COLUMN project_expert.expert_accept IS '专家接受任务标志。';
COMMENT ON COLUMN project_expert.create_by IS '创建者';
COMMENT ON COLUMN project_expert.create_date IS '创建时间';
COMMENT ON COLUMN project_expert.update_by IS '更新者';
COMMENT ON COLUMN project_expert.update_date IS '更新时间';
COMMENT ON COLUMN project_expert.remarks IS '备注信息';
COMMENT ON COLUMN project_expert.del_flag IS '删除标记';
COMMENT ON TABLE project_info IS '项目信息表';
COMMENT ON COLUMN project_info.id IS '项目ID';
COMMENT ON COLUMN project_info.prj_name IS '项目名称';
COMMENT ON COLUMN project_info.prj_type IS '项目类别';
COMMENT ON COLUMN project_info.prj_duty IS '项目负责人';
COMMENT ON COLUMN project_info.prj_unit IS '项目主体单位';
COMMENT ON COLUMN project_info.prj_money IS '投资金额';
COMMENT ON COLUMN project_info.prj_level IS '项目级别';
COMMENT ON COLUMN project_info.prj_address IS '项目地址';
COMMENT ON COLUMN project_info.prj_notes IS '项目说明';
COMMENT ON COLUMN project_info.prj_status IS '项目状态';
COMMENT ON COLUMN project_info.prj_begin IS '项目开始时间';
COMMENT ON COLUMN project_info.prj_end IS '项目结束时间';
COMMENT ON COLUMN project_info.create_by IS '创建者';
COMMENT ON COLUMN project_info.create_date IS '创建时间';
COMMENT ON COLUMN project_info.update_by IS '更新者';
COMMENT ON COLUMN project_info.update_date IS '更新时间';
COMMENT ON COLUMN project_info.remarks IS '备注信息';
COMMENT ON COLUMN project_info.del_flag IS '删除标记';



