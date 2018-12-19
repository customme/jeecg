package com.jeecg.sched.service;
import com.jeecg.sched.entity.ClusterEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ClusterServiceI extends CommonService{
	
 	public void delete(ClusterEntity entity) throws Exception;
 	
 	public Serializable save(ClusterEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ClusterEntity entity) throws Exception;
 	
}
