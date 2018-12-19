package com.jeecg.pas.service;
import com.jeecg.pas.entity.OrderEntity;
import com.jeecg.pas.entity.OrderItemEntity;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface OrderServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(OrderEntity order,
	        List<OrderItemEntity> orderItemList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(OrderEntity order,
	        List<OrderItemEntity> orderItemList);
	public void delMain (OrderEntity order);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(OrderEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(OrderEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(OrderEntity t);
}
