<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="cust-temp-top.jsp" />

<li class="active">Request Check</li>
</ol>
</div>
<h3 class="page-header" style="margin-left: 15px; font-size: 21px">
  Request Check
  <span style="font-size: 19px; float: right; margin-top: 10px; margin-right: 5px">
	Customer : ${customer.firstName} ${customer.lastName} ( ID : ${customer.customerId} )
  </span>
</h3>

<jsp:include page="error-list.jsp" />

<h5 style="margin-left: 30px;font-size: 17px">
	Balance:
	$<fmt:formatNumber type="number" minFractionDigits="2"
		maxFractionDigits="2" value="${customer.cash/100}" />
</h5>
<h5 style="margin-left: 30px;margin-top:20px;font-size: 17px">
	Available Balance:
	$<fmt:formatNumber type="number" minFractionDigits="2"
		maxFractionDigits="2" value="${customer.pendingCash/100}" />
</h5>
<br>
<form action="request-check.do" method="POST" class="form-inline" style="margin-left:30px;font-size: 17px">
    <span><strong>Amount:</strong></span> 
    <div class="input-group">
       <div class="input-group-addon" style="border: 1px solid">$</div>
	   <input class="form-control" name="amount" type="text" placeholder="Dollar" 
	     style="width: 250px;border: 1px solid">
	</div>
	&nbsp;
	   <input name="action" class="btn btn-primary" type="submit" value="Request Check">
	

</form>
<br>
<br>
<br>
</div>
</div>
</div>
</div>
<!-- /body container -->

<jsp:include page="template-bottom.jsp" />
