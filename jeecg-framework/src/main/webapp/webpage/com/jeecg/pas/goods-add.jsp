<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>商品</title>
<t:base type="jquery,easyui,tools"></t:base>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="goodsController.do?doAdd" callback="jeecgFormFileCallBack@Override">
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">商品分类: </label>
				</td>
				<td class="value">
					<t:comboTree id="goodsClass" name="goodsClass.id" url="goodsClassController.do?getComboTreeData" width="170"></t:comboTree>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">商品名称: </label>
				</td>
				<td class="value">
					<input id="goodsName" name="goodsName" type="text" datatype="*" ignore="checked" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">商品规格: </label>
				</td>
				<td class="value">
					<input id="goodsSpecs" name="goodsSpecs" type="text" datatype="*" ignore="checked" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">商品规格</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">进价: </label>
				</td>
				<td class="value">
					<input id="bid" name="bid" type="text" datatype="/^(-?\d+)(\.\d+)?$/" ignore="checked" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">进价</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">零售价: </label>
				</td>
				<td class="value">
					<input id="price" name="price" type="text" datatype="/^(-?\d+)(\.\d+)?$/" ignore="checked" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">零售价</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">库存: </label>
				</td>
				<td class="value">
					<input id="stock" name="stock" type="text" datatype="n" ignore="ignore" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">库存</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">图片: </label>
				</td>
				<td class="value">
					<div class="form jeecgDetail">
						<script type="text/javascript">
							var serverMsg = "";
							$(function() {
								$('#goodsImg').uploadify(
									{
										buttonText : '添加图片',
										auto : false,
										progressData : 'speed',
										multi : true,
										height : 25,
										overrideEvents : [ 'onDialogClose' ],
										fileTypeDesc : '文件格式:',
										queueID : 'filediv_goodsImg',
										fileSizeLimit : '15MB',
										swf : 'plug-in/uploadify/uploadify.swf',
										uploader : 'cgUploadController.do?saveFiles&jsessionid=' + $("#sessionUID").val() + '',
										onUploadStart : function(file) {
											var cgFormId = $("input[name='id']").val();
											$('#goodsImg').uploadify("settings", "formData",
												{
													'cgFormId' : cgFormId,
													'cgFormName' : 'ps_goods',
													'cgFormField' : 'GOODS_IMG'
												}
											);
										},
										onQueueComplete : function(queueData) {
											var win = frameElement.api.opener;
											win.reloadTable();
											win.tip(serverMsg);
											frameElement.api.close();
										},
										onUploadSuccess : function(file, data, response) {
											var d = $.parseJSON(data);
											if (d.success) {
												var win = frameElement.api.opener;
												serverMsg = d.msg;
											}
										},
										onFallback : function() {
											tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
										},
										onSelectError : function(file, errorCode, errorMsg) {
											switch (errorCode) {
											case -100:
												tip("上传的文件数量已经超出系统限制的" + $('#file').uploadify('settings', 'queueSizeLimit') + "个文件！");
												break;
											case -110:
												tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#file').uploadify('settings', 'fileSizeLimit') + "大小！");
												break;
											case -120:
												tip("文件 [" + file.name + "] 大小异常！");
												break;
											case -130:
												tip("文件 [" + file.name + "] 类型不正确！");
												break;
											}
										},
										onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {}
									}
								);
							});
						</script>
						<span id="file_uploadspan">
							<input type="file" name="goodsImg" id="goodsImg" />
						</span>
					</div>
					<div class="form" id="filediv_goodsImg"></div>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">图片</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">描述: </label>
				</td>
				<td class="value">
					<textarea id="description" name="description"></textarea>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/pas/goods.js"></script>
<script type="text/javascript">
	function jeecgFormFileCallBack(data) {
		if (data.success == true) {
			uploadFile(data);
		} else {
			if (data.responseText == '' || data.responseText == undefined) {
				$.messager.alert('错误', data.msg);
				$.Hidemsg();
			} else {
				try {
					var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
					$.messager.alert('错误', emsg);
					$.Hidemsg();
				} catch (ex) {
					$.messager.alert('错误', data.responseText + '');
				}
			}
			return false;
		}
		if (!neibuClickFlag) {
			var win = frameElement.api.opener;
			win.reloadTable();
		}
	}
	function upload() {
		$('#goodsImg').uploadify('upload', '*');
	}

	var neibuClickFlag = false;
	function neibuClick() {
		neibuClickFlag = true;
		$('#btn_sub').trigger('click');
	}
	function cancel() {
		$('#goodsImg').uploadify('cancel', '*');
	}
	function uploadFile(data) {
		if (!$("input[name='id']").val()) {
			if (data.obj != null && data.obj != 'undefined') {
				$("input[name='id']").val(data.obj.id);
			}
		}
		if ($(".uploadify-queue-item").length > 0) {
			upload();
		} else {
			if (neibuClickFlag) {
				alert(data.msg);
				neibuClickFlag = false;
			} else {
				var win = frameElement.api.opener;
				win.reloadTable();
				win.tip(data.msg);
				frameElement.api.close();
			}
		}
	}
</script>