<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家注册</title>
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
		<li class="active">基本信息</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expertInfo" action="${ctx}/experts/stepone" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<tags:message content="${message}"/>
          <div class ="row-fluid"> 
          <div class ="span6">		
          <div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
                <form:input type="text" path="name"  value="M"/>	
            </div>
           </div>
		  <div class="control-group">
			<label class="control-label">性别:</label>
			<div class="controls">
                                 男<form:radiobutton path="sex"  value="M"/>   女 <form:radiobutton path="sex"  value="F"/>	
            </div>
		  </div>
		<div class="control-group">
			<label class="control-label">出生年月:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
				<form:input path="birthdate" htmlEscape="false" maxlength="50" class="required userName"/>
			</div>
		</div>
		</div>
		 <div class ="span6"> 
		<div class="control-group">
			<label class="control-label">照片:</label>
			<a href="#" class="thumbnail">
            <img src="http://placehold.it/160x120" alt="">
             </a>
		</div>
		</div> 
		</div>
		<div class="control-group">
			<label class="control-label">民    族:</label>
			<div class="controls">
				<form:input path="nation" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号:</label>
			<div class="controls">
          <form:input path="identifyCode" htmlEscape="false" maxlength="50" class="required"/>
          </div>	
		</div>
		<div class="control-group">
			<label class="control-label">毕业学校:</label>
			<div class="controls">
				<form:input path="collage" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业时间:</label>
			<div class="controls">
				<input id="graduateTime" name="graduateTime" type="password" value="" maxlength="50" minlength="3" class="${empty user.id?'required':''}"/>
				<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学    历:</label>
			<div class="controls">
				<input id="education" name="education" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所学专业:</label>
			<div class="controls">
				<form:input path="studySpecial" htmlEscape="false" maxlength="100" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所 获学 位:</label>
			<div class="controls">
				<form:input path="myDegree" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">家庭地址:</label>
			<div class="controls">
				<form:input path="homeAddr" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">健康状况:</label>
			<div class="controls">
				<form:input path="health" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">住宅电话:</label>
			<div class="controls">
				<form:input path="homePhone" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<c:if test="${not empty user.id}">
			<div class="control-group">
				<label class="control-label">邮政编码:</label>
			<div class="controls">
				<form:input path="homeMailcode" htmlEscape="false" maxlength="100"/>
			</div>
			</div>
		</c:if>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="下一步"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>