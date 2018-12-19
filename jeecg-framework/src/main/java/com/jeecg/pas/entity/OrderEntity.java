package com.jeecg.pas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 订单
 * @author onlineGenerator
 * @date 2018-07-19 11:48:34
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_order", schema = "")
@SuppressWarnings("serial")
public class OrderEntity implements java.io.Serializable {

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
	/** 客户 */
	private CustomerEntity customer;
	/** 商品总价 **/
	private java.lang.Double price;
	/** 运费 */
	@Excel(name = "运费", width = 15)
	private java.lang.Double freight;
	/** 优惠金额 */
	@Excel(name = "优惠金额", width = 15)
	private java.lang.Double discountAmount;
	/** 实付 **/
	private java.lang.Double paid;
	/** 订单状态 */
	@Excel(name = "订单状态", width = 15, dicCode = "order_stat")
	private java.lang.Integer orderStatus;
	/** 支付方式 */
	@Excel(name = "支付方式", width = 15, dicCode = "pay_mode")
	private java.lang.Integer payMode;
	/** 收货人 */
	@Excel(name = "收货人", width = 15)
	private java.lang.String receiver;
	/** 手机号 */
	@Excel(name = "手机号", width = 15)
	private java.lang.String mobile;
	/** 所在地区 */
	@Excel(name = "所在地区", width = 15)
	private java.lang.String region;
	/** 详细地址 */
	@Excel(name = "详细地址", width = 15)
	private java.lang.String address;
	/** 运送方式 */
	@Excel(name = "运送方式", width = 15, dicCode = "delvy_type")
	private java.lang.Integer deliverType;
	/** 运单号 */
	@Excel(name = "运单号", width = 15)
	private java.lang.String expressNo;
	/** 备注 */
	@Excel(name = "备注", width = 15)
	private java.lang.String remark;

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

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMER_ID")
	public CustomerEntity getCustomer() {
		return this.customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	@Column(name = "PRICE", nullable = false, precision = 11, scale = 2)
	public java.lang.Double getPrice() {
		return price;
	}

	public void setPrice(java.lang.Double price) {
		this.price = price;
	}

	@Column(name = "FREIGHT", nullable = true, precision = 11, scale = 2)
	public java.lang.Double getFreight() {
		return freight;
	}

	public void setFreight(java.lang.Double freight) {
		this.freight = freight;
	}

	@Column(name = "DISCOUNT_AMOUNT", nullable = true, precision = 11, scale = 2)
	public java.lang.Double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(java.lang.Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name = "PAID", nullable = true, precision = 11, scale = 2)
	public java.lang.Double getPaid() {
		return paid;
	}

	public void setPaid(java.lang.Double paid) {
		this.paid = paid;
	}

	@Column(name = "ORDER_STATUS", nullable = true, length = 4)
	public java.lang.Integer getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(java.lang.Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "PAY_MODE", nullable = true, length = 4)
	public java.lang.Integer getPayMode() {
		return this.payMode;
	}

	public void setPayMode(java.lang.Integer payMode) {
		this.payMode = payMode;
	}

	@Column(name = "RECEIVER", nullable = true, length = 32)
	public java.lang.String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(java.lang.String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "MOBILE", nullable = true, length = 16)
	public java.lang.String getMobile() {
		return this.mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "REGION", nullable = true, length = 32)
	public java.lang.String getRegion() {
		return this.region;
	}

	public void setRegion(java.lang.String region) {
		this.region = region;
	}

	@Column(name = "ADDRESS", nullable = true, length = 32)
	public java.lang.String getAddress() {
		return this.address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	@Column(name = "DELIVER_TYPE", nullable = true, length = 4)
	public java.lang.Integer getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(java.lang.Integer deliverType) {
		this.deliverType = deliverType;
	}

	@Column(name = "EXPRESS_NO", nullable = true, length = 32)
	public java.lang.String getExpressNo() {
		return this.expressNo;
	}

	public void setExpressNo(java.lang.String expressNo) {
		this.expressNo = expressNo;
	}

	@Column(name = "REMARK", nullable = true, length = 255)
	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	/** 省 **/
	private java.lang.String province;
	/** 市 **/
	private java.lang.String city;
	/** 地区 **/
	private java.lang.String area;

	/**
	 * 应付总额
	 */
	@Transient
	public java.lang.Double getPayment() {
		return this.price + ObjectUtils.defaultIfNull(this.freight, 0d)
				- ObjectUtils.defaultIfNull(this.discountAmount, 0d);
	}

	/**
	 * 省
	 */
	@Transient
	public java.lang.String getProvince() {
		String province = "";
		if (StringUtils.isNotBlank(this.region)) {
			String[] arr = this.region.split(" ");
			province = arr[0];
		}
		return province;
	}

	public void setProvince(java.lang.String province) {
		this.province = province;
	}

	/**
	 * 市
	 */
	@Transient
	public java.lang.String getCity() {
		String city = "";
		if (StringUtils.isNotBlank(this.region)) {
			String[] arr = this.region.split(" ");
			if (arr.length > 1)
				city = arr[1];
		}
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}

	/**
	 * 县
	 */
	@Transient
	public java.lang.String getArea() {
		String area = "";
		if (StringUtils.isNotBlank(this.region)) {
			String[] arr = this.region.split(" ");
			if (arr.length > 2)
				area = arr[2];
		}
		return area;
	}

	public void setArea(java.lang.String area) {
		this.area = area;
	}

	/**
	 * 设置默认值
	 * 
	 * @return
	 */
	public OrderEntity setDefault() {
		if (StringUtils.isNotBlank(this.area)) {
			this.region = this.province + " " + this.city + " " + this.area;
		} else if (StringUtils.isNotBlank(this.city)) {
			this.region = this.province + " " + this.city;
		} else if (StringUtils.isNotBlank(this.province)) {
			this.region = this.province;
		}
		this.paid = ObjectUtils.defaultIfNull(this.paid, 0d);
		this.discountAmount = ObjectUtils.defaultIfNull(this.discountAmount, 0d);
		this.freight = ObjectUtils.defaultIfNull(this.freight, 0d);

		return this;
	}

}
