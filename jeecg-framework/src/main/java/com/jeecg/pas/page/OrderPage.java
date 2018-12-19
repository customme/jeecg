
package com.jeecg.pas.page;
import com.jeecg.pas.entity.OrderEntity;
import com.jeecg.pas.entity.OrderItemEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

/**   
 * @Title: Entity
 * @Description: 订单
 * @author onlineGenerator
 * @date 2018-07-19 11:48:34
 * @version V1.0   
 *
 */
public class OrderPage implements java.io.Serializable {
	/**主键*/
	private java.lang.Integer id;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**总金额*/
    @Excel(name="总金额")
	private java.lang.Double amount;
	/**快递费*/
    @Excel(name="快递费")
	private java.lang.Double expressFee;
	/**订单状态*/
    @Excel(name="订单状态")
	private java.lang.Integer orderStatus;
	/**支付方式*/
    @Excel(name="支付方式")
	private java.lang.Integer payMode;
	/**收货人*/
    @Excel(name="收货人")
	private java.lang.String realname;
	/**手机号*/
    @Excel(name="手机号")
	private java.lang.String mobile;
	/**所在地区*/
    @Excel(name="所在地区")
	private java.lang.String region;
	/**详细地址*/
    @Excel(name="详细地址")
	private java.lang.String address;
	/**运送方式*/
    @Excel(name="运送方式")
	private java.lang.Integer delivery;
	/**运单号*/
    @Excel(name="运单号")
	private java.lang.String expressNo;
	/**备注*/
    @Excel(name="备注")
	private java.lang.String remark;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  主键
	 */
	public java.lang.Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  主键
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  总金额
	 */
	public java.lang.Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  总金额
	 */
	public void setAmount(java.lang.Double amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  快递费
	 */
	public java.lang.Double getExpressFee(){
		return this.expressFee;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  快递费
	 */
	public void setExpressFee(java.lang.Double expressFee){
		this.expressFee = expressFee;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  订单状态
	 */
	public java.lang.Integer getOrderStatus(){
		return this.orderStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  订单状态
	 */
	public void setOrderStatus(java.lang.Integer orderStatus){
		this.orderStatus = orderStatus;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  支付方式
	 */
	public java.lang.Integer getPayMode(){
		return this.payMode;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  支付方式
	 */
	public void setPayMode(java.lang.Integer payMode){
		this.payMode = payMode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人
	 */
	public java.lang.String getRealname(){
		return this.realname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人
	 */
	public void setRealname(java.lang.String realname){
		this.realname = realname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号
	 */
	public java.lang.String getMobile(){
		return this.mobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号
	 */
	public void setMobile(java.lang.String mobile){
		this.mobile = mobile;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所在地区
	 */
	public java.lang.String getRegion(){
		return this.region;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所在地区
	 */
	public void setRegion(java.lang.String region){
		this.region = region;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  详细地址
	 */
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  详细地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  运送方式
	 */
	public java.lang.Integer getDelivery(){
		return this.delivery;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  运送方式
	 */
	public void setDelivery(java.lang.Integer delivery){
		this.delivery = delivery;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  运单号
	 */
	public java.lang.String getExpressNo(){
		return this.expressNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  运单号
	 */
	public void setExpressNo(java.lang.String expressNo){
		this.expressNo = expressNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}

	/**保存-订单明细*/
    @ExcelCollection(name="订单明细")
	private List<OrderItemEntity> orderItemList = new ArrayList<OrderItemEntity>();
		public List<OrderItemEntity> getOrderItemList() {
		return orderItemList;
		}
		public void setOrderItemList(List<OrderItemEntity> orderItemList) {
		this.orderItemList = orderItemList;
		}
}
