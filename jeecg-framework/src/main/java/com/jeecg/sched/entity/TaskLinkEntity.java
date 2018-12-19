package com.jeecg.sched.entity;

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

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 任务依赖
 * @author onlineGenerator
 * @date 2018-05-12 15:19:56
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_task_link", schema = "")
@SuppressWarnings("serial")
public class TaskLinkEntity implements java.io.Serializable {

	/** 主键 */
	private java.lang.Integer id;
	/** 创建人登录名称 */
	private java.lang.String createBy;
	/** 创建日期 */
	private java.util.Date createDate;
	/** 任务 */
	private TaskEntity task;
	/** 依赖任务 */
	private TaskEntity ptask;
	/** 依赖类型 */
	@Excel(name = "依赖类型", width = 15, dicCode = "link_type")
	private java.lang.Integer linkType;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TASK_ID")
	public TaskEntity getTask() {
		return this.task;
	}

	public void setTask(TaskEntity task) {
		this.task = task;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TASK_PID")
	public TaskEntity getPtask() {
		return this.ptask;
	}

	public void setPtask(TaskEntity ptask) {
		this.ptask = ptask;
	}

	@Column(name = "LINK_TYPE", nullable = false, length = 4)
	public java.lang.Integer getLinkType() {
		return this.linkType;
	}

	public void setLinkType(java.lang.Integer linkType) {
		this.linkType = linkType;
	}

	private List<TaskLinkEntity> taskLinkList;

	@Transient
	public List<TaskLinkEntity> getTaskLinkList() {
		return taskLinkList;
	}

	public void setTaskLinkList(List<TaskLinkEntity> taskLinkList) {
		this.taskLinkList = taskLinkList;
	}

	/**
	 * 重置系统自动填充字段
	 * 
	 * @return
	 */
	public TaskLinkEntity reset() {
		this.setId(null);
		this.setCreateBy(null);
		this.setCreateDate(null);

		return this;
	}

}
