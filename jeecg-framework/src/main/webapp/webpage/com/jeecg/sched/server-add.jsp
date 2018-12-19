<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>调度服务器</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="serverController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${serverPage.id }" />
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">名称: </label>
				</td>
				<td class="value">
					<input id="name" name="name" type="text" style="width:250px" />
					<span class="Validform_checktip">默认为IP地址</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">所属集群: </label>
				</td>
				<td class="value">
					<t:dictSelect field="clusterId" type="list" datatype="n" dictTable="t_cluster" dictField="id"
						dictText="name" title="所属集群"></t:dictSelect>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">IP地址: </label>
				</td>
				<td class="value">
					<input id="ip" name="ip" type="text" datatype="*" ignore="checked" />
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">主机名: </label>
				</td>
				<td class="value">
					<input id="hostname" name="hostname" type="text" datatype="*0-64" ignore="ignore" />
					<span class="Validform_checktip">最多64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">操作系统: </label>
				</td>
				<td class="value">
					<input id="os" name="os" type="text" datatype="*0-64" ignore="ignore" />
					<span class="Validform_checktip">最多64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">最大并行数: </label>
				</td>
				<td class="value">
					<input id="taskMaximum" name="taskMaximum" type="text" datatype="n" ignore="ignore" />
					<span class="Validform_checktip">默认为"5"个</span>
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