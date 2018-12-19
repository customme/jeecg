package com.jeecg.pas.service;
import com.jeecg.pas.entity.CustomerClassEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface CustomerClassServiceI extends CommonService{
	
 	public void delete(CustomerClassEntity entity) throws Exception;
 	
 	public Serializable save(CustomerClassEntity entity) throws Exception;
 	
 	public void saveOrUpdate(CustomerClassEntity entity) throws Exception;
 	
}
