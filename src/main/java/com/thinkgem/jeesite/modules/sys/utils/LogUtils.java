package com.thinkgem.jeesite.modules.sys.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import com.thinkgem.jeesite.common.utils.Constants;
import com.thinkgem.jeesite.modules.experts.entity.ExpertInfo;
import com.thinkgem.jeesite.modules.loginfo.entity.ExpertdbLog;
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
	
	public static String compareTwoBean(Object oldb,Object newb){
		StringBuffer strb = new StringBuffer();
		Method[] methods = oldb.getClass().getMethods();
		for(Method method:methods){
			//if(!Reflections.i)
		}
	return null;
	}

	public static ExpertdbLog getLogByCompareExpert(ExpertInfo oldexpert,ExpertInfo newexpert,User user){
		if (oldexpert!=null&&newexpert!=null){
			ExpertdbLog expertdbLog = new ExpertdbLog();
			expertdbLog.setObjectName(newexpert.getName());
			expertdbLog.setObjectType(Constants.Log_Type_Expert);
			expertdbLog.setObjectUser(user);
			
			StringBuffer strb = new StringBuffer();
			strb.append(Constants.Log_Function_ExpertEdit).append("修改了一位专家,具体变更如下：");
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			if(!oldexpert.getSex().equals(newexpert.getSex())){
				strb.append(oldexpert.getSex()).append("->").append(newexpert.getSex()).append("|");
			}
			if(!oldexpert.getNation().equals(newexpert.getNation())){
				strb.append(oldexpert.getNation()).append("->").append(newexpert.getNation()).append("|");
			}
			if(!oldexpert.getIdentifyCode().equals(newexpert.getIdentifyCode())){
				strb.append(oldexpert.getIdentifyCode()).append("->").append(newexpert.getIdentifyCode()).append("|");
			}
			if(!oldexpert.getBirthdate().equals(newexpert.getBirthdate())){
				strb.append(oldexpert.getBirthdate()).append("->").append(newexpert.getBirthdate()).append("|");
			}
			if(!oldexpert.getJob().equals(newexpert.getJob())){
				strb.append(oldexpert.getJob()).append("->").append(newexpert.getJob()).append("|");
			}
			if(!oldexpert.getTechnical().equals(newexpert.getTechnical())){
				strb.append(oldexpert.getTechnical()).append("->").append(newexpert.getTechnical()).append("|");
			}
			if(!oldexpert.getSpecialist().equals(newexpert.getSpecialist())){
				strb.append(oldexpert.getSpecialist()).append("->").append(newexpert.getSpecialist()).append("|");
			}
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			if(!oldexpert.getName().equals(newexpert.getName())){
				strb.append(oldexpert.getName()).append("->").append(newexpert.getName()).append("|");
			}
			return expertdbLog;
		}
		return null;
	}
}
