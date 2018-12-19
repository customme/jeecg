package com.jeecg.sched.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.jeecg.sched.constant.TaskConstant;
import com.jeecg.sched.utils.DateUtils;

/**
 * @Title: Entity
 * @Description: 任务
 * @author onlineGenerator
 * @date 2018-05-10 17:38:43
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_task", schema = "")
@SuppressWarnings("serial")
public class TaskEntity implements java.io.Serializable {

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
	/** 任务名称 */
	@Excel(name = "任务名称", width = 15)
	private java.lang.String name;
	/** 任务组 */
	@Excel(name = "任务组", width = 15)
	private java.lang.String taskGroup;
	/** 任务类型 */
	@Excel(name = "任务类型", width = 15, dictTable = "t_task_type", dicCode = "id", dicText = "code")
	private java.lang.Integer typeId;
	/** 任务状态 */
	@Excel(name = "任务状态", width = 15, dicCode = "task_stat")
	private java.lang.Integer taskStatus;
	/** 任务周期 */
	@Excel(name = "任务周期", width = 15, dicCode = "task_cycle")
	private java.lang.String taskCycle;
	/** 周期值 */
	@Excel(name = "周期值", width = 15)
	private java.lang.String cycleValue;
	/** 时间串行 */
	private java.lang.Integer dateSerial;
	/** 优先级 */
	@Excel(name = "优先级", width = 15)
	private java.lang.Integer priority;
	/** 最多尝试次数 */
	@Excel(name = "最多尝试次数", width = 15)
	private java.lang.Integer maxTryTimes;
	/** 超时时间 */
	@Excel(name = "超时时间", width = 15)
	private java.lang.Integer timeout;
	/** 所属集群 */
	@Excel(name = "所属集群", width = 15, dictTable = "t_cluster", dicCode = "id", dicText = "name")
	private java.lang.Integer clusterId;
	/** 服务器 */
	@Excel(name = "服务器", width = 15)
	private java.lang.Integer serverId;
	/** 开始时间 */
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	private java.util.Date startTime;
	/** 结束时间 */
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	private java.util.Date endTime;
	/** 首次运行时间 */
	private java.util.Date firstTime;
	/** 任务描述 */
	@Excel(name = "任务描述", width = 15)
	private java.lang.String description;

	/** 复制扩展属性 **/
	private java.lang.Integer copyExt;
	/** 复制依赖关系 **/
	private java.lang.Integer copyLink;

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

	@Column(name = "TASK_GROUP", nullable = false, length = 32)
	public java.lang.String getTaskGroup() {
		return this.taskGroup;
	}

	public void setTaskGroup(java.lang.String taskGroup) {
		this.taskGroup = taskGroup;
	}

	@Column(name = "TYPE_ID", nullable = false, length = 4)
	public java.lang.Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(java.lang.Integer typeId) {
		this.typeId = typeId;
	}

	@Column(name = "TASK_STATUS", nullable = false, length = 4)
	public java.lang.Integer getTaskStatus() {
		return this.taskStatus;
	}

	public void setTaskStatus(java.lang.Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Column(name = "TASK_CYCLE", nullable = false, length = 16)
	public java.lang.String getTaskCycle() {
		return this.taskCycle;
	}

	public void setTaskCycle(java.lang.String taskCycle) {
		this.taskCycle = taskCycle;
	}

	@Column(name = "CYCLE_VALUE", nullable = true, length = 64)
	public java.lang.String getCycleValue() {
		return this.cycleValue;
	}

	public void setCycleValue(java.lang.String cycleValue) {
		this.cycleValue = cycleValue;
	}

	@Column(name = "DATE_SERIAL", nullable = false, length = 4)
	public java.lang.Integer getDateSerial() {
		return dateSerial;
	}

	public void setDateSerial(java.lang.Integer dateSerial) {
		this.dateSerial = dateSerial;
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

	@Column(name = "TIMEOUT", nullable = false, length = 6)
	public java.lang.Integer getTimeout() {
		return this.timeout;
	}

	public void setTimeout(java.lang.Integer timeout) {
		this.timeout = timeout;
	}

	@Column(name = "CLUSTER_ID", nullable = false, length = 4)
	public java.lang.Integer getClusterId() {
		return this.clusterId;
	}

	public void setClusterId(java.lang.Integer clusterId) {
		this.clusterId = clusterId;
	}

	@Column(name = "SERVER_ID", nullable = true, length = 6)
	public java.lang.Integer getServerId() {
		return this.serverId;
	}

	public void setServerId(java.lang.Integer serverId) {
		this.serverId = serverId;
	}

	@Column(name = "START_TIME", nullable = false, length = 32)
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

	@Column(name = "FIRST_TIME", nullable = true, length = 32)
	public java.util.Date getFirstTime() {
		return this.firstTime;
	}

	public void setFirstTime(java.util.Date firstTime) {
		this.firstTime = firstTime;
	}

	@Column(name = "DESCRIPTION", nullable = true, length = 255)
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	@Transient
	public java.lang.Integer getCopyExt() {
		return copyExt;
	}

	public void setCopyExt(java.lang.Integer copyExt) {
		this.copyExt = copyExt;
	}

	@Transient
	public java.lang.Integer getCopyLink() {
		return copyLink;
	}

	public void setCopyLink(java.lang.Integer copyLink) {
		this.copyLink = copyLink;
	}

	/**
	 * 设置默认值
	 */
	public TaskEntity setDefault() {
		this.taskGroup = StringUtils.defaultIfBlank(this.taskGroup, TaskConstant.DEFAULT_GROUP);
		this.priority = ObjectUtils.defaultIfNull(this.priority, TaskConstant.PRIORITY);
		this.maxTryTimes = ObjectUtils.defaultIfNull(this.maxTryTimes, TaskConstant.MAX_TRY_TIMES);
		this.timeout = ObjectUtils.defaultIfNull(this.timeout, TaskConstant.TIMEOUT);
		this.dateSerial = ObjectUtils.defaultIfNull(this.dateSerial, TaskConstant.DATE_SERIAL_NO);

		// 计算第一个周期运行时间
		if (TaskConstant.TASK_CYCLE_DAY.equals(this.taskCycle)) {
			this.firstTime = DateUtils.trimTime(this.startTime);
		} else if (TaskConstant.TASK_CYCLE_WEEK.equals(this.taskCycle)) {
			this.firstTime = DateUtils.trimTime(DateUtils.changeWeek(this.startTime, Integer.valueOf(this.cycleValue)));
			if (this.firstTime.before(this.startTime)) {
				this.firstTime = DateUtils.nextWeek(1, this.firstTime);
			}
		} else if (TaskConstant.TASK_CYCLE_MONTH.equals(this.taskCycle)) {
			this.firstTime = DateUtils.trimTime(DateUtils.changeDate(this.startTime, Integer.valueOf(this.cycleValue)));
			if (this.firstTime.before(this.startTime)) {
				this.firstTime = DateUtils.nextMonth(1, this.firstTime);
			}
		} else if (TaskConstant.TASK_CYCLE_HOUR.equals(this.taskCycle)) {
			this.firstTime = DateUtils.trimMS(this.startTime);
		}

		return this;
	}

}
