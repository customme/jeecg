<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="orderList" checkbox="false" fitColumns="true"
			actionUrl="orderController.do?datagrid" idField="id" fit="true" queryMode="group"
			isShowSubGrid="true" configId="ps_order" extendParams="queryParams:{customerIds:${customer.id}},subTab:{width:600,height:170}" sortName="createDate" sortOrder="desc">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="客户" field="customer.realname" rowspan="2" frozenColumn="true"></t:dgCol>

			<t:dgCol title="订单信息" colspan="6"></t:dgCol>
			<t:dgCol title="物流信息" colspan="6"></t:dgCol>
			<t:dgCol title="备注" field="remark" rowspan="2" sortable="false" showLen="15"></t:dgCol>
			<t:dgCol title="操作" field="opt" rowspan="2" newColumn="true"></t:dgCol>

			<t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" query="true" queryMode="group" align="center"></t:dgCol>
			<t:dgCol title="应付总额" field="payment" align="right"></t:dgCol>
			<t:dgCol title="运费" field="freight" align="right"></t:dgCol>
			<t:dgCol title="实付" field="paid" align="right"></t:dgCol>
			<t:dgCol title="支付方式" field="payMode" dictionary="pay_mode" align="center"></t:dgCol>
			<t:dgCol title="订单状态" field="orderStatus" query="true" queryMode="single" dictionary="order_stat" align="center" extend="{style:'width:70px'}"></t:dgCol>
			<t:dgCol title="收货人" field="receiver" align="center"></t:dgCol>
			<t:dgCol title="手机号" field="mobile" align="center"></t:dgCol>
			<t:dgCol title="所在地区" field="region"></t:dgCol>
			<t:dgCol title="详细地址" field="address" showLen="15" sortable="false"></t:dgCol>
			<t:dgCol title="运送方式" field="deliverType" dictionary="deliver_type" align="center"></t:dgCol>
			<t:dgCol title="运单号" field="expressNo" query="true" queryMode="single" align="center"></t:dgCol>

			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgFunOpt funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgDelOpt title="删除" url="orderController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
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
<script type="text/javascript">
	$(function() {
		var datagrid = $("#orderListtb");
		//客户搜索
		datagrid.find("div[name='searchColums']").find("form#orderListForm span:first span:first").before($("#customerSearch").html());
		$("#customerSearch").html('');
	});

	//详情
	function _detail(id) {
		createdetailwindow("详情", 'orderController.do?goUpdate&load=detail&id=' + id, 760, 600);
	}

	//编辑
	function _edit(id) {
		createwindow("编辑", 'orderController.do?goUpdate&id=' + id, 760, 600);
	}
</script>