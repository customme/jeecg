package com.jeecg.sched.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.jeecg.sched.constant.TaskConstant;

/**
 * @Title: Entity
 * @Description: 任务类型
 * @author onlineGenerator
 * @date 2018-05-10 17:38:56
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_task_type", schema = "")
@SuppressWarnings("serial")
public class TaskTypeEntity implements java.io.Serializable {

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
	/** 任务执行器 */
	@Excel(name = "任务执行器", width = 15)
	private java.lang.String taskExecutor;
	/** 最多尝试次数 */
	@Excel(name = "最多尝试次数", width = 15)
	private java.lang.Integer maxTryTimes;
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

	@Column(name = "TASK_EXECUTOR", nullable = false, length = 128)
	public java.lang.String getTaskExecutor() {
		return this.taskExecutor;
	}

	public void setTaskExecutor(java.lang.String taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@Column(name = "MAX_TRY_TIMES", nullable = false, length = 4)
	public java.lang.Integer getMaxTryTimes() {
		return this.maxTryTimes;
	}

	public void setMaxTryTimes(java.lang.Integer maxTryTimes) {
		this.maxTryTimes = ObjectUtils.defaultIfNull(maxTryTimes, TaskConstant.MAX_TRY_TIMES);
	}

	@Column(name = "DESCRIPTION", nullable = true, length = 255)
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

}
