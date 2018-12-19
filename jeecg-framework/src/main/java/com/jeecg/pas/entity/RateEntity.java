package com.jeecg.pas.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Title: Entity
 * @Description: 成本
 * @date 2018-08-21 10:00:09
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_rate", schema = "")
@SuppressWarnings("serial")
public class RateEntity implements java.io.Serializable {

	/** 主键 */
	private java.lang.Long id;
	/** 源币种 **/
	private java.lang.Integer srcCur;
	/** 目标币种 **/
	private java.lang.Integer tarCur;
	/** 汇率 **/
	private java.lang.Double rate;
	/** 更新时间 */
	private java.util.Date updateTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, length = 20)
	public java.lang.Long getId() {
		return this.id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	@Column(name = "SRC_CUR", nullable = false, length = 4)
	public java.lang.Integer getSrcCur() {
		return srcCur;
	}

	public void setSrcCur(java.lang.Integer srcCur) {
		this.srcCur = srcCur;
	}

	@Column(name = "TAR_CUR", nullable = false, length = 4)
	public java.lang.Integer getTarCur() {
		return tarCur;
	}

	public void setTarCur(java.lang.Integer tarCur) {
		this.tarCur = tarCur;
	}

	@Column(name = "RATE", nullable = false, precision = 9, scale = 5)
	public java.lang.Double getRate() {
		return rate;
	}

	public void setRate(java.lang.Double rate) {
		this.rate = rate;
	}

	@Column(name = "UPDATE_TIME", nullable = false, length = 20)
	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	private List<RateEntity> rateList;

	@Transient
	public List<RateEntity> getRateList() {
		return rateList;
	}

	public void setRateList(List<RateEntity> rateList) {
		this.rateList = rateList;
	}

}
