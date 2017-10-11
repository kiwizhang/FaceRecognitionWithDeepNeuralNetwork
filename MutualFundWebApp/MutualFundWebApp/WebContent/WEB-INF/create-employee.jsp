<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="empl-temp-top.jsp"/>

           <li class="active">Create Employee Account</li>
		</ol>
	</div>
<h3 class="page-header" style="margin-left: 15px;font-size:21px">Create Employee Account</h3>

<jsp:include page="error-list.jsp" />

<div class="col-md-4">
	<form role="form" action="createEmployee.do" method="post">
		<table class="content" style="height:200px;width:300px">
		    <tbody>
		    <tr style="height:20px">
				<td style="vertical-align:bottom"><strong>Username*</strong></td>
			</tr>
			<tr>
			    <td>
				    <input type="text" class="form-control" style="border: 1px solid" name="userName"
				    placeholder="within 16 characters" value="${form.userName}"/>
				</td>
			</tr>
			<tr style="height:30px">
				<td style="vertical-align:bottom"><strong>First Name*</strong></td>
			</tr>
			<tr>
			    <td>
				    <input type="text" class="form-control" style="border: 1px solid" name="firstName"
				    placeholder="within 16 characters" value="${form.firstName}"/>
				</td>
			</tr>
			<tr style="height:30px">
				<td style="vertical-align:bottom"><strong>Last Name*</strong></td>
			</tr>
			<tr>
			    <td>
				    <input type="text" class="form-control" style="border: 1px solid" name="lastName"
				    placeholder="within 16 characters" value="${form.lastName}"/>
				</td>
			</tr>
			<tr style="height:30px">
				<td style="vertical-align:bottom"><strong>Password*</strong></td>
			</tr>
			<tr>
			    <td>
				    <input type="password" class="form-control" style="border: 1px solid" name="password"
				    placeholder="within 16 characters" value=""/>
				</td>
			</tr>
			<tr style="height:30px">
				<td style="vertical-align:bottom"><strong>Confirm Password*</strong></td>
			</tr>
			<tr>
			    <td>
				    <input type="password" class="form-control" style="border: 1px solid" name="confirm" 
				    placeholder="same as password" value=""/>
				</td>
			</tr>
			<tr>
			    <td>&nbsp;</td>
			</tr>
			<tr style="height:40px">
				<td colspan="2" align="center">
					<input type="submit" name="action" class="btn btn-primary btn-block"
					    value="Create Employee Account"/>
				</td>
			</tr>
			</tbody>
		</table>
	</form>
</div>

<br><br><br><br><br>
			</div>			
		</div>
	</div>
</div>
<!-- /body container -->

<jsp:include page="template-bottom.jsp" />

