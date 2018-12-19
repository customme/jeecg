<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="goodsList" checkbox="false" pagination="true" fitColumns="true" 
			actionUrl="goodsController.do?datagrid" idField="id" fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="商品分类" field="goodsClass.className"></t:dgCol>
			<t:dgCol title="图片" field="goodsImg" image="true" imageSize="50,50" align="center" sortable="false"></t:dgCol>
			<t:dgCol title="商品名称" field="goodsName" query="true" queryMode="single"></t:dgCol>
			<t:dgCol title="商品规格" field="goodsSpecs" showLen="30" sortable="false"></t:dgCol>
			<t:dgCol title="进价" field="bid" align="right"></t:dgCol>
			<t:dgCol title="零售价" field="price" align="right"></t:dgCol>
			<t:dgCol title="库存" field="stock" query="true" queryMode="group" align="center"></t:dgCol>
			<t:dgCol title="录入日期" field="createDate" formatter="yyyy-MM-dd" query="true" queryMode="group" align="center"></t:dgCol>
			<t:dgCol title="描述" field="description" showLen="10" sortable="false"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgOpenOpt url="cartController.do?list&goodsId={id}" title="购物车" openModel="OpenTab" urlclass="ace_button" urlfont="fa-th" />
			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgFunOpt operationCode="edit" funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgFunOpt funname="_copy(id)" title="复制" urlclass="ace_button" urlfont="fa-copy" />
			<t:dgDelOpt operationCode="delete" title="删除" url="goodsController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="goodsController.do?goAdd" funname="add" height="500"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<div id="classSearch" style="display: none">
	<span>
		<span title="商品分类" style="display: inline-block; width: 80px; text-align: right;">商品分类：</span>
		<input name="classId" id="goodsClass" />
	</span>
</div>
<script type="text/javascript">
	$(function() {
		var datagrid = $("#goodsListtb");
		datagrid.find("div[name='searchColums']").find("form#goodsListForm span:first span:first").before($("#classSearch").html());
		$("#classSearch").html('');
		$('#goodsClass').combotree({
			url : 'goodsClassController.do?getComboTreeData',
			width : '140',
			multiple : false,
			onlyLeafCheck : false,
			onLoadSuccess : function() {
				$('#goodsClass').combotree('tree')
			}
		});
	});

	//详情
	function _detail(id) {
		createdetailwindow("详情", 'goodsController.do?goUpdate&load=detail&id=' + id, null, 500);
	}

	//编辑
	function _edit(id) {
		createwindow("编辑", 'goodsController.do?goUpdate&id=' + id, null, 500);
	}

	//复制
	function _copy(id) {
		createwindow("复制", 'goodsController.do?goCopy&id=' + id, null, 500);
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("goodsController.do?exportXls", "goodsList");
	}
</script>
<style>
span.combo {
	padding: 2px 0 2px 5px;
}
</style>