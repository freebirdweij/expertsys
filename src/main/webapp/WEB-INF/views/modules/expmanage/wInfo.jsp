<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家信息</title>
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
		<li><a href="${ctx}/expmanage/uinfo">专家个人信息</a></li>
		<li><a href="${ctx}/expmanage/binfo?id=${id}">专家基本信息</a></li>
		<li class="active"><a href="${ctx}/expmanage/winfo?id=${id}">专家职业信息</a></li>
		<li><a href="${ctx}/expmanage/ainfo?id=${id}">审核通过信息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expertInfo" action="${ctx}/experts/workform" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">工作单位:</label>
			<div class="controls">
						<label class="lbl">${expertInfo.unit.name}</label>
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
						<label class="lbl">${expertInfo.job}</label>
          </div>	
		</div>
		<div class="control-group">
			<label class="control-label">从事专业:</label>
			<div class="controls">
						<label class="lbl">${expertInfo.specialist}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现从事专业时间:</label>
			<div class="controls">
				从
						<label class="lbl">${expertInfo.specialFrom}</label>
				至
						<label class="lbl">${expertInfo.specialTo}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职    称:</label>
			<div class="controls">
				<form:select path="technical" class="span2 required" disabled="true">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_tech_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职称评定时间:</label>
			<div class="controls">
						<label class="lbl">${expertInfo.techGettime}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参加工作时间:</label>
			<div class="controls">
						<label class="lbl">${expertInfo.startworkTime}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执业资格:</label>
			<div class="controls">
						<label class="lbl">${expertInfo.workCert}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执业资格取得时间:</label>
			<div class="controls">
						<label class="lbl">${expertInfo.certGettime}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌:</label>
			<div class="controls">
				<form:select path="politics" class="span2 required" disabled="true">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_politics_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位地址:</label>
			<div class="controls">
						<label class="lbl">${expertInfo.companyAddr}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位电话:</label>
			<div class="controls">
						<label class="lbl">${expertInfo.companyPhone}</label>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">单位邮编:</label>
				<div class="controls">
						<label class="lbl">${expertInfo.companyMailcode}</label>
				</div>
			</div>
		<div class="control-group">
			<label class="control-label">工作经历:</label>
			<div class="controls">
						<label class="lbl">${expertInfo.workThrough}</label>
			</div>
		</div>
	</form:form>
</body>
</html>