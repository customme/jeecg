<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>任务</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="taskController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${taskPage.id }" />
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">任务名称: </label>
				</td>
				<td class="value">
					<input id="name" name="name" type="text" datatype="*2-64" ignore="checked" />
					<span class="Validform_checktip">范围为2-64位字符</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">任务组: </label>
				</td>
				<td class="value">
					<input id="taskGroup" name="taskGroup" type="text" />
					<span class="Validform_checktip">默认为"default"组</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">任务类型: </label>
				</td>
				<td class="value">
					<t:dictSelect id="typeId" field="typeId" type="list" dictTable="t_task_type" dictField="id"
						dictText="code" defaultVal="1" extendJson="{ignore:'checked'}" title="任务类型"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">任务状态: </label>
				</td>
				<td class="value">
					<t:dictSelect id="taskStatus" field="taskStatus" type="list" typeGroupCode="task_stat"
						defaultVal="0" extendJson="{ignore:'checked'}" title="任务状态"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">任务周期: </label>
				</td>
				<td class="value">
					<t:dictSelect id="taskCycle" field="taskCycle" type="list" typeGroupCode="task_cycle"
						defaultVal="day" extendJson="{ignore:'checked'}" title="任务周期"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">优先级: </label>
				</td>
				<td class="value">
					<input id="priority" name="priority" type="text" datatype="n" ignore="ignore" />
					<span class="Validform_checktip">值为数字，越小优先级越高，默认为"0"</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">最多尝试次数: </label>
				</td>
				<td class="value">
					<input id="maxTryTimes" name="maxTryTimes" type="text" datatype="n" ignore="ignore" />
					<span class="Validform_checktip">默认为"3"次</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">超时时间(秒): </label>
				</td>
				<td class="value">
					<input id="timeout" name="timeout" type="text" datatype="n" ignore="ignore" />
					<span class="Validform_checktip">默认为"1800"秒</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">所属集群: </label>
				</td>
				<td class="value">
					<t:dictSelect id="clusterId" field="clusterId" type="list" datatype="n" dictTable="t_cluster" dictField="id"
						dictText="name" defaultVal="${taskPage.clusterId}" title="所属集群"></t:dictSelect>
					<label style="padding: 20px">服务器: </label>
					<select id="serverId" name="serverId">
						<option selected="selected"></option>
					</select>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">起止时间: </label>
				</td>
				<td class="value">
					<input id="startTime" name="startTime" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="*" ignore="checked" />
					~ <input id="endTime" name="endTime" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" ignore="ignore" />
					<span class="Validform_checktip">截止时间为空，则表示任务永久有效</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">时间串行: </label>
				</td>
				<td class="value">
					<input id="dateSerial" name="dateSerial" type="checkbox" value="1" />
					<span class="Validform_checktip">时间串行表示下一周期的任务必须等上一周期的任务执行成功后才可以执行</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">任务描述: </label>
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
<script src="webpage/com/jeecg/sched/task.js"></script>