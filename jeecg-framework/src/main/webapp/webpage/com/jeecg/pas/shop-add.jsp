<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>商铺</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="shopController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${shopPage.id }" />
		<table class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">商铺名称: </label>
				</td>
				<td class="value">
					<input id="shopName" name="shopName" type="text" datatype="*" ignore="checked" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">商铺名称</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">商圈: </label>
				</td>
				<td class="value">
					<input id="tradeArea" name="tradeArea" type="text" datatype="*" ignore="checked" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">商圈</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">地址: </label>
				</td>
				<td class="value">
					<input id="address" name="address" type="text" ignore="ignore" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">地址</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">联系人: </label>
				</td>
				<td class="value">
					<input id="linkman" name="linkman" type="text" ignore="ignore" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">联系人</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">联系方式: </label>
				</td>
				<td class="value">
					<input id="linkway" name="linkway" type="text" ignore="ignore" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">联系方式</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">交通信息: </label>
				</td>
				<td class="value">
					<textarea id="trafficInfo" name="trafficInfo"></textarea>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/pas/common.js"></script>
<script src="webpage/com/jeecg/pas/shop.js"></script>