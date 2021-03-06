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
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#inputForm").attr("action","${ctx}/infoview/exportExpertFetch");
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
			$("#searchForm").attr("action","${ctx}/infoview/explist");
			$("#searchForm").submit();
	    	return false;
	    }
	    
		function rSubmit(){
			$("#inputForm").attr("action","${ctx}/infoview/exportExpertFetch");
			$("#inputForm").submit();
	    	return false;
	    }
	    
		function rCancel(){
			$("#inputForm").attr("action","${ctx}/infoview/cancelunitresult");
			$("#inputForm").submit();
	    	return false;
	    }
	    
		function bCancel(){
			$("#inputForm").attr("action","${ctx}/infoview/reviewinglist");
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
		<li class="active">专家抽取信息</li>
	</ul>
	<form:form id="inputForm" modelAttribute="projectExpert" action="${ctx}/infoview/directdrawunit" method="post" enctype="multipart/form-data" class="form-horizontal">
	<tags:message content="${message}"/>
		<input id="expid" name="expid" type="hidden" value="${expid}"/>
	<c:if test="${rewlist.size() gt '0'}">
      <div class="span10">
        <h4>以下为评审首次抽选的项目：</h4>
      </div>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${rewlist}" var="projectInfo">
			<tr>
				<td>${projectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectInfo.id}">${projectInfo.prjName}</a></td>
				<td>${projectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectInfo.prjMoney}</td>
				<td>${projectInfo.prjYear}</td>
				<td>
    				<a href="${ctx}/infoview/checkprojectfetchexp?prjid=${projectInfo.id}">查看抽取记录</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${rewrdlist.size() gt '0'}">
      <div class="span10">
        <h4>以下评审补抽的项目：</h4>
      </div>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${rewrdlist}" var="projectInfo">
			<tr>
				<td>${projectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectInfo.id}">${projectInfo.prjName}</a></td>
				<td>${projectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectInfo.prjMoney}</td>
				<td>${projectInfo.prjYear}</td>
				<td>
    				<a href="${ctx}/infoview/checkprojectfetchexp?prjid=${projectInfo.id}">查看抽取记录</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${rewnlist.size() gt '0'}">
      <div class="span10">
        <h4>以下为评审中抽取无效的项目：</h4>
      </div>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${rewnlist}" var="projectInfo">
			<tr>
				<td>${projectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectInfo.id}">${projectInfo.prjName}</a></td>
				<td>${projectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectInfo.prjMoney}</td>
				<td>${projectInfo.prjYear}</td>
				<td>
    				<a href="${ctx}/infoview/checkprojectfetchexp?prjid=${projectInfo.id}">查看抽取记录</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${acptlist.size() gt '0'}">
      <div class="span10">
        <h4>以下为交工验收首次抽选的项目：</h4>
      </div>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${acptlist}" var="projectInfo">
			<tr>
				<td>${projectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectInfo.id}">${projectInfo.prjName}</a></td>
				<td>${projectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectInfo.prjMoney}</td>
				<td>${projectInfo.prjYear}</td>
				<td>
    				<a href="${ctx}/infoview/checkprojectfetchexp?prjid=${projectInfo.id}">查看抽取记录</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${acptrdlist.size() gt '0'}">
      <div class="span10">
        <h4>以下交工验收中补抽的项目：</h4>
      </div>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${acptrdlist}" var="projectInfo">
			<tr>
				<td>${projectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectInfo.id}">${projectInfo.prjName}</a></td>
				<td>${projectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectInfo.prjMoney}</td>
				<td>${projectInfo.prjYear}</td>
				<td>
    				<a href="${ctx}/infoview/checkprojectfetchexp?prjid=${projectInfo.id}">查看抽取记录</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${acptnlist.size() gt '0'}">
      <div class="span10">
        <h4>以下为交工验收中抽取无效的项目：</h4>
      </div>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${acptnlist}" var="projectInfo">
			<tr>
				<td>${projectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectInfo.id}">${projectInfo.prjName}</a></td>
				<td>${projectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectInfo.prjMoney}</td>
				<td>${projectInfo.prjYear}</td>
				<td>
    				<a href="${ctx}/infoview/checkprojectfetchexp?prjid=${projectInfo.id}">查看抽取记录</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${acptdlist.size() gt '0'}">
      <div class="span10">
        <h4>以下为竣工验收首次抽选的项目：</h4>
      </div>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${acptdlist}" var="projectInfo">
			<tr>
				<td>${projectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectInfo.id}">${projectInfo.prjName}</a></td>
				<td>${projectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectInfo.prjMoney}</td>
				<td>${projectInfo.prjYear}</td>
				<td>
    				<a href="${ctx}/infoview/checkprojectfetch?prjid=${projectInfo.id}">查看抽取记录</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${acptdrdlist.size() gt '0'}">
      <div class="span10">
        <h4>以下竣工验收中补抽的项目：</h4>
      </div>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${acptdrdlist}" var="projectInfo">
			<tr>
				<td>${projectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectInfo.id}">${projectInfo.prjName}</a></td>
				<td>${projectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectInfo.prjMoney}</td>
				<td>${projectInfo.prjYear}</td>
				<td>
    				<a href="${ctx}/infoview/checkprojectfetch?prjid=${projectInfo.id}">查看抽取记录</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${acptdnlist.size() gt '0'}">
      <div class="span10">
        <h4>以下为竣工验收中抽取无效的项目：</h4>
      </div>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>项目编号</th><th>项目名称</th><th>建设单位</th><th>状态</th><th>投资金额</th><th>项目年度</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${acptdnlist}" var="projectInfo">
			<tr>
				<td>${projectInfo.id}</td>
				<td><a href="${ctx}/project/info?id=${projectInfo.id}">${projectInfo.prjName}</a></td>
				<td>${projectInfo.unit.name}</td>
				<td>${fns:getDictLabel(projectInfo.prjStatus,'sys_prjstatus_type','')}</td>
				<td>${projectInfo.prjMoney}</td>
				<td>${projectInfo.prjYear}</td>
				<td>
    				<a href="${ctx}/infoview/checkprojectfetch?prjid=${projectInfo.id}">查看抽取记录</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</c:if>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			&nbsp;<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
		</div>
	</form:form>

</body>
</html>