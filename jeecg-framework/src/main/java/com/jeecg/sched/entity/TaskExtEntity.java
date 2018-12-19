package com.jeecg.sched.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 任务扩展
 * @author onlineGenerator
 * @date 2018-05-12 14:47:41
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_task_ext", schema = "")
@SuppressWarnings("serial")
public class TaskExtEntity implements java.io.Serializable {

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
	/** 任务ID */
	private java.lang.Integer taskId;
	/** 属性名 */
	@Excel(name = "属性名", width = 15)
	private java.lang.String propName;
	/** 属性值 */
	@Excel(name = "属性值", width = 15)
	private java.lang.String propValue;

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

	@Column(name = "TASK_ID", nullable = false, length = 20)
	public java.lang.Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(java.lang.Integer taskId) {
		this.taskId = taskId;
	}

	@Column(name = "PROP_NAME", nullable = false, length = 64)
	public java.lang.String getPropName() {
		return this.propName;
	}

	public void setPropName(java.lang.String propName) {
		this.propName = propName;
	}

	@Column(name = "PROP_VALUE", nullable = false, length = 32)
	public java.lang.String getPropValue() {
		return this.propValue;
	}

	public void setPropValue(java.lang.String propValue) {
		this.propValue = propValue;
	}

	private List<TaskExtEntity> taskExtList;

	@Transient
	public List<TaskExtEntity> getTaskExtList() {
		return taskExtList;
	}

	public void setTaskExtList(List<TaskExtEntity> taskExtList) {
		this.taskExtList = taskExtList;
	}

	/**
	 * 重置系统自动填充字段
	 * 
	 * @return
	 */
	public TaskExtEntity reset() {
		this.setId(null);
		this.setCreateBy(null);
		this.setCreateDate(null);
		this.setUpdateBy(null);
		this.setUpdateDate(null);

		return this;
	}

}
