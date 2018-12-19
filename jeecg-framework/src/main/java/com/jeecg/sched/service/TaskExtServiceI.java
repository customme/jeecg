package com.jeecg.sched.service;
import com.jeecg.sched.entity.TaskExtEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TaskExtServiceI extends CommonService{
	
 	public void delete(TaskExtEntity entity) throws Exception;
 	
 	public Serializable save(TaskExtEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TaskExtEntity entity) throws Exception;
 	
}
