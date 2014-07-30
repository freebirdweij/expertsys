package com.thinkgem.jeesite.common.utils;

public class Constants {
	
	//项目状态
	public static final String Project_Status_Start = "10";//立项
	public static final String Project_Status_Apply = "11";//评审
	public static final String Project_Status_Work = "12";//实施
	public static final String Project_Status_Receive = "13";//验收
	public static final String Project_Status_Save = "14";//归档

	//每次专家抽取的状态
	public static final String Fetch_Status_Failure = "10";//无效
	public static final String Fetch_Status_Sussess = "11";//有效
	
	//冲突屏蔽方式
	public static final String Time_Clash_Ignore = "0";//无效
	public static final String Time_Clash_OneDay = "1";//有效
	public static final String Time_Clash_HalfDay = "2";//有效
	
	//专家请假标志
	public static final String Expert_Status_Leave = "1";//无效
	public static final String Expert_Status_Work = "0";//有效

	//个人注册专家时的状态
	public static final String Register_Status_First = "1";//完成第一步
	public static final String Register_Status_Second = "2";//完成第二步
	public static final String Register_Status_Third = "3";//完成第三步
	public static final String Register_Status_Apply = "4";//已提交申请
	public static final String Register_Status_Accept = "5";//批准成为专家

}
