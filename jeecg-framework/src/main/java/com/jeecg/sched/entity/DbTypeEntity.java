package com.jeecg.sched.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 数据库类型
 * @author onlineGenerator
 * @date 2018-05-10 17:39:40
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_db_type", schema = "")
@SuppressWarnings("serial")
public class DbTypeEntity implements java.io.Serializable {

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
	/** 编码 */
	@Excel(name = "编码", width = 15)
	private java.lang.String code;
	/** 默认端口 */
	private java.lang.Integer defaultPort;
	/** 默认字符集 */
	private java.lang.String defaultCharset;
	/** 描述 */
	@Excel(name = "描述", width = 15)
	private java.lang.String description;

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

	@Column(name = "CODE", nullable = false, length = 64)
	public java.lang.String getCode() {
		return this.code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	@Column(name = "DEFAULT_PORT", nullable = false, length = 11)
	public java.lang.Integer getDefaultPort() {
		return defaultPort;
	}

	public void setDefaultPort(java.lang.Integer defaultPort) {
		this.defaultPort = defaultPort;
	}

	@Column(name = "DEFAULT_CHARSET", nullable = false, length = 64)
	public java.lang.String getDefaultCharset() {
		return defaultCharset;
	}

	public void setDefaultCharset(java.lang.String defaultCharset) {
		this.defaultCharset = defaultCharset;
	}

	@Column(name = "DESCRIPTION", nullable = true, length = 255)
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

}
