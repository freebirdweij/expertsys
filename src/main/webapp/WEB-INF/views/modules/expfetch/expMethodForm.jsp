<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家抽取</title>
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
		<li class="active">专家审核</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expertConfirm" action="${ctx}/expmanage/confirm" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<tags:message content="${message}"/>
				<div class="control-group">
					<label class="control-label">输入专家编号:</label>
					<div class="controls">
						<form:input path="expertCode" htmlEscape="false" maxlength="20"
							class="span10 required userName"/>
					</div>
				</div>
		<div class="control-group">
			<label class="control-label">批准类别一:</label>
			<div class="controls">
				<form:select path="kindOne" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_specialkind_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">批准专业:</label>
			<div class="controls">
				<form:select path="specialOne" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_special_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
          </div>	
			<label class="control-label">所属系列:</label>
			<div class="controls">
				<form:select path="seriesOne" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_series_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准类别二:</label>
			<div class="controls">
				<form:select path="kindTwo" class="span2">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_specialkind_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">批准专业:</label>
			<div class="controls">
				<form:select path="specialTwo" class="span2">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_special_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
          </div>	
			<label class="control-label">所属系列:</label>
			<div class="controls">
				<form:select path="seriesTwo" class="span2">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_series_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业部门〈或管理单位)初审意见:</label>
			<div class="controls">
					<form:textarea path="deptormanageAdvice" rows="6" cols="50" htmlEscape="false" maxlength="100" class="span4 required"/>
			</div>
		</div>
		<div class="form-actions">
			<a href="../../static/ckfinder/ckfinder.html?type=expert&start=expert:/${expertInfo.name}(ID${id})/">上传审核资料</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>