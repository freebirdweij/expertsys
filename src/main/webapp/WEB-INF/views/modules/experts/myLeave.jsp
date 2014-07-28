<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家请假</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
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
		<li class="active">专家请假</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expertConfirm" action="${ctx}/experts/job/myleave" method="post" class="form-horizontal">
		<form:hidden path="expertLevel"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">确定要请假吗，请假后，将不能被抽取！:</label>
			<div class="controls">
			<input id="btnLeave" class="btn btn-primary" type="submit" value="请假"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">您已请假，确定要取消请假吗？:</label>
			<div class="controls">
			<input id="btnCancel" class="btn btn-primary" type="submit" value="取消"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">您还不是专家，无需请假！:</label>
		</div>
	</form:form>
</body>
</html>