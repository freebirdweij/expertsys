<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目录入</title>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginName").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">项目信息</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectInfo" action="${ctx}/project/info" method="post" class="form-horizontal">
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">项目编号:</label>
			<div class="controls">
						<label class="lbl">${projectInfo.id}</label>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">项目名称:</label>
			<div class="controls">
						<label class="lbl">${projectInfo.prjName}</label>
          </div>	
		</div>
		<div class="control-group">
			<label class="control-label">项目类别:</label>
			<div class="controls">
				<form:select path="prjType" class="span2" disabled="true">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_prjtype_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">主体单位:</label>
			<div class="controls">
                <tags:treeselect id="unit" name="unit.id" value="${projectInfo.unit.id}" labelName="unit.name" labelValue="${projectInfo.unit.name}"
					title="公司" url="/sys/office/treeData?type=1" cssClass="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投资金额:</label>
			<div class="controls">
						<label class="lbl">${projectInfo.prjMoney}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目年度:</label>
			<div class="controls">
			<label class="lbl">${projectInfo.prjYear}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目说明:</label>
			<div class="controls">
						<label class="lbl">${projectInfo.prjNotes}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目状态:</label>
			<div class="controls">
				<form:select path="prjStatus" class="span2" disabled="true">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_prjstatus_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>