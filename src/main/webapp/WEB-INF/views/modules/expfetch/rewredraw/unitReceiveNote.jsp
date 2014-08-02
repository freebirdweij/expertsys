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
		
		Array.prototype.remove = function(dx) {
			if (isNaN(dx) || dx > this.length) {
				return false;
			}

			for ( var i = 0, n = 0; i < this.length; i++) {
				if (this[i] != this[dx]) {
					this[n++] = this[i];
				}
			}
			this.length -= 1;
		};
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/expmanage/explist");
			$("#searchForm").submit();
	    	return false;
	    }
	    
		function rSubmit(){
			$("#inputForm").attr("action","${ctx}/expfetch/rewredraw/export");
			$("#inputForm").submit();
	    	return false;
	    }
	    
		function rCancel(){
			$("#inputForm").attr("action","${ctx}/expfetch/rewredraw/cancelunitresult");
			$("#inputForm").submit();
	    	return false;
	    }
	    
		function bCancel(){
			$("#inputForm").attr("action","${ctx}/expfetch/rewredraw/reviewinglist");
			$("#inputForm").submit();
	    	return false;
	    }
	    
	    var disc = [];
		function discard(id){
			disc.push(id);
			$("#discIds").val(disc);
			$("#btnDiscard"+id).hide();
			$("#discCancel"+id).show();
	    	return true;
	    }
	    
		function discancel(id){
			for(var i=0; i<disc.length; i++) {
				if(disc[i]=id){
				disc.remove(i);						
				}
		   }
			$("#discIds").val(disc);
			$("#discCancel"+id).hide();
			$("#btnDiscard"+id).show();
	    	return true;
	    }
	    
		function cUnit(){
			$("#rejectUnit").val('1');
			$("#cancelUnit").hide();
			$("#backUnit").show();
	    	return true;
	    }
	    
		function bUnit(){
			$("#rejectUnit").val('0');
			$("#backUnit").hide();
			$("#cancelUnit").show();
	    	return true;
	    }
	    
		function cThree(){
			$("#rejectRecent").val('1');
			$("#cancelThree").hide();
			$("#backThree").show();
	    	return true;
	    }
	    
		function bThree(){
			$("#rejectRecent").val('0');
			$("#backThree").hide();
			$("#cancelThree").show();
	    	return true;
	    }
	    
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">项目抽取信息</li>
	</ul>
	<form:form id="inputForm" modelAttribute="projectExpert" action="${ctx}/expfetch/rewredraw/directdrawunit" method="post" enctype="multipart/form-data" class="form-horizontal">
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>名称</th><th>主体单位</th><th>状态</th><th>金额</th><th>时间</th></tr></thead>
		<tbody>
			<tr>
				<td>${projectExpert.prjProjectInfo.id}</td>
				<td><a href="${ctx}/project/form?id=${projectExpert.prjProjectInfo.id}">${projectExpert.prjProjectInfo.prjName}</a></td>
				<td>${projectExpert.prjProjectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectExpert.prjProjectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectExpert.prjProjectInfo.prjMoney}</td>
				<td>${projectExpert.prjProjectInfo.prjBegin}</td>
			</tr>
		</tbody>
	</table>
      <div class="span10">
        <h4>以下为抽选结果：</h4>
      </div>
			<form:hidden path="resIds"/>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>归属单位</th><th>类别</th><th>专业</th><th>职务</th><th>职称</th><th>学历</th></tr></thead>
		<tbody>
		<c:forEach items="${rlist}" var="expertConfirm">
			<tr>
				<td><a href="${ctx}/expmanage/binfo?id=${expertConfirm.id}">${expertConfirm.expertInfo.name}</a></td>
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
			<input id="resSubmit" class="btn btn-primary" type="button" value="导出专家列表" onclick="rSubmit()"/>
			<input id="btnCancel" class="btn btn-primary" type="button" value="退出" onclick="bCancel()"/>
		</div>
	</form:form>

</body>
</html>