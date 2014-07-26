<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结果专家列表</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/expmanage/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#resSubmit").click(function(){
				top.$.jBox.confirm("确返回吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#inputForm").attr("action","${ctx}/expfetch/backexpresult");
						$("#inputForm").submit();
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
			$("#searchForm").attr("action","${ctx}/expmanage/explist");
			$("#searchForm").submit();
	    	return false;
	    }
	    
	    var resIds = [];
		function selectExp(id){
			$("#btnSelect"+id).hide();
			$("#resultTable").append($("#row"+id).clone());
			resIds.push(id);
			$("#resIds").val(resIds);
	    	return false;
	    }
	    
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">专家列表</li>
	</ul>
	<form:form id="inputForm" modelAttribute="projectExpert" action="${ctx}/expfetch/drawexpert" method="post" class="form-horizontal">
	<tags:message content="${message}"/>
			<input id="resIds" name="resIds" type="hidden"/>
			<input id="unitIdsYes" name="unitIdsYes" type="hidden"/>
			<input id="kindIdsYes" name="kindIdsYes" type="hidden"/>
			<input id="specialIdsYes" name="specialIdsYes" type="hidden"/>
			<input id="techIdsYes" name="techIdsYes" type="hidden"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>归属单位</th><th>类别</th><th>专业</th><th>职务</th><th>职称</th><th>学历</th><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="expertConfirm">
			<tr id="row${expertConfirm.id}" >
				<td><a href="${ctx}/expmanage/expinfo?id=${expertConfirm.id}">${expertConfirm.expertInfo.name}</a></td>
				<td>${expertConfirm.expertInfo.unit.name}</td>
				<td>${expertConfirm.expertKind}</td>
				<td>${expertConfirm.expertSpecial}</td>
				<td>${expertConfirm.expertInfo.job}</td>
				<td>${expertConfirm.expertInfo.technical}</td>
				<td>${expertConfirm.expertInfo.education}</td>
				<shiro:hasPermission name="sys:user:edit"><td>
 			<input id="btnSelect${expertConfirm.id}" class="btn btn-primary" type="button" value="选择" onclick="selectExp('${expertConfirm.id}')"/>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
		<div class="form-actions">
			输入抽取数<input id="expertCount" name="expertCount" type="text" value=""/>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="进行随机抽取"/>
		</div>
      <div class="span10">
        <h4>以下为抽选结果：</h4>
      </div>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>归属单位</th><th>类别</th><th>专业</th><th>职务</th><th>职称</th><th>学历</th></tr></thead>
		<tbody>
		<c:forEach items="${rlist}" var="expertConfirm">
			<tr>
				<td><a href="${ctx}/expmanage/expinfo?id=${expertConfirm.id}">${expertConfirm.expertInfo.name}</a></td>
				<td>${expertConfirm.expertInfo.unit.name}</td>
				<td>${expertConfirm.expertKind}</td>
				<td>${expertConfirm.expertSpecial}</td>
				<td>${expertConfirm.expertInfo.job}</td>
				<td>${expertConfirm.expertInfo.technical}</td>
				<td>${expertConfirm.expertInfo.education}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<div class="form-actions">
			<input id="resSubmit" class="btn btn-primary" type="button" value="确认返回"/>
		</div>
	</form:form>

</body>
</html>