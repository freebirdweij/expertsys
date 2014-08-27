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
					var vcheck = document.getElementsByName("checkboxid");
					var res = document.getElementsByName("committeeName");
					var dres = "";
					for (var i=0;i<vcheck.length;i++)
					{
					  if(vcheck[i].checked == true){
						  dres = dres+"|"+vcheck[i].id+":"+res[i].value;
					  }
					}
					  $("#discIds").val(dres);
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
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#inputForm").attr("action","${ctx}/expfetch/rewredraw/unitfetch?repage=1");
			$("#inputForm").submit();
	    	return false;
	    }
	    
		function rSubmit(){
			$("#inputForm").attr("action","${ctx}/expfetch/rewredraw/receiveunitresult");
			$("#inputForm").submit();
	    	return false;
	    }
	    
		function rCancel(){
			$("#inputForm").attr("action","${ctx}/expfetch/rewredraw/cancelunitresult");
			$("#inputForm").submit();
	    	return false;
	    }
	    
		function bCancel(){
			$("#inputForm").attr("action","${ctx}/expfetch/rewredraw/backunitmethod");
			$("#inputForm").submit();
	    	return false;
	    }
	    
	    var disc = [];
		function discard(id){
			if($("#"+id).checked==true){
			  $("#committeeName"+id).show();
			}else{
			  $("#committeeName"+id).hide();
			}
	    }
	    
		function discancel(id){
			for(var i=0; i<disc.length; i++) {
				if(disc[i]=id){
				disc.remove(i);						
				}
		   }
			$("#discIds").val(disc);
			$("#discCancel"+id).hide();
			$("#btnDiscard"+id).show();
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
	    
		function cThree(){
			$("#rejectRecent").val('1');
			$("#cancelThree").hide();
			$("#backThree").show();
	    	return true;
	    }
	    
		function bThree(){
			$("#rejectRecent").val('0');
			$("#backThree").hide();
			$("#cancelThree").show();
	    	return true;
	    }
	    
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">按单位方式补抽专家</li>
	</ul>
	<form:form id="inputForm" modelAttribute="projectExpert" action="${ctx}/expfetch/rewredraw/directdrawunit" method="post" class="form-horizontal">
	<tags:message content="${message}"/>
		<form:hidden path="prjid"/>
		<form:hidden path="fetchTime"/>
		<div class="control-group">
			<label class="control-label">评审时间:</label>
			<div class="controls">
				从<form:input path="reviewBegin" maxlength="20"
						class="span2 input-small Wdate" value="1900-01-01" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
				至<form:input path="reviewEnd" maxlength="20"
						class="span2 input-small Wdate" value="1900-01-01" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>
      <div class="span10">
        <h4>原抽选结果：</h4>
      </div>
	<table id="oldTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>归属单位</th><th>类别</th><th>专业</th><th>职称</th><th>是否缺席</th><th>缺席原因</th></tr></thead>
		<tbody>
		<c:forEach items="${olist}" var="pExpert">
			<tr>
				<td><a href="${ctx}/expmanage/binfo?id=${pExpert.expertExpertConfirm.id}">${pExpert.expertExpertConfirm.expertInfo.name}</a></td>
				<td>${pExpert.expertExpertConfirm.expertInfo.unit.name}</td>
				<td>${fns:getDictLabel(pExpert.expertExpertConfirm.expertKind,'sys_specialkind_type','')}</td>
				<td>${fns:getDictLabel(pExpert.expertExpertConfirm.expertSpecial,'sys_special_type','')}</td>
				<td>${fns:getDictLabel(pExpert.expertExpertConfirm.expertInfo.technical,'sys_tech_type','')}</td>
				<td>
		        <c:if test="${pExpert.expertAccept eq '1'}">
				<input type="checkbox" id="${pExpert.expertExpertConfirm.id}" name="checkboxid" onclick="discard('${pExpert.expertExpertConfirm.id}');"/>
		        </c:if>
		        <c:if test="${pExpert.expertAccept eq '0'}">
				<input type="checkbox" id="${pExpert.expertExpertConfirm.id}" name="checkboxid" onclick="discard('${pExpert.expertExpertConfirm.id}');" checked="true" readonly="true"/>
		        </c:if>
				</td>
				<td>
		        <c:if test="${pExpert.expertAccept eq '1'}">
                <input type="text" name="committeeName" id="committeeName${pExpert.expertExpertConfirm.id}" style=" width:270px; height:54px; font-size:18px; line-height:54px;display:none;"  
                value="输入缺席原因" onFocus="if(value=='输入缺席原因') {value=''}" onBlur="if(value==''){value='输入缺席原因'}"/>
		        </c:if>
		        <c:if test="${pExpert.expertAccept eq '0'}">
                <input type="text" name="committeeName" id="committeeName${pExpert.expertExpertConfirm.id}" style=" width:270px; height:54px; font-size:18px; line-height:54px;"  
                value="输入缺席原因" onFocus="if(value=='输入缺席原因') {value=''}" onBlur="if(value==''){value='输入缺席原因'}" value="${pExpert.committeeName}" readonly="true"/>
		        </c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<div class="control-group">
			<label class="control-label">新增补抽类型及数量:</label>
			<div class="controls">
				<div style="margin-right:40px; float:left;">
				<form:select path="techcnt" class="span2">
					<form:option value="" label="技术类"/>
					<form:options items="${fns:getDictList('sys_techcnt_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				</div>
				<div style="margin-left:30px; float:left;">
				<form:select path="ecomcnt" class="span2">
					<form:option value="" label="经济类"/>
					<form:options items="${fns:getDictList('sys_ecomcnt_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">录入补抽原因:</label>
			<div class="controls">
						<form:input path="remarks" htmlEscape="false" maxlength="50"
							class="span2 required/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监督人:
						<form:input path="supervise" htmlEscape="false" maxlength="20"
							class="span2 required userName"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="进行补抽"/>
		</div>
      <div class="span10">
        <h4>以下为补抽结果：</h4>
      </div>
			<form:hidden path="unitIdsNo"/>
			<form:hidden path="resIds"/>
			<form:hidden path="discIds"/>
			<form:hidden path="seriesIdsYes"/>
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
			<input id="resSubmit" class="btn btn-primary" type="button" value="采用本次补抽结果" onclick="rSubmit()"/>
			<input id="resCancel" class="btn btn-primary" type="button" value="放弃本次补抽" onclick="rCancel()"/>
		</div>
	</form:form>

</body>
</html>