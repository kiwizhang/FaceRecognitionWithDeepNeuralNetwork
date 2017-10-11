<%@page import="databean.FundPriceBean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="cust-temp-top.jsp" />

<li><a href="managefund.do">Manage Fund</a></li>
<li class="active">Sell Fund</li>
</ol>
</div>
<h3 class="page-header" style="margin-left: 15px; font-size: 21px">Sell Fund
   <span style="font-size: 19px; float: right; margin-top: 10px; margin-right: 5px">
		Customer : ${customer.firstName} ${customer.lastName} ( ID : ${customer.customerId} )
   </span>
</h3>
<jsp:include page="error-list.jsp" />  
<%
	String fundId = (String) session.getAttribute("fundId");
	String name = (String) session.getAttribute("name");
	String symbol = (String) session.getAttribute("symbol");
	String shares = (String) session.getAttribute("shares");
	String pendingShares = (String) session.getAttribute("pendingShares");
	String price = (String) session.getAttribute("price");
	String sharesToSell = (String) session.getAttribute("sharesToSell");
%>
<h4 style="margin-left: 15px; font-size:16px; margin-bottom:25px;margin-top:25px">Your Available Shares: 
  <fmt:formatNumber type="number" value="${pendingShares/1000}" minFractionDigits="3" maxFractionDigits="3"/>
</h4>
            
<div class="col-md-12">
	<table class="table table-striped" style="width: 1000px; height: 30px;">
		<tbody style="vertical-align: middle;">
			<tr style="text-align: center; font-size: 15px">
				<td style="height: 30px; vertical-align: middle; width: 330px;line-height:200%"><strong>Fund Name</strong></td>
				<td style="width: 80px; vertical-align: middle;"><strong>Ticker</strong></td>
				<td style="width: 130px; vertical-align: middle;"><strong>Shares</strong></td>
				<td style="width: 160px; vertical-align: middle;"><strong>Available Shares</strong></td>
				<td style="width: 150px; vertical-align: middle;"><strong>Latest Price</strong></td>
				<td style="width: 130px; vertical-align: middle;text-align: left;text-indent:8px"><strong>Shares to Sell</strong></td>
				<td style="width: 140px; vertical-align: middle;"></td>
			</tr>

			<form action="SellFund.do" method="post">
				<input type="hidden" name="fundId" value=<%=fundId%>>
				<tr style="text-align: center;">
					<td style="height: 30px; vertical-align: middle;"><a href="stats.do?fundId=${fundId}">${name}</a></td>
					<td style="vertical-align: middle;">${symbol}</td>
					<td style="vertical-align: middle;text-align: right; padding-right:8px">
				 		<fmt:formatNumber type="number" value="${shares/1000}" minFractionDigits="3" maxFractionDigits="3"/>
					</td>
					<td style="vertical-align: middle;text-align: right; padding-right:10px">
				 		<fmt:formatNumber type="number" value="${pendingShares/1000}" minFractionDigits="3" maxFractionDigits="3"/>
					</td>
					<td style="vertical-align: middle;text-align: right; padding-right:20px">
				 		$<fmt:formatNumber type="number" value="${price/100}" minFractionDigits="2" maxFractionDigits="2"/>
					</td>
					<td>
					  <input class="form-control" name="sharesToSell" placeholder="within 1 million"
						style="width: 120px; height: 30px; border: 1px solid;">
					</td>
					<td style="vertical-align: middle;text-align: left;">
					  <input name="action" type="submit" value="Sell" style="width:80px" class="btn btn-primary btn-sm">
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