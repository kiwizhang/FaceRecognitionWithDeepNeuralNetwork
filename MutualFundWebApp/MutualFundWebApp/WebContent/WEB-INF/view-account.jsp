<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
    <c:when test="${ isEmployee == false }">
        <jsp:include page="cust-temp-top.jsp"/>
        <li class="active">View Your Account</li>
    </ol>
</div>
<div>
	<h3 class="page-header" style="margin-left: 15px;font-size:21px">
		View Your Account 
		<span style="font-size: 19px; float: right; margin-top: 10px; margin-right: 5px">
		    Customer : ${customer.firstName} ${customer.lastName} ( ID : ${customer.customerId} )
		</span>
	</h3>
    </c:when>
    <c:otherwise>
        <jsp:include page="empl-temp-top.jsp"/>
        <li><a href="customerList.do">Manage Customer Account</a></li>
        <li class="active">View Customer Account</li>
    </ol>
</div>
<div>
	<h3 class="page-header" style="margin-left: 15px;font-size:21px">
		View Customer Account 
		<span style="font-size: 19px; float: right; margin-top: 10px; margin-right: 5px">
		    Customer : ${currentCustomer.firstName} ${currentCustomer.lastName} ( ID: ${currentCustomer.customerId} )
		</span>
	</h3>
    </c:otherwise>
</c:choose>

<jsp:include page="error-list.jsp" />

</div>
<div class="col-md-12" style="margin-top: 15px">
	<div class="col-md-5">
		<div style="font-size: 19px"><Strong>Account Information</Strong></div>
		<br>
	      <table class="table table-striped" style="width: 380px; height: 30px; border: hidden;">
			<tbody>
				<tr>
					<td style="height: 25px; text-align: right; vertical-align: middle; width: 170px;line-height:200%">
						<strong>Name</strong></td>
					<td style="width: 10px">&nbsp;</td>
					<td style="vertical-align: middle; width: 200px">${currentCustomer.firstName} ${currentCustomer.lastName}</td>
				</tr>
				<tr>
					<td style="height: 25px; text-align: right; vertical-align: middle;line-height:200%">
						<strong>Username</strong></td>
					<td>&nbsp;</td>
					<td style="vertical-align: middle;">${currentCustomer.userName}</td>
				</tr>
				<tr>
					<td style="height: 25px; text-align: right; vertical-align: middle;line-height:200%">
						<strong>Last Trading day</strong>
					</td>
					<td>&nbsp;</td>
					<td style="vertical-align: middle;">
					  <fmt:formatDate pattern="yyyy-MM-dd" value="${ date }" />
					</td>
				</tr>
				<tr>
					<td style="height: 25px; text-align: right; vertical-align: middle;line-height:200%">
						<strong>Cash Balance</strong>
					</td>
					<td>&nbsp;</td>
					<td style="vertical-align: middle;">
					  $<fmt:formatNumber type="number" value="${currentCustomer.cash/100}" 
					    minFractionDigits="2" maxFractionDigits="2"/>
					</td>
				</tr>
				<tr>
					<td style="height: 25px; text-align: right; vertical-align: middle;line-height:200%">
						<strong>Available Balance</strong></td>
					<td>&nbsp;</td>
					<td style="vertical-align: middle;">
					  $<fmt:formatNumber type="number" value="${currentCustomer.pendingCash/100}" 
					    minFractionDigits="2" maxFractionDigits="2"/></td>
				</tr>
			<c:choose>
                <c:when test="${ isEmployee == false }">
                </c:when>
                <c:otherwise>
				<tr>
					<td colspan="2">&nbsp;</td>
					<td>
					    <form role="form" action="reset-pwd.do" method="POST">
			                <input type="hidden" name="id" value="${currentCustomer.customerId}">
			                <input name="resetpsw" class="btn btn-sm btn-primary" value="Reset Password"
				                style="font-size: 15px; float: right; width: 200px" type="submit">
		                </form>
					</td>
			    </tr>
			    </c:otherwise>
            </c:choose>
			</tbody>
		</table>
	</div>
	<div class="col-md-1"></div>
	<div class="col-md-5">
		<div style="font-size: 19px"><Strong>Contact Information</Strong></div>
		<br>
            <table class="table table-striped" style="width: 380px; height: 30px; border: hidden;">
		      <tbody>
				<tr>
					<td style="height: 25px; width: 170px; text-align: right; vertical-align: middle;line-height:200%">
						<strong>Address Line 1</strong></td>
					<td style="width: 10px">&nbsp;</td>
					<td style="vertical-align: middle; width: 200px">${currentCustomer.addrLine1}</td>
				</tr>
				<tr>
					<td style="height: 25px; text-align: right; vertical-align: middle;line-height:200%">
						<strong>Address Line 2</strong></td>
					<td style="width: 10px">&nbsp;</td>
					<td style="vertical-align: middle;">${currentCustomer.addrLine2}</td>
				</tr>
				<tr>
					<td style="height: 25px; text-align: right; vertical-align: middle;line-height:200%">
						<strong>City</strong></td>
					<td style="width: 10px">&nbsp;</td>
					<td style="vertical-align: middle;">${currentCustomer.city}</td>
				</tr>
				<tr>
					<td style="height: 25px; text-align: right; vertical-align: middle;line-height:200%">
						<strong>State</strong></td>
					<td style="width: 10px">&nbsp;</td>
					<td style="vertical-align: middle;">${currentCustomer.state}</td>
				</tr>
				<tr>
					<td style="height: 25px; text-align: right; vertical-align: middle;line-height:200%">
						<strong>Zipcode</strong></td>
					<td style="width: 10px">&nbsp;</td>
					<td style="vertical-align: middle;">${currentCustomer.zip}</td>
				</tr>
			<c:choose>
                <c:when test="${ isEmployee == false }">
                </c:when>
                <c:otherwise>
				<tr>
				    <td colspan="2">&nbsp;</td>
				    <td>
				        <form role="form" action="deposit-check.do" method="POST">
				            <input type="hidden" name="id" value="${currentCustomer.customerId}">
		          	        <input name="deposit" class="btn btn-sm btn-primary" value="Deposit Check"
				                style="font-size: 15px; float: right; width: 210px" type="submit">
		                </form>
				    </td>
				</tr>
				</c:otherwise>
            </c:choose>
			</tbody>
		</table>
	</div>
