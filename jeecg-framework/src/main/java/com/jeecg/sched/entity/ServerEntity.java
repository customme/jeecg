package com.jeecg.sched.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.jeecg.sched.constant.TaskConstant;

/**
 * @Title: Entity
 * @Description: 调度服务器
 * @author onlineGenerator
 * @date 2018-05-11 16:47:36
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_server", schema = "")
@SuppressWarnings("serial")
public class ServerEntity implements java.io.Serializable {

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
	/** 所属集群 */
	@Excel(name = "所属集群", width = 15, dictTable = "t_cluster", dicCode = "id", dicText = "name")
	private java.lang.Integer clusterId;
	/** IP地址 */
	@Excel(name = "IP地址", width = 15)
	private java.lang.String ip;
	/** 主机名 */
	@Excel(name = "主机名", width = 15)
	private java.lang.String hostname;
	/** 操作系统 */
	@Excel(name = "操作系统", width = 15)
	private java.lang.String os;
	/** 最大任务并行数 */
	@Excel(name = "最大任务并行数", width = 15)
	private java.lang.Integer taskMaximum;
	/** 心跳时间 */
	private java.util.Date beatTime;
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

	@Column(name = "NAME", nullable = false, length = 64)
	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	@Column(name = "CLUSTER_ID", nullable = false, length = 4)
	public java.lang.Integer getClusterId() {
		return this.clusterId;
	}

	public void setClusterId(java.lang.Integer clusterId) {
		this.clusterId = clusterId;
	}

	@Column(name = "IP", nullable = false, length = 64)
	public java.lang.String getIp() {
		return this.ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	@Column(name = "HOSTNAME", nullable = true, length = 64)
	public java.lang.String getHostname() {
		return this.hostname;
	}

	public void setHostname(java.lang.String hostname) {
		this.hostname = hostname;
	}

	@Column(name = "OS", nullable = true, length = 64)
	public java.lang.String getOs() {
		return this.os;
	}

	public void setOs(java.lang.String os) {
		this.os = os;
	}

	@Column(name = "TASK_MAXIMUM", nullable = false, length = 4, columnDefinition = "TINYINT DEFAULT '5'")
	public java.lang.Integer getTaskMaximum() {
		return this.taskMaximum;
	}

	public void setTaskMaximum(java.lang.Integer taskMaximum) {
		this.taskMaximum = taskMaximum;
	}

	@Column(name = "BEAT_TIME", nullable = true, length = 32)
	public java.util.Date getBeatTime() {
		return this.beatTime;
	}

	public void setBeatTime(java.util.Date beatTime) {
		this.beatTime = beatTime;
	}

	@Column(name = "DESCRIPTION", nullable = true, length = 255)
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	/**
	 * 设置默认值
	 * 
	 * @return
	 */
	public ServerEntity setDefault() {
		this.name = StringUtils.defaultIfBlank(this.name, StringUtils.defaultIfBlank(this.hostname, this.ip));
		this.taskMaximum = ObjectUtils.defaultIfNull(this.taskMaximum, TaskConstant.TASK_MAXIMUM);

		return this;
	}

}
