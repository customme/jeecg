<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>任务日志</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="taskLogController.do?doUpdate">
		<input id="id" name="id" type="hidden" value="${taskLogPage.id }" />
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">任务名称: </label>
				</td>
				<td class="value">
					<input type="text" value='${taskLogPage.task.name}' />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">运行时间: </label>
				</td>
				<td class="value">
					<input type="text" value='${taskLogPage.runTime}' />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">流水号: </label>
				</td>
				<td class="value">
					<input type="text" value='${taskLogPage.seqNo}' />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">日志级别: </label>
				</td>
				<td class="value">
					<t:dictSelect field="level" type="list" typeGroupCode="log_level" defaultVal="${taskLogPage.level}" title="日志级别"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">记录时间: </label>
				</td>
				<td class="value">
					<input type="text" value='${taskLogPage.logTime}' />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">日志内容: </label>
				</td>
				<td class="value">
					<textarea>${taskLogPage.content}</textarea>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/sched/common.js"></script>
<script type="text/javascript">
	$(function() {
		// 设置个性化样式
		$("#formobj textarea").attr("rows", "25");
	});
</script>