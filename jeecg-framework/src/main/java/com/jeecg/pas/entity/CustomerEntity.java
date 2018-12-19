package com.jeecg.pas.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 客户
 * @author onlineGenerator
 * @date 2018-06-01 19:23:26
 * @version V1.0
 *
 */
@Entity
@Table(name = "ps_customer", schema = "")
@SuppressWarnings("serial")
public class CustomerEntity implements java.io.Serializable {

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
	/** 姓名 */
	@Excel(name = "姓名", width = 15)
	private java.lang.String realname;
	/** 昵称 */
	@Excel(name = "昵称", width = 15)
	private java.lang.String nickname;
	/** 性别 */
	@Excel(name = "性别", width = 15, dicCode = "sex")
	private java.lang.Integer sex;
	/** 生日 */
	@Excel(name = "生日", width = 15, format = "yyyy-MM-dd")
	private java.util.Date birthday;
	/** 手机号码 */
	@Excel(name = "手机号码", width = 15)
	private java.lang.String mobile;
	/** 所在城市 */
	@Excel(name = "所在城市", width = 15)
	private java.lang.String region;
	/** 详细地址 */
	@Excel(name = "详细地址", width = 15)
	private java.lang.String address;
	/** 微信号 */
	@Excel(name = "微信号", width = 15)
	private java.lang.String wetchat;
	/** 客户等级 */
	@Excel(name = "客户等级", width = 15, dictTable = "ps_customer_class", dicCode = "id", dicText = "class_name")
	private java.lang.Integer customerClass;
	/** 备注 */
	@Excel(name = "备注", width = 15)
	private java.lang.String remark;

	private List<CartEntity> cartList;

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

	@Column(name = "REALNAME", nullable = false, length = 32)
	public java.lang.String getRealname() {
		return this.realname;
	}

	public void setRealname(java.lang.String realname) {
		this.realname = realname;
	}

	@Column(name = "NICKNAME", nullable = true, length = 32)
	public java.lang.String getNickname() {
		return this.nickname;
	}

	public void setNickname(java.lang.String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "SEX", nullable = false, length = 1)
	public java.lang.Integer getSex() {
		return this.sex;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}

	@Column(name = "BIRTHDAY", nullable = true)
	public java.util.Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "MOBILE", nullable = false, length = 16)
	public java.lang.String getMobile() {
		return this.mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "REGION", nullable = false, length = 64)
	public java.lang.String getRegion() {
		return region;
	}

	public void setRegion(java.lang.String region) {
		this.region = region;
	}

	@Column(name = "ADDRESS", nullable = false, length = 64)
	public java.lang.String getAddress() {
		return this.address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	@Column(name = "WETCHAT", nullable = true, length = 64)
	public java.lang.String getWetchat() {
		return this.wetchat;
	}

	public void setWetchat(java.lang.String wetchat) {
		this.wetchat = wetchat;
	}

	@Column(name = "CUSTOMER_CLASS", nullable = false, length = 4)
	public java.lang.Integer getCustomerClass() {
		return this.customerClass;
	}

	public void setCustomerClass(java.lang.Integer customerClass) {
		this.customerClass = customerClass;
	}

	@Column(name = "REMARK", nullable = true, length = 255)
	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	public List<CartEntity> getCartList() {
		return cartList;
	}

	public void setCartList(List<CartEntity> cartList) {
		this.cartList = cartList;
	}

	/** 省 **/
	private java.lang.String province;
	/** 市 **/
	private java.lang.String city;
	/** 地区 **/
	private java.lang.String area;

	@Transient
	public java.lang.String getProvince() {
		if (StringUtils.isNotBlank(this.region)) {
			String[] arr = this.region.split(" ");
			return arr[0];
		}
		return province;
	}

	public void setProvince(java.lang.String province) {
		this.province = province;
	}

	@Transient
	public java.lang.String getCity() {
		if (StringUtils.isNotBlank(this.region)) {
			String[] arr = this.region.split(" ");
			if (arr.length > 1)
				return arr[1];
		}
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}

	@Transient
	public java.lang.String getArea() {
		if (StringUtils.isNotBlank(this.region)) {
			String[] arr = this.region.split(" ");
			if (arr.length > 2)
				return arr[2];
		}
		return area;
	}

	public void setArea(java.lang.String area) {
		this.area = area;
	}

	/**
	 * 购物车商品数量
	 */
	@Transient
	public int getCartCount() {
		return this.cartList.size();
	}

	/**
	 * 设置默认值
	 * 
	 * @return
	 */
	public CustomerEntity setDefault() {
		if (StringUtils.isNotBlank(this.area)) {
			this.region = this.province + " " + this.city + " " + this.area;
		} else if (StringUtils.isNotBlank(this.city)) {
			this.region = this.province + " " + this.city;
		} else if (StringUtils.isNotBlank(this.province)) {
			this.region = this.province;
		}

		return this;
	}

	/**
	 * 设置模糊查询
	 * 
	 * @return
	 */
	public CustomerEntity setFuzzy() {
		if (StringUtils.isNotBlank(this.realname)) {
			this.realname = "*" + this.realname.replaceAll("\\*", "") + "*";
		}
		if (StringUtils.isNotBlank(this.region)) {
			this.region = "*" + this.region.replaceAll("\\*", "") + "*";
		}

		return this;
	}

}
