<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="com.thinkgem.jeesite.common.beanvalidator.BeanValidators"%>
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
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/expmanage/import" method="post" enctype="multipart/form-data"
			style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/expmanage/import/template">下载模板</a>
		</form>
	</div>
	<form:form id="inputForm" modelAttribute="expertConfirm" action="${ctx}/expmanage/impnote" method="post" class="form-horizontal">
	<div class="container-fluid">
		<div class="page-header"><h1>专家导入提示.</h1></div>
		<p>提示信息：</p><p>
		<c:if test="${expertConfirm.kindTwo eq 'init'}">
		批量录入专家信息，注意严格遵守模板格式，否则导入会失败！
		</c:if>
		<c:if test="${expertConfirm.kindTwo eq 'import'}">
		批量录入完成，录入成功数：${expertConfirm.specialTwo}，失败数：${expertConfirm.specialist}。
		</c:if>
		</p>
		<script>try{top.$.jBox.closeTip();}catch(e){}</script>
	</div>
		<div class="form-actions">
			&nbsp;<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
				<input id="btnCancel" class="btn" type="button"	value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>