package com.jeecg.pas.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 客户分级
 * @author onlineGenerator
 * @date 2018-06-01 19:38:18
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_customer_class", schema = "")
@SuppressWarnings("serial")
public class CustomerClassEntity implements java.io.Serializable {

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
	/** 等级名称 */
	@Excel(name = "等级名称", width = 15)
	private java.lang.String className;
	/** 优先级 */
	@Excel(name = "优先级", width = 15)
	private java.lang.Integer classOrder;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, length = 4)
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

	@Column(name = "CLASS_NAME", nullable = false, length = 32)
	public java.lang.String getClassName() {
		return this.className;
	}

	public void setClassName(java.lang.String className) {
		this.className = className;
	}

	@Column(name = "CLASS_ORDER", nullable = false, length = 4)
	public java.lang.Integer getClassOrder() {
		return this.classOrder;
	}

	public void setClassOrder(java.lang.Integer classOrder) {
		this.classOrder = classOrder;
	}

	private List<CustomerClassEntity> customerClassList;

	@Transient
	public List<CustomerClassEntity> getCustomerClassList() {
		return customerClassList;
	}

	public void setCustomerClassList(List<CustomerClassEntity> customerClassList) {
		this.customerClassList = customerClassList;
	}

}
