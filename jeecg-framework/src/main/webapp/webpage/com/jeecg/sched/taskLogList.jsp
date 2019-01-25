<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="taskLogList" checkbox="false" pagination="true" pageSize="20" fitColumns="true"
			actionUrl="taskLogController.do?datagrid" idField="id" fit="true" queryMode="group"
			extendParams="queryParams:{taskIds:${task.id }}">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="任务ID" field="task.id" align="center" frozenColumn="true"></t:dgCol>
			<t:dgCol title="任务名称" field="task.name" align="center"></t:dgCol>
			<c:choose>
				<c:when test="${not empty runTime }">
					<t:dgCol title="运行时间" field="runTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" defaultVal="${runTime },${runTime }"></t:dgCol>
				</c:when>
				<c:otherwise>
					<t:dgCol title="运行时间" field="runTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"></t:dgCol>
				</c:otherwise>
			</c:choose>
			<t:dgCol title="流水号" field="seqNo"></t:dgCol>
			<t:dgCol title="日志级别" field="level" query="true" queryMode="single" dictionary="log_level" align="center" extend="{style:'width:60px'}"></t:dgCol>
			<t:dgCol title="记录时间" field="logTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
			<t:dgCol title="日志内容" field="content" query="true" queryMode="single" showLen="50" sortable="false"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div><div id="taskSearch" style="display:none">
	<span>
		<span title="任务" style="display:inline-block;width:60px;text-align:right;">任务：</span>
		<input type="hidden" name="taskIds" value="${task.id }" />
		<input name="taskNames" type="text" onclick="popupClick(this,'id,name','taskIds,taskNames','task_list')" value="${task.name }" />
	</span>
</div>
<script type="text/javascript">
	$(function() {
		var datagrid = $("#taskLogListtb");
		datagrid.find("div[name='searchColums']").find("form#taskLogListForm span:first span:first").before($("#taskSearch").html());
		$("#taskSearch").html('');
	});

	//详情
	function _detail(id) {
		createdetailwindow("详情", 'taskLogController.do?goUpdate&load=detail&id=' + id, null, 600);
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("taskLogController.do?exportXls", "taskLogList");
	}
</script>