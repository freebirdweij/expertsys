﻿



/* Drop Tables */

DROP TABLE project_committee CASCADE CONSTRAINTS;
DROP TABLE project_attach CASCADE CONSTRAINTS;
DROP TABLE project_expert CASCADE CONSTRAINTS;
DROP TABLE project_info CASCADE CONSTRAINTS;
DROP TABLE expertdb_log CASCADE CONSTRAINTS;




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

CREATE TABLE expertdb_log
(
	id number NOT NULL,
	object_id nvarchar2(64),
	object_user nvarchar2(64),
	object_type char(2),
	operation nvarchar2(1000),
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


create sequence project_seq nocycle maxvalue 9999999999 start with 1;


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
COMMENT ON TABLE expertdb_log IS '专家库日志';
COMMENT ON COLUMN expertdb_log.id IS '日志ID';
COMMENT ON COLUMN expertdb_log.object_id IS '日志关联的对象ID（专家、项目...）';
COMMENT ON COLUMN expertdb_log.object_user IS '对象的操作者（对专家、项目等进行操作的系统用户）';
COMMENT ON COLUMN expertdb_log.object_type IS '被操作的对象类型（专家、项目等）';
COMMENT ON COLUMN expertdb_log.operation IS '操作内容';
COMMENT ON COLUMN expertdb_log.create_by IS '创建者';
COMMENT ON COLUMN expertdb_log.create_date IS '创建时间';
COMMENT ON COLUMN expertdb_log.update_by IS '更新者';
COMMENT ON COLUMN expertdb_log.update_date IS '更新时间';
COMMENT ON COLUMN expertdb_log.remarks IS '备注信息';
COMMENT ON COLUMN expertdb_log.del_flag IS '删除标记';


