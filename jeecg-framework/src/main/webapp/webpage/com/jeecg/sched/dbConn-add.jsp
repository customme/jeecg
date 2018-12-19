<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>数据库连接</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="dbConnController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${dbConnPage.id }" />
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">名称: </label>
				</td>
				<td class="value">
					<input id="name" name="name" type="text" style="width:250px" />
					<span class="Validform_checktip">默认为"主机名+数据库名"</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">数据库类型: </label>
				</td>
				<td class="value">
					<t:dictSelect field="typeId" type="list" datatype="n" dictTable="t_db_type" dictField="id"
						dictText="code" title="数据库类型"></t:dictSelect>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">主机名: </label>
				</td>
				<td class="value">
					<input id="hostname" name="hostname" type="text" datatype="*1-64" ignore="checked" />
					<span class="Validform_checktip">范围为1-64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">端口号: </label>
				</td>
				<td class="value">
					<input id="port" name="port" type="text" datatype="n" ignore="ignore" />
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">数据库名: </label>
				</td>
				<td class="value">
					<input id="dbName" name="dbName" type="text" datatype="s1-64" ignore="checked" />
					<span class="Validform_checktip">范围为1-64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">用户名: </label>
				</td>
				<td class="value">
					<input id="username" name="username" type="text" datatype="s1-64" ignore="checked" />
					<span class="Validform_checktip">范围为1-64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">密码: </label>
				</td>
				<td class="value">
					<input id="password" name="password" type="text" datatype="*0-255" ignore="ignore" />
					<span class="Validform_checktip">最多255位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">数据库编码: </label>
				</td>
				<td class="value">
					<input id="charset" name="charset" type="text" datatype="*0-32" ignore="ignore" />
					<span class="Validform_checktip">最多32位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">连接方式: </label>
				</td>
				<td class="value">
					<t:dictSelect id="connType" field="connType" type="list" typeGroupCode="conn_type"
						defaultVal="0" extendJson="{ignore:'checked'}" title="连接方式"></t:dictSelect>
					<span class="Validform_checktip"></span>
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