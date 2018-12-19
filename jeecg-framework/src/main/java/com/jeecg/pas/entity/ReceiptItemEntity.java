package com.jeecg.pas.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 小票明细
 * @author onlineGenerator
 * @date 2018-06-01 19:37:51
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_receipt_item", schema = "")
@SuppressWarnings("serial")
public class ReceiptItemEntity implements java.io.Serializable {

	/** 主键 */
	private java.lang.Integer id;
	/** 小票 */
	private ReceiptEntity receipt;
	/** 商品 */
	private GoodsEntity goods;
	/** 单价 */
	@Excel(name = "单价", width = 15)
	private java.lang.Double unitPrice;
	/** 数量 */
	@Excel(name = "数量", width = 15)
	private java.lang.Integer quantity;
	/** 总价 */
	@Excel(name = "总价", width = 15)
	private java.lang.Double totalPrice;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, length = 20)
	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RECEIPT_ID")
	public ReceiptEntity getReceipt() {
		return this.receipt;
	}

	public void setReceipt(ReceiptEntity receipt) {
		this.receipt = receipt;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GOODS_ID")
	public GoodsEntity getGoods() {
		return this.goods;
	}

	public void setGoods(GoodsEntity goods) {
		this.goods = goods;
	}

	@Column(name = "UNIT_PRICE", nullable = false, precision = 9, scale = 2)
	public java.lang.Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(java.lang.Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "QUANTITY", nullable = false, precision = 11, scale = 2)
	public java.lang.Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(java.lang.Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "TOTAL_PRICE", nullable = false, precision = 11, scale = 2)
	public java.lang.Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(java.lang.Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	private List<ReceiptItemEntity> receiptItemList;

	@Transient
	public List<ReceiptItemEntity> getReceiptItemList() {
		return receiptItemList;
	}

	public void setReceiptItemList(List<ReceiptItemEntity> receiptItemList) {
		this.receiptItemList = receiptItemList;
	}

}
