<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="cust-temp-top.jsp"/>

           <li class="active">Login</li>
		</ol>
	</div>
<h3 class="page-header" style="margin-left: 15px;font-size:21px">Dear Customer, Please Login</h3>
<jsp:include page="error-list.jsp" />

				<div class="col-md-4">
				<form role="form" action="loginCustomer.do" method="POST">
					<h5>User name</h5>
					 <input class="form-control" style="border: 1px solid" type="text" name="username" value="${form.username}"/>
							
					<h5>Password</h5>
					 <input type="password" style="border: 1px solid" type="password" class="form-control" name="password" value=""/><br>
					 <input name="action" class="btn btn-lg btn-primary btn-block" type="submit" value="Login"> 
				</form>
				</div>
				
				<br><br><br><br>			
			</div>		
		</div>
	</div>
</div>
<!-- /body container -->

<jsp:include page="template-bottom.jsp" />