</div>
<div class="col-md-12" style="height:35px">&nbsp;</div>

<c:choose>
  <c:when test="${ isEmployee == false }">
<div class="col-md-12">
	<div style="font-size: 19px;">
		<strong>Your Funds</strong>
		<span style="float: right;margin-right:95px;">
        <form role="form" action="viewTransaction.do" method="POST">
		  <input type="hidden" name="id" value="${customer.customerId}">
		  <input class="btn btn-sm btn-primary" value="View Transaction History"
			style="font-size: 15px; margin-bottom:15px; width: 210px" type="submit">
		</form>
		</span>
	</div>
        <table class="table table-striped" style="width: 1000px; height: 20px;">
		  <tbody>
			<tr style="text-align: center; vertical-align: middle; font-size: 15px">
				<td style="height: 25px;vertical-align: middle; width: 20px;line-height:200%">
				    <strong>#</strong></td>
				<td style="vertical-align: middle; width: 330px;text-align:left; text-indent:70px">
				    <strong>Fund Name</strong></td>
				<td style="width: 60px; vertical-align: middle;">
				    <strong>Ticker</strong></td>
				<td style="width: 130px; vertical-align: middle;">
				    <strong>Shares</strong></td>
				<td style="width: 140px; vertical-align: middle;">
				    <strong>Available Shares</strong></td>
				<td style="width: 140px; vertical-align: middle;">
				    <strong>Latest Price</strong></td>
				<td style="width: 130px; vertical-align: middle;">
				    <strong>Value</strong></td>
				<td style="vertical-align: middle;width: 60px"><strong>Buy</strong></td>
                <td style="vertical-align: middle;width: 60px"><strong>Sell</strong></td>
              </tr>
		    <c:forEach var="fund" items="${fundList}" varStatus="count">
			  <tr>
				<td style="vertical-align: middle;text-align:center;line-height:200%">${count.index+1}</td>
				<td style="vertical-align: middle;"><a href="stats.do?fundId=${fund.fundId}" >${fund.name}</a></td>
				<td style="vertical-align: middle;text-align: center;">${fund.symbol}</td>
				<td style="vertical-align: middle;text-align: right; padding-right:7px">
				  <fmt:formatNumber type="number" value="${fund.shares/1000}" minFractionDigits="3" maxFractionDigits="3"/>
				</td>
				<td style="vertical-align: middle;text-align: right; padding-right:8px">
				  <fmt:formatNumber type="number" value="${fund.pendingShares/1000}" minFractionDigits="3" maxFractionDigits="3"/>
				</td>
				<td style="vertical-align: middle;text-align: right; padding-right:15px">
				  $<fmt:formatNumber type="number" value="${fund.price/100}" minFractionDigits="2" maxFractionDigits="2"/>
				</td>
				<td style="vertical-align: middle;text-align: right; padding-right:7px">
				  $<fmt:formatNumber type="number" value="${fund.shares*fund.price/100000}" 
				    minFractionDigits="2" maxFractionDigits="2"/>
				</td>
				
                  <td style="text-align:center">
                    <form action="managefund.do" method="post">
                      <input type="hidden" name="fundId" value="${fund.fundId}">
	                  <input type="hidden" name="name" value="${fund.name}">
	                  <input type="hidden" name="symbol" value="${fund.symbol}">
	                  <input type="hidden" name="shares" value="${fund.shares}">
	                  <input type="hidden" name="price" value="${fund.price}">
	                  <input type="hidden" name="pendingShares" value="${fund.pendingShares}"> 
                      <input type="submit" name="action" class="btn btn-sm btn-primary" style="width:60px" value="Buy">
				    </form>
                  </td>
                  <td style="text-align:center">
                    <form action="managefund.do" method="post">
                      <input type="hidden" name="fundId" value="${fund.fundId}">
	                  <input type="hidden" name="name" value="${fund.name}">
	                  <input type="hidden" name="symbol" value="${fund.symbol}">
	                  <input type="hidden" name="shares" value="${fund.shares}">
	                  <input type="hidden" name="price" value="${fund.price}">
	                  <input type="hidden" name="pendingShares" value="${fund.pendingShares}"> 
                      <input type="submit" name="action" class="btn btn-sm btn-primary" style="width:60px" value="Sell">
				    </form>
                  </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
   </c:when>
   <c:otherwise>
   <div class="col-md-12">
	  <div style="font-size: 19px;">
		<strong>Your Funds</strong>
		<span style="float: right;margin-right:105px;">
        <form role="form" action="viewTransaction.do" method="POST">
		  <input type="hidden" name="id" value="${currentCustomer.customerId}">
		  <input class="btn btn-sm btn-primary" value="View Transaction History"
			style="font-size: 15px; margin-bottom:15px; width: 210px" type="submit">
		</form>
		</span>
		</div>
        <table class="table table-striped" style="width: 930px; height: 20px;">
		  <tbody>
			<tr style="text-align: center; vertical-align: middle; font-size: 15px">
				<td style="height: 25px;vertical-align: middle;text-align:center; width: 30px;line-height:200%">
				    <strong>#</strong></td>
				<td style="vertical-align: middle; width: 350px">
				    <strong>Fund Name</strong></td>
				<td style="width: 65px; vertical-align: middle;">
				    <strong>Ticker</strong></td>
				<td style="width: 130px; vertical-align: middle;">
				    <strong>Shares</strong></td>
			    <td style="width: 160px; vertical-align: middle;">
				    <strong>Available Shares</strong></td>
				<td style="width: 130px; vertical-align: middle;">
				    <strong>Latest Price</strong></td>
				<td style="width: 130px; vertical-align: middle;">
				    <strong>Value</strong></td>
			</tr>
		<c:forEach var="fund" items="${fundList}" varStatus="count">
			<tr>
				<td style="vertical-align: middle;text-align:center;line-height:200%">${count.index+1}</td>
				<td style="vertical-align: middle;"><a href="stats.do?fundId=${fund.fundId}" >${fund.name}</a></td>
				<td style="vertical-align: middle;text-align: center;">${fund.symbol}</td>
				<td style="vertical-align: middle;text-align: right; padding-right:8px">
				  <fmt:formatNumber type="number" value="${fund.shares/1000}" minFractionDigits="3" maxFractionDigits="3"/>
				</td>
				<td style="vertical-align: middle;text-align: right; padding-right:15px">
				  <fmt:formatNumber type="number" value="${fund.pendingShares/1000}" minFractionDigits="3" maxFractionDigits="3"/>
				</td>
				<td style="vertical-align: middle;text-align: right; padding-right:25px">
				  $<fmt:formatNumber type="number" value="${fund.price/100}" minFractionDigits="2" maxFractionDigits="2"/>
				</td>
				<td style="vertical-align: middle;text-align: right; padding-right:7px">
				  $<fmt:formatNumber type="number" value="${fund.shares*fund.price/100000}" minFractionDigits="2" maxFractionDigits="2"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</c:otherwise>
    </c:choose>
<br> <br> <br>
</div>
</div>
</div>
</div>
<!-- /body container -->

<jsp:include page="template-bottom.jsp" />
