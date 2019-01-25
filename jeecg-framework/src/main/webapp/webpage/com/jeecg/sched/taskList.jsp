<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="taskList" checkbox="false" pagination="true" pageSize="20" fitColumns="true" 
			actionUrl="taskController.do?datagrid" idField="id" fit="true" queryMode="group"
			sortName="createDate,updateDate" sortOrder="desc,desc">
			<t:dgCol title="任务ID" field="id" align="center" frozenColumn="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="任务名称" field="name" query="true" queryMode="single" frozenColumn="true"></t:dgCol>
			<t:dgCol title="任务组" field="taskGroup" query="true" queryMode="single" extend="{style:'width:100px'}"></t:dgCol>
			<t:dgCol title="任务类型" field="typeId" query="true" queryMode="single" dictionary="t_task_type,id,code"></t:dgCol>
			<t:dgCol title="任务状态" field="taskStatus" query="true" queryMode="single" dictionary="task_stat" align="center" extend="{style:'width:60px'}"></t:dgCol>
			<t:dgCol title="任务周期" field="taskCycle" query="true" queryMode="single" dictionary="task_cycle" align="center" extend="{style:'width:80px'}"></t:dgCol>
			<t:dgCol title="周期值" field="cycleValue" hidden="true"></t:dgCol>
			<t:dgCol title="优先级" field="priority" hidden="true"></t:dgCol>
			<t:dgCol title="最多尝试次数" field="maxTryTimes" hidden="true"></t:dgCol>
			<t:dgCol title="超时时间" field="timeout" hidden="true"></t:dgCol>
			<t:dgCol title="所属集群" field="clusterId" query="true" queryMode="single" dictionary="t_cluster,id,name"></t:dgCol>
			<t:dgCol title="服务器" field="serverId" dictionary="t_server,id,ip"></t:dgCol>
			<t:dgCol title="开始时间" field="startTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
			<t:dgCol title="结束时间" field="endTime" hidden="true"></t:dgCol>
			<t:dgCol title="首次运行时间" field="firstTime" hidden="true"></t:dgCol>
			<t:dgCol title="任务描述" field="description" hidden="true"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgFunOpt funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgFunOpt funname="task_ext(id)" title="扩展属性" urlclass="ace_button" urlfont="fa-plus" />
			<t:dgFunOpt funname="task_link(id)" title="任务依赖" urlclass="ace_button" urlfont="fa-link" />
			<t:dgOpenOpt url="taskPoolController.do?list&taskId={id}" title="任务实例" openModel="OpenTab" urlclass="ace_button" urlfont="fa-th" />
			<t:dgFunOpt funname="_copy(id)" title="复制" urlclass="ace_button" urlfont="fa-copy" />
			<t:dgDelOpt title="删除" url="taskController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="taskController.do?goAdd" funname="add" height="585"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	//详情
	function _detail(id) {
		createdetailwindow("详情", 'taskController.do?goUpdate&load=detail&id=' + id, null, 585);
	}

	//编辑
	function _edit(id) {
		createwindow("编辑", 'taskController.do?goUpdate&id=' + id, null, 585);
	}

	//扩展属性
	function task_ext(id) {
		createdetailwindow('扩展属性', 'taskExtController.do?list&taskId=' + id, null, 400);
	}

	//任务依赖
	function task_link(id) {
		createdetailwindow('任务依赖', 'taskLinkController.do?list&taskId=' + id, 500, 350);
	}

	//复制
	function _copy(id) {
		createwindow("复制", 'taskController.do?goCopy&id=' + id, null, 630);
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("taskController.do?exportXls", "taskList");
	}
</script>