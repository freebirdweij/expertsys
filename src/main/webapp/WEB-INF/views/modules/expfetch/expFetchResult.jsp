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
		
		function page(n, s) {
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action", "${ctx}/expmanage/explist");
			$("#searchForm").submit();
			return false;
		}
		function resSubmit() {
			$("#inputForm").attr("action",
					"${ctx}/expfetch/receiveexpertresult");
			$("#inputForm").submit();
			return false;
		}

		function resCancel() {
			$("#inputForm")
					.attr("action", "${ctx}/expfetch/cancelexpertresult");
			$("#inputForm").submit();
			return false;
		}

		function btnCancel() {
			$("#inputForm").attr("action", "${ctx}/expfetch/expertmethod");
			$("#inputForm").submit();
			return false;
		}

		var disc = [];
		function discard(id) {
			disc.push(id);
			$("#discIds").val(disc);
			$("#btnDiscard" + id).hide();
			$("#discCancel" + id).show();
			return false;
		}

		function discancel(id) {
			for ( var i = 0; i < disc.length; i++) {
				if (disc[i] = id) {
					disc.remove(i);
				}
			}
			$("#discIds").val(disc);
			$("#discCancel" + id).hide();
			$("#btnDiscard" + id).show();
			return false;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">专家列表</li>
	</ul>
	<form:form id="inputForm" modelAttribute="projectExpert" action="${ctx}/expfetch/directdrawexpert" method="post" class="form-horizontal">
	<tags:message content="${message}"/>
			<input id="discIds" name="discIds" type="hidden"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>归属单位</th><th class="sort loginName">类别</th><th class="sort name">专业</th><th>职务</th><th>职称</th><th>学历</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="expertConfirm">
			<tr>
				<td>${expertConfirm.expertInfo.name}</td>
				<td><a href="${ctx}/expfetch/conditionexp?unitid=${expertConfirm.expertCompany.id}&resIds=$('#resIds').val()">${expertConfirm.expertInfo.unit.name}</a></td>
				<td><a href="${ctx}/expfetch/conditionexp?kind=${expertConfirm.expertKind}&resIds=$('#resIds').val()">${expertConfirm.expertKind}</a></td>
				<td><a href="${ctx}/expfetch/conditionexp?special=${expertConfirm.expertSpecial}&resIds=$('#resIds').val()">${expertConfirm.expertSpecial}</a></td>
				<td>${expertConfirm.expertInfo.job}</td>
				<td><a href="${ctx}/expfetch/conditionexp?technical=${expertConfirm.expertTechnical}&resIds=$('#resIds').val()">${expertConfirm.expertTechnical}</a></td>
				<td>${expertConfirm.expertInfo.education}</td>
				<td>
			<input id="btnDiscard${expertConfirm.id}" class="btn btn-primary" type="button" value="屏蔽" onclick="discard('${expertConfirm.id}')"/>
			<input id="discCancel${expertConfirm.id}" class="btn btn-primary" type="button" value="取消" onclick="discancel('${expertConfirm.id}')" style="display:none;"/>
				</td>
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