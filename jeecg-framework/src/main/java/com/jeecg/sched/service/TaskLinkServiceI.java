package com.jeecg.sched.service;
import com.jeecg.sched.entity.TaskLinkEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TaskLinkServiceI extends CommonService{
	
 	public void delete(TaskLinkEntity entity) throws Exception;
 	
 	public Serializable save(TaskLinkEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TaskLinkEntity entity) throws Exception;
 	
}
