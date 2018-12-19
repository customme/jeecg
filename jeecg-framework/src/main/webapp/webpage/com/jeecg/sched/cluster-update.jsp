<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>集群</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="clusterController.do?doUpdate">
		<input id="id" name="id" type="hidden" value="${clusterPage.id }" />
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">集群名称: </label>
				</td>
				<td class="value">
					<input id="name" name="name" type="text" datatype="*2-64" ignore="checked" value='${clusterPage.name}' style="width:200px" />
					<span class="Validform_checktip">范围为2-64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">描述: </label>
				</td>
				<td class="value">
					<textarea id="description" name="description" datatype="*0-255" ignore="ignore">${clusterPage.description}</textarea>
					<span class="Validform_checktip">最多255位字符</span>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/sched/common.js"></script>