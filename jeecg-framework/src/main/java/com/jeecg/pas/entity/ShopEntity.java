package com.jeecg.pas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 商铺
 * @author onlineGenerator
 * @date 2018-06-01 19:35:27
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_shop", schema = "")
@SuppressWarnings("serial")
public class ShopEntity implements java.io.Serializable {

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
	/** 商铺名称 */
	@Excel(name = "商铺名称", width = 15)
	private java.lang.String shopName;
	/** 商圈 */
	@Excel(name = "商圈", width = 15)
	private java.lang.String tradeArea;
	/** 地址 */
	@Excel(name = "地址", width = 15)
	private java.lang.String address;
	/** 交通信息 */
	@Excel(name = "交通信息", width = 15)
	private java.lang.String trafficInfo;
	/** 联系人 */
	@Excel(name = "联系人", width = 15)
	private java.lang.String linkman;
	/** 联系方式 */
	@Excel(name = "联系方式", width = 15)
	private java.lang.String linkway;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, length = 11)
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

	@Column(name = "SHOP_NAME", nullable = false, length = 32)
	public java.lang.String getShopName() {
		return this.shopName;
	}

	public void setShopName(java.lang.String shopName) {
		this.shopName = shopName;
	}

	@Column(name = "TRADE_AREA", nullable = true, length = 32)
	public java.lang.String getTradeArea() {
		return this.tradeArea;
	}

	public void setTradeArea(java.lang.String tradeArea) {
		this.tradeArea = tradeArea;
	}

	@Column(name = "ADDRESS", nullable = true, length = 64)
	public java.lang.String getAddress() {
		return this.address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	@Column(name = "TRAFFIC_INFO", nullable = true, length = 255)
	public java.lang.String getTrafficInfo() {
		return this.trafficInfo;
	}

	public void setTrafficInfo(java.lang.String trafficInfo) {
		this.trafficInfo = trafficInfo;
	}

	@Column(name = "LINKMAN", nullable = true, length = 32)
	public java.lang.String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(java.lang.String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "LINKWAY", nullable = true, length = 32)
	public java.lang.String getLinkway() {
		return this.linkway;
	}

	public void setLinkway(java.lang.String linkway) {
		this.linkway = linkway;
	}

	/**
	 * 设置模糊查询
	 * 
	 * @return
	 */
	public ShopEntity setFuzzy() {
		if (StringUtils.isNotBlank(this.shopName)) {
			this.shopName = "*" + this.shopName.replaceAll("\\*", "") + "*";
		}
		if (StringUtils.isNotBlank(this.tradeArea)) {
			this.tradeArea = "*" + this.tradeArea.replaceAll("\\*", "") + "*";
		}

		return this;
	}

}
