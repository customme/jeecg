package com.jeecg.pas.service;
import com.jeecg.pas.entity.ReceiptItemEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ReceiptItemServiceI extends CommonService{
	
 	public void delete(ReceiptItemEntity entity) throws Exception;
 	
 	public Serializable save(ReceiptItemEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ReceiptItemEntity entity) throws Exception;
 	
}
