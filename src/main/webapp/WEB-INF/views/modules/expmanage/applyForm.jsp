<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家修改</title>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginName").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/expmanage/userform?id=${id}">专家个人信息</a></li>
		<li><a href="${ctx}/expmanage/baseform?id=${id}">专家基本信息</a></li>
		<li><a href="${ctx}/expmanage/workform?id=${id}">专家职业信息</a></li>
		<li class="active"><a href="${ctx}/expmanage/applyform?id=${id}">审核通过信息</a></li>
		<li><a href="${ctx}/expmanage/modifyPwd?id=${id}">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expertInfo" action="${ctx}/expmanage/saveapply" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">申报类别一:</label>
			<div class="controls">
				<form:select path="specialKind1" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_specialkind_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">申报专业:</label>
			<div class="controls">
				<form:select path="kind1Special1" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_special_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:select path="kind1Special2" class="span2">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_special_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
          </div>	
		</div>
		<div class="control-group">
			<label class="control-label">申报类别二:</label>
			<div class="controls">
				<form:select path="specialKind2" class="span2">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_specialkind_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">申报专业:</label>
			<div class="controls">
				<form:select path="kind2Special1" class="span2">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_special_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:select path="kind2Special2" class="span2">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_special_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
          </div>	
		</div>
		<div class="control-group">
			<label class="control-label">职称所属系列:</label>
			<div class="controls">
				<form:select path="certSeries" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_series_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要业绩:</label>
			<div class="controls">
					<form:textarea path="achievement" rows="6" cols="50" htmlEscape="false" maxlength="100" class="span4 required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责或参与评审的重大项目及学术论著:</label>
			<div class="controls">
					<form:textarea path="hardProjectsArticals" rows="6" cols="50" htmlEscape="false" maxlength="100" class="span4 required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本人的专业特长（请从自己最熟悉的专业开始，依1、2、3、…排序说明）:</label>
			<div class="controls">
					<form:textarea path="mySpecials" rows="6" cols="50" htmlEscape="false" maxlength="100" class="span4 required"/>
			</div>
		</div>
		<div class="form-actions">
			<a href="../../static/ckfinder/ckfinder.html?type=expert">上传资料</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>