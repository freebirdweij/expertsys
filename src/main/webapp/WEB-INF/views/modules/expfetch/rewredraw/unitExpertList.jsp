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
						$("#searchForm").attr("action","${ctx}/expmanage/export");
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
			$("#searchForm").attr("action","${ctx}/expmanage/explist");
			$("#searchForm").submit();
	    	return false;
	    }
	    
	    var resIds = [];
		function selectExp(id){
			$("#resultTable").append($("#row"+id).clone());
			resIds.push(id);
			$("#resIds").val(resIds);
			$("#btnSelect"+id).disabled = true;
	    	return false;
	    }
	    
		function resBack(){
			$("#inputForm").attr("action","${ctx}/expfetch/rewredraw/backunitresult");
			$("#inputForm").submit();
	    	return false;
	    }
	    
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/expmanage/explist">专家列表</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="projectExpert" action="${ctx}/expfetch/rewredraw/drawunitexpert" method="post" class="form-horizontal">
	<tags:message content="${message}"/>
			<input id="resIds" name="resIds" type="hidden"/>
			<input id="unitIdsYes" name="unitIdsYes" type="hidden"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>归属单位</th><th class="sort loginName">类别</th><th class="sort name">专业</th><th>职务</th><th>职称</th><th>学历</th><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="expertConfirm">
			<tr id="row${expertConfirm.id}" >
				<td><a href="${ctx}/expmanage/expinfo?id=${expertConfirm.id}">${expertConfirm.expertInfo.name}</a></td>
				<td>${expertConfirm.expertInfo.unit.name}</td>
				<td>${fns:getDictLabel(expertConfirm.expertKind,'sys_specialkind_type','')}</td>
				<td>${fns:getDictLabel(expertConfirm.expertSpecial,'sys_special_type','')}</td>
				<td>${expertConfirm.expertInfo.job}</td>
				<td>${fns:getDictLabel(expertConfirm.expertInfo.technical,'sys_tech_type','')}</td>
				<td>${fns:getDictLabel(expertConfirm.expertInfo.education,'sys_education_type','')}</td>
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
		<thead><tr><th>姓名</th><th>归属单位</th><th class="sort loginName">类别</th><th class="sort name">专业</th><th>职务</th><th>职称</th><th>学历</th></tr></thead>
		<tbody>
		<c:forEach items="${rlist}" var="expertConfirm">
			<tr>
				<td><a href="${ctx}/expmanage/expinfo?id=${expertConfirm.id}">${expertConfirm.expertInfo.name}</a></td>
				<td>${expertConfirm.expertInfo.unit.name}</td>
				<td>${fns:getDictLabel(expertConfirm.expertKind,'sys_specialkind_type','')}</td>
				<td>${fns:getDictLabel(expertConfirm.expertSpecial,'sys_special_type','')}</td>
				<td>${expertConfirm.expertInfo.job}</td>
				<td>${fns:getDictLabel(expertConfirm.expertInfo.technical,'sys_tech_type','')}</td>
				<td>${fns:getDictLabel(expertConfirm.expertInfo.education,'sys_education_type','')}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<div class="form-actions">
			<input id="resSubmit" class="btn btn-primary" type="button" value="确认返回" onclick="resBack()"/>
		</div>
	</form:form>

</body>
</html>