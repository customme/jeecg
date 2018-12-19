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
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">名称: </label>
				</td>
				<td class="value">
					<input id="name" name="name" type="text" value='${dbConnPage.name} - 副本' style="width:250px" />
					<span class="Validform_checktip">默认为"主机名+数据库名"</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">数据库类型: </label>
				</td>
				<td class="value">
					<t:dictSelect field="typeId" type="list" datatype="n" dictTable="t_db_type" dictField="id"
						dictText="code" defaultVal="${dbConnPage.typeId}" title="数据库类型"></t:dictSelect>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">主机名: </label>
				</td>
				<td class="value">
					<input id="hostname" name="hostname" type="text" datatype="*1-64" ignore="checked" value='${dbConnPage.hostname}' />
					<span class="Validform_checktip">范围为1-64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">端口号: </label>
				</td>
				<td class="value">
					<input id="port" name="port" type="text" datatype="n" ignore="ignore" value='${dbConnPage.port}' />
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">数据库名: </label>
				</td>
				<td class="value">
					<input id="dbName" name="dbName" type="text" datatype="s1-64" ignore="checked" value='${dbConnPage.dbName}' />
					<span class="Validform_checktip">范围为1-64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">用户名: </label>
				</td>
				<td class="value">
					<input id="username" name="username" type="text" datatype="s1-64" ignore="checked" value='${dbConnPage.username}' />
					<span class="Validform_checktip">范围为1-64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">密码: </label>
				</td>
				<td class="value">
					<input id="password" name="password" type="text" datatype="*0-255" ignore="ignore" value='${dbConnPage.password}' />
					<span class="Validform_checktip">最多255位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">数据库编码: </label>
				</td>
				<td class="value">
					<input id="charset" name="charset" type="text" datatype="*0-32" ignore="ignore" value='${dbConnPage.charset}' />
					<span class="Validform_checktip">最多32位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">连接方式: </label>
				</td>
				<td class="value">
					<t:dictSelect id="connType" field="connType" type="list" typeGroupCode="conn_type"
						defaultVal="${dbConnPage.connType}" extendJson="{ignore:'checked'}" title="连接方式"></t:dictSelect>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">描述: </label>
				</td>
				<td class="value">
					<textarea id="description" name="description" datatype="*0-255" ignore="ignore">${dbConnPage.description}</textarea>
					<span class="Validform_checktip">最多255位字符</span>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/sched/common.js"></script>