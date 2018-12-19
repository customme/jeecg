package com.jeecg.sched.service;
import com.jeecg.sched.entity.DbConnEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface DbConnServiceI extends CommonService{
	
 	public void delete(DbConnEntity entity) throws Exception;
 	
 	public Serializable save(DbConnEntity entity) throws Exception;
 	
 	public void saveOrUpdate(DbConnEntity entity) throws Exception;
 	
}
