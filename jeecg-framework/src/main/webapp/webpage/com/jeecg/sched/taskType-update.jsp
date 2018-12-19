<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>任务类型</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="taskTypeController.do?doUpdate">
		<input id="id" name="id" type="hidden" value="${taskTypePage.id }" />
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">编码: </label>
				</td>
				<td class="value">
					<input id="code" name="code" type="text" validType="t_task_type,code,id" datatype="s4-64" ignore="checked" value='${taskTypePage.code}' />
					<span class="Validform_checktip">范围为4-64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">任务执行器: </label>
				</td>
				<td class="value">
					<input id="taskExecutor" name="taskExecutor" type="text" validType="t_task_type,task_executor,id" datatype="*8-255" ignore="checked" value='${taskTypePage.taskExecutor}' />
					<span class="Validform_checktip">范围为8-255位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">最多尝试次数: </label>
				</td>
				<td class="value">
					<input id="maxTryTimes" name="maxTryTimes" type="text" datatype="n" ignore="ignore" value='${taskTypePage.maxTryTimes}' />
					<span class="Validform_checktip">默认为"3"次</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">描述: </label>
				</td>
				<td class="value">
					<textarea id="description" name="description" datatype="*0-500" ignore="ignore">${taskTypePage.description}</textarea>
					<span class="Validform_checktip">最多500位字符</span>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/sched/common.js"></script>
<script type="text/javascript">
	$(function() {
		// 设置个性化样式
		$("#taskExecutor").css("width", "300px");
		$("#description").attr("rows", "18");
	});
</script>