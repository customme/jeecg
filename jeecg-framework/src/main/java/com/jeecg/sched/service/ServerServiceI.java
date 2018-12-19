package com.jeecg.sched.service;
import com.jeecg.sched.entity.ServerEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ServerServiceI extends CommonService{
	
 	public void delete(ServerEntity entity) throws Exception;
 	
 	public Serializable save(ServerEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ServerEntity entity) throws Exception;
 	
}
