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

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 任务日志
 * @author onlineGenerator
 * @date 2018-05-10 17:40:00
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_task_log", schema = "")
@SuppressWarnings("serial")
public class TaskLogEntity implements java.io.Serializable {

	/** 主键 */
	private java.lang.Integer id;
	/** 任务 */
	private TaskEntity task;
	/** 运行时间 */
	@Excel(name = "运行时间", width = 15, format = "yyyy-MM-dd")
	private java.util.Date runTime;
	/** 流水号 */
	@Excel(name = "流水号", width = 15)
	private java.lang.Long seqNo;
	/** 日志级别 */
	@Excel(name = "日志级别", width = 15, dicCode = "log_level")
	private java.lang.Integer level;
	/** 日志内容 */
	@Excel(name = "日志内容", width = 15)
	private java.lang.String content;
	/** 记录时间 */
	@Excel(name = "记录时间", width = 15, format = "yyyy-MM-dd")
	private java.util.Date logTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, length = 20)
	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
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

	@Column(name = "SEQ_NO", nullable = true, length = 20)
	public java.lang.Long getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(java.lang.Long seqNo) {
		this.seqNo = seqNo;
	}

	@Column(name = "LEVEL", nullable = false, length = 4)
	public java.lang.Integer getLevel() {
		return this.level;
	}

	public void setLevel(java.lang.Integer level) {
		this.level = level;
	}

	@Column(name = "CONTENT", nullable = true, length = 32)
	public java.lang.String getContent() {
		return this.content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	@Column(name = "LOG_TIME", nullable = false, length = 32)
	public java.util.Date getLogTime() {
		return this.logTime;
	}

	public void setLogTime(java.util.Date logTime) {
		this.logTime = logTime;
	}

}
