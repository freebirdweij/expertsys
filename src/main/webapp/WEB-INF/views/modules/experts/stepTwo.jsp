<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家注册</title>
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
		<li class="active"><a href="${ctx}/experts/register">职业信息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expertInfo" action="${ctx}/experts/saveTwo" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">工作单位:</label>
			<div class="controls">
                <tags:treeselect id="unit" name="unit.id" value="${expertInfo.unit.id}" labelName="unit.name" labelValue="${expertInfo.unit.name}"
					title="单位" url="/sys/office/treeData?type=1" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否所属单位组长人员:</label>
			<div class="controls">
                                         是<form:radiobutton path="ifTeamleader"  value="1"/>   否 <form:radiobutton path="ifTeamleader"  value="0"/>	
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">职务:</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" maxlength="20"
					class="span2 required" />
          </div>	
		</div>
		<div class="control-group">
			<label class="control-label">从事专业:</label>
			<div class="controls">
				<form:input path="specialist" htmlEscape="false" maxlength="20"
					class="span3 required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现从事专业时间:</label>
			<div class="controls">
				从<form:input path="specialFrom" maxlength="20"
						class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
				至<form:input path="specialTo" maxlength="20"
						class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职    称:</label>
			<div class="controls">
				<form:select path="technical" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_tech_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职称评定时间:</label>
			<div class="controls">
				<form:input path="techGettime" maxlength="20"
						class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加工作时间:</label>
			<div class="controls">
				<form:input path="startworkTime" maxlength="20"
						class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执业资格:</label>
			<div class="controls">
				<form:input path="workCert" htmlEscape="false" maxlength="20"
					class="span3 required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执业资格取得时间:</label>
			<div class="controls">
				<form:input path="certGettime" maxlength="20"
						class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌:</label>
			<div class="controls">
				<form:select path="politics" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_politics_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位地址:</label>
			<div class="controls">
				<form:input path="companyAddr" htmlEscape="false" maxlength="200" class="span5 required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位电话:</label>
			<div class="controls">
				<form:input path="companyPhone" htmlEscape="false" maxlength="30" class="span2 required" />
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">单位邮编:</label>
				<div class="controls">
					<form:input path="companyMailcode" htmlEscape="false" maxlength="20" class="span2 required"/>
				</div>
			</div>
		<div class="control-group">
			<label class="control-label">工作经历:</label>
			<div class="controls">
					<form:textarea path="workThrough" rows="6" cols="50" htmlEscape="false" maxlength="100" class="span4 required"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="下一步"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>