<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家请假</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnLeave").click(function(){
						$("#expertLevel").val("1");
						$("#inputForm").submit();
			});
			$("#btnCancel").click(function(){
						$("#expertLevel").val("0");
						$("#inputForm").submit();
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
		<c:if test="${expertConfirm.expertLevel eq '0'}">
		<div class="control-group">
			<label class="control-label">确定要请假吗，请假后，将不能被抽取！:</label>
			<div class="controls">
			<input id="btnLeave" class="btn btn-primary" type="submit" value="请假"/>
			</div>
		</div>
		</c:if>
		<c:if test="${expertConfirm.expertLevel eq '1'}">
		<div class="control-group">
			<label class="control-label">您已请假，确定要取消请假吗？:</label>
			<div class="controls">
			<input id="btnCancel" class="btn btn-primary" type="submit" value="取消"/>
			</div>
		</div>
		</c:if>
		<c:if test="${expertConfirm.expertLevel eq '2'}">
		<div class="control-group">
			<label class="control-label">您还不是专家，无需请假！:</label>
		</div>
		</c:if>
	</form:form>
</body>
</html>