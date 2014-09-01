<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志处理模块管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/loginfo/expertdbLog/">日志处理模块列表</a></li>
		<shiro:hasPermission name="loginfo:expertdbLog:edit"><li><a href="${ctx}/loginfo/expertdbLog/form">日志处理模块添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="expertdbLog" action="${ctx}/loginfo/expertdbLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>日志时间</th><th>专家姓名</th><th>操作者</th><th>操作内容</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="expertdbLog">
			<tr>
				<td>${expertdbLog.createDate}</td>
				<td>${expertdbLog.objectName}</td>
				<td>${expertdbLog.objectUser.name}</td>
				<td>${expertdbLog.operation}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
