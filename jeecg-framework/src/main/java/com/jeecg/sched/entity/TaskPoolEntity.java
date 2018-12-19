package com.jeecg.sched.entity;

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
import org.jeecgframework.poi.excel.annotation.Excel;

import com.jeecg.sched.constant.TaskConstant;

/**
 * @Title: Entity
 * @Description: 任务实例
 * @author onlineGenerator
 * @date 2018-05-14 14:30:07
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_task_pool", schema = "")
@SuppressWarnings("serial")
public class TaskPoolEntity implements java.io.Serializable {

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
	/** 任务 */
	private TaskEntity task;
	/** 运行时间 */
	@Excel(name = "运行时间", width = 15, format = "yyyy-MM-dd")
	private java.util.Date runTime;
	/** 任务状态 */
	@Excel(name = "任务状态", width = 15, dicCode = "task_state")
	private java.lang.Integer taskState;
	/** 优先级 */
	@Excel(name = "优先级", width = 15)
	private java.lang.Integer priority;
	/** 最多尝试次数 */
	@Excel(name = "最多尝试次数", width = 15)
	private java.lang.Integer maxTryTimes;
	/** 已经尝试次数 */
	private java.lang.Integer triedTimes;
	/** 是否重做 */
	@Excel(name = "是否重做", width = 15, dicCode = "yorn")
	private java.lang.Integer redoFlag;
	/** 运行服务器 */
	private java.lang.Integer runServer;
	/** 运行时参数 */
	@Excel(name = "运行时参数", width = 15)
	private java.lang.String runParams;
	/** 开始时间 */
	private java.util.Date startTime;
	/** 结束时间 */
	private java.util.Date endTime;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TASK_ID")
	public TaskEntity getTask() {
		return task;
	}

	public void setTask(TaskEntity task) {
		this.task = task;
	}

	@Column(name = "RUN_TIME", nullable = false, length = 32)
	public java.util.Date getRunTime() {
		return this.runTime;
	}

	public void setRunTime(java.util.Date runTime) {
		this.runTime = runTime;
	}

	@Column(name = "TASK_STATE", nullable = false, length = 4)
	public java.lang.Integer getTaskState() {
		return this.taskState;
	}

	public void setTaskState(java.lang.Integer taskState) {
		this.taskState = taskState;
	}

	@Column(name = "PRIORITY", nullable = false, length = 4)
	public java.lang.Integer getPriority() {
		return this.priority;
	}

	public void setPriority(java.lang.Integer priority) {
		this.priority = priority;
	}

	@Column(name = "MAX_TRY_TIMES", nullable = false, length = 4)
	public java.lang.Integer getMaxTryTimes() {
		return this.maxTryTimes;
	}

	public void setMaxTryTimes(java.lang.Integer maxTryTimes) {
		this.maxTryTimes = maxTryTimes;
	}

	@Column(name = "TRIED_TIMES", nullable = false, length = 4)
	public java.lang.Integer getTriedTimes() {
		return this.triedTimes;
	}

	public void setTriedTimes(java.lang.Integer triedTimes) {
		this.triedTimes = triedTimes;
	}

	@Column(name = "REDO_FLAG", nullable = false, length = 1)
	public java.lang.Integer getRedoFlag() {
		return this.redoFlag;
	}

	public void setRedoFlag(java.lang.Integer redoFlag) {
		this.redoFlag = redoFlag;
	}

	@Column(name = "RUN_SERVER", nullable = true, length = 6)
	public java.lang.Integer getRunServer() {
		return this.runServer;
	}

	public void setRunServer(java.lang.Integer runServer) {
		this.runServer = runServer;
	}

	@Column(name = "RUN_PARAMS", nullable = true, length = 255)
	public java.lang.String getRunParams() {
		return this.runParams;
	}

	public void setRunParams(java.lang.String runParams) {
		this.runParams = runParams;
	}

	@Column(name = "START_TIME", nullable = true, length = 32)
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", nullable = true, length = 32)
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 耗时
	 * 
	 * @return
	 */
	@Transient
	public Long getConsumeTime() {
		Long consumeTime = null;
		if (this.startTime != null && this.endTime != null) {
			consumeTime = (this.endTime.getTime() - this.startTime.getTime()) / 1000;
		}
		return consumeTime;
	}

	/**
	 * 设置默认值
	 * 
	 * @return
	 */
	public TaskPoolEntity setDefault() {
		if (this.task == null)
			this.task = new TaskEntity();
		this.priority = ObjectUtils.defaultIfNull(this.priority, ObjectUtils.defaultIfNull(this.task.getPriority(), TaskConstant.PRIORITY));
		this.maxTryTimes = ObjectUtils.defaultIfNull(this.maxTryTimes, ObjectUtils.defaultIfNull(this.task.getMaxTryTimes(), TaskConstant.MAX_TRY_TIMES));
		this.triedTimes = ObjectUtils.defaultIfNull(this.triedTimes, 0);
		this.redoFlag = ObjectUtils.defaultIfNull(this.redoFlag, TaskConstant.REDO_FLAG);

		return this;
	}

}
