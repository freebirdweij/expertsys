<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监督管理</title>
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
			$("#searchForm").attr("action","${ctx}/project/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/supervise/reviewinglist">评审中项目</a></li>
		<li><a href="${ctx}/supervise/acceptinglist">交工验收中项目</a></li>
		<li class="active"><a href="${ctx}/supervise/saveinglist">竣工验收中项目</a></li>
	</ul>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectInfo">
			<tr>
				<td>${projectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectInfo.id}">${projectInfo.prjName}</a></td>
				<td>${projectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectInfo.prjMoney}</td>
				<td>${projectInfo.prjYear}</td>
				<td>
    				<a href="${ctx}/supervise/checksavefetch?prjid=${projectInfo.id}">审核抽取记录</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>