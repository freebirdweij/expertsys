package com.thinkgem.jeesite.modules.sys.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.loginfo.entity.ExpertdbLog;
import com.thinkgem.jeesite.modules.project.entity.ProjectInfo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.common.utils.Reflections;

public class LogUtils {

	public static ExpertdbLog getLogByExpert(ExpertInfo expertInfo,User user){
		if (expertInfo!=null&&StringUtils.isNotBlank(expertInfo.getName())){
			ExpertdbLog expertdbLog = new ExpertdbLog();
			expertdbLog.setObjectName(expertInfo.getName());
			expertdbLog.setObjectType(Constants.Log_Type_Expert);
			expertdbLog.setObjectUser(user);
			
			return expertdbLog;
		}
		return null;
	}
	
	public static ExpertdbLog getLogByProject(ProjectInfo projectInfo,User user){
		if (projectInfo!=null&&StringUtils.isNotBlank(projectInfo.getPrjName())){
			ExpertdbLog expertdbLog = new ExpertdbLog();
			expertdbLog.setObjectId(projectInfo.getId());
			expertdbLog.setObjectName(projectInfo.getPrjName());
			expertdbLog.setObjectType(Constants.Log_Type_Project);
			expertdbLog.setObjectUser(user);
			
			return expertdbLog;
		}
		return null;
	}
	
	public static String compareTwoBean(Object oldb,Object newb,Class<?> clas){
		StringBuffer strb = new StringBuffer();
		Method[] methods = clas.getMethods();
		for(Method method:methods){
			  if(method.getName().startsWith("get")){
                  try {
					if(!method.invoke(oldb, null).equals(method.invoke(newb, null))){
						strb.append(method.invoke(oldb, null).toString()).append("->").append(method.invoke(newb, null).toString()).append("|");						
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              }
		}
	return strb.toString();
	}

	public static ExpertdbLog getLogByCompareExpert(ExpertInfo oldexpert,ExpertInfo newexpert,User user){
		if (oldexpert!=null&&newexpert!=null){
			ExpertdbLog expertdbLog = new ExpertdbLog();
			expertdbLog.setObjectName(newexpert.getName());
			expertdbLog.setObjectType(Constants.Log_Type_Expert);
			expertdbLog.setObjectUser(user);
			
			StringBuffer strb = new StringBuffer();
			strb.append(Constants.Log_Function_ExpertEdit).append("修改了一位专家,具体变更如下：");
			strb.append(compareTwoBean(oldexpert,newexpert,ExpertInfo.class));
			expertdbLog.setOperation(strb.toString());
			
			return expertdbLog;
		}
		return null;
	}
	
	public static ExpertdbLog getLogByCompareProject(ProjectInfo oldproject,ProjectInfo newproject,User user){
		if (oldproject!=null&&newproject!=null){
			ExpertdbLog expertdbLog = new ExpertdbLog();
			expertdbLog.setObjectId(oldproject.getId());
			expertdbLog.setObjectName(newproject.getPrjName());
			expertdbLog.setObjectType(Constants.Log_Type_Project);
			expertdbLog.setObjectUser(user);
			
			StringBuffer strb = new StringBuffer();
			strb.append(Constants.Log_Function_ProjectEdit).append("修改了项目信息,具体变更如下：");
			strb.append(compareTwoBean(oldproject,newproject,ProjectInfo.class));
			expertdbLog.setOperation(strb.toString());
			
			return expertdbLog;
		}
		return null;
	}
}
