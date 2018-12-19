package com.jeecg.sched.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 数据库连接
 * @author onlineGenerator
 * @date 2018-05-10 17:39:49
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_db_conn", schema = "")
@SuppressWarnings("serial")
public class DbConnEntity implements java.io.Serializable {

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
	/** 名称 */
	@Excel(name = "名称", width = 15)
	private java.lang.String name;
	/** 数据库名 */
	@Excel(name = "数据库名", width = 15)
	private java.lang.String dbName;
	/** 数据库类型 */
	@Excel(name = "数据库类型", width = 15, dictTable = "t_db_type", dicCode = "id", dicText = "code")
	private java.lang.Integer typeId;
	/** 连接方式 */
	@Excel(name = "连接方式", width = 15, dicCode = "conn_type")
	private java.lang.Integer connType;
	/** 用户名 */
	@Excel(name = "用户名", width = 15)
	private java.lang.String username;
	/** 密码 */
	@Excel(name = "密码", width = 15)
	private java.lang.String password;
	/** 主机名 */
	@Excel(name = "主机名", width = 15)
	private java.lang.String hostname;
	/** 端口号 */
	@Excel(name = "端口号", width = 15)
	private java.lang.Integer port;
	/** 数据库编码 */
	@Excel(name = "数据库编码", width = 15)
	private java.lang.String charset;
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

	@Column(name = "NAME", nullable = false, length = 128)
	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = StringUtils.defaultIfBlank(name, this.hostname + "-" + this.dbName);
	}

	@Column(name = "DB_NAME", nullable = false, length = 64)
	public java.lang.String getDbName() {
		return this.dbName;
	}

	public void setDbName(java.lang.String dbName) {
		this.dbName = dbName;
	}

	@Column(name = "TYPE_ID", nullable = false, length = 4)
	public java.lang.Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(java.lang.Integer typeId) {
		this.typeId = typeId;
	}

	@Column(name = "CONN_TYPE", nullable = false, length = 4)
	public java.lang.Integer getConnType() {
		return this.connType;
	}

	public void setConnType(java.lang.Integer connType) {
		this.connType = connType;
	}

	@Column(name = "USERNAME", nullable = false, length = 64)
	public java.lang.String getUsername() {
		return this.username;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", nullable = true, length = 255)
	public java.lang.String getPassword() {
		return this.password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	@Column(name = "HOSTNAME", nullable = false, length = 64)
	public java.lang.String getHostname() {
		return this.hostname;
	}

	public void setHostname(java.lang.String hostname) {
		this.hostname = hostname;
	}

	@Column(name = "PORT", nullable = true, length = 11)
	public java.lang.Integer getPort() {
		return this.port;
	}

	public void setPort(java.lang.Integer port) {
		this.port = port;
	}

	@Column(name = "CHARSET", nullable = true, length = 32)
	public java.lang.String getCharset() {
		return this.charset;
	}

	public void setCharset(java.lang.String charset) {
		this.charset = charset;
	}

	@Column(name = "DESCRIPTION", nullable = true, length = 255)
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

}
