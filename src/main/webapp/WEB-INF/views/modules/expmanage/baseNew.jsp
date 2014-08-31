<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html> 
<head>
	<title>专家修改</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#expertCode").focus();
		$("#inputForm").validate({
			rules: {
			    expertCode: {remote: "${ctx}/expmanage/checkExpertID?oldExpertId=" + encodeURIComponent('')},
                "specialFrom":{
                    required: true
                },
                "specialTo": {
                    required: true,
                    compareDate: "#specialFrom"
                }
			},
			messages: {
				expertCode: {remote: "专家编号已存在"},
				confirmNewPassword: {equalTo: "输入与上面相同的密码"},
	               "specialFrom":{
	                    required: "开始时间不能为空"
	                },
	                "specialTo":{
	                    required: "结束时间不能为空",
	                    compareDate: "结束日期必须大于开始日期!"
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
                "specialFrom":{
                    required: true
                },
                "specialTo": {
                    required: true,
                    compareDate: "#specialFrom"
                }
            },
            messages:{
                "specialFrom":{
                    required: "开始时间不能为空"
                },
                "specialTo":{
                    required: "结束时间不能为空",
                    compareDate: "结束日期必须大于开始日期!"
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
	
    document.onreadystatechange = function(){	
      	 var rewb = "";
      	var rew = $("#birthdate");
      	rewb = rew.val();
      	rewb = rewb.substr(0,10);
      	$("#birthdate").val(rewb);
      }
   	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">录入专家</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expertInfo"
		action="${ctx}/expmanage/addbase" enctype="multipart/form-data" method="post" class="form-horizontal">
		<tags:message content="${message}" />
		<!--<div class="row-fluid">
			 <div class="span4"> -->
		<div class="control-group">
			<label class="control-label">归属公司:</label>
			<div class="controls">
                <tags:treeselect id="company" name="company.id" value="${expertInfo.user.company.id}" labelName="company.name" labelValue="${expertInfo.user.company.name}"
					title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden"/>
				<form:input path="user.loginName" htmlEscape="false" maxlength="50" class="required userName"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
			</div>
		</div>
				<div class="control-group">
					<label class="control-label">姓名:</label>
					<div class="controls">
						<form:input path="name" htmlEscape="false" maxlength="20"
							class="span2 required userName"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">性别:</label>
					<div class="controls">男<form:radiobutton path="sex" value="1" class="required"/>&nbsp;&nbsp;&nbsp;&nbsp;女<form:radiobutton path="sex" value="0" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">出生年月:</label>
					<div class="controls">
						<form:input path="birthdate" maxlength="20"
							class="span2 input-small Wdate required" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
					</div>
				</div>				
			<!-- </div> -->
			<!-- <div class="span2">
					<input type="file" name="picture0" accept="image/*" onchange="handleFiles(this)" alt="选择照片"/>
												
		           <div id="imgList" style="width:100;height:105px;"> 个人照片</div>
			</div> 				
		</div>-->
		<div class="control-group">
			<label class="control-label">民 族:</label>
			<div class="controls">
				<form:select path="nation" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_nation_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号:</label>
			<div class="controls">
				<form:input path="identifyCode" htmlEscape="false" maxlength="50"
					class="span4 required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业学校:</label>
			<div class="controls">
				<form:input path="collage" htmlEscape="false" maxlength="50"
					class="required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学 历:</label>
			<div class="controls">
				<form:select path="education" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_education_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所学专业:</label>
			<div class="controls">
				<form:input path="studySpecial" htmlEscape="false" maxlength="100"
					class="span3 required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现从事专业:</label>
			<div class="controls">
				<form:input path="specialist" htmlEscape="false" maxlength="20"
					class="span3 required" />
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
			<label class="control-label">职    称:</label>
			<div class="controls">
				<form:select path="technical" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_tech_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
				<%-- <div class="control-group">
					<label class="control-label">输入专家编号:</label>
					<div class="controls">
				<input id="oldExpertId" name="oldExpertId" type="hidden" value="">
						<form:input path="expertCode" htmlEscape="false" maxlength="20"
							class="span4 required"/>
					</div>
				</div> --%>
		<div class="control-group">
			<label class="control-label">工作经历:</label>
			<div class="controls">
					<form:textarea path="workThrough" rows="6" cols="50" htmlEscape="false" maxlength="100" class="span4 required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要业绩:</label>
			<div class="controls">
					<form:textarea path="achievement" rows="6" cols="50" htmlEscape="false" maxlength="100" class="span4 required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐单位意见:</label>
			<div class="controls">
		        <input id="pushAdvice" name="pushAdvice" type="textarea" rows="6" cols="50">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专家类别:</label>
			<div class="controls">
				<form:select path="specialKind1" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_specialkind_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">专家专业:</label>
			<div class="controls">
				<form:select path="kind1Special1" class="span2 required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_special_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
          </div>	
		</div>
		<div class="control-group">
			<label class="control-label">行业部门〈或管理单位)初审意见:</label>
			<div class="controls">
		        <textarea id="deptormanageAdvice" name="deptormanageAdvice" value="${deptormanageAdvice}"  rows="5" cols="80"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				value="确定" />&nbsp; <input id="btnCancel" class="btn" type="button"
				value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>