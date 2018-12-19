package com.jeecg.pas.service;
import com.jeecg.pas.entity.CartEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface CartServiceI extends CommonService{
	
 	public void delete(CartEntity entity) throws Exception;
 	
 	public Serializable save(CartEntity entity) throws Exception;
 	
 	public void saveOrUpdate(CartEntity entity) throws Exception;
 	
}
