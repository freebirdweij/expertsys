<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/treeview.jsp" %>
<html>
<head>
	<title>专家抽取</title>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#menuIds").val(ids);
					var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
					for(var i=0; i<nodes2.length; i++) {
						ids2.push(nodes2[i].id);
					}
					$("#officeIds").val(ids2);
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
			
			// 用户-菜单
			var zNodes=[
					<c:forEach items="${menuList}" var="menu">{id:'${menu.id}', pId:'${not empty menu.parent.id?menu.parent.id:0}', name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 默认选择节点
			var ids = "${role.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(true);
			
			// 用户-机构
			var zNodes2=[
					<c:forEach items="${officeList}" var="office">{id:'${office.id}', pId:'${not empty office.parent?office.parent.id:0}', name:"${office.name}"},
		            </c:forEach>];
			// 初始化树结构
			var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
			// 不选择父节点
			tree2.setting.check.chkboxType = { "Y" : "s", "N" : "s" };
			// 默认选择节点
			var ids2 = "${role.officeIds}".split(",");
			for(var i=0; i<ids2.length; i++) {
				var node = tree2.getNodeByParam("id", ids2[i]);
				try{tree2.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree2.expandAll(true);
			// 刷新（显示/隐藏）机构
			refreshOfficeTree();
			$("#dataScope").change(function(){
				refreshOfficeTree();
			});
		});
		function refreshOfficeTree(){
			if($("#dataScope").val()==9){
				$("#officeTree").show();
			}else{
				$("#officeTree").hide();
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">按专家个人方式抽取</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expertConfirm" action="${ctx}/expmanage/confirm" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<tags:message content="${message}"/>
      <div class="span10">
        <h4>请选择符合性条件（以下选项可多选，如某项不选，则忽略该项作为筛选条件）</h4>
      </div>
				<div class="control-group">
					<label class="control-label">专家所属区域:</label>
					<div class="controls">
				       <div id="areaTreeYes" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="areaIdsYes"/>
					</div>
				</div>
		<div class="control-group">
			<label class="control-label">专家所属单位:</label>
			<div class="controls">
				       <div id="unitTreeYes" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="unitIdsYes"/>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">专家类别:</label>
			<div class="controls">
				       <div id="kindTreeYes" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="kindIdsYes"/>
          </div>	
		</div>
		<div class="control-group">
			<label class="control-label">专家专业:</label>
			<div class="controls">
				       <div id="specialTreeYes" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="specialIdsYes"/>
			</div>
		</div>
		<div class="control-group">
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
		</div>
      <div class="span10">
        <h4>请选择拒绝性条件（以下选项可多选，如某项不选，则忽略该项作为筛选条件）</h4>
      </div>
				<div class="control-group">
					<label class="control-label">专家所属区域:</label>
					<div class="controls">
				       <div id="areaTreeNo" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="areaIdsNo"/>
					</div>
				</div>
		<div class="control-group">
			<label class="control-label">专家所属单位:</label>
			<div class="controls">
				       <div id="unitTreeNo" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="unitIdsNo"/>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">专家类别:</label>
			<div class="controls">
				       <div id="kindTreeNo" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="kindIdsNo"/>
          </div>	
		</div>
		<div class="control-group">
			<label class="control-label">专家专业:</label>
			<div class="controls">
				       <div id="specialTreeNo" class="ztree" style="margin-top:3px;float:left;"></div>
		               <form:hidden path="specialIdsNo"/>
			</div>
		</div>
		<div class="control-group">
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
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="确定"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>