<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="raterowList" checkbox="true" pagination="true" fitColumns="false" 
			actionUrl="rateController.do?datagrid" idField="id" queryMode="group" sortName="updateTime" sortOrder="desc">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="源币种" field="srcCur" query="true" queryMode="single" dictionary="currency" extendParams="editor:'combobox'" width="80" align="center" extend="{style:'width:70px'}"></t:dgCol>
			<t:dgCol title="目标币种" field="tarCur" query="true" queryMode="single" dictionary="currency" extendParams="editor:'combobox'" width="80" align="center" extend="{style:'width:70px'}"></t:dgCol>
			<t:dgCol title="汇率" field="rate" extendParams="editor:{type:'numberbox',options:{required:true,precision:5}}" width="80" align="right"></t:dgCol>
			<t:dgCol title="更新时间" field="updateTime" formatter="yyyy-MM-dd hh:mm:ss" extendParams="editor:{type:'datetimebox',options:{required: true}}" width="150" align="center"></t:dgCol>
			<t:dgToolBar operationCode="add" title="录入" icon="icon-add" funname="addRow"></t:dgToolBar>
			<t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" funname="editRow"></t:dgToolBar>
			<t:dgToolBar operationCode="save" title="保存" icon="icon-save" url="rateController.do?saveRows" funname="saveData"></t:dgToolBar>
			<t:dgToolBar operationCode="undo" title="取消编辑" icon="icon-undo" funname="reject"></t:dgToolBar>
			<t:dgToolBar operationCode="delete" title="批量删除" icon="icon-remove" url="rateController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<div id="dateSearch" style="display: none">
	<span>
		<span title="更新日期" style="display: inline-block; width: 80px; text-align: right;">更新日期：</span>
		<input name="updateTime_begin" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 100px" />~<input name="updateTime_end" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 100px" />
	</span>
</div>
<script src="webpage/com/jeecg/pas/datetimebox.js"></script>
<script type="text/javascript">
	$(function() {
		var datagrid = $("#raterowListtb");
		//日期搜索
		datagrid.find("div[name='searchColums']").find("form#raterowListForm span:first").append($("#dateSearch").html());
		$("#dateSearch").html('');
	});

    //添加行
	function addRow(title,addurl,gname){
		$('#'+gname).datagrid('appendRow',{srcCur:'1',tarCur:'0'});
		var editIndex = $('#'+gname).datagrid('getRows').length-1;
		$('#'+gname).datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
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
				result["rateList["+i+"]."+d]=rows[i][d];
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
		}
	}
	//取消编辑
	function reject(title, addurl, gname) {
		$('#' + gname).datagrid('clearChecked');
		$('#' + gname).datagrid('rejectChanges');
	}
</script>