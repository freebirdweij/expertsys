<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志处理模块管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate({
				rules: {
					name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")},
                "logBegin":{
                    required: true
                },
                "logEnd": {
                    required: true,
                    compareDate: "#logBegin"
                }
				},
				messages: {
					name: {remote: "角色名已存在"},
                "logBegin":{
                    required: "开始时间不能为空"
                },
                "logEnd":{
                    required: "结束时间不能为空",
                    compareDate: "结束日期必须大于等于开始日期!"
                }
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
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	    jQuery(function(){        
	        jQuery.validator.methods.compareDate = function(value, element, param) {
	            //var startDate = jQuery(param).val() + ":00";补全yyyy-MM-dd HH:mm:ss格式
	            //value = value + ":00";
	            
	            var startDate = jQuery(param).val();
	            
	            var date1 = new Date(Date.parse(startDate.replace("-", "/")));
	            var date2 = new Date(Date.parse(value.replace("-", "/")));
	            
	            
	            return date1 <= date2;
	        };
	        
	        jQuery("#searchForm").validate({
	            focusInvalid:false,
	            rules:{
	                "logBegin":{
	                    required: true
	                },
	                "logEnd": {
	                    required: true,
	                    compareDate: "#logBegin"
	                }
	            },
	            messages:{
	                "logBegin":{
	                    required: "开始时间不能为空"
	                },
	                "logEnd":{
	                    required: "结束时间不能为空",
	                    compareDate: "结束日期必须大于等于开始日期!"
	                }
	            }
	        });
	    });
	    document.onreadystatechange = function(){	
	    	 var rewb = "";
	    	var rew = $("#logBegin");
	    	rewb = rew.val();
	    	rewb = rewb.substr(0,10);
	    	$("#logBegin").val(rewb);
	    	 var rewe = ""; 
	    	var ree = $("#logEnd");
	    			rewe = ree.val();
	    	rewe = rewe.substr(0,10);
	    	$("#logEnd").val(rewe); 
	    	//alert("kkk");
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/loginfo/projectLog">项目日志</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="expertdbLog" action="${ctx}/loginfo/projectLog" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div style="margin-top:8px;">
		<label>日志时间 ：</label>				
		                     从<form:input path="logBegin" maxlength="20"
						class="span2 input-small Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				至<form:input path="logEnd" maxlength="20"
						class="span2 input-small Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
		</div>
		<div style="margin-top:8px;">
		<label>项目名称 ：</label><form:input path="objectName" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>操作者 ：</label><form:input path="objectUser.name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>日志时间</th><th>项目名称</th><th>操作者</th><th>操作内容</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="expertdbLog">
			<tr>
				<td><fmt:formatDate value="${expertdbLog.createDate}" type="both"/></td>
				<td>${expertdbLog.objectName}</td>
				<td>${expertdbLog.objectUser.name}</td>
				<td>${expertdbLog.operation}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
