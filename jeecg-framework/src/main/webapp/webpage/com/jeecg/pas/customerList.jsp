<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="customerList" checkbox="false" pagination="true" fitColumns="true"
			actionUrl="customerController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="realname" sortOrder="asc">
			<t:dgCol title="客户ID" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="姓名" field="realname" query="true" queryMode="single" frozenColumn="true"></t:dgCol>
			<t:dgCol title="昵称" field="nickname" hidden="true"></t:dgCol>
			<t:dgCol title="性别" field="sex" query="true" queryMode="single" dictionary="sex" align="center" extend="{style:'width:50px'}"></t:dgCol>
			<t:dgCol title="生日" field="birthday" hidden="true"></t:dgCol>
			<t:dgCol title="手机号码" field="mobile" align="center"></t:dgCol>
			<t:dgCol title="所在城市" field="region" query="true" queryMode="single"></t:dgCol>
			<t:dgCol title="详细地址" field="address" hidden="true"></t:dgCol>
			<t:dgCol title="微信" field="wetchat"></t:dgCol>
			<t:dgCol title="客户等级" field="customerClass" query="true" queryMode="single" dictionary="ps_customer_class,id,class_name" extend="{style:'width:70px'}"></t:dgCol>
			<t:dgCol title="注册日期" field="createDate" formatter="yyyy-MM-dd" query="true" queryMode="group" align="center"></t:dgCol>
			<t:dgCol title="备注" field="remark" showLen="10" sortable="false"></t:dgCol>
			<t:dgCol title="购物车" field="cartCount" align="center" sortable="false"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt funname="_cart(realname,id)" title="购物车" urlclass="ace_button" urlfont="fa-shopping-cart" />
			<t:dgOpenOpt url="orderController.do?list&customerId={id}" title="订单" openModel="OpenTab" urlclass="ace_button" urlfont="fa-th" />
			<t:dgFunOpt funname="_detail(id)" title="详情" urlclass="ace_button" urlfont="fa-file-o" />
			<t:dgFunOpt funname="_edit(id)" title="编辑" urlclass="ace_button" urlfont="fa-edit" />
			<t:dgDelOpt title="删除" url="customerController.do?doDel&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgToolBar title="录入" icon="icon-add" url="customerController.do?goAdd" funname="add" height="500"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	//详情
	function _detail(id) {
		createdetailwindow("详情", 'customerController.do?goUpdate&load=detail&id=' + id, null, 500);
	}

	//编辑
	function _edit(id) {
		createwindow("编辑", 'customerController.do?goUpdate&id=' + id, null, 500);
	}

	//购物车
	function _cart(realname, id) {
		$.dialog({
			content : 'url:cartController.do?cart&customerId=' + id,
			zIndex : getzIndex(),
			lock : true,
			width : 1000,
			height : 500,
			title : realname + '的购物车',
			opacity : 0.3,
			cache : false,
			ok : function() {
				this.opener.reloadTable();
			},
			cancelVal : '关闭',
			cancel : true
		});
	}

	// 导出
	function ExportXls() {
		JeecgExcelExport("customerController.do?exportXls", "customerList");
	}
</script>