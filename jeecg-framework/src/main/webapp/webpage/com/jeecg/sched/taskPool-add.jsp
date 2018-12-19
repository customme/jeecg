<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>任务实例</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="taskPoolController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${taskPoolPage.id }" />
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">任务名称: </label>
				</td>
				<td class="value">
					<input type="hidden" name="task.id" />
					<input name="task.name" type="text" datatype="*" ignore="checked" readonly="readonly"
						onclick="popupClick(this,'id,name,priority,max_try_times','id,name,priority,maxTryTimes','task_select')" />
					<span class="Validform_checktip">点击文本框选择</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">运行时间: </label>
				</td>
				<td class="value">
					<input id="runTime" name="runTime" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="*" ignore="checked" />
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">任务状态: </label>
				</td>
				<td class="value">
					<t:dictSelect id="taskState" field="taskState" type="list" typeGroupCode="task_state"
						defaultVal="0" extendJson="{ignore:'checked'}" title="任务状态"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">优先级: </label>
				</td>
				<td class="value">
					<input type="hidden" name="task.priority" />
					<input id="priority" name="priority" type="text" datatype="n" ignore="ignore" />
					<span class="Validform_checktip">默认继承自该任务配置</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">最多尝试次数: </label>
				</td>
				<td class="value">
					<input type="hidden" name="task.maxTryTimes" />
					<input id="maxTryTimes" name="maxTryTimes" type="text" datatype="n" ignore="ignore" />
					<span class="Validform_checktip">默认继承自该任务配置</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">是否重做: </label>
				</td>
				<td class="value">
					<t:dictSelect id="redoFlag" field="redoFlag" type="list" typeGroupCode="yorn"
						defaultVal="0" extendJson="{ignore:'checked'}" title="是否重做"></t:dictSelect>
					<span class="Validform_checktip">重做表示该任务执行成功，由于其他原因（例如数据源错误），需要重做</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">运行时参数: </label>
				</td>
				<td class="value">
					<textarea id="runParams" name="runParams" datatype="*0-255" ignore="ignore"></textarea>
					<span class="Validform_checktip">格式为JSON，最多255位字符</span>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/sched/common.js"></script>