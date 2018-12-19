<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="shopList" checkbox="false" pagination="true" fitColumns="true" 
			actionUrl="shopController.do?datagrid" idField="id" fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="商铺名称" field="shopName" query="true" queryMode="single"></t:dgCol>
			<t:dgCol title="商业圈" field="tradeArea" query="true" queryMode="single"></t:dgCol>
			<t:dgCol title="详细地址" field="address" sortable="false"></t:dgCol>
			<t:dgCol title="交通信息" field="trafficInfo" showLen="30" sortable="false"></t:dgCol>
			<t:dgCol title="联系人" field="linkman"></t:dgCol>
			<t:dgCol title="联系方式" field="linkway"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt operationCode="edit" funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgDelOpt operationCode="delete" title="删除" url="shopController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="shopController.do?goAdd" funname="add" height="350"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	//编辑
	function _edit(id) {
		createwindow("编辑", 'shopController.do?goUpdate&id=' + id, null, 350);
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("shopController.do?exportXls", "shopList");
	}
</script>