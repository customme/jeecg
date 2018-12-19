package com.jeecg.sched.service;
import com.jeecg.sched.entity.TaskLogEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TaskLogServiceI extends CommonService{
	
 	public void delete(TaskLogEntity entity) throws Exception;
 	
 	public Serializable save(TaskLogEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TaskLogEntity entity) throws Exception;
 	
}
