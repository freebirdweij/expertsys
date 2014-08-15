package com.thinkgem.jeesite.common.utils;

public class Constants {
	
	//系统角色ID
	public static final String Sys_Role_Expert = "41c9510047ec4a61ba247c11604da888";//专家

	//项目状态
	public static final String Project_Status_Start = "10";//立项
	public static final String Project_Status_Apply = "11";//评审
	public static final String Project_Status_Work = "12";//实施
	public static final String Project_Status_Receive = "13";//验收
	public static final String Project_Status_Save = "14";//归档

	//每次专家抽取的状态
	public static final String Fetch_Review_Failure = "10";//项目评审抽取无效
	public static final String Fetch_Review_Sussess = "11";//项目评审首次抽取有效
	public static final String Fetch_ReviewRedraw_Failure = "20";//项目评审补抽无效
	public static final String Fetch_ReviewRedraw_Sussess = "21";//项目评审补抽有效
	public static final String Fetch_Accept_Failure = "30";//项目验收抽取无效
	public static final String Fetch_Accept_Sussess = "31";//项目验收首次抽取有效
	public static final String Fetch_AcceptRedraw_Failure = "40";//项目验收补抽无效
	public static final String Fetch_AcceptRedraw_Sussess = "41";//项目验收补抽有效
	
	//专家抽取的方式
	public static final String Fetch_Method_Unit = "0";//无效
	public static final String Fetch_Method_Expert = "1";//有效
	
	//专家性别
	public static final String Expert_Sex_Girl = "0";//女
	public static final String Expert_Sex_Boy = "1";//男
	
	//冲突屏蔽方式
	public static final String Time_Clash_Ignore = "0";//忽略冲突
	public static final String Time_Clash_OneDay = "1";//当日冲突
	public static final String Time_Clash_HalfDay = "2";//半日冲突
	
	//屏蔽主体单位
	public static final String Accept_Main_Unit = "0";//取消
	public static final String Reject_Main_Unit = "1";//屏蔽
	
	//屏蔽最近三次抽取
	public static final String Accept_Recent_Three = "0";//取消
	public static final String Reject_Recent_Three = "1";//屏蔽
	
	//专家请假标志
	public static final String Expert_Status_Leave = "1";//请假
	public static final String Expert_Status_Work = "0";//工作

	//个人注册专家时的状态
	public static final String Register_Status_First = "1";//完成第一步
	public static final String Register_Status_Second = "2";//完成第二步
	public static final String Register_Status_Third = "3";//完成第三步
	public static final String Register_Status_Apply = "4";//已提交申请
	public static final String Register_Status_Accept = "5";//批准成为专家

	//统计分类定义
	public static final String Statistics_Kind_Area = "1";//完成第一步
	public static final String Statistics_Kind_Unit = "2";//完成第二步
	public static final String Statistics_Kind_Type = "3";//完成第三步
	public static final String Statistics_Kind_Special = "4";//已提交申请

}
