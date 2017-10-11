<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:choose>
    <c:when test="${ employee == null }">
        <jsp:include page="cust-temp-top.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="empl-temp-top.jsp"/>
    </c:otherwise>
</c:choose>
            <li class="active">Change Your Password</li>
		</ol>
	</div>
<h3 class="page-header" style="margin-left: 15px;font-size:21px">Change Your Password</h3>
<jsp:include page="error-list.jsp" />

<div class="col-md-4">
	<form role="form" method="POST" action="change-pwd.do">
		<table class="content" style="height:140px;width:300px">
		<tbody>
			<tr style="height:20px">
				<td style="vertical-align:bottom"><strong>New Password*</strong> </td>
			</tr>
			<tr>
			    <td><input type="password" class="form-control" style="border: 1px solid" name="newPassword" value=""/></td>
			</tr>
			<tr style="height:30px">
				<td style="vertical-align:bottom"> <strong>Confirm New Password*</strong> </td>
			</tr>
			<tr>
			    <td><input type="password" class="form-control" style="border: 1px solid" name="confirmPassword" value=""/></td>
			</tr>
			<tr>
			    <td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<input type="submit" class="btn btn-primary btn-block" name="action" value="Change Password"/>
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

<jsp:include page="template-bottom.jsp" />
