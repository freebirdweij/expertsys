<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家抽取</title>
	<meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
    jQuery(function(){        
        jQuery.validator.methods.compareDate = function(value, element, param) {
            //var startDate = jQuery(param).val() + ":00";补全yyyy-MM-dd HH:mm:ss格式
            //value = value + ":00";
            
            var startDate = jQuery(param).val();
            
            var date1 = new Date(Date.parse(startDate.replace("-", "/")));
            var date2 = new Date(Date.parse(value.replace("-", "/")));
            return date1 < date2;
        };
        
        jQuery("#inputForm").validate({
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
                    compareDate: "结束日期必须大于开始日期!"
                }
            }
        });
    });
		$(document).ready(function(){
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")}
				},
				messages: {
					name: {remote: "角色名已存在"}
				},
				submitHandler: function(form){
					var ids1 = [], nodes1 = areatreeyes.getCheckedNodes(true);
					for(var i=0; i<nodes1.length; i++) {
						ids1.push(nodes1[i].id);
					}
					$("#areaIdsYes").val(ids1);
					
					var ids2 = [], nodes2 = areatreeno.getCheckedNodes(true);
					for(var i=0; i<nodes2.length; i++) {
						ids2.push(nodes2[i].id);
					}
					$("#areaIdsNo").val(ids2);
					
					var ids3 = [], nodes3 = unittreeyes.getCheckedNodes(true);
					for(var i=0; i<nodes3.length; i++) {
						ids3.push(nodes3[i].id);
					}
					$("#unitIdsYes").val(ids3);
					
					var ids4 = [], nodes4 = unittreeno.getCheckedNodes(true);
					for(var i=0; i<nodes4.length; i++) {
						ids4.push(nodes4[i].id);
					}
					$("#unitIdsNo").val(ids4);
					
					var ids5 = [], nodes5 = kindtreeyes.getCheckedNodes(true);
					for(var i=0; i<nodes5.length; i++) {
						ids5.push(nodes5[i].id);
					}
					$("#kindIdsYes").val(ids5);
					
					var ids6 = [], nodes6 = kindtreeno.getCheckedNodes(true);
					for(var i=0; i<nodes6.length; i++) {
						ids6.push(nodes6[i].id);
					}
					$("#kindIdsNo").val(ids6);
					
					var ids7 = [], nodes7 = specialtreeyes.getCheckedNodes(true);
					for(var i=0; i<nodes7.length; i++) {
						ids7.push(nodes7[i].id);
					}
					$("#specialIdsYes").val(ids7);
					
					var ids8 = [], nodes8 = specialtreeno.getCheckedNodes(true);
					for(var i=0; i<nodes8.length; i++) {
						ids8.push(nodes8[i].id);
					}
					$("#specialIdsNo").val(ids8);
					
					var ids9 = [], nodes9 = seriestreeyes.getCheckedNodes(true);
					for(var i=0; i<nodes9.length; i++) {
						ids9.push(nodes9[i].id);
					}
					$("#seriesIdsYes").val(ids9);
					
					var ids10 = [], nodes10 = seriestreeno.getCheckedNodes(true);
					for(var i=0; i<nodes10.length; i++) {
						ids10.push(nodes10[i].id);
					}
					$("#seriesIdsNo").val(ids10);
					
					var ids11 = [], nodes11 = techtreeyes.getCheckedNodes(true);
					for(var i=0; i<nodes11.length; i++) {
						ids11.push(nodes11[i].id);
					}
					$("#techIdsYes").val(ids11);
					
					var ids12 = [], nodes12 = techtreeno.getCheckedNodes(true);
					for(var i=0; i<nodes12.length; i++) {
						ids12.push(nodes12[i].id);
					}
					$("#techIdsNo").val(ids12);
					
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

			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			var areaNodes=[
					<c:forEach items="${areaList}" var="area">{id:'${area.id}', pId:'${not empty area.parent.id?area.parent.id:0}', name:"${not empty area.parent.id?area.name:'地区列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var areatreeyes = $.fn.zTree.init($("#areaTreeYes"), setting, areaNodes);
			var areatreeno = $.fn.zTree.init($("#areaTreeNo"), setting, areaNodes);
			
			var unitNodes=[
					<c:forEach items="${unitList}" var="unit">{id:'${unit.id}', pId:'${not empty unit.parent.id?unit.parent.id:0}', name:"${not empty unit.parent.id?unit.name:'单位列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var unittreeyes = $.fn.zTree.init($("#unitTreeYes"), setting, unitNodes);
			var unittreeno = $.fn.zTree.init($("#unitTreeNo"), setting, unitNodes);
			
			var kindNodes=[
					<c:forEach items="${kindList}" var="kind">{id:'${kind.id}', pId:'0', name:"${not empty kind.id?kind.label:'类型列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var kindtreeyes = $.fn.zTree.init($("#kindTreeYes"), setting, kindNodes);
			var kindtreeno = $.fn.zTree.init($("#kindTreeNo"), setting, kindNodes);
			
			var specialNodes=[
					<c:forEach items="${specialList}" var="special">{id:'${special.id}', pId:'0', name:"${not empty special.id?special.label:'专业列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var specialtreeyes = $.fn.zTree.init($("#specialTreeYes"), setting, specialNodes);
			var specialtreeno = $.fn.zTree.init($("#specialTreeNo"), setting, specialNodes);
			
			var seriesNodes=[
					<c:forEach items="${seriesList}" var="series">{id:'${series.id}', pId:'0', name:"${not empty series.id?series.label:'行业列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var seriestreeyes = $.fn.zTree.init($("#seriesTreeYes"), setting, seriesNodes);
			var seriestreeno = $.fn.zTree.init($("#seriesTreeNo"), setting, seriesNodes);
			
			var techNodes=[
					<c:forEach items="${techList}" var="tech">{id:'${tech.id}', pId:'0', name:"${not empty tech.id?tech.label:'职称列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var techtreeyes = $.fn.zTree.init($("#techTreeYes"), setting, techNodes);
			var techtreeno = $.fn.zTree.init($("#techTreeNo"), setting, techNodes);
			});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">按专家个人方式抽取</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectExpert" action="${ctx}/expfetch/rewredraw/expertfetch" method="post" class="form-horizontal">
		<form:hidden path="prjid"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">请输入项目的评审时间:</label>
			<div class="controls">
				从<form:input path="reviewBegin" maxlength="20"
						class="span2 input-small Wdate" value="1900-01-01" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" readonly="true"/>
				至<form:input path="reviewEnd" maxlength="20"
						class="span2 input-small Wdate" value="1900-01-01" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" readonly="true"/>
			</div>
		</div>
      <div class="row">
      <div class="span12">
        <h5>请选择符合性条件（以下选项可多选，如某项不选，则忽略该项作为筛选条件）</h5>
      </div>
      </div>
      <div class="row-fluid">
         <div class="span4">
				<div class="control-group">
					<label class="control-label">专家所属区域:</label>
					<div class="controls">
				       <div id="areaTreeYes" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="areaIdsYes"/>
					</div>
				</div>
		</div>
       <div class="span4">
		<div class="control-group">
			<label class="control-label">专家所属单位:</label>
			<div class="controls">
				       <div id="unitTreeYes" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="unitIdsYes"/>
            </div>
		</div>
	  </div>
	</div>
      <div class="row-fluid">
         <div class="span4">
		<div class="control-group">
			<label class="control-label">专家类别:</label>
			<div class="controls">
				       <div id="kindTreeYes" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="kindIdsYes"/>
          </div>	
		</div>
		</div>
       <div class="span4">
		<div class="control-group">
			<label class="control-label">专家专业:</label>
			<div class="controls">
				       <div id="specialTreeYes" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="specialIdsYes"/>
			</div>
		</div>
	  </div>
	</div>
		<%-- <div class="control-group">
			<label class="control-label">专家所属行业:</label>
			<div class="controls">
				       <div id="seriesTreeYes" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="seriesIdsYes"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专家职称:</label>
			<div class="controls">
				       <div id="techTreeYes" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="techIdsYes"/>
			</div>
		</div> --%>
      <div class="row">
      <div class="span10">
        <h5>请选择拒绝性条件（以下选项可多选，如某项不选，则忽略该项作为筛选条件）</h5>
      </div>
      </div>
      <div class="row-fluid">
         <div class="span4">
				<div class="control-group">
					<label class="control-label">专家所属区域:</label>
					<div class="controls">
				       <div id="areaTreeNo" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="areaIdsNo"/>
					</div>
				</div>
		</div>
       <div class="span4">
		<div class="control-group">
			<label class="control-label">专家所属单位:</label>
			<div class="controls">
				       <div id="unitTreeNo" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="unitIdsNo"/>
            </div>
		</div>
	  </div>
	</div>
      <div class="row-fluid">
         <div class="span4">
		<div class="control-group">
			<label class="control-label">专家类别:</label>
			<div class="controls">
				       <div id="kindTreeNo" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="kindIdsNo"/>
          </div>	
		</div>
		</div>
       <div class="span4">
		<div class="control-group">
			<label class="control-label">专家专业:</label>
			<div class="controls">
				       <div id="specialTreeNo" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="specialIdsNo"/>
			</div>
		</div>
	  </div>
	</div>
		<%-- <div class="control-group">
			<label class="control-label">专家所属行业:</label>
			<div class="controls">
				       <div id="seriesTreeNo" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="seriesIdsNo"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专家职称:</label>
			<div class="controls">
				       <div id="techTreeNo" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="techIdsNo"/>
			</div>
		</div> --%>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="确定"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>