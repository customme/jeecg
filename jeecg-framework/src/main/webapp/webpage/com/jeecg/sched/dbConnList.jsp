<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="dbConnList" checkbox="false" pagination="true" fitColumns="true"
			actionUrl="dbConnController.do?datagrid" idField="id" fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true" queryMode="single"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="名称" field="name"></t:dgCol>
			<t:dgCol title="数据库名" field="dbName" query="true" queryMode="single"></t:dgCol>
			<t:dgCol title="数据库类型" field="typeId" query="true" queryMode="single" dictionary="t_db_type,id,code" align="center"></t:dgCol>
			<t:dgCol title="连接方式" field="connType" query="true" queryMode="single" dictionary="conn_type" align="center"></t:dgCol>
			<t:dgCol title="用户名" field="username"></t:dgCol>
			<t:dgCol title="密码" field="password"></t:dgCol>
			<t:dgCol title="主机名" field="hostname"></t:dgCol>
			<t:dgCol title="端口号" field="port" query="true" queryMode="single" align="center"></t:dgCol>
			<t:dgCol title="数据库编码" field="charset" align="center"></t:dgCol>
			<t:dgCol title="描述" field="description" hidden="true"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgFunOpt funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgFunOpt funname="_copy(id)" title="复制" urlclass="ace_button" urlfont="fa-copy" />
			<t:dgDelOpt title="删除" url="dbConnController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="dbConnController.do?goAdd" funname="add" height="510"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	//详情
	function _detail(id) {
		createdetailwindow("详情", 'dbConnController.do?goUpdate&load=detail&id=' + id, null, 510);
	}

	//编辑
	function _edit(id) {
		createwindow("编辑", 'dbConnController.do?goUpdate&id=' + id, null, 510);
	}

	//复制
	function _copy(id) {
		createwindow("复制", 'dbConnController.do?goCopy&id=' + id, null, 510);
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("dbConnController.do?exportXls", "dbConnList");
	}
</script>