<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="cartrowList" checkbox="true" pagination="true" fitColumns="true"
			actionUrl="cartController.do?datagrid&customerIds=${customerId}" idField="id" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true"></t:dgCol>
			<t:dgCol title="客户ID" field="customer.id" hidden="true"></t:dgCol>
			<t:dgCol title="商品ID" field="goods.id" hidden="true" extendParams="editor:'text'"></t:dgCol>
			<t:dgCol title="商品名称" field="goods.goodsName" query="true" queryMode="single" extendParams="editor:{type:'validatebox',options:{required:true}}" width="150"></t:dgCol>
			<t:dgCol title="商品规格" field="goods.goodsSpecs" extendParams="editor:'text'" width="150" showLen="20" sortable="false"></t:dgCol>
			<t:dgCol title="零售价" field="goods.price" extendParams="editor:'text'" width="80" align="right"></t:dgCol>
			<t:dgCol title="数量" field="quantity" extendParams="editor:{type:'numberbox',options:{required:true}}" width="80" align="center"></t:dgCol>
			<t:dgCol title="购买状态" field="buyStatus" query="true" queryMode="single" dictionary="buy_status" extendParams="editor:'combobox'" width="80" align="center" extend="{style:'width:70px'}"></t:dgCol>
			<t:dgCol title="购买日期" field="buyTime" formatter="yyyy-MM-dd" extendParams="editor:'datebox'" width="80" align="center"></t:dgCol>
			<t:dgCol title="备注" field="remark" extendParams="editor:'textarea'" width="150" showLen="20" sortable="false"></t:dgCol>
			<t:dgToolBar operationCode="add" title="录入" icon="icon-add" funname="addRow"></t:dgToolBar>
			<t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" funname="editRow"></t:dgToolBar>
			<t:dgToolBar operationCode="save" title="保存" icon="icon-save" url="cartController.do?saveRows" funname="saveData"></t:dgToolBar>
			<t:dgToolBar operationCode="undo" title="取消编辑" icon="icon-undo" funname="reject"></t:dgToolBar>
			<t:dgToolBar operationCode="delete" title="批量删除" icon="icon-remove" url="cartController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar operationCode="order" title="生成订单" icon="icon-add" url="orderController.do?goAdd&customerId=${customerId}" funname="gen_order"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
    //添加行
	function addRow(title,addurl,gname){
		$('#'+gname).datagrid('appendRow',{quantity:'1',buyStatus:'1'});
		var editIndex = $('#'+gname).datagrid('getRows').length-1;
		//填充customerId
		var params = $('#'+gname).datagrid('options').url.split("&");
		for(var i in params){
			var arr = params[i].split("=");
			if(arr[0] == "customerIds") var customerId = arr[1];
		}
		$('#'+gname).datagrid('updateRow', {index: editIndex, row: {'customer.id':customerId}});
		$('#'+gname).datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);

		//添加name readonly属性
		$('#'+gname).datagrid('getEditor', {index:editIndex,field:'goods.id'}).target.attr('name','goods.id[' + editIndex + ']');

		var goodsName = $('#'+gname).datagrid('getEditor', {index:editIndex,field:'goods.goodsName'});
		$(goodsName.target).attr('name','goods.goodsName[' + editIndex + ']');
		$(goodsName.target).attr('readonly',true);

		var goodsSpecs = $('#'+gname).datagrid('getEditor', {index:editIndex,field:'goods.goodsSpecs'});
		$(goodsSpecs.target).attr('name','goods.goodsSpecs[' + editIndex + ']');
		$(goodsSpecs.target).attr('readonly',true);

		var price = $('#' + gname).datagrid('getEditor', {
			index : editIndex,
			field : 'goods.price'
		});
		$(price.target).attr('name', 'goods.price[' + editIndex + ']');
		$(price.target).attr('readonly', true);

		//绑定事件
		goodsName.target.bind('click', function() {
			popupClick(this, 'id,goods_name,goods_specs,price', 'id[' + editIndex + '],goodsName[' + editIndex + '],goodsSpecs[' + editIndex + '],price[' + editIndex + ']', 'goods_select');
		});
	}

	//保存数据
	function saveData(title, addurl, gname) {
		if (!endEdit(gname))
			return false;
		var rows = $('#' + gname).datagrid("getChanges", "inserted");
		var uprows = $('#' + gname).datagrid("getChanges", "updated");
		rows = rows.concat(uprows);
		if (rows.length <= 0) {
			tip("没有需要保存的数据！")
			return false;
		}
		var result = {};
		for (var i = 0; i < rows.length; i++) {
			for ( var d in rows[i]) {
				result["cartList[" + i + "]." + d] = rows[i][d];
			}
		}
		$.ajax({
			url : "<%=basePath%>/" + addurl,
			type : "post",
			data : result,
			dataType : "json",
			success : function(data) {
				tip(data.msg);
				if (data.success) {
					reloadTable();
				}
			}
		})
	}

	//结束编辑
	function endEdit(gname) {
		var editIndex = $('#' + gname).datagrid('getRows').length - 1;
		for (var i = 0; i <= editIndex; i++) {
			if ($('#' + gname).datagrid('validateRow', i))
				$('#' + gname).datagrid('endEdit', i);
			else
				return false;
		}
		return true;
	}

	//编辑行
	function editRow(title, addurl, gname) {
		var rows = $('#' + gname).datagrid("getChecked");
		if (rows.length == 0) {
			tip("请选择条目");
			return false;
		}
		for (var i = 0; i < rows.length; i++) {
			var index = $('#' + gname).datagrid('getRowIndex', rows[i]);
			$('#' + gname).datagrid('beginEdit', index);

			//添加name readonly属性
			$('#' + gname).datagrid('getEditor', {
				index : index,
				field : 'goods.id'
			}).target.attr('name', 'goods.id[' + index + ']');

			var goodsName = $('#' + gname).datagrid('getEditor', {
				index : index,
				field : 'goods.goodsName'
			});
			$(goodsName.target).attr('name', 'goods.goodsName[' + index + ']');
			$(goodsName.target).attr('readonly', true);

			var goodsSpecs = $('#' + gname).datagrid('getEditor', {
				index : index,
				field : 'goods.goodsSpecs'
			});
			$(goodsSpecs.target).attr('name', 'goods.goodsSpecs[' + index + ']');
			$(goodsSpecs.target).attr('readonly', true);

			var price = $('#' + gname).datagrid('getEditor', {
				index : index,
				field : 'goods.price'
			});
			$(price.target).attr('name', 'goods.price[' + index + ']');
			$(price.target).attr('readonly', true);

			//绑定事件
			goodsName.target.bind('click', function() {
				popupClick(this, 'id,goods_name,goods_specs,price', 'id[' + index + '],goodsName[' + index + '],goodsSpecs[' + index + '],price[' + index + ']', 'goods_select');
			});
		}
	}

	//取消编辑
	function reject(title, addurl, gname) {
		$('#' + gname).datagrid('clearChecked');
		$('#' + gname).datagrid('rejectChanges');
	}

	//生成订单
	function gen_order(title, addurl, gname){
		var rows = $('#' + gname).datagrid("getChecked");
		if (rows.length == 0) {
			tip("请选择条目");
			return false;
		}
		var cartIds = rows[0].id;
		for(var i=1;i<rows.length;i++){
			cartIds += "," + rows[i].id;
		}
		createwindow("生成订单", addurl + "&cartIds=" + cartIds, 760, 600);
	}
</script>