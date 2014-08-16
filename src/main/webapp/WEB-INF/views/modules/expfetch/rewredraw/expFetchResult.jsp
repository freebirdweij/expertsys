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
		function rSubmit() {
			$("#inputForm").attr("action",
					"${ctx}/expfetch/rewredraw/receiveexpertresult");
			$("#inputForm").submit();
			return false;
		}

		function rCancel() {
			$("#inputForm")
					.attr("action", "${ctx}/expfetch/rewredraw/cancelexpertresult");
			$("#inputForm").submit();
			return false;
		}

		function bCancel() {
			$("#inputForm").attr("action", "${ctx}/expfetch/rewredraw/backexpmethod");
			$("#inputForm").submit();
			return false;
		}

		var disc = [];
		function discard(id) {
			disc.push(id);
			$("#discIds").val(disc);
			$("#btnDiscard" + id).hide();
			$("#discCancel" + id).show();
			
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
	    
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">专家列表</li>
	</ul>
	<form:form id="inputForm" modelAttribute="projectExpert" action="${ctx}/expfetch/rewredraw/directdrawexpert" method="post" class="form-horizontal">
	<tags:message content="${message}"/>
			<form:hidden path="discIds"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>归属单位</th><th class="sort loginName">类别</th><th class="sort name">专业</th><th>职称</th><th>学历</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="expertConfirm">
			<tr>
				<td>${expertConfirm.expertInfo.name}</td>
				<td>${expertConfirm.expertInfo.unit.name}</td>
				<td>${fns:getDictLabel(expertConfirm.expertKind,'sys_specialkind_type','')}</td>
				<td>${fns:getDictLabel(expertConfirm.expertSpecial,'sys_special_type','')}</td>
				<td>${fns:getDictLabel(expertConfirm.expertInfo.technical,'sys_tech_type','')}</td>
				<td>${fns:getDictLabel(expertConfirm.expertInfo.education,'sys_education_type','')}</td>
				<td>
				<a id="btnDiscard${expertConfirm.id}" href="javascript:discard('${expertConfirm.id}')">屏蔽</a>
				<a id="discCancel${expertConfirm.id}" href="javascript:discancel('${expertConfirm.id}')"  style="display:none;">取消</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
			<form:hidden path="rejectUnit"/>
	<div class="pagination">${page}</div>
		<div class="form-actions">
			<form:select path="timeClash" class="span2" ><form:option value="" label="冲突屏蔽方式"/>
			<form:options items="${fns:getDictList('sys_time_clash')}" itemLabel="label" itemValue="value" htmlEscape="false"/></form:select>
			&nbsp;&nbsp;&nbsp;&nbsp;<label>输入抽取数</label><form:input path="expertCount" htmlEscape="false" max="${page.list.size()}" class="required digits"/>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="进行随机抽取"/>
			<input id="cancelUnit" class="btn btn-primary" type="button" onclick="cUnit()" value="屏蔽项目主体单位"/>
			<input id="backUnit" class="btn btn-primary" type="button" onclick="bUnit()" value="取消屏蔽" style="display:none;"/>
		</div>
      <div class="span10">
        <h4>以下为抽选结果：</h4>
      </div>
			<form:hidden path="resIds"/>
	<table id="resultTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>归属单位</th><th>类别</th><th>专业</th><th>职称</th><th>学历</th></tr></thead>
		<tbody>
		<c:forEach items="${rlist}" var="expertConfirm">
			<tr>
				<td><a href="${ctx}/expmanage/binfo?id=${expertConfirm.id}">${expertConfirm.expertInfo.name}</a></td>
				<td>${expertConfirm.expertInfo.unit.name}</td>
				<td>${fns:getDictLabel(expertConfirm.expertKind,'sys_specialkind_type','')}</td>
				<td>${fns:getDictLabel(expertConfirm.expertSpecial,'sys_special_type','')}</td>
				<td>${fns:getDictLabel(expertConfirm.expertInfo.technical,'sys_tech_type','')}</td>
				<td>${fns:getDictLabel(expertConfirm.expertInfo.education,'sys_education_type','')}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<div class="form-actions">
			<input id="resSubmit" class="btn btn-primary" type="button" value="确认采用本次抽选结果" onclick="rSubmit()"/>
			<input id="resCancel" class="btn btn-primary" type="button" value="放弃本次抽选" onclick="rCancel()"/>
			<input id="btnCancel" class="btn btn-primary" type="button" value="返回重新选择筛选条件" onclick="bCancel()"/>
		</div>
	</form:form>
</body>
</html>