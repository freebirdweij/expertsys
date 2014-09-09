<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="com.freebirdweij.cloudroom.common.beanvalidator.BeanValidators"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%response.setStatus(200);%>
<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
	//记录日志
	if (ex!=null){
		Logger logger = LoggerFactory.getLogger("500.jsp");
		logger.error(ex.getMessage(), ex);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>专家注册提示</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
	<form:form id="inputForm" modelAttribute="expertInfo" action="${ctx}/experts/register" method="post" class="form-horizontal">
	<div class="container-fluid">
		<div class="page-header"><h1>专家注册提示.</h1></div>
		<p>提示信息：</p><p>
		<c:if test="${expertInfo.regStep eq '3'}">
		您已完成专家注册信息录入，如需要修改注册信息或者提交审核，请进入信息维护功能模块进行。
		</c:if>
		<c:if test="${expertInfo.regStep eq '4'}">
		您已完成专家注册信息录入并提交了申请，目前正等待审核当中,如需要修改注册信息，请进入信息维护功能模块进行。
		</c:if>
		<c:if test="${expertInfo.regStep eq '5'}">
		您已被批准成为专家！如需要修改专家信息或者重新提交申请，请进入信息维护功能模块进行。
		</c:if>
		</p>
		<script>try{top.$.jBox.closeTip();}catch(e){}</script>
	</div>
	</form:form>
</body>
</html>