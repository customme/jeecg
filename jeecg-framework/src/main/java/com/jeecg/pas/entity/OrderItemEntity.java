package com.jeecg.pas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 订单明细
 * @author onlineGenerator
 * @date 2018-07-19 11:48:34
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_order_item", schema = "")
@SuppressWarnings("serial")
public class OrderItemEntity implements java.io.Serializable {

	/** 主键 */
	private java.lang.Integer id;
	/** 订单ID */
	private java.lang.Integer orderId;
	/** 商品ID */
	private java.lang.Integer goodsId;
	/** 商品名称 */
	@Excel(name = "商品名称", width = 15)
	private java.lang.String goodsName;
	/** 商品规格 */
	@Excel(name = "商品规格", width = 15)
	private java.lang.String goodsSpecs;
	/** 商品图片 */
	@Excel(name = "商品图片", width = 15)
	private java.lang.String goodsImg;
	/** 单价 */
	@Excel(name = "单价", width = 15)
	private java.lang.Double price;
	/** 数量 */
	@Excel(name = "数量", width = 15)
	private java.lang.Integer quantity;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, length = 20)
	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	@Column(name = "ORDER_ID", nullable = true, length = 32)
	public java.lang.Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(java.lang.Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "GOODS_ID", nullable = true, length = 32)
	public java.lang.Integer getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(java.lang.Integer goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "GOODS_NAME", nullable = true, length = 32)
	public java.lang.String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name = "GOODS_SPECS", nullable = true, length = 128)
	public java.lang.String getGoodsSpecs() {
		return this.goodsSpecs;
	}

	public void setGoodsSpecs(java.lang.String goodsSpecs) {
		this.goodsSpecs = goodsSpecs;
	}

	@Column(name = "GOODS_IMG", nullable = true, length = 255)
	public java.lang.String getGoodsImg() {
		return this.goodsImg;
	}

	public void setGoodsImg(java.lang.String goodsImg) {
		this.goodsImg = goodsImg;
	}

	@Column(name = "PRICE", nullable = true, precision = 11, scale = 2)
	public java.lang.Double getPrice() {
		return this.price;
	}

	public void setPrice(java.lang.Double price) {
		this.price = price;
	}

	@Column(name = "QUANTITY", nullable = true, length = 11)
	public java.lang.Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(java.lang.Integer quantity) {
		this.quantity = quantity;
	}

}
