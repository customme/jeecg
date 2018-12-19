package com.jeecg.pas.service;
import com.jeecg.pas.entity.CostEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface CostServiceI extends CommonService{
	
 	public void delete(CostEntity entity) throws Exception;
 	
 	public Serializable save(CostEntity entity) throws Exception;
 	
 	public void saveOrUpdate(CostEntity entity) throws Exception;
 	
}
