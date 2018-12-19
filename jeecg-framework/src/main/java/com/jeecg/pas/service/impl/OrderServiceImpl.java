package com.jeecg.pas.service.impl;
import com.jeecg.pas.service.OrderServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import com.jeecg.pas.entity.CartEntity;
import com.jeecg.pas.entity.OrderEntity;
import com.jeecg.pas.entity.OrderItemEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;


@Service("orderService")
@Transactional
public class OrderServiceImpl extends CommonServiceImpl implements OrderServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((OrderEntity)entity);
 	}
	
	public void addMain(OrderEntity order,
	        List<OrderItemEntity> orderItemList){
			//保存主信息
			this.save(order);
		
			/**保存-订单明细*/
			for(OrderItemEntity orderItem:orderItemList){
				//外键设置
				orderItem.setOrderId(order.getId());
				this.save(orderItem);

	 			// 从购物车删除
				super.executeSql("DELETE FROM ps_cart WHERE customer_id = ? AND goods_id = ?", order.getCustomer().getId(), orderItem.getGoodsId());
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(order);
 			
	}

	
	public void updateMain(OrderEntity order,
	        List<OrderItemEntity> orderItemList) {
		//保存主表信息
		if(StringUtil.isNotEmpty(order.getId())){
			try {
				OrderEntity temp = findUniqueByProperty(OrderEntity.class, "id", order.getId());
				MyBeanUtils.copyBeanNotNull2Bean(order, temp);
				this.saveOrUpdate(temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			this.saveOrUpdate(order);
		}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(order);
	}

	
	public void delMain(OrderEntity order) {
		//删除主表信息
		this.delete(order);
		//===================================================================================
		//获取参数
		Object id0 = order.getId();
		//===================================================================================
		//删除-订单明细
	    String hql0 = "from OrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
	    List<OrderItemEntity> orderItemOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(orderItemOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(OrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(OrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(OrderEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,OrderEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{price}",String.valueOf(t.getPrice()));
 		sql  = sql.replace("#{freight}",String.valueOf(t.getFreight()));
 		sql  = sql.replace("#{order_status}",String.valueOf(t.getOrderStatus()));
 		sql  = sql.replace("#{pay_mode}",String.valueOf(t.getPayMode()));
 		sql  = sql.replace("#{receiver}",String.valueOf(t.getReceiver()));
 		sql  = sql.replace("#{mobile}",String.valueOf(t.getMobile()));
 		sql  = sql.replace("#{region}",String.valueOf(t.getRegion()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{deliver_type}",String.valueOf(t.getDeliverType()));
 		sql  = sql.replace("#{express_no}",String.valueOf(t.getExpressNo()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}