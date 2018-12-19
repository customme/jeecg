<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
		<t:datagrid name="receiptItemrowList" checkbox="true" pagination="true" fitColumns="true"
			actionUrl="receiptItemController.do?datagrid&receipt.id=${receiptId}" idField="id" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="订单ID" field="receipt.id" hidden="true"></t:dgCol>
			<t:dgCol title="商品ID" field="goods.id" hidden="true" extendParams="editor:'text'"></t:dgCol>
			<t:dgCol title="商品名称" field="goods.goodsName" query="true" queryMode="single" extendParams="editor:{type:'validatebox',options:{required:true}}" width="35"></t:dgCol>
			<t:dgCol title="商品规格" field="goods.goodsSpecs" extendParams="editor:'text'" width="35" showLen="20"></t:dgCol>
			<t:dgCol title="单价" field="unitPrice" extendParams="editor:{type:'numberbox',options:{required:true,precision:2}}" width="15" align="right"></t:dgCol>
			<t:dgCol title="数量" field="quantity" extendParams="editor:{type:'numberbox',options:{required:true}}" width="10" align="center"></t:dgCol>
			<t:dgCol title="总价" field="totalPrice" extendParams="editor:{type:'numberbox',options:{required:true,precision:2}}" width="15" align="right"></t:dgCol>
			<t:dgToolBar operationCode="add" title="录入" icon="icon-add" funname="addRow"></t:dgToolBar>
			<t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" funname="editRow"></t:dgToolBar>
			<t:dgToolBar operationCode="save" title="保存" icon="icon-save" url="receiptItemController.do?saveRows" funname="saveData"></t:dgToolBar>
			<t:dgToolBar operationCode="undo" title="取消编辑" icon="icon-undo" funname="reject"></t:dgToolBar>
			<t:dgToolBar operationCode="delete" title="批量删除" icon="icon-remove" url="receiptItemController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
		</t:datagrid>
	</div>
 </div>
 <script type="text/javascript">
    //添加行
	function addRow(title,addurl,gname){
		$('#'+gname).datagrid('appendRow',{quantity:'1'});
		var editIndex = $('#'+gname).datagrid('getRows').length-1;
		//填充receiptId
		var params = $('#'+gname).datagrid('options').url.split("&");
		for(var i in params){
			var arr = params[i].split("=");
			if(arr[0] == "receipt.id") var receiptId = arr[1];
		}
		$('#'+gname).datagrid('updateRow', {index: editIndex, row: {'receipt.id':receiptId}});
		$('#'+gname).datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);

		//添加name readonly属性
		$('#'+gname).datagrid('getEditor', {index:editIndex,field:'goods.id'}).target.attr('name','goods.id[' + editIndex + ']');

		var goodsName = $('#'+gname).datagrid('getEditor', {index:editIndex,field:'goods.goodsName'});
		$(goodsName.target).attr('name','goods.goodsName[' + editIndex + ']');
		$(goodsName.target).attr('readonly',true);

		var goodsSpecs = $('#'+gname).datagrid('getEditor', {index:editIndex,field:'goods.goodsSpecs'});
		$(goodsSpecs.target).attr('name','goods.goodsSpecs[' + editIndex + ']');
		$(goodsSpecs.target).attr('readonly',true);

		//绑定事件
		goodsName.target.bind('click',function(){
			popupClick(this,'id,goods_name,goods_specs','id[' + editIndex + '],goodsName[' + editIndex + '],goodsSpecs[' + editIndex + ']','goods_select');
		});

		// 自动计算总价
		var quantity = $('#'+gname).datagrid('getEditor', {index:editIndex,field:'quantity'});
		var unitPrice = $('#'+gname).datagrid('getEditor', {index:editIndex,field:'unitPrice'});
		var totalPrice = $('#'+gname).datagrid('getEditor', {index:editIndex,field:'totalPrice'});
		quantity.target.bind('change',function(){
			var _quantity = $(this).val() - 0;
			var _unitPrice = $(unitPrice.target).val() - 0;
			totalPrice.target.numberbox('setValue',(_quantity * _unitPrice).toFixed(2));
		});
		unitPrice.target.bind('change',function(){
			var _unitPrice = $(this).val() - 0;
			var _quantity = $(quantity.target).val() - 0;
			totalPrice.target.numberbox('setValue',(_quantity * _unitPrice).toFixed(2));
		});
	}
	//保存数据
	function saveData(title,addurl,gname){
		if(!endEdit(gname))
			return false;
		var rows=$('#'+gname).datagrid("getChanges","inserted");
		var uprows=$('#'+gname).datagrid("getChanges","updated");
		rows=rows.concat(uprows);
		if(rows.length<=0){
			tip("没有需要保存的数据！")
			return false;
		}
		var result={};
		for(var i=0;i<rows.length;i++){
			for(var d in rows[i]){
				result["receiptItemList["+i+"]."+d]=rows[i][d];
			}
		}
		$.ajax({
			url:"<%=basePath%>/"+addurl,
			type:"post",
			data:result,
			dataType:"json",
			success:function(data){
				tip(data.msg);
				if(data.success){
					reloadTable();
				}
			}
		})
	}
	//结束编辑
	function endEdit(gname){
		var  editIndex = $('#'+gname).datagrid('getRows').length-1;
		for(var i=0;i<=editIndex;i++){
			if($('#'+gname).datagrid('validateRow', i))
				$('#'+gname).datagrid('endEdit', i);
			else
				return false;
		}
		return true;
	}
	//编辑行
	function editRow(title,addurl,gname){
		var rows=$('#'+gname).datagrid("getChecked");
		if(rows.length==0){
			tip("请选择条目");
			return false;
		}
		for(var i=0;i<rows.length;i++){
			var index= $('#'+gname).datagrid('getRowIndex', rows[i]);
			$('#'+gname).datagrid('beginEdit', index);

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

			//绑定事件
			goodsName.target.bind('click', function() {
				popupClick(this, 'id,goods_name,goods_specs', 'id[' + index + '],goodsName[' + index + '],goodsSpecs[' + index + ']', 'goods_select');
			});

			// 自动计算总价
			$('#'+gname).datagrid('getEditor', {index:index,field:'quantity'}).target.bind('change',function(){
				var rowIndex = getRowIndex(this);
				var _quantity = $(this).val() - 0;
				var _unitPrice = $('#'+gname).datagrid('getEditor', {index:rowIndex,field:'unitPrice'}).target.val() - 0;
				var _totalPrice = (_quantity * _unitPrice).toFixed(2);
				$('#'+gname).datagrid('getEditor', {index:rowIndex,field:'totalPrice'}).target.numberbox('setValue',_totalPrice);
			});
			$('#'+gname).datagrid('getEditor', {index:index,field:'unitPrice'}).target.bind('change',function(){
				var rowIndex = getRowIndex(this);
				var _unitPrice = $(this).val() - 0;
				var _quantity = $('#'+gname).datagrid('getEditor', {index:rowIndex,field:'quantity'}).target.val() - 0;
				var _totalPrice = (_quantity * _unitPrice).toFixed(2);
				$('#'+gname).datagrid('getEditor', {index:rowIndex,field:'totalPrice'}).target.numberbox('setValue',_totalPrice);
			});
		}
	}

	// 获取行号
	function getRowIndex(target){
	    var tr =  $(target).closest("tr.datagrid-row");
	    return parseInt(tr.attr("datagrid-row-index"));               
	}

	//取消编辑
	function reject(title,addurl,gname){
		$('#'+gname).datagrid('clearChecked');
		$('#'+gname).datagrid('rejectChanges');
	}
 </script>