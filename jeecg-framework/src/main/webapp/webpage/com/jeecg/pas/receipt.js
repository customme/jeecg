// 目标币种
var tarCur = 0;

$(function() {
	// 总金额改变
	$("#totalAmount").change(function(){
		exchange($(this).val(), $("#currency").val(), $("#buyTime").val());
	})
	// 支付币种改变
	$("#currency").change(function(){
		exchange($("#totalAmount").val(), $(this).val(), $("#buyTime").val());
	})
	// 购买时间改变
	$("#buyTime").blur(function() {
		exchange($("#totalAmount").val(), $("#currency").val(), $(this).val());
	})
});

// 兑换
function exchange(amount,srcCur, updateTime) {
	if(amount == "" || updateTime == ""){
		return
	}
	if(srcCur == tarCur){
		$("#actualAmount").val(amount);
	}
	
	$.ajax({
		url : "rateController.do?get",
		data : {
			srcCur : srcCur,
			tarCur : tarCur,
			updateTime : updateTime
		},
		type : "get",
		dataType : "json",
		success : function(result) {
			var rate = result.data;
			if(rate > 0){
				$("#actualAmount").val((amount * rate).toFixed(2));
			}
		}
	});
}