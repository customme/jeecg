<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!-- 联动 -->
<script src="plug-in/jquery/jquery.regionselect.js" type="text/javascript"></script>
</head>
<body style="overflow-x:hidden;">
	<t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="orderController.do?doUpdate">
		<input type="hidden" name="id" value="${orderPage.id}" />
		<input type="hidden" name="customer.id" value="${orderPage.customer.id}" />
		<table class="formtable">
			<tr>
				<td colspan="4" style="padding:4px 0px 4px 0px">
					<label class="Validform_label" style="font-weight:bold">物流信息</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">收货人:</label>
				</td>
				<td class="value">
					<input id="receiver" name="receiver" type="text" datatype="*" ignore="checked" value='${orderPage.receiver}' />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">收货人</label>
				</td>
				<td align="right">
					<label class="Validform_label">手机号:</label>
				</td>
				<td class="value">
					<input id="mobile" name="mobile" type="text" datatype="m" ignore="checked" value='${orderPage.mobile}' />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">手机号</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">所在地区:</label>
				</td>
				<td class="value" colspan="3">
					<input type="text" id="province" value="${orderPage.province}" />
					<input type="text" id="city" value="${orderPage.city}" />
					<input type="text" id="area" value="${orderPage.area}" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">详细地址:</label>
				</td>
				<td class="value" colspan="3">
					<input id="address" name="address" type="text" datatype="*" ignore="checked" value='${orderPage.address}' />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">详细地址</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">运送方式:</label>
				</td>
				<td class="value">
					<t:dictSelect field="deliverType" type="list" typeGroupCode="deliver_type" title="运送方式" defaultVal="${orderPage.deliverType}"></t:dictSelect>
				</td>
				<td align="right">
					<label class="Validform_label">运单号:</label>
				</td>
				<td class="value">
					<input id="expressNo" name="expressNo" type="text" value="${orderPage.expressNo}" />
				</td>
			</tr>
			<tr>
				<td colspan="4" style="padding:4px 0px 4px 0px">
					<label class="Validform_label" style="font-weight:bold">订单信息</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">商品总价:</label>
				</td>
				<td class="value">
					<input id="price" name="price" type="text" readonly="readonly" value="${orderPage.price}" />
				</td>
				<td align="right">
					<label class="Validform_label">运费:</label>
				</td>
				<td class="value">
					<input id="freight" name="freight" type="text" datatype="/^(-?\d+)(\.\d+)?$/" ignore="ignore" value="${orderPage.freight}" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">运费</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">应付:</label>
				</td>
				<td class="value">
					<input id="payment" type="text" readonly="readonly" value="${orderPage.payment}" />
				</td>
				<td align="right">
					<label class="Validform_label">优惠金额:</label>
				</td>
				<td class="value">
					<input id="discountAmount" name="discountAmount" type="text" datatype="/^(-?\d+)(\.\d+)?$/" ignore="ignore" value="${orderPage.discountAmount}" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">优惠金额</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">实付:</label>
				</td>
				<td class="value">
					<input id="paid" name="paid" type="text" datatype="/^(-?\d+)(\.\d+)?$/" ignore="ignore" value="${orderPage.paid}" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">实付</label>
				</td>
				<td align="right">
					<label class="Validform_label">支付方式:</label>
				</td>
				<td class="value">
					<t:dictSelect field="payMode" type="list" typeGroupCode="pay_mode" title="支付方式" defaultVal="${orderPage.payMode}"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">订单状态:</label>
				</td>
				<td class="value">
					<t:dictSelect field="orderStatus" type="list" typeGroupCode="order_stat" defaultVal="${orderPage.orderStatus}" extendJson="{ignore:'checked'}" title="订单状态"></t:dictSelect>
				</td>
				<td align="right">
					<label class="Validform_label">备注:</label>
				</td>
				<td class="value" colspan="3" style="padding-bottom:0px">
					<textarea id="remark" name="remark">${orderPage.remark}</textarea>
				</td>
			</tr>
		</table>
		<div style="width:auto; height:200px;">
			<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
			<div style="width:770px;height:1px;"></div>
			<t:tabs id="orderItemTab" iframe="false" tabPosition="top" fit="false">
				<t:tab href="orderController.do?orderItemList&orderId=${orderPage.id}" icon="icon-search" title="订单明细" id="orderItem"></t:tab>
			</t:tabs>
		</div>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/pas/common.js"></script>
<script type="text/javascript">
	$(function() {
		// 省市区下拉
	 	$("#province").regionselect({url: '<%=basePath%>/jeecgFormDemoController.do?regionSelect'});

		// 订单明细
		$('#orderItemTab').tabs({
			onSelect : function(title) {
				$('#orderItemTab .panel-body').css('width', 'auto');
			}
		});

		// 设置样式
		$("#formobj table").css("width", "739px");
		$("#address").css("width", "300px");
		$("#remark").attr("rows", 2);
		$("#remark").css("width", "300px");
	});
</script>