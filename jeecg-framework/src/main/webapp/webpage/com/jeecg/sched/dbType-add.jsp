<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>数据库类型</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="dbTypeController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${dbTypePage.id }" />
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">编码: </label>
				</td>
				<td class="value">
					<input id="code" name="code" type="text" validType="t_db_type,code,id" datatype="s1-32" ignore="checked" />
					<span class="Validform_checktip">范围为1-32位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">默认端口: </label>
				</td>
				<td class="value">
					<input id="defaultPort" name="defaultPort" type="text" datatype="n" ignore="ignore" />
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">默认字符集: </label>
				</td>
				<td class="value">
					<input id="defaultCharset" name="defaultCharset" type="text" datatype="*0-32" ignore="ignore" />
					<span class="Validform_checktip">最多32位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">描述: </label>
				</td>
				<td class="value">
					<textarea id="description" name="description" datatype="*0-255" ignore="ignore"></textarea>
					<span class="Validform_checktip">最多255位字符</span>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/sched/common.js"></script>