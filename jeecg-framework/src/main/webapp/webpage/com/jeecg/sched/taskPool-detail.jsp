<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>任务实例</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="taskPoolController.do?doUpdate">
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">任务:</label>
				</td>
				<td class="value" colspan="3">
					<input type="text" value='${taskPoolPage.task.name}' />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">运行时间:</label>
				</td>
				<td class="value" colspan="3">
					<input type="text" value="<fmt:formatDate value='${taskPoolPage.runTime}' type='date' pattern='yyyy-MM-dd HH:mm:ss'/>" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">任务状态:</label>
				</td>
				<td class="value" colspan="3">
					<t:dictSelect field="taskState" type="list" typeGroupCode="task_state"
						defaultVal="${taskPoolPage.taskState}" extendJson="{ignore:'checked'}"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">优先级:</label>
				</td>
				<td class="value" colspan="3">
					<input type="text" value='${taskPoolPage.priority}' />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">最多尝试次数: </label>
				</td>
				<td class="value">
					<input type="text" value='${taskPoolPage.maxTryTimes}' />
				</td>
				<td align="right">
					<label class="Validform_label">已经尝试次数:</label>
				</td>
				<td class="value">
					<input type="text" value='${taskPoolPage.triedTimes}' />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">是否重做:</label>
				</td>
				<td class="value" colspan="3">
					<t:dictSelect field="redoFlag" type="list" typeGroupCode="yorn"
						defaultVal="${taskPoolPage.redoFlag}" extendJson="{ignore:'checked'}"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">运行服务器: </label>
				</td>
				<td class="value" colspan="3">
					<t:dictSelect field="runServer" type="select" dictTable="t_server" dictField="id" dictText="ip" defaultVal="${taskPoolPage.runServer}"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">开始时间:</label>
				</td>
				<td class="value">
					<input type="text" value='<fmt:formatDate value='${taskPoolPage.startTime}' type='date' pattern='yyyy-MM-dd HH:mm:ss'/>' />
				</td>
				<td align="right">
					<label class="Validform_label">结束时间:</label>
				</td>
				<td class="value">
					<input type="text" value='<fmt:formatDate value='${taskPoolPage.endTime}' type='date' pattern='yyyy-MM-dd HH:mm:ss'/>' />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">运行时参数: </label>
				</td>
				<td class="value" colspan="3">
					<textarea>${taskPoolPage.runParams}</textarea>
					<span class="Validform_checktip">格式为JSON，最多255位字符</span>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/sched/common.js"></script>