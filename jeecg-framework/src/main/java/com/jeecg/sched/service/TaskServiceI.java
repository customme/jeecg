package com.jeecg.sched.service;
import com.jeecg.sched.entity.TaskEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TaskServiceI extends CommonService{
	
 	public void delete(TaskEntity entity) throws Exception;
 	
 	public Serializable save(TaskEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TaskEntity entity) throws Exception;
 	
}
