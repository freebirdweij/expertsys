<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html> 
<head>
	<title>专家注册</title>
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
		
		function sticsExpert() {
			$("#inputForm").attr("action",
					"${ctx}/supervise/statisticsexpert");
			$("#inputForm").submit();
			return false;
		}

		function sticsUnit() {
			$("#inputForm").attr("action",
					"${ctx}/supervise/statisticsunit");
			$("#inputForm").submit();
			return false;
		}

		function sticsKind() {
			$("#inputForm").attr("action",
					"${ctx}/supervise/statisticskind");
			$("#inputForm").submit();
			return false;
		}

		function sticsFetch() {
			$("#inputForm").attr("action",
					"${ctx}/supervise/statisticsfetch");
			$("#inputForm").submit();
			return false;
		}

		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">抽取统计</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fetchSupervise" action="${ctx}/supervise/statistics" enctype="multipart/form-data" method="post" class="form-horizontal">
		<tags:message content="${message}" />
      <div class="row">
        <div class="span10">
          <h4>统计一段时间内各专家被抽中的次数：</h4>
        </div>
      </div>
		<div class="control-group">
			<label class="control-label">选择时间:</label>
			<div class="controls">
			  从<form:input path="expertBegin" maxlength="20"
				class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			  至<form:input path="expertEnd" maxlength="20"
				class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a id="btnExpert" href="javascript:sticsExpert()">开始</a>
			</div>
		</div>
      <div class="row">
      <div class="span10">
        <h4>统计一段时间内各单位被抽中的次数：</h4>
      </div>
      </div>
		<div class="control-group">
			<label class="control-label">选择时间:</label>
			<div class="controls">
			  从<form:input path="unitBegin" maxlength="20"
				class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			  至<form:input path="unitEnd" maxlength="20"
				class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a id="btnUnit" href="javascript:sticsUnit()">开始</a>
			</div>
		</div>
      <div class="row">
      <div class="span10">
        <h4>统计各地区、单位、类别、专业的专家数：</h4>
      </div>
      </div>
		<div class="control-group">
			<label class="control-label">选择统计分类:</label>
			<div class="controls">
				地区<form:radiobutton path="sticsKind" value="1" />&nbsp;&nbsp;&nbsp;&nbsp;单位<form:radiobutton path="sticsKind" value="2" />
				类别<form:radiobutton path="sticsKind" value="3" />&nbsp;&nbsp;&nbsp;&nbsp;专业<form:radiobutton path="sticsKind" value="4" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a id="btnKind" href="javascript:sticsKind()">开始</a>
			</div>
		</div>
      <div class="row">
      <div class="span10">
        <h4>统计一段时间内按地区、类别、专业分专家被抽中的次数：</h4>
      </div>
      </div>
		<div class="control-group">
			<label class="control-label">选择时间和分类:</label>
			<div class="controls">
			  从<form:input path="fetchBegin" maxlength="20"
				class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			  至<form:input path="fetchEnd" maxlength="20"
				class="span2 input-small Wdate" value="0000-00-00" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
				地区<form:radiobutton path="fetchKind" value="1" />&nbsp;&nbsp;&nbsp;&nbsp;
				类别<form:radiobutton path="fetchKind" value="3" />&nbsp;&nbsp;&nbsp;&nbsp;专业<form:radiobutton path="fetchKind" value="4" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a id="btnFetch" href="javascript:sticsFetch()">开始</a>
			</div>
		</div>
	</form:form>
</body>
</html>