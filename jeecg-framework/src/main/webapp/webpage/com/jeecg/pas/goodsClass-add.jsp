<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>商品分类</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="goodsClassController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${goodsClassPage.id }" />
		<table class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">分类名称: </label>
				</td>
				<td class="value">
					<input id="className" name="className" type="text" class="inputxt" datatype="*" ignore="checked" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">分类名称</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">父分类: </label>
				</td>
				<td class="value">
					<t:comboTree id="pid" name="parent.id" url="goodsClassController.do?getComboTreeData" onlyLeafCheck="true" width="157"></t:comboTree>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/pas/goodsClass.js"></script>