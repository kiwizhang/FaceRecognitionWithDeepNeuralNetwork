<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="cust-temp-top.jsp" />

<li class="active">Manage Fund</li>
</ol>
</div>
<h3 class="page-header" style="margin-left: 15px; font-size: 21px">Manage Fund
    <span style="font-size: 19px; float: right; margin-top: 10px; margin-right: 5px">
		Customer : ${customer.firstName} ${customer.lastName} ( ID : ${customer.customerId} )
    </span>

</h3>
<jsp:include page="error-list.jsp" />


<div class="col-md-12">
<h3 style="font-size: 19px">Your Funds</h3>
	<table class="table table-striped" style="width:995px;height:30px;">
		<tbody>
		    <tr style=" font-size:15px">
				<th style="vertical-align:middle;text-align:center;width:30px;line-height:200%">#</th>
				<th style="vertical-align:middle;text-indent:60px;width:300px;">Fund Name</th>
				<th style="vertical-align:middle;text-align:center;width:70px;">Ticker</th>
				<th style="vertical-align:middle;text-align:center;width:100px;">Shares</th>
				<th style="vertical-align:middle;text-align:center;width:140px;">Available Shares</th>				
				<th style="vertical-align:middle;text-align:center;width:120px;">Latest Price</th>
				<th style="width:70px;">&nbsp;</th>
				<th style="width:70px;">&nbsp;</th>
				<th style="width:70px;">&nbsp;</th>
		    </tr>
			<c:forEach var="item" items="${mytransactionlist}" varStatus="count">
				<tr>
					<td style="vertical-align:middle;text-align:center;line-height:200%">${count.index+1}</td>
					<td style="vertical-align:middle;"><a href="stats.do?fundId=${item.fundId}">${item.name}</a></td>
					<td style="vertical-align:middle;text-align:center;">${item.symbol}</td>
					<td style="vertical-align:middle;text-align:right;padding-right:5px">
					    <fmt:formatNumber type="number" value="${item.shares/1000}"
							minFractionDigits="3" maxFractionDigits="3" /></td>
							<td style="vertical-align:middle;text-align:right;padding-right:15px">
					    <fmt:formatNumber type="number" value="${item.pendingShares/1000}"
							minFractionDigits="3" maxFractionDigits="3" /></td>
					<td style="vertical-align:middle;text-align:right;padding-right:28px">
					    $<fmt:formatNumber type="number" value="${item.price/100}"
							minFractionDigits="2" maxFractionDigits="2" /></td>

					<td style="text-align:center;">
						<form action="stats.do" method="post">
							<input type="hidden" name="fundId" value="${item.fundId}">
							<input type="hidden" name="name" value="${item.name}"> 
							<input type="hidden" name="symbol" value="${item.symbol}"> 
							<input type="hidden" name="shares" value="${item.shares}">
							<input type="hidden" name="pendingShares" value="${item.pendingShares}"> 
							<input
								type="hidden" name="price" value="${item.price}"> <input
								type="submit" name="action" value="View"
								class="btn btn-primary btn-sm" style="width:60px">
						</form>
					</td>
					<td style="text-align:center;">
						<form action="managefund.do" method="post">
							<input type="hidden" name="fundId" value="${item.fundId}">
							<input type="hidden" name="name" value="${item.name}"> 
							<input type="hidden" name="symbol" value="${item.symbol}">
							 <input type="hidden" name="shares" value="${item.shares}"> 
							 <input type="hidden" name="pendingShares" value="${item.pendingShares}">
							 <input
								type="hidden" name="price" value="${item.price}"> <input
								type="submit" name="action" value="Sell"
								class="btn btn-primary btn-sm" style="width:60px">
						</form>
					</td>
					<td style="text-align:center;">
						<form action="managefund.do" method="post">
							<input type="hidden" name="fundId" value="${item.fundId}">
							<input type="hidden" name="name" value="${item.name}">
							 <input type="hidden" name="symbol" value="${item.symbol}"> 
							 <input	type="hidden" name="shares" value="${item.shares}">
								<input type="hidden" name="pendingShares" value="${item.pendingShares}">
								 <input type="hidden" name="price" value="${item.price}"> <input
								type="submit" name="action" value="Buy"
								class="btn btn-primary btn-sm" style="width:60px">
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<c:choose>
    <c:when test="${ length == 0 }">
    </c:when>
    <c:otherwise>
<div class="col-md-12">
<h3 style="font-size: 19px">Funds Recommendation</h3>	
	<table class="table table-striped" style="width:995px;height:30px;">
		<tbody>
		    <tr style=" font-size:15px">
				<th style="vertical-align:middle;text-align:center;width:30px;line-height:200%">#</th>
				<th style="vertical-align:middle;text-indent:80px;width:400px;">Fund Name</th>
				<th style="vertical-align:middle;text-align:center;width:140px;">Ticker</th>
				<th style="vertical-align:middle;text-align:center;width:160px;">Latest Price</th>
				<th style="width:70px;">&nbsp;</th>
				<th style="width:70px;">&nbsp;</th>
				<th style="width:70px;">&nbsp;</th>
			</tr>
			<c:forEach var="item" items="${recommendationlist}" varStatus="count">
				<tr>
					<td style="vertical-align:middle;text-align:center;line-height:200%">${count.index+1}</td>
					<td style="vertical-align:middle;"><a href="stats.do?fundId=${item.fundId}">${item.name}</a></td>
					<td style="vertical-align:middle;text-align:center;">${item.symbol}</td>
					<td style="vertical-align:middle;text-align:right;padding-right:50px">	
					    <c:choose>
                                 <c:when test="${ item.price == 0 }">
                                 </c:when>
                                 <c:otherwise>									
					    $<fmt:formatNumber type="number" value="${item.price/100}"
							minFractionDigits="2" maxFractionDigits="2" />
							  </c:otherwise>
                               </c:choose>
							</td>
					<td style="text-align:center;">
						<form action="stats.do" method="post">
							<input type="hidden" name="fundId" value="${item.fundId}">
							<input type="hidden" name="name" value="${item.name}"> <input
								type="hidden" name="symbol" value="${item.symbol}"> <input
								type="hidden" name="shares" value="${item.shares}"> <input
								type="hidden" name="price" value="${item.price}"> <input
								type="submit" name="action" value="View"
								class="btn btn-primary btn-sm" style="width:60px">
						</form>
					</td>
					<td>&nbsp;</td>
					<td style="text-align:center;">
						<form action="managefund.do" method="post">
							<input type="hidden" name="fundId" value="${item.fundId}">
							<input type="hidden" name="name" value="${item.name}"> <input
								type="hidden" name="symbol" value="${item.symbol}"> <input
								type="hidden" name="shares" value="${item.shares}"> <input
								type="hidden" name="price" value="${item.price}"> <input
								type="submit" name="action" value="Buy"
								class="btn btn-primary btn-sm" style="width:60px">
						</form>
					</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</div>
  </c:otherwise>
</c:choose>

<br>
<br>
<br>
</div>
</div>
</div>
</div>
<!-- /body container -->

<jsp:include page="template-bottom.jsp" />


