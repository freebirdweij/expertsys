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
                "reviewBegin":{
                    required: true
                },
                "reviewEnd": {
                    required: true,
                    compareDate: "#reviewBegin"
                }
				},
				messages: {
					name: {remote: "角色名已存在"},
                "reviewBegin":{
                    required: "开始时间不能为空"
                },
                "reviewEnd":{
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
	                "reviewBegin":{
	                    required: true
	                },
	                "reviewEnd": {
	                    required: true,
	                    compareDate: "#reviewBegin"
	                }
	            },
	            messages:{
	                "reviewBegin":{
	                    required: "开始时间不能为空"
	                },
	                "reviewEnd":{
	                    required: "结束时间不能为空",
	                    compareDate: "结束日期必须大于等于开始日期!"
	                }
	            }
	        });
	    });
	    document.onreadystatechange = function(){	
	    	 var rewb = "";
	    	var rew = $("#reviewBegin");
	    	rewb = rew.val();
	    	rewb = rewb.substr(0,10);
	    	$("#reviewBegin").val(rewb);
	    	 var rewe = ""; 
	    	var ree = $("#reviewEnd");
	    			rewe = ree.val();
	    	rewe = rewe.substr(0,10);
	    	$("#reviewEnd").val(rewe); 
	    	//alert("kkk");
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/loginfo/fetchLog">抽取日志</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="projectExpert" action="${ctx}/loginfo/fetchLog" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div style="margin-top:8px;">
		<label>日志时间 ：</label>				
		                     从<form:input path="reviewBegin" maxlength="20"
						class="span2 input-small Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				至<form:input path="reviewEnd" maxlength="20"
						class="span2 input-small Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
		</div>
		<div style="margin-top:8px;">
		<label>项目名称 ：</label><form:input path="prjProjectInfo.prjName" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>专家姓名 ：</label><form:input path="expertExpertConfirm.expertInfo.name" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>操作者 ：</label><form:input path="createBy.name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>日志时间</th><th>抽取流水</th><th>项目名称</th><th>专家姓名</th><th>抽取状态</th><th>操作者</th><th>操作内容</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectExpert">
			<tr>
				<td><fmt:formatDate value="${projectExpert.createDate}" type="both"/></td>
				<td>${projectExpert.fetchTime}</td>
				<td>${projectExpert.prjProjectInfo.prjName}</td>
				<td>${projectExpert.expertExpertConfirm.expertInfo.name}</td>
				<td>${fns:getDictLabel(projectExpert.fetchStatus,'fetch_status_type','')}</td>
				<td>${projectExpert.createBy.name}</td>
				<td>${projectExpert.remarks}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
