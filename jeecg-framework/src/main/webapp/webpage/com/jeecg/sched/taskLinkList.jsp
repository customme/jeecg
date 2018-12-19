<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="taskLinkrowList" checkbox="true" pagination="true" fitColumns="true"
			actionUrl="taskLinkController.do?datagrid&task.id=${taskId}" idField="id">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" hidden="true"></t:dgCol>
			<t:dgCol title="任务ID" field="task.id" hidden="true"></t:dgCol>
			<t:dgCol title="依赖任务ID" field="ptask.id" hidden="true" extendParams="editor:'text'"></t:dgCol>
			<t:dgCol title="任务名称" field="ptask.name" extendParams="editor:{type:'validatebox',options:{required:true}}" width="50"></t:dgCol>
			<t:dgCol title="任务组" field="ptask.taskGroup" extendParams="editor:'text'" width="30"></t:dgCol>
			<t:dgCol title="依赖类型" field="linkType" dictionary="link_type" extendParams="editor:'combobox'" width="30"></t:dgCol>
			<t:dgToolBar operationCode="add" title="录入" icon="icon-add" funname="addRow"></t:dgToolBar>
			<t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" funname="editRow"></t:dgToolBar>
			<t:dgToolBar operationCode="save" title="保存" icon="icon-save" url="taskLinkController.do?saveRows" funname="saveData"></t:dgToolBar>
			<t:dgToolBar operationCode="undo" title="取消编辑" icon="icon-undo" funname="reject"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove" url="taskLinkController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
    //添加行
	function addRow(title,addurl,gname){
		$('#'+gname).datagrid('appendRow',{});
		var editIndex = $('#'+gname).datagrid('getRows').length-1;
		//填充taskId
		var params = $('#'+gname).datagrid('options').url.split("&");
		for(var i in params){
			var arr = params[i].split("=");
			if(arr[0] == "task.id") var taskId = arr[1];
		}
		$('#'+gname).datagrid('updateRow', {index: editIndex, row: {'task.id':taskId}});
		$('#'+gname).datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);

		//添加name readonly属性
		$('#'+gname).datagrid('getEditor', {index:editIndex,field:'ptask.id'}).target.attr('name','ptask.id[' + editIndex + ']');

		var taskName = $('#'+gname).datagrid('getEditor', {index:editIndex,field:'ptask.name'});
		$(taskName.target).attr('name','ptask.name[' + editIndex + ']');
		$(taskName.target).attr('readonly',true);

		var taskGroup = $('#'+gname).datagrid('getEditor', {index:editIndex,field:'ptask.taskGroup'});
		$(taskGroup.target).attr('name','ptask.taskGroup[' + editIndex + ']');
		$(taskGroup.target).attr('readonly',true);

		//绑定事件
		taskName.target.bind('click',function(){
			popupClick(this,'id,name,task_group','id[' + editIndex + '],name[' + editIndex + '],taskGroup[' + editIndex + ']','task_select');
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
				result["taskLinkList["+i+"]."+d]=rows[i][d];
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
				field : 'ptask.id'
			}).target.attr('name', 'ptask.id[' + index + ']');

			var taskName = $('#' + gname).datagrid('getEditor', {
				index : index,
				field : 'ptask.name'
			});
			$(taskName.target).attr('name', 'ptask.name[' + index + ']');
			$(taskName.target).attr('readonly', true);

			var taskGroup = $('#' + gname).datagrid('getEditor', {
				index : index,
				field : 'ptask.taskGroup'
			});
			$(taskGroup.target).attr('name', 'ptask.taskGroup[' + index + ']');
			$(taskGroup.target).attr('readonly', true);

			//绑定事件
			taskName.target.bind('click', function() {
				popupClick(this, 'id,name,task_group', 'id[' + index + '],name[' + index + '],taskGroup[' + index + ']', 'task_select');
			});
		}
	}
	//取消编辑
	function reject(title, addurl, gname) {
		$('#' + gname).datagrid('clearChecked');
		$('#' + gname).datagrid('rejectChanges');
	}
</script>