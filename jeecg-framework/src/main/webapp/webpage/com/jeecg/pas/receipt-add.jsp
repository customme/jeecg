<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="receiptController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${receiptPage.id }" />
		<table class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">商铺: </label>
				</td>
				<td class="value">
					<t:dictSelect field="shopId" type="list" datatype="n" dictTable="ps_shop" dictField="id"
						dictText="shop_name" defaultVal="${receiptPage.shopId}" title="商铺"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">商铺</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">总金额: </label>
				</td>
				<td class="value">
					<input id="totalAmount" name="totalAmount" type="text" datatype="/^(-?\d+)(\.\d+)?$/" ignore="checked" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">总金额</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">优惠金额: </label>
				</td>
				<td class="value">
					<input id="discountAmount" name="discountAmount" type="text" datatype="/^(-?\d+)(\.\d+)?$/" ignore="ignore" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">优惠金额</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">支付币种: </label>
				</td>
				<td class="value">
					<t:dictSelect id="currency" field="currency" type="list" typeGroupCode="currency"
						defaultVal="1" extendJson="{ignore:'checked'}" title="币种"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">币种</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">支付方式: </label>
				</td>
				<td class="value">
					<t:dictSelect id="payMode" field="payMode" type="list" typeGroupCode="pay_mode"
						defaultVal="1" extendJson="{ignore:'checked'}" title="支付方式"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">支付方式</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">购买时间: </label>
				</td>
				<td class="value">
					<input id="buyTime" name="buyTime" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="*" ignore="checked" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">购买时间</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">实付(人民币): </label>
				</td>
				<td class="value">
					<input id="actualAmount" name="actualAmount" type="text" datatype="/^(-?\d+)(\.\d+)?$/" ignore="ignore" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">实付(人民币)</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">备注: </label>
				</td>
				<td class="value">
					<textarea id="remark" name="remark"></textarea>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/pas/common.js"></script>
<script src="webpage/com/jeecg/pas/receipt.js"></script>