<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="taskPoolList" checkbox="false" pagination="true" pageSize="20" fitColumns="true"
			actionUrl="taskPoolController.do?datagrid" idField="id" fit="true" queryMode="group" 
			sortName="runTime,taskState,priority" sortOrder="desc,asc,asc" extendParams="queryParams:{taskIds:${task.id }}">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="任务ID" field="task.id" align="center" frozenColumn="true"></t:dgCol>
			<t:dgCol title="任务名称" field="task.name" frozenColumn="true"></t:dgCol>
			<t:dgCol title="任务类型" field="task.typeId" query="true" queryMode="single" dictionary="t_task_type,id,code"></t:dgCol>
			<t:dgCol title="运行时间" field="runTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"></t:dgCol>
			<t:dgCol title="任务状态" field="taskState" query="true" queryMode="single" dictionary="task_state" align="center" extend="{style:'width:80px'}"></t:dgCol>
			<t:dgCol title="优先级" field="priority" align="center"></t:dgCol>
			<t:dgCol title="最多尝试次数" field="maxTryTimes" align="center"></t:dgCol>
			<t:dgCol title="已经尝试次数" field="triedTimes" align="center"></t:dgCol>
			<t:dgCol title="是否重做" field="redoFlag" dictionary="yorn" align="center"></t:dgCol>
			<t:dgCol title="运行服务器" field="runServer" dictionary="t_server,id,ip"></t:dgCol>
			<t:dgCol title="运行时参数" field="runParams" hidden="true"></t:dgCol>
			<t:dgCol title="开始时间" field="startTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
			<t:dgCol title="结束时间" field="endTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
			<t:dgCol title="耗时(秒)" field="consumeTime" align="center" sortable="false"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgFunOpt funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgOpenOpt url="taskLogController.do?list&taskId={['task.id']}&runTime={runTime}" title="任务日志" openModel="OpenTab" urlclass="ace_button" urlfont="fa-eye" />
			<t:dgFunOpt funname="_copy(id)" title="复制" urlclass="ace_button" urlfont="fa-copy" />
			<t:dgDelOpt title="删除" url="taskPoolController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="taskPoolController.do?goAdd" funname="add" height="400"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<div id="taskSearch" style="display:none">
	<span>
		<span title="任务" style="display:inline-block;width:60px;text-align:right;">任务：</span>
		<input type="hidden" name="taskIds" value="${task.id }" />
		<input name="taskNames" type="text" onclick="popupClick(this,'id,name','taskIds,taskNames','task_list')" value="${task.name }" />
	</span>
</div>
<script type="text/javascript">
	$(function() {
		var datagrid = $("#taskPoolListtb");
		datagrid.find("div[name='searchColums']").find("form#taskPoolListForm span:first span:first").before($("#taskSearch").html());
		$("#taskSearch").html('');
	});

	//详情
	function _detail(id) {
		createdetailwindow("详情", 'taskPoolController.do?goDetail&load=detail&id=' + id, null, 470);
	}

	//编辑
	function _edit(id) {
		createwindow("编辑", 'taskPoolController.do?goUpdate&id=' + id, null, 400);
	}

	//复制
	function _copy(id) {
		createwindow("复制", 'taskPoolController.do?goCopy&id=' + id, null, 400);
	}
</script>