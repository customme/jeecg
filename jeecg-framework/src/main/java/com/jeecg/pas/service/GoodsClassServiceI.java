package com.jeecg.pas.service;
import com.jeecg.pas.entity.GoodsClassEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface GoodsClassServiceI extends CommonService{
	
 	public void delete(GoodsClassEntity entity) throws Exception;
 	
 	public Serializable save(GoodsClassEntity entity) throws Exception;
 	
 	public void saveOrUpdate(GoodsClassEntity entity) throws Exception;
 	
}
