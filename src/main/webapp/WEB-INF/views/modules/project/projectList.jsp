<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			// 表格排序
			var orderBy = $("#orderBy").val().split(" ");
			$("#contentTable th.sort").each(function(){
				if ($(this).hasClass(orderBy[0])){
					orderBy[1] = orderBy[1]&&orderBy[1].toUpperCase()=="DESC"?"down":"up";
					$(this).html($(this).html()+" <i class=\"icon icon-arrow-"+orderBy[1]+"\"></i>");
				}
			});
			$("#contentTable th.sort").click(function(){
				var order = $(this).attr("class").split(" ");
				var sort = $("#orderBy").val().split(" ");
				for(var i=0; i<order.length; i++){
					if (order[i] == "sort"){order = order[i+1]; break;}
				}
				if (order == sort[0]){
					sort = (sort[1]&&sort[1].toUpperCase()=="DESC"?"ASC":"DESC");
					$("#orderBy").val(order+" DESC"!=order+" "+sort?"":order+" "+sort);
				}else{
					$("#orderBy").val(order+" ASC");
				}
				page();
			});
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
		<li class="active"><a href="${ctx}/project/list">项目列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="projectInfo" action="${ctx}/project/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<%-- <div>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>建设单位：</label>
                <tags:treeselect id="unit" name="unit.id" value="${projectInfo.unit.id}" labelName="unit.name" labelValue="${projectInfo.unit.name}"
					title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
		</div> --%>
		<div style="margin-top:8px;">
			<form:select path="prjStatus" class="span2" ><form:option value="" label="项目状态"/>
			<form:options items="${fns:getDictList('sys_prjstatus_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/></form:select>
			<label>项目编号：</label><form:input path="id" htmlEscape="false" maxlength="50" class="span2"/>
			<label>项目名称：</label><form:input path="prjName" htmlEscape="false" maxlength="50" class="span3"/>
			<label>项目年度：</label><form:input path="prjYear" maxlength="20" class="span2 input-small Wdate" onclick="WdatePicker({dateFmt:'yyyy',isShowClear:true});" />
			&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
		</div>
	</form:form>
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
				<shiro:hasPermission name="project:projectInfo:edit"><td>
		            <c:if test="${projectInfo.prjStatus eq '10'}">
    				<a href="${ctx}/project/form?id=${projectInfo.id}">修改</a>
		            <c:if test="${projectInfo.prjStatus eq '10'}">
		            </c:if>
					<a href="${ctx}/project/delete?id=${projectInfo.id}" onclick="return confirmx('确认要删除该项目信息吗？', this.href)">删除</a>
		            </c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>