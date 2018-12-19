package com.jeecg.pas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 商品
 * @author onlineGenerator
 * @date 2018-06-01 19:30:31
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_goods", schema = "")
@SuppressWarnings("serial")
public class GoodsEntity implements java.io.Serializable {

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
	/** 商品名称 */
	@Excel(name = "商品名称", width = 15)
	private java.lang.String goodsName;
	/** 商品分类 */
	private GoodsClassEntity goodsClass;
	/** 商品规格 */
	@Excel(name = "商品规格", width = 15)
	private java.lang.String goodsSpecs;
	/** 进价 */
	@Excel(name = "进价", width = 15)
	private java.lang.Double bid;
	/** 零售价 */
	@Excel(name = "零售价", width = 15)
	private java.lang.Double price;
	/** 库存 */
	@Excel(name = "库存", width = 15)
	private java.lang.Integer stock;
	/** 描述 */
	@Excel(name = "描述", width = 15)
	private java.lang.String description;
	/** 图片 */
	@Excel(name = "图片", width = 15)
	private java.lang.String goodsImg;

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

	@Column(name = "GOODS_NAME", nullable = false, length = 32)
	public java.lang.String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GOODS_CLASS")
	public GoodsClassEntity getGoodsClass() {
		return this.goodsClass;
	}

	public void setGoodsClass(GoodsClassEntity goodsClass) {
		this.goodsClass = goodsClass;
	}

	@Column(name = "GOODS_SPECS", nullable = false, length = 255)
	public java.lang.String getGoodsSpecs() {
		return this.goodsSpecs;
	}

	public void setGoodsSpecs(java.lang.String goodsSpecs) {
		this.goodsSpecs = goodsSpecs;
	}

	@Column(name = "BID", nullable = false, precision = 9, scale = 2)
	public java.lang.Double getBid() {
		return this.bid;
	}

	public void setBid(java.lang.Double bid) {
		this.bid = bid;
	}

	@Column(name = "PRICE", nullable = true, precision = 9, scale = 2)
	public java.lang.Double getPrice() {
		return this.price;
	}

	public void setPrice(java.lang.Double price) {
		this.price = price;
	}

	@Column(name = "STOCK", nullable = false, length = 11)
	public java.lang.Integer getStock() {
		return this.stock;
	}

	public void setStock(java.lang.Integer stock) {
		this.stock = stock;
	}

	@Column(name = "DESCRIPTION", nullable = true, length = 255)
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	@Column(name = "GOODS_IMG", nullable = true, length = 255)
	public java.lang.String getGoodsImg() {
		return this.goodsImg;
	}

	public void setGoodsImg(java.lang.String goodsImg) {
		this.goodsImg = goodsImg;
	}

	/**
	 * 设置默认值
	 * 
	 * @return
	 */
	public GoodsEntity setDefault() {
		this.stock = ObjectUtils.defaultIfNull(this.stock, 0);

		return this;
	}

	/**
	 * 设置模糊查询
	 * 
	 * @return
	 */
	public GoodsEntity setFuzzy() {
		if (StringUtils.isNotBlank(this.goodsName)) {
			this.goodsName = "*" + this.goodsName.replaceAll("\\*", "") + "*";
		}

		return this;
	}

}
