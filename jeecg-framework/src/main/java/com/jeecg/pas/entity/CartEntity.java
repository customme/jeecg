package com.jeecg.pas.entity;

import java.util.List;

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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 购物车
 * @author onlineGenerator
 * @date 2018-06-01 19:32:35
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_cart", schema = "")
@SuppressWarnings("serial")
public class CartEntity implements java.io.Serializable {

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
	/** 商品 */
	private GoodsEntity goods;
	/** 数量 */
	@Excel(name = "数量", width = 15)
	private java.lang.Integer quantity;
	/** 购买状态 */
	@Excel(name = "购买状态", width = 15, dicCode = "buy_status")
	private java.lang.Integer buyStatus;
	/** 备注 */
	@Excel(name = "备注", width = 15)
	private java.lang.String remark;
	/** 购买时间 **/
	private java.util.Date buyTime;

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

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GOODS_ID")
	public GoodsEntity getGoods() {
		return this.goods;
	}

	public void setGoods(GoodsEntity goods) {
		this.goods = goods;
	}

	@Column(name = "QUANTITY", nullable = true, length = 11)
	public java.lang.Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(java.lang.Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "BUY_STATUS", nullable = false, length = 4)
	public java.lang.Integer getBuyStatus() {
		return this.buyStatus;
	}

	public void setBuyStatus(java.lang.Integer buyStatus) {
		this.buyStatus = buyStatus;
	}

	@Column(name = "BUY_TIME", nullable = false)
	public java.util.Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(java.util.Date buyTime) {
		this.buyTime = buyTime;
	}

	@Column(name = "REMARK", nullable = true, length = 255)
	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	private List<CartEntity> cartList;

	@Transient
	public List<CartEntity> getCartList() {
		return cartList;
	}

	public void setCartList(List<CartEntity> cartList) {
		this.cartList = cartList;
	}

	/**
	 * 设置模糊查询
	 * 
	 * @return
	 */
	public CartEntity setFuzzy() {
		if (this.goods != null && StringUtils.isNotBlank(this.goods.getGoodsName())) {
			this.goods.setGoodsName("*" + this.goods.getGoodsName().replaceAll("\\*", "") + "*");
		}

		return this;
	}

}
