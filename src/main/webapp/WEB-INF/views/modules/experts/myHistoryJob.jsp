<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家个人</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
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
		<li><a href="${ctx}/experts/job/myHistory">我的历史项目</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="projectExpert" action="${ctx}/experts/job/myHistory" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div style="margin-top:8px;">
			<label>建设单位：</label>
            <form:input path="prjProjectInfo.unit.name" htmlEscape="false" maxlength="50" class="span3"/>			
			<label>项目编号：</label><form:input path="prjProjectInfo.id" htmlEscape="false" maxlength="50" class="span3"/>
		</div>
		<div style="margin-top:8px;">
		<label>项目名称 ：</label><form:input path="prjProjectInfo.prjName" htmlEscape="false" maxlength="50" class="span3"/>
		<label>项目年度：</label><form:input path="prjProjectInfo.prjYear" maxlength="20" class="span2 input-small Wdate" onclick="WdatePicker({dateFmt:'yyyy',isShowClear:true});" />
			&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>评审时间</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectExpert">
			<tr>
				<td>${projectExpert.prjProjectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectExpert.prjProjectInfo.id}">${projectExpert.prjProjectInfo.prjName}</a></td>
				<td>${projectExpert.prjProjectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectExpert.prjProjectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectExpert.prjProjectInfo.prjMoney}</td>
				<td>${projectExpert.prjProjectInfo.prjYear}</td>
				<td>${projectExpert.reviewBegin}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>