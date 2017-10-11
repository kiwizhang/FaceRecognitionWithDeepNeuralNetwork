<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="empl-temp-top.jsp" />

<li><a href="customerList.do">Manage Customer Account</a></li>
<li class="active">Deposit Check</li>
</ol>
</div>
<h3 class="page-header" style="margin-left: 15px; font-size: 21px">
	Deposit Check <span
		style="font-size: 19px; float: right; margin-top: 10px; margin-right: 5px">
		Customer : ${currentCustomer.firstName} ${currentCustomer.lastName}
		( ID: ${currentCustomer.customerId} ) </span>
</h3>
<jsp:include page="error-list.jsp" />

<form action="deposit-check.do" method="POST" class="form-inline"
	style="margin-left: 30px;font-size: 17px; margin-top:30px">
    <span><strong>Amount : </strong></span>
    <input type="hidden" name="id" value="${currentCustomer.customerId}">
	<div class="input-group">
		<div class="input-group-addon" style="border: 1px solid">$</div>
		<input name="amount" type="text" placeholder="Dollar (cannot exceed $1 million)"
			style="width: 270px; border: 1px solid;" class="form-control">
		&nbsp;&nbsp;
	</div>
	<input name="action" class="btn btn-primary" type="submit"
		value="Deposit Check">

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
