<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="serverList" checkbox="false" pagination="true" fitColumns="true" 
			actionUrl="serverController.do?datagrid" idField="id" fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="名称" field="name" query="true" queryMode="single"></t:dgCol>
			<t:dgCol title="所属集群" field="clusterId" query="true" queryMode="single" dictionary="t_cluster,id,name"></t:dgCol>
			<t:dgCol title="IP地址" field="ip"></t:dgCol>
			<t:dgCol title="主机名" field="hostname"></t:dgCol>
			<t:dgCol title="操作系统" field="os" query="true" queryMode="single"></t:dgCol>
			<t:dgCol title="最大任务并行数" field="taskMaximum" align="center"></t:dgCol>
			<t:dgCol title="心跳时间" field="beatTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"></t:dgCol>
			<t:dgCol title="描述" field="description" hidden="true"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgFunOpt funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgFunOpt funname="_copy(id)" title="复制" urlclass="ace_button" urlfont="fa-copy" />
			<t:dgDelOpt title="删除" url="serverController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="serverController.do?goAdd" funname="add" height="390"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	//详情
	function _detail(id) {
		createdetailwindow("详情", 'serverController.do?goUpdate&load=detail&id=' + id, null, 390);
	}

	//编辑
	function _edit(id) {
		createwindow("编辑", 'serverController.do?goUpdate&id=' + id, null, 390);
	}

	//复制
	function _copy(id) {
		createwindow("复制", 'serverController.do?goCopy&id=' + id, null, 390);
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("serverController.do?exportXls", "serverList");
	}
</script>