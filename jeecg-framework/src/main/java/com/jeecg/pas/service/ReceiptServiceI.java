package com.jeecg.pas.service;
import com.jeecg.pas.entity.ReceiptEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ReceiptServiceI extends CommonService{
	
 	public void delete(ReceiptEntity entity) throws Exception;
 	
 	public Serializable save(ReceiptEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ReceiptEntity entity) throws Exception;
 	
}
