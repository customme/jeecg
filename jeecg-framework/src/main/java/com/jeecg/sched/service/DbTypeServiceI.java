package com.jeecg.sched.service;
import com.jeecg.sched.entity.DbTypeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface DbTypeServiceI extends CommonService{
	
 	public void delete(DbTypeEntity entity) throws Exception;
 	
 	public Serializable save(DbTypeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(DbTypeEntity entity) throws Exception;
 	
}
