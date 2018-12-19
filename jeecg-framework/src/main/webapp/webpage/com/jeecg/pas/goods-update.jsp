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
	<t:formvalid formid="formobj" dialog="true" layout="table" action="goodsController.do?doUpdate" callback="jeecgFormFileCallBack@Override">
		<input id="id" name="id" type="hidden" value="${goodsPage.id }" />
		<table>
			<tr>
				<td align="right">
					<label class="Validform_label">商品分类: </label>
				</td>
				<td class="value">
					<t:comboTree url="goodsClassController.do?getComboTreeData" value='${goodsPage.goodsClass.id}' id="goodsClass" name="goodsClass.id" onlyLeafCheck="true" width="170"></t:comboTree>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">商品名称: </label>
				</td>
				<td class="value">
					<input id="goodsName" name="goodsName" type="text" datatype="*" ignore="checked" value='${goodsPage.goodsName}' />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">商品规格: </label>
				</td>
				<td class="value">
					<input id="goodsSpecs" name="goodsSpecs" type="text" datatype="*" ignore="checked" value='${goodsPage.goodsSpecs}' />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">商品规格</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">进价: </label>
				</td>
				<td class="value">
					<input id="bid" name="bid" type="text" datatype="/^(-?\d+)(\.\d+)?$/" ignore="checked" value='${goodsPage.bid}' />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">进价</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">零售价: </label>
				</td>
				<td class="value">
					<input id="price" name="price" type="text" datatype="/^(-?\d+)(\.\d+)?$/" ignore="checked" value='${goodsPage.price}' />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">零售价</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">库存: </label>
				</td>
				<td class="value">
					<input id="stock" name="stock" type="text" datatype="n" ignore="ignore" value='${goodsPage.stock}' />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">库存</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">图片: </label>
				</td>
				<td class="value">
					<table id="goods_img_fileTable"></table>
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
					<textarea id="description" name="description">${goodsPage.description}</textarea>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/jeecg/pas/goods.js"></script>
<script type="text/javascript">
	$(function() {
		//加载 已存在的 文件
		var cgFormId = $("input[name='id']").val();
		$.ajax({
			type : "post",
			url : "goodsController.do?getFiles&id=" + cgFormId,
			success : function(data) {
				var arrayFileObj = jQuery.parseJSON(data).obj;
				$.each(
					arrayFileObj,
					function(n, file) {
						var fieldName = file.field.toLowerCase();
						var table = $("#" + fieldName + "_fileTable");
						var tr = $("<tr style=\"height:34px;\"></tr>");
						var title = file.title;
						if (title.length > 15) {
							title = title.substring(0, 12) + "...";
						}
						var td_title = $("<td>" + title + "</td>");
						var td_download = $("<td><a style=\"margin-left:10px;\" href=\"commonController.do?viewFile&fileid=" + file.fileKey
							+ "&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity\" title=\"下载\">下载</a></td>")
						var td_view = $("<td><a style=\"margin-left:10px;\" href=\"javascript:void(0);\" onclick=\"openwindow('预览','commonController.do?openViewFile&fileid=" + file.fileKey
							+ "&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity','fList',700,500)\">预览</a></td>");
						var td_del = $("<td><a style=\"margin-left:10px;\" href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=" + file.fileKey
							+ "',this)\">删除</a></td>");
						tr.appendTo(table);
						td_title.appendTo(tr);
						td_download.appendTo(tr);
						td_view.appendTo(tr);
						td_del.appendTo(tr);
					}
				);
			}
		});
	});

	/**
	 * 删除图片数据资源
	 */
	function del(url, obj) {
		var content = "请问是否要删除该资源";
		var navigatorName = "Microsoft Internet Explorer";
		if (navigator.appName == navigatorName) {
			$.dialog.confirm(content, function() {
				submit(url, obj);
			}, function() {
			});
		} else {
			layer.open({
				title : "提示",
				content : content,
				icon : 7,
				yes : function(index) {
					submit(url, obj);
				},
				btn : [ '确定', '取消' ],
				btn2 : function(index) {
					layer.close(index);
				}
			});
		}
	}

	function submit(url, obj) {
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					tip(msg);
					obj.parentNode.parentNode.parentNode.deleteRow(obj.parentNode.parentNode);
				} else {
					tip(d.msg);
				}
			}
		});
	}

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