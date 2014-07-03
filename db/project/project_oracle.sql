



/* Drop Tables */

DROP TABLE project_committee CASCADE CONSTRAINTS;
DROP TABLE project_attach CASCADE CONSTRAINTS;
DROP TABLE project_expert CASCADE CONSTRAINTS;
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


CREATE TABLE project_info
(
	id nvarchar2(64) NOT NULL,
	prj_name varchar2(200) NOT NULL,
	prj_type char(3),
	prj_duty varchar2(100),
	prj_money number(19,4),
	prj_level char,
	prj_notes varchar2(1000),
	prj_status char,
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


CREATE TABLE project_expert
(
	prj_id nvarchar2(64) NOT NULL,
	expert_id nvarchar2(64) NOT NULL,
	fetch_time number NOT NULL,
	fetch_status char(2),
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




/* Create Foreign Keys */

ALTER TABLE project_attach
	ADD FOREIGN KEY (prj_id)
	REFERENCES project_info (id)
;


ALTER TABLE project_committee
	ADD FOREIGN KEY (prj_id)
	REFERENCES project_info (id)
;


ALTER TABLE project_expert
	ADD FOREIGN KEY (prj_id)
	REFERENCES project_info (id)
;


ALTER TABLE project_expert
	ADD FOREIGN KEY (expert_id)
	REFERENCES expert_confirm (id)
;


ALTER TABLE project_committee
	ADD FOREIGN KEY (committee_id)
	REFERENCES committee_info (id)
;





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
COMMENT ON TABLE project_info IS '项目信息表';
COMMENT ON COLUMN project_info.id IS '项目ID';
COMMENT ON COLUMN project_info.prj_name IS '项目名称';
COMMENT ON COLUMN project_info.prj_type IS '项目类别';
COMMENT ON COLUMN project_info.prj_duty IS '项目负责人';
COMMENT ON COLUMN project_info.prj_money IS '投资金额';
COMMENT ON COLUMN project_info.prj_level IS '项目级别';
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
COMMENT ON TABLE project_expert IS '项目专家表';
COMMENT ON COLUMN project_expert.prj_id IS '项目ID';
COMMENT ON COLUMN project_expert.expert_id IS '专家确认ID';
COMMENT ON COLUMN project_expert.fetch_time IS '第几次抽取';
COMMENT ON COLUMN project_expert.fetch_status IS '抽取有效标志';
COMMENT ON COLUMN project_expert.review_begin IS '执行评审开始时间';
COMMENT ON COLUMN project_expert.review_end IS '执行评审结束时间';
COMMENT ON COLUMN project_expert.expert_accept IS '专家接受任务标志。';
COMMENT ON COLUMN project_expert.create_by IS '创建者';
COMMENT ON COLUMN project_expert.create_date IS '创建时间';
COMMENT ON COLUMN project_expert.update_by IS '更新者';
COMMENT ON COLUMN project_expert.update_date IS '更新时间';
COMMENT ON COLUMN project_expert.remarks IS '备注信息';
COMMENT ON COLUMN project_expert.del_flag IS '删除标记';



