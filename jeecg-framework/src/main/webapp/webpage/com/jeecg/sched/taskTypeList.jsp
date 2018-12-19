<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="taskTypeList" checkbox="false" pagination="true" fitColumns="true"
			actionUrl="taskTypeController.do?datagrid" idField="id" fit="true">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="编码" field="code"></t:dgCol>
			<t:dgCol title="任务执行器" field="taskExecutor"></t:dgCol>
			<t:dgCol title="最多尝试次数" field="maxTryTimes" align="center"></t:dgCol>
			<t:dgCol title="描述" field="description" showLen="100" sortable="false"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgFunOpt funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgDelOpt title="删除" url="taskTypeController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="taskTypeController.do?goAdd" funname="add" height="430"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	//详情
	function _detail(id) {
		createdetailwindow("详情", 'taskTypeController.do?goUpdate&load=detail&id=' + id, null, 430);
	}

	//编辑
	function _edit(id) {
		createwindow("编辑", 'taskTypeController.do?goUpdate&id=' + id, null, 430);
	}
</script>