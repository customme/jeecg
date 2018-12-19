$.extend($.fn.datagrid.defaults.editors, {
	label : {
		init : function(container, options) {
			var input = $('<div></div>').appendTo(container);
			return input;
		},
		destroy : function(target) {

		},
		getValue : function(target) {
			return $(target).html();
		},
		setValue : function(target, value) {
			$(target).html(value);
		},
		resize : function(target, width) {

		}
	},
	// 扩展dategrid的editors的类型
	datetimebox : {
		init : function(container, options) {
			var input = $('<input type="text" class="easyui-datetimebox">')
					.appendTo(container);
			// 编辑框延迟加载
			window.setTimeout(function() {
				input.datetimebox($.extend({
					editable : false
				}, options));
			}, 10);
			return input;
		},
		getValue : function(target) {
			return $(target).datetimebox('getValue');
		},
		setValue : function(target, value) {
			$(target).val(value);
			window.setTimeout(function() {
				$(target).datetimebox('setValue', value);
			}, 150);
		},
		resize : function(target, width) {
			var input = $(target);
			if ($.boxModel == true) {
				input.width(width - (input.outerWidth() - input.width()));
			} else {
				input.width(width - 5);
			}
		}
	}
});