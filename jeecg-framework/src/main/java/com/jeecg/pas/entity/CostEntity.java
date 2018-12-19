package com.jeecg.pas.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 成本
 * @author onlineGenerator
 * @date 2018-06-22 10:04:43
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_cost", schema = "")
@SuppressWarnings("serial")
public class CostEntity implements java.io.Serializable {

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
	/** 成本类型 **/
	@Excel(name = "成本类型", width = 15, dicCode = "cost_type")
	private java.lang.Integer costType;
	/** 成本 */
	@Excel(name = "成本", width = 15)
	private java.lang.Double cost;
	/** 币种 */
	@Excel(name = "币种", width = 15, dicCode = "currency")
	private java.lang.Integer currency;
	/** 花费时间 */
	@Excel(name = "花费时间", width = 15, format = "yyyy-MM-dd")
	private java.util.Date costDate;
	/** 备注 */
	@Excel(name = "备注", width = 15)
	private java.lang.String remark;

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

	@Column(name = "COST_TYPE", nullable = false, length = 4)
	public java.lang.Integer getCostType() {
		return costType;
	}

	public void setCostType(java.lang.Integer costType) {
		this.costType = costType;
	}

	@Column(name = "COST", nullable = false, precision = 9, scale = 2)
	public java.lang.Double getCost() {
		return this.cost;
	}

	public void setCost(java.lang.Double cost) {
		this.cost = cost;
	}

	@Column(name = "CURRENCY", nullable = false, length = 4)
	public java.lang.Integer getCurrency() {
		return this.currency;
	}

	public void setCurrency(java.lang.Integer currency) {
		this.currency = currency;
	}

	@Column(name = "COST_DATE", nullable = false, length = 10)
	public java.util.Date getCostDate() {
		return this.costDate;
	}

	public void setCostDate(java.util.Date costDate) {
		this.costDate = costDate;
	}

	@Column(name = "REMARK", nullable = false, length = 64)
	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	private List<CostEntity> costList;

	@Transient
	public List<CostEntity> getCostList() {
		return costList;
	}

	public void setCostList(List<CostEntity> costList) {
		this.costList = costList;
	}

	/**
	 * 设置模糊查询
	 * 
	 * @return
	 */
	public CostEntity setFuzzy() {
		if (StringUtils.isNotBlank(this.remark)) {
			this.remark = "*" + this.remark.replaceAll("\\*", "") + "*";
		}

		return this;
	}

}
