package com.jeecg.sched.service;
import com.jeecg.sched.entity.TaskPoolEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TaskPoolServiceI extends CommonService{
	
 	public void delete(TaskPoolEntity entity) throws Exception;
 	
 	public Serializable save(TaskPoolEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TaskPoolEntity entity) throws Exception;
 	
}
