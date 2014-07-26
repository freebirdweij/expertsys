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
	    
		function resSubmit(){
			$("#inputForm").attr("action","${ctx}/expfetch/receiveunitresult");
			$("#inputForm").submit();
	    	return false;
	    }
	    
		function resCancel(){
			$("#inputForm").attr("action","${ctx}/expfetch/cancelunitresult");
			$("#inputForm").submit();
	    	return false;
	    }
	    
		function btnCancel(){
			$("#inputForm").attr("action","${ctx}/expfetch/unitmethod");
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">单位列表</li>
	</ul>
	<form:form id="inputForm" modelAttribute="projectExpert" action="${ctx}/expfetch/directdrawunit" method="post" class="form-horizontal">
	<tags:message content="${message}"/>
			<input id="discIds" name="discIds" type="hidden"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>归属区域</th><th>单位名称</th><th class="sort loginName">单位类型</th><th class="sort name">上级机构</th><th>电话</th><th>联系地址</th><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="office">
			<tr>
				<td><a href="${ctx}/expfetch/unitexp?id=${office.id}&resIds=$('#resIds').val()">${office.area.name}</a></td>
				<td>${office.name}</td>
				<td>${fns:getDictLabel('${office.type}','sys_office_type','1')}</td>
				<td>${office.parent.name}</td>
				<td>${office.phone}</td>
				<td>${office.address}</td>
				<td>
			<input id="btnDiscard${office.id}" class="btn btn-primary" type="button" value="屏蔽" onclick="discard('${office.id}')"/>
			<input id="discCancel${office.id}" class="btn btn-primary" type="hidden" value="取消" onclick="discancel('${office.id}')"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
		<div class="form-actions">
			<input id="expertCount" name="expertCount" type="text" value="输入抽取数"/>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="进行随机抽取"/>
		</div>
      <div class="span10">
        <h4>以下为抽选结果：</h4>
      </div>
			<input id="resIds" name="resIds" type="hidden"/>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>归属单位</th><th class="sort loginName">类别</th><th class="sort name">专业</th><th>职务</th><th>职称</th><th>学历</th></tr></thead>
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
			<input id="resSubmit" class="btn btn-primary" type="button" value="确认采用本次抽选结果" onclick="resSubmit()"/>
			<input id="resCancel" class="btn" type="button" value="放弃本次抽选" onclick="resCancel()"/>
			<input id="btnCancel" class="btn" type="button" value="返回重新选择筛选条件" onclick="btnCancel()"/>
		</div>
	</form:form>

</body>
</html>