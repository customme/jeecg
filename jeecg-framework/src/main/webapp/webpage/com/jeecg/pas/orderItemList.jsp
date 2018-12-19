<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
#orderItem {
	width: 660px;
	border-collapse : separate;
	border-spacing: 2px;
	table-layout: fixed;
	border-collapse: separate;
}

#orderItem td {
	border-collapse: separate;
	border-color: #e7e7e7;
	border-width: 0 1px 1px 0;
	border-style: solid;
	margin: 0;
	padding: 5px;
}

#orderItem td.bc {
	border-width: 0 1px 1px 1px;
}

#orderItem td.image {
	padding: 0px;
}
</style>
<table border="0" cellpadding="0" cellspacing="0" id="orderItem">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE" style="width: 30px;" class="bc">序号</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 60px">商品图片</td>
		<td align="left" bgcolor="#EEEEEE" style="width: 220px;">商品名称</td>
		<td align="left" bgcolor="#EEEEEE" style="width: 230px;">商品规格</td>
		<td align="right" bgcolor="#EEEEEE" style="width: 70px;">零售价(元)</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 50px;">数量</td>
	</tr>
	<tbody>
		<c:forEach items="${orderItemList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" class="bc">${stuts.index+1}</td>
				<input name="orderItemList[${stuts.index}].id" type="hidden" value="${poVal.id}" />
				<input name="orderItemList[${stuts.index}].orderId" type="hidden" value="${poVal.orderId}" />
				<input name="orderItemList[${stuts.index}].goodsId" type="hidden" value="${poVal.goodsId}" />
				<input name="orderItemList[${stuts.index}].goodsImg" type="hidden" value="${poVal.goodsImg}" />
				<input name="orderItemList[${stuts.index}].goodsName" type="hidden" value="${poVal.goodsName}" />
				<input name="orderItemList[${stuts.index}].goodsSpecs" type="hidden" value="${poVal.goodsSpecs}" />
				<input name="orderItemList[${stuts.index}].price" type="hidden" value="${poVal.price}" />
				<input name="orderItemList[${stuts.index}].quantity" type="hidden" value="${poVal.quantity}" />
				<td align="center" class="image">
					<img src="${poVal.goodsImg}" width="50" height="50" border="0" onmouseover="tipImg(this)" onmouseout="moveTipImg()">
				</td>
				<td align="left">${poVal.goodsName}</td>
				<td align="left">${poVal.goodsSpecs}</td>
				<td align="right">${poVal.price}</td>
				<td align="center">${poVal.quantity}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
	$(function() {
		// 商品总价
		var total = 0;
		$("#orderItem tr:gt(0)").each(function() {
			var price = $(this).children('td:eq(4)').text() - 0;
			var quantity = $(this).children('td:eq(5)').text() - 0;
			total += price * quantity;
		});
		$("#price").val(total.toFixed(2));

		// 应付金额
		setPayment();

		// 运费变动时修改应付金额
		$("#freight").change(function() {
			setPayment();
		});

		// 优惠金额变动时修改应付金额
		$("#discountAmount").change(function() {
			setPayment();
		});
	});

	// 修改应付金额
	function setPayment() {
		var price = $("#price").val() - 0;
		var freight = $("#freight").val() - 0;
		var discountAmount = $("#discountAmount").val() - 0;
		$("#payment").val((price + freight - discountAmount).toFixed(2));
	}
</script>