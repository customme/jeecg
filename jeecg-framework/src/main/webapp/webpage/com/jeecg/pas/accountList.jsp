<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="accountList" checkbox="false" pagination="true" pageSize="30" fitColumns="true" 
			actionUrl="accountController.do?datagrid" idField="id" fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="业务日期" field="bizDate" formatter="yyyy-MM-dd" query="true" queryMode="group" align="center"></t:dgCol>
			<t:dgCol title="商品成本" field="goodsCost" align="right"></t:dgCol>
			<t:dgCol title="其他成本" field="expense" align="right"></t:dgCol>
			<t:dgCol title="应收款" field="receivable" align="right"></t:dgCol>
			<t:dgCol title="实收款" field="received" align="right"></t:dgCol>
			<t:dgCol title="预期利润" field="profit" sortable="false" align="right"></t:dgCol>
			<t:dgCol title="实际利润" field="actualProfit" sortable="false" align="right"></t:dgCol>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>