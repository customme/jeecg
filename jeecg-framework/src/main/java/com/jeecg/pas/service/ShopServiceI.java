package com.jeecg.pas.service;
import com.jeecg.pas.entity.ShopEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ShopServiceI extends CommonService{
	
 	public void delete(ShopEntity entity) throws Exception;
 	
 	public Serializable save(ShopEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ShopEntity entity) throws Exception;
 	
}
