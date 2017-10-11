<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="cust-temp-top.jsp" />

<li><a href="managefund.do">Manage Fund</a></li>
<li>Buy Fund</li>
</ol>
</div>
<h3 class="page-header" style="margin-left: 15px; font-size: 21px">Buy Fund
	<span style="font-size: 19px; float: right; margin-top: 10px; margin-right: 5px">
		Customer : ${customer.firstName} ${customer.lastName} ( ID : ${customer.customerId} )
   </span>
</h3>
<jsp:include page="error-list.jsp" />

<h5 style="margin-left: 15px;font-size:16px; margin-bottom:25px;margin-top:25px">
	Available Balance: 
	$<fmt:formatNumber type="number" minFractionDigits="2"
		maxFractionDigits="2" value="${customer.pendingCash/100}"/>
</h5>


<div class="col-md-12">
	<table class="table table-striped" style="width: 1000px; height: 30px;">
		<tbody>
			<tr style="text-align: center; vertical-align: middle; font-size:15px">
				<td style="height: 30px; width:320px; vertical-align: middle;line-height:200%;"><strong>Fund
						Name</strong></td>
				<td style="width: 100px; vertical-align: middle;"><strong>Ticker</strong></td>
				<td style="width: 150px; vertical-align: middle;"><strong>Latest
						Price</strong></td>
				<td style="width: 200px; vertical-align: middle;text-align: left;text-indent:40px"><strong>Dollar
						Amount</strong></td>
				<td style="width: 120px; vertical-align: middle;"></td>
			</tr>
			<%
				String fundId = (String) session.getAttribute("fundId");
				String name = (String) session.getAttribute("name");
				String symbol = (String) session.getAttribute("symbol");
				String shares = (String) session.getAttribute("shares");
				String amount = (String) session.getAttribute("amount");
				String price = (String) session.getAttribute("price");
			%>
			<form action="BuyFund.do" method="post">
				<input type="hidden" name="fundId" value=<%=fundId%>>
				<tr style="text-align: center; vertical-align: middle;">
					<td style="height: 10px; vertical-align: middle;">
					<a href="stats.do?fundId=${fundId}">${name}</a>
					</td>
					<td style="vertical-align: middle;">${symbol}
					</td>
					<td style="vertical-align: middle; text-align:right; padding-right: 43px">
						$<fmt:formatNumber type="number" value="${price/100}"
							minFractionDigits="2" maxFractionDigits="2" />
					</td>
					<td style="vertical-align: middle;">
					 <div class="input-group">
                       <div class="input-group-addon" style="border: 1px solid">$</div>
					   <input class="form-control" name="amount" placeholder="Dollar ( within $1 million )"
						style="width: 200px; height: 30px; border: 1px solid;">
					</div>
					</td>
					<td style="text-align: left"><input name="action" style="width:80px"
						type="submit" value="Buy" class="btn btn-primary btn-sm">
					</td>
				</tr>
			</form>
			

		</tbody>
	</table>
</div>

<br>
<br>
<br>
</div>
</div>
</div>
</div>
<!-- /body container -->

<jsp:include page="template-bottom.jsp" />