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
		
	function ajaxFileUpload()
	{
	
	$("#loading")
        .ajaxStart(function(){
            $(this).show();
        })//开始上传文件时显示一个图片
        .ajaxComplete(function(){
            $(this).hide();
        });//文件上传完成将图片隐藏起来
        
       $.ajaxFileUpload({
                 url:'${ctx}/experts/picture',             //需要链接到服务器地址
                 secureuri:false,
                 fileElementId:'picture0',                         //文件选择框的id属性
                 dataType: 'json',                                     //服务器返回的格式，可以是json
                 success: function (data, status)             //相当于java中try语句块的用法
                 {   
                 //alert(data);       //data是从服务器返回来的值   
                     $('#result').html('上传图片成功!请复制图片地址<br/>'+data.src);
 
                 },
                 error: function (data, status, e)             //相当于java中catch语句块的用法
                 {
                     $('#result').html('上传图片失败');
                 }
               }
             );
    }
    
	window.URL = window.URL || window.webkitURL;
	function handleFiles(obj) {
	var fileList = null;
	    fileList = document.getElementById("imgList");
	    if(fileList.hasChildNodes())
	          fileList.removeChild(fileList.firstChild)
	   
		var files = obj.files,
			img = new Image();
		if(window.URL){
			//File API
			  alert(files[0].name + "," + files[0].size + " bytes");
		      img.src = window.URL.createObjectURL(files[0]); //创建一个object URL，并不是你的本地路径
		      img.width = 100;
		      img.onload = function(e) {
		         window.URL.revokeObjectURL(this.src); //图片加载后，释放object URL
		      }
		      fileList.appendChild(img);
		}else if(window.FileReader){
			//opera不支持createObjectURL/revokeObjectURL方法。我们用FileReader对象来处理
			var reader = new FileReader();
			reader.readAsDataURL(files[0]);
			reader.onload = function(e){
				alert(files[0].name + "," +e.total + " bytes");
				img.src = this.result;
				img.width = 100;
		      fileList.appendChild(img);
			}
		}else{
			//ie
			obj.select();
			obj.blur();
			var nfile = document.selection.createRange().text;
			document.selection.empty();
			img.src = nfile;
			img.width = 100;
			img.onload=function(){
		      alert(nfile+","+img.fileSize + " bytes");
		    }
		      fileList.appendChild(img);
			//fileList.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src='"+nfile+"')";
		}
		
		
	}
	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/experts/binfo?id=${id}">专家信息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expertInfo"
		action="${ctx}/expmanage/verify" enctype="multipart/form-data" method="post" class="form-horizontal">
		<form:hidden path="userId" />
		<tags:message content="${message}" />
		<!-- <div class="row-fluid">
			<div class="span4"> -->
				<div class="control-group">
					<label class="control-label">姓名:</label>
					<div class="controls">
						<label class="lbl">${expertInfo.name}</label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">性别:</label>
					<div class="controls">男<form:radiobutton path="sex" value="1"  readonly="true"/>&nbsp;&nbsp;&nbsp;&nbsp;女<form:radiobutton path="sex" value="0"  readonly="true"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">出生年月:</label>
					<div class="controls">
						<label class="lbl">${expertInfo.birthdate}</label>
					</div>
				</div>				
			<!-- </div>
			<div class="span2">
					<input type="file" name="picture0" accept="image/*" onchange="handleFiles(this)" alt="选择照片"/>
												
		           <div id="imgList" style="width:100;height:105px;"> 个人照片</div>
			</div>				
		</div> -->
		<div class="control-group">
			<label class="control-label">身份证号:</label>
			<div class="controls">
				<label class="lbl">${expertInfo.identifyCode}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业学校:</label>
			<div class="controls">
				<label class="lbl">${expertInfo.collage}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学 历:</label>
			<div class="controls">
				<form:select path="education" class="span2 required" disabled="true">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_education_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所学专业:</label>
			<div class="controls">
				<label class="lbl">${expertInfo.studySpecial}</label>
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
			<label class="control-label">参加工作时间:</label>
			<div class="controls">
						<label class="lbl">${expertInfo.startworkTime}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址:</label>
			<div class="controls">
				<label class="lbl">${expertInfo.homeAddr}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报类别:</label>
			<div class="controls">
				<form:select path="specialKind1" class="span2 required" disabled="true">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_specialkind_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">申报专业:</label>
			<div class="controls">
				<form:select path="kind1Special1" class="span2 required" disabled="true">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_special_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
          </div>	
		</div>
		<div class="form-actions">
			<a href="../../static/ckfinder/ckfinder.html?type=expert&start=expert:/${expertInfo.name}(ID${id})/">专家资料</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="填写审核表"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>