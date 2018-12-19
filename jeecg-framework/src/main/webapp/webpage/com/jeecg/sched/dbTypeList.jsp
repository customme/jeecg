<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="dbTypeList" checkbox="false" pagination="true" fitColumns="true"
			actionUrl="dbTypeController.do?datagrid" idField="id" fit="true">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="编码" field="code"></t:dgCol>
			<t:dgCol title="默认端口" field="defaultPort"></t:dgCol>
			<t:dgCol title="默认字符集" field="defaultCharset"></t:dgCol>
			<t:dgCol title="描述" field="description" showLen="60" sortable="false"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgFunOpt funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgDelOpt title="删除" url="dbTypeController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="dbTypeController.do?goAdd" funname="add" height="260"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	//详情
	function _detail(id) {
		createdetailwindow("详情", 'dbTypeController.do?goUpdate&load=detail&id=' + id, null, 260);
	}

	//编辑
	function _edit(id) {
		createwindow("编辑", 'dbTypeController.do?goUpdate&id=' + id, null, 260);
	}
</script>