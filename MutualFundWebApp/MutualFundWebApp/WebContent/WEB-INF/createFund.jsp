<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="empl-temp-top.jsp" />

        <li class="active">Create Fund</li>
    </ol>
</div>
<h3 class="page-header" style="margin-left: 15px; font-size: 21px">Create Fund</h3>

<jsp:include page="error-list.jsp" />

<div class="col-md-11">
	<form action="createFund.do" method="POST">
		<table class="content" style="width: 300px; height: 20px; margin-left: 5px;">
			<tbody>
				<tr style="font-size: 15px">
					<td style="height: 25px; vertical-align: bottom;"><strong>Fund
							Name* </strong></td>
				</tr>
				<tr>
					<td><input class="form-control" style="border: 1px solid" name="name" 
					placeholder="within 30 characters" value="${form.name}"></td>
				</tr>
				<tr style="height:30px">
					<td style="vertical-align: bottom;"><strong>Ticker*</strong></td>
				</tr>
				<tr>
					<td><input class="form-control" style="border: 1px solid" name="symbol"
					  value="${form.symbol}" placeholder="one to five letters"></td>
				</tr>
				<tr>
			        <td>&nbsp;</td>
			    </tr>
				<tr>
					<td><input name="action" type="submit" value="Create New Fund" 
					    class="btn btn-primary btn-block"></td>
				</tr>
			</tbody>
		</table>
	</form>
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
