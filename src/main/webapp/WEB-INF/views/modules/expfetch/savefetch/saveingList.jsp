<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家抽取</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					var vcheck = document.getElementsByName("checkboxid");
					var dres = "";
					for (var i=0;i<vcheck.length;i++)
					{
					  if(vcheck[i].checked == true){
						  dres = dres+","+vcheck[i].id;
					  }
					}
					  $("#prjid").val(dres);
					if(dres==""){
						top.$.jBox.confirm("您还未选择项目！","系统提示",function(v,h,f){
							if(v=="ok"){
								return false;
							}
						},{buttonsFocus:1});						
					}else{
					loading('正在提交，请稍等...');
					form.submit();
					}
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
			$("#searchForm").attr("action","${ctx}/expfetch/savefetch/saveinglist");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/expfetch/reviewinglist">待评审项目</a></li>
		<li><a href="${ctx}/expfetch/acptfetch/acceptinglist">待交工验收项目</a></li>
		<li class="active"><a href="${ctx}/expfetch/savefetch/saveinglist">待竣工验收项目</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="projectExpert" action="${ctx}/expfetch/savefetch/unitmethod" method="post" class="form-horizontal">
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>抽取选择</th><th>是否结束</th></tr></thead>
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
				   <input type="checkbox" id="${projectInfo.id}" name="checkboxid"/>
				</td>
				<td>
    				<a href="${ctx}/expfetch/savefetch/saveproject?prjid=${projectInfo.id}">结束归档</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="进入抽取"/>
		</div>
	</form:form>
</body>
</html>