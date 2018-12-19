package com.jeecg.sched.service;
import com.jeecg.sched.entity.TaskTypeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TaskTypeServiceI extends CommonService{
	
 	public void delete(TaskTypeEntity entity) throws Exception;
 	
 	public Serializable save(TaskTypeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TaskTypeEntity entity) throws Exception;
 	
}
