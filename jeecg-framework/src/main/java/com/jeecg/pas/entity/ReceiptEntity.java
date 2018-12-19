package com.jeecg.pas.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.ObjectUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 订单
 * @author onlineGenerator
 * @date 2018-06-01 19:36:19
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_receipt", schema = "")
@SuppressWarnings("serial")
public class ReceiptEntity implements java.io.Serializable {

	/** 主键 */
	private java.lang.Integer id;
	/** 创建人登录名称 */
	private java.lang.String createBy;
	/** 创建日期 */
	private java.util.Date createDate;
	/** 更新人登录名称 */
	private java.lang.String updateBy;
	/** 更新日期 */
	private java.util.Date updateDate;
	/** 商铺ID */
	@Excel(name = "商铺ID", width = 15, dictTable = "ps_shop", dicCode = "id", dicText = "shop_name")
	private java.lang.Integer shopId;
	/** 总价 */
	@Excel(name = "总价", width = 15)
	private java.lang.Double totalAmount;
	/** 优惠金额 */
	@Excel(name = "优惠金额", width = 15)
	private java.lang.Double discountAmount;
	/** 购买时间 **/
	private java.util.Date buyTime;
	/** 支付方式 */
	@Excel(name = "支付方式", width = 15, dicCode = "pay_mode")
	private java.lang.Integer payMode;
	/** 币种 */
	@Excel(name = "币种", width = 15, dicCode = "currency")
	private java.lang.Integer currency;
	/** 实际支付 **/
	@Excel(name = "实际支付", width = 15)
	private java.lang.Double actualAmount;
	/** 备注 */
	@Excel(name = "备注", width = 15)
	private java.lang.String remark;

	private List<ReceiptItemEntity> receiptItems;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, length = 20)
	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	@Column(name = "CREATE_BY", nullable = true, length = 50)
	public java.lang.String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "CREATE_DATE", nullable = true, length = 20)
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATE_BY", nullable = true, length = 50)
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "UPDATE_DATE", nullable = true, length = 20)
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "SHOP_ID", nullable = true, length = 11)
	public java.lang.Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(java.lang.Integer shopId) {
		this.shopId = shopId;
	}

	@Column(name = "TOTAL_AMOUNT", nullable = false, precision = 11, scale = 2)
	public java.lang.Double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(java.lang.Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "DISCOUNT_AMOUNT", nullable = true, precision = 11, scale = 2)
	public java.lang.Double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(java.lang.Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name = "BUY_TIME", nullable = false)
	public java.util.Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(java.util.Date buyTime) {
		this.buyTime = buyTime;
	}

	@Column(name = "PAY_MODE", nullable = false, length = 4)
	public java.lang.Integer getPayMode() {
		return this.payMode;
	}

	public void setPayMode(java.lang.Integer payMode) {
		this.payMode = payMode;
	}

	@Column(name = "CURRENCY", nullable = false, length = 4)
	public java.lang.Integer getCurrency() {
		return this.currency;
	}

	public void setCurrency(java.lang.Integer currency) {
		this.currency = currency;
	}

	@Column(name = "ACTUAL_AMOUNT", nullable = true, precision = 11, scale = 2)
	public java.lang.Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(java.lang.Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	@Column(name = "REMARK", nullable = true, length = 255)
	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "receipt")
	public List<ReceiptItemEntity> getReceiptItems() {
		return receiptItems;
	}

	public void setReceiptItems(List<ReceiptItemEntity> receiptItems) {
		this.receiptItems = receiptItems;
	}

	@Transient
	public java.lang.Integer getItemCount() {
		return this.receiptItems.size();
	}

	@Transient
	public java.lang.Double getExchangeRate() {
		if (this.actualAmount != null) {
			return new BigDecimal(this.actualAmount / this.totalAmount).setScale(5, RoundingMode.HALF_UP).doubleValue();
		}
		return null;
	}

	public ReceiptEntity setDefault() {
		this.discountAmount = ObjectUtils.defaultIfNull(this.discountAmount, 0d);
		this.actualAmount = ObjectUtils.defaultIfNull(this.actualAmount, 0d);

		return this;
	}

}
