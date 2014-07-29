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
		<li><a href="${ctx}/expmanage/uinfo">专家个人信息</a></li>
		<li><a href="${ctx}/expmanage/binfo?id=${id}">专家基本信息</a></li>
		<li><a href="${ctx}/expmanage/winfo?id=${id}">专家职业信息</a></li>
		<li class="active"><a href="${ctx}/expmanage/ainfo?id=${id}">审核通过信息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expertConfirm" action="${ctx}/expmanage/verify" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">专家类别:</label>
			<div class="controls">
				<label class="lbl">${fns:getDictLabel(expertConfirm.expertKind, 'sys_specialkind_type', '无')}</label>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">专家专业:</label>
			<div class="controls">
				<label class="lbl">${fns:getDictLabel(expertConfirm.expertSpecial, 'sys_special_type', '无')}</label>
          </div>	
		</div>
		<div class="control-group">
			<label class="control-label">主要业绩:</label>
			<div class="controls">
						<label class="lbl">${expertConfirm.expertInfo.achievement}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责或参与评审的重大项目及学术论著:</label>
			<div class="controls">
						<label class="lbl">${expertConfirm.expertInfo.hardProjectsArticals}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本人的专业特长（请从自己最熟悉的专业开始，依1、2、3、…排序说明）:</label>
			<div class="controls">
						<label class="lbl">${expertConfirm.expertInfo.mySpecials}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业部门〈或管理单位)初审意见:</label>
			<div class="controls">
						<label class="lbl">${expertConfirm.deptormanageAdvice}</label>
			</div>
		</div>
		<div class="form-actions">
			<a href="../../static/ckfinder/ckfinder.html?type=expert&start=expert:/${expertConfirm.expertInfo.name}(ID${expertConfirm.expertInfo.userId})/">专家资料</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</form:form>
</body>
</html>