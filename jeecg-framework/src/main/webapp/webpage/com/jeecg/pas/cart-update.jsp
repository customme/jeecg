<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>购物车</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="cartController.do?doUpdate">
		<input id="id" name="id" type="hidden" value="${cartPage.id }" />
		<table class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">客户姓名: </label>
				</td>
				<td class="value">
					<input type="hidden" name="customer.id" value='${cartPage.customer.id}' />
					<input type="text" value='${cartPage.customer.realname}' readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">商品名称: </label>
				</td>
				<td class="value">
					<input type="hidden" name="goods.id" value='${cartPage.goods.id}' />
					<input type="text" value='${cartPage.goods.goodsName}' readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">数量: </label>
				</td>
				<td class="value">
					<input id="quantity" name="quantity" type="text" datatype="n" ignore="checked" value='${cartPage.quantity}' />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">订单状态: </label>
				</td>
				<td class="value">
					<t:dictSelect id="buyStatus" field="buyStatus" type="list" typeGroupCode="buy_status"
						defaultVal="${cartPage.buyStatus}" extendJson="{ignore:'checked'}" title="购买状态"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">购买日期: </label>
				</td>
				<td class="value">
					<input id="buyTime" name="buyTime" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						value='<fmt:formatDate value='${cartPage.buyTime}' type="date" pattern="yyyy-MM-dd"/>' />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">备注: </label>
				</td>
				<td class="value">
					<textarea id="remark" name="remark">${cartPage.remark}</textarea>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/pas/common.js"></script>
<script type="text/javascript">
	$(function() {
		$("#buyStatus").change(function() {
			if ($(this).val() == 2 && $("#buyTime").val() == "") {
				var sysDate = new Date();
				var theMonth = sysDate.getMonth() + 1;
				if(theMonth < 10) theMonth = "0" + theMonth;
				$("#buyTime").val(sysDate.getFullYear() + "-" + theMonth + "-" + sysDate.getDate());
			}
		})
	});
</script>