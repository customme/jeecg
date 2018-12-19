package com.jeecg.pas.service;
import com.jeecg.pas.entity.GoodsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface GoodsServiceI extends CommonService{
	
 	public void delete(GoodsEntity entity) throws Exception;
 	
 	public Serializable save(GoodsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(GoodsEntity entity) throws Exception;
 	
}
