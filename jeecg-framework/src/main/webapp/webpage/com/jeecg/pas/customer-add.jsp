<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>客户</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!-- 联动 -->
<script src="plug-in/jquery/jquery.regionselect.js" type="text/javascript"></script>
<script type="text/javascript">
  $(function() {
		//省市区下拉
	 	$("#province").regionselect({url: '<%=basePath%>/jeecgFormDemoController.do?regionSelect'});
	});
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="customerController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${customerPage.id }" />
		<table class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">姓名: </label>
				</td>
				<td class="value">
					<input id="realname" name="realname" type="text" datatype="*" ignore="checked" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">姓名</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">昵称: </label>
				</td>
				<td class="value">
					<input id="nickname" name="nickname" type="text" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">性别: </label>
				</td>
				<td class="value">
					<t:dictSelect id="sex" field="sex" type="radio" typeGroupCode="sex" defaultVal="1" title="性别"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">性别</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">生日: </label>
				</td>
				<td class="value">
					<input id="birthday" name="birthday" type="text" class="Wdate" onClick="WdatePicker()" ignore="ignore" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">生日</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">手机号码: </label>
				</td>
				<td class="value">
					<input id="mobile" name="mobile" type="text" datatype="m" ignore="ignore" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">手机号码</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">所在城市: </label>
				</td>
				<td class="value">
					<input type="text" id="province" value="" />
					<input type="text" id="city" value="" />
					<input type="text" id="area" value="" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">详细地址: </label>
				</td>
				<td class="value">
					<input id="address" name="address" type="text" style="width:350px" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">微信号: </label>
				</td>
				<td class="value">
					<input id="wetchat" name="wetchat" type="text" />
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">客户等级: </label>
				</td>
				<td class="value">
					<t:dictSelect id="customerClass" field="customerClass" type="list" dictTable="ps_customer_class" dictField="id" dictText="class_name"
						defaultVal="1" extendJson="{ignore:'checked'}" title="客户等级"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">备注: </label>
				</td>
				<td class="value">
					<textarea id="remark" name="remark"></textarea>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/pas/common.js"></script>