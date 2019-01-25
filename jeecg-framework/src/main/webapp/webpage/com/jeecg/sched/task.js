$(function() {
	// 设置个性化样式
	$("#name").css("width", "300px");
	$("#cycleValue").css("width", "80px");
	$("#serverId").css("width", "120px");
	$("#copyExt").prev().css("margin-left", "20px");
	$("#copyExt").css("margin-right", "30px");

	// 任务周期
	$("#taskCycle").change(function() {
		switchCycleValue($(this).val());
	});

	// 所属集群
	$("#clusterId").change(function() {
		changeServer($(this).val());
	});
});

function switchCycleValue(val) {
	var arr = [ "", "day", "hour", "instant", "incessant" ];
	$("#taskCycle").nextAll().remove();
	if ($.inArray(val, arr) > 0) {
		$("#priority").focus();
	} else if(val == "week"){
		var weekday = [ '', '一', '二', '三', '四', '五', '六', '日' ];
		var html = "<label class='Validform_label'>周几: </label><select id='cycleValue' name='cycleValue'>";
		for(var i=1;i<=7;i++){
			html += "<option value='" + i + "'>" + weekday[i] + "</option>";
		}
		html += "</select>";
		$("#taskCycle").parent().append(html);
		$("#cycleValue").focus();
	}else if(val == "month"){
		var html = "<label class='Validform_label'>第几日: </label><select id='cycleValue' name='cycleValue'>";
		for(var i = 1;i<=31;i++){
			html += "<option value='" + i + "'>" + i + "</option>";
		}
		html += "</select>";
		$("#taskCycle").parent().append(html);
		$("#cycleValue").focus();
	}else if(val = "interval"){
		var html = "<label class='Validform_label'>间隔秒: </label><input type='text' id='cycleValue' name='cycleValue' ignore='checked' datatype='n' />";
		html += "<span class='Validform_checktip'></span>";
		$("#taskCycle").parent().append(html);
		$("#cycleValue").focus();
	}
	$("#cycleValue").css("width", "80px");
}

function changeServer(val) {
	var initVal = "<option value selected=\"selected\"></option>";
	if (val == "") {
		$("#serverId").html(initVal);
		return
	}

	$.ajax({
		url : "serverController.do",
		data : {
			clusterId : val
		},
		type : "get",
		dataType : "json",
		success : function(result) {
			var data = result.data;
			$("#serverId").html(initVal);
			for (var i = 0; i < data.length; i++) {
				var html = '<option value="' + data[i].id + '">' + data[i].ip + '</option>';
				$("#serverId").append(html);
			}
		}
	});
}