<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="goodsClassList" checkbox="false" pagination="false" treegrid="true" treeField="className" fitColumns="true"
			actionUrl="goodsClassController.do?datagrid" idField="id" fit="true">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="分类名称" field="className"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt operationCode="edit" funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgDelOpt operationCode="delete" title="删除" url="goodsClassController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="goodsClassController.do?goAdd" funname="add" height="300" width="500"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(
		function() {
			$("#goodsClassList").treegrid(
				{
					onExpand : function(row) {
						var children = $("#goodsClassList").treegrid('getChildren', row.id);
						if (children.length <= 0) {
							row.leaf = true;
							$("#goodsClassList").treegrid('refresh', row.id);
						}
					}
				});
	});

	//编辑
	function _edit(id) {
		createwindow("编辑", 'goodsClassController.do?goUpdate&id=' + id, 500, 300);
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("goodsClassController.do?exportXls", "goodsClassList");
	}

	/**
	 * 获取表格对象
	 * @return 表格对象
	 */
	function getDataGrid() {
		var datagrid = $('#' + gridname);
		return datagrid;
	}
</script>