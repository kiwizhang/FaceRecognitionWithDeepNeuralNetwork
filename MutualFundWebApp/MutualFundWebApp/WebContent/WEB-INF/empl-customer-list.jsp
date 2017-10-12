<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="empl-temp-top.jsp" />

                            <li class="active">Manage Customer Account</li>
						</ol>
					</div>
					<h3 class="page-header" style="margin-left: 15px;font-size:21px">Manage Customer Account</h3>
<jsp:include page="error-list.jsp" />				

					<div class="col-md-12">
						<div style="font-size: 20px; margin-top: 10px;margin-bottom:10px;">
							<strong>Customer List</strong>
						</div>
					</div>
					<table class="table table-striped"
						style="width: 1000px; height: 30px; margin-left: 15px; border: hidden">
						<tbody style="vertical-align: middle;">
							<tr style="text-align: center; vertical-align: middle; font-size: 15px">
								<td style="height: 35px; vertical-align: middle; width: 220px;line-height:200%">
								    <strong>Name</strong></td>
								<td style="width: 130px; vertical-align: middle;">
								    <strong>Username</strong></td>
								<td style="width: 130px; vertical-align: middle;">
								    <strong>Customer ID</strong></td>
								<td style="width: 170px; vertical-align: middle;">
								    <strong>View Account</strong></td>
								<td style="width: 170px; vertical-align: middle;">
								    <strong>Transaction History</strong></td>
							    <td style="width: 150px; vertical-align: middle;">
								    <strong>Deposit Check</strong></td>
								<td style="width: 150px; vertical-align: middle;">
								    <strong>Reset Password</strong></td>
							</tr>
						<c:forEach var="customer" items="${ customerList }">
						
							<tr style="text-align: center">
								<td style="height: 45px; vertical-align: middle;line-height:200%">
									<strong>${customer.firstName} ${customer.lastName}</strong>
								</td>
								<td style=" vertical-align: middle;">${customer.userName}</td>
								<td style="vertical-align: middle;">${customer.customerId}</td>
								<td style="vertical-align: middle;">
								  <form action="viewAccount.do" method="post">
								    <input type="hidden" name="id" value="${customer.customerId}">
								    <input type="submit" class="btn btn-sm btn-primary" value="View Account">
								  </form>
								</td>
								<td style=" vertical-align: middle;">
								  <form action="viewTransaction.do" method="post">
								    <input type="hidden" name="id" value="${customer.customerId}">
								    <input type="submit" class="btn btn-sm btn-primary" value="View Transaction">
								  </form>
								</td>
								<td style="vertical-align: middle;">
								  <form action="deposit-check.do" method="post">
								    <input type="hidden" name="id" value="${customer.customerId}">
								    <input type="submit" class="btn btn-sm btn-primary" value="Deposit Check">
								  </form>
								</td>
								<td style="vertical-align: middle;">
								  <form action="reset-pwd.do" method="post">
								    <input type="hidden" name="id" value="${customer.customerId}">
								    <input type="submit" class="btn btn-sm btn-primary" value="Reset Password">
								  </form>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
				<br> <br> <br>
			</div>
		</div>
	</div>
	</div>
	<!-- /body container -->

<jsp:include page="template-bottom.jsp" />
