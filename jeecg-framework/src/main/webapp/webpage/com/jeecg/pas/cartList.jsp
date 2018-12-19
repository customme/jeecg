<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="cartList" checkbox="false" pagination="true" fitColumns="true"
			actionUrl="cartController.do?datagrid" idField="id" fit="true" queryMode="group"
			extendParams="queryParams:{customerIds:${customer.id},goodsIds:${goods.id}}">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="客户姓名" field="customer.realname" frozenColumn="true"></t:dgCol>
			<t:dgCol title="商品分类" field="goods.goodsClass.className" sortable="false"></t:dgCol>
			<t:dgCol title="商品图片" field="goods.goodsImg" image="true" imageSize="50,50" align="center" sortable="false"></t:dgCol>
			<t:dgCol title="商品名称" field="goods.goodsName"></t:dgCol>
			<t:dgCol title="商品规格" field="goods.goodsSpecs" sortable="false"></t:dgCol>
			<t:dgCol title="零售价" field="goods.price" align="center"></t:dgCol>
			<t:dgCol title="库存" field="goods.stock" align="center"></t:dgCol>
			<t:dgCol title="数量" field="quantity" align="center"></t:dgCol>
			<t:dgCol title="购买状态" field="buyStatus" query="true" queryMode="single" dictionary="buy_status" align="center" extend="{style:'width:70px'}"></t:dgCol>
			<t:dgCol title="购买日期" field="buyTime" formatter="yyyy-MM-dd" align="center"></t:dgCol>
			<t:dgCol title="备注" field="remark" showLen="10" sortable="false"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgConfOpt title="已购买" url="cartController.do?setBought&id={id}" exp="buyStatus#eq#1" message="确认已购买该商品" urlclass="ace_button" urlfont="fa-thumbs-up" ></t:dgConfOpt>
			<t:dgFunOpt title="编辑" funname="_edit(id)" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgDelOpt title="删除" url="cartController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<div id="customerSearch" style="display: none">
	<span>
		<span title="客户" style="display: inline-block; width: 60px; text-align: right;">客户：</span>
		<input type="hidden" name="customerIds" value="${customer.id}" />
		<input name="realnames" type="text" onclick="popupClick(this,'id,realname','customerIds,realnames','customer_list')" value="${customer.realname}" />
	</span>
</div>
<div id="goodsSearch" style="display: none">
	<span>
		<span title="商品" style="display: inline-block; width: 60px; text-align: right;">商品：</span>
		<input type="hidden" name="goodsIds" value="${goods.id}" />
		<input name="goodsNames" type="text" onclick="popupClick(this,'id,goods_name','goodsIds,goodsNames','goods_list')" value="${goods.goodsName}" />
	</span>
</div>
<script type="text/javascript">
	$(function() {
		var datagrid = $("#cartListtb");
		//客户搜索
		datagrid.find("div[name='searchColums']").find("form#cartListForm span:first span:first").before($("#customerSearch").html());
		$("#customerSearch").html('');
		//商品搜索
		datagrid.find("div[name='searchColums']").find("form#cartListForm span:first span:first").after($("#goodsSearch").html());
		$("#goodsSearch").html('');
	});
</script>
<script type="text/javascript">
	//编辑
	function _edit(id) {
		createwindow("编辑", 'cartController.do?goUpdate&id=' + id, null, 350);
	}
	//导出
	function ExportXls() {
		JeecgExcelExport("cartController.do?exportXls", "cartList");
	}
</script>