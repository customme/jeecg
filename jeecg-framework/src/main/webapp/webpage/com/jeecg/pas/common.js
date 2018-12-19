$(function() {
	// 表格样式
	$("#formobj table").css("width", "600px");
	$("#formobj table").attr("cellpadding", "0");
	$("#formobj table").attr("cellspacing", "1");
	// 文本框样式
	$("#formobj input[type='text']").addClass("inputxt");
	// 文本域样式
	$("#formobj textarea").css("width", "560px");
	$("#formobj textarea").attr("rows", "6");
	// 日期控件样式
	$("#formobj input.Wdate").css("min-height", "14px");
	// 去掉下拉列表空值选项
	$("#formobj select[ignore='checked'] option[value='']").each(function() {
		var regex = /^\s*$/;
		if (regex.test($(this).text())) {
			$(this).remove();
		}
	});
});
