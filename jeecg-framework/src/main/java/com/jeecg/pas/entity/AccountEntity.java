package com.jeecg.pas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Title: Entity
 * @Description: 收入支出
 * @date 2018-08-16 17:58:35
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_account", schema = "")
@SuppressWarnings("serial")
public class AccountEntity implements java.io.Serializable {

	/** 主键 */
	private java.lang.Integer id;
	/** 登录名称 */
	private java.lang.String createBy;
	/** 业务日期 */
	private java.util.Date bizDate;
	/** 商品成本 **/
	private java.lang.Double goodsCost;
	/** 应收款 **/
	private java.lang.Double receivable;
	/** 实收款 **/
	private java.lang.Double received;
	/** 其他成本 **/
	private java.lang.Double expense;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, length = 11)
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

	@Column(name = "BIZ_DATE", nullable = true, length = 10)
	public java.util.Date getBizDate() {
		return bizDate;
	}

	public void setBizDate(java.util.Date bizDate) {
		this.bizDate = bizDate;
	}

	@Column(name = "GOODS_COST", nullable = false, precision = 11, scale = 2)
	public java.lang.Double getGoodsCost() {
		return goodsCost;
	}

	public void setGoodsCost(java.lang.Double goodsCost) {
		this.goodsCost = goodsCost;
	}

	@Column(name = "RECEIVABLE", nullable = false, precision = 11, scale = 2)
	public java.lang.Double getReceivable() {
		return receivable;
	}

	public void setReceivable(java.lang.Double receivable) {
		this.receivable = receivable;
	}

	@Column(name = "RECEIVED", nullable = false, precision = 11, scale = 2)
	public java.lang.Double getReceived() {
		return received;
	}

	public void setReceived(java.lang.Double received) {
		this.received = received;
	}

	@Column(name = "EXPENSE", nullable = false, precision = 11, scale = 2)
	public java.lang.Double getExpense() {
		return expense;
	}

	public void setExpense(java.lang.Double expense) {
		this.expense = expense;
	}

	/**
	 * 预期利润
	 * 
	 * @return
	 */
	@Transient
	public java.lang.Double getProfit() {
		return this.receivable - this.goodsCost - this.expense;
	}

	/**
	 * 实际利润
	 * 
	 * @return
	 */
	@Transient
	public java.lang.Double getActualProfit() {
		return this.received - this.goodsCost - this.expense;
	}

}
