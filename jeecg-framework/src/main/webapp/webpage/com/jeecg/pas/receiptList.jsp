<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="receiptList" checkbox="false" pagination="true" fitColumns="true" 
			actionUrl="receiptController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="buyTime" sortOrder="desc">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="商铺" field="shopId" query="true" queryMode="single" dictionary="ps_shop,id,shop_name"></t:dgCol>
			<t:dgCol title="总金额" field="totalAmount" align="right"></t:dgCol>
			<t:dgCol title="优惠金额" field="discountAmount" align="right"></t:dgCol>
			<t:dgCol title="实际支付" field="actualAmount" align="right"></t:dgCol>
			<t:dgCol title="汇率" field="exchangeRate" align="right" sortable="false"></t:dgCol>
			<t:dgCol title="购买时间" field="buyTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
			<t:dgCol title="支付方式" field="payMode" query="true" queryMode="single" dictionary="pay_mode" align="center" extend="{style:'width:70px'}"></t:dgCol>
			<t:dgCol title="支付币种" field="currency" dictionary="currency" align="center"></t:dgCol>
			<t:dgCol title="备注" field="remark" showLen="10" sortable="false"></t:dgCol>
			<t:dgCol title="明细数" field="itemCount" align="center" sortable="false"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt funname="receipt_item(id)" title="小票明细" urlclass="ace_button" urlfont="fa-plus" />
			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgFunOpt funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgDelOpt title="删除" url="receiptController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="receiptController.do?goAdd" funname="add" height="420"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	//详情
	function _detail(id) {
		createdetailwindow("详情", 'receiptController.do?goUpdate&load=detail&id=' + id, null, 420);
	}

	//编辑
	function _edit(id) {
		createwindow("编辑", 'receiptController.do?goUpdate&id=' + id, null, 420);
	}

	//小票明细
	function receipt_item(id) {
		createdetailwindow('小票明细', 'receiptItemController.do?list&receiptId=' + id, 800, 400);
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("receiptController.do?exportXls", "receiptList");
	}
</script>