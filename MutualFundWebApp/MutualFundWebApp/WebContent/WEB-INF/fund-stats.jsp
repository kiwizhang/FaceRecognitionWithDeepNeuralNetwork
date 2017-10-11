<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
    <c:when test="${ isEmployee == false }">
        <jsp:include page="cust-temp-top.jsp"/>
          <li><a href="latestFund.do">Research Fund</a></li>
          <li class="active">Fund Statistics</li>
        </ol>
      </div>
    </c:when>
    <c:otherwise>
        <jsp:include page="empl-temp-top.jsp"/>
        <li class="active">Fund Statistics</li>
        </ol>
      </div>
    </c:otherwise>
</c:choose>


<h3 class="page-header" style="margin-left: 15px; font-size: 21px">Price
	History of ${ curFund.name }
	<span style="font-size: 19px; float: right; margin-top: 10px; margin-right: 5px">
		    Ticker : ${curFund.symbol} ( Fund ID: ${curFund.fundId} )
	</span>
	</h3>
<jsp:include page="error-list.jsp" />

<div id="ex1"></div> <br>
<div class="table-responsive">
	<table class="table table-striped" style="width: 1000px">
	    <tbody>
			<tr>
				<th style="height:25px;vertical-align:middle; text-indent:50px; width: 200px;line-height:200%">Price Date History</th>
				<th style="vertical-align:middle; text-indent:50px; width: 200px;">Closing Price History</th>
			</tr>
		
			<c:forEach var="ph" items="${ fph }">
				<tr>
					<td style="text-indent:75px">
					  <fmt:formatDate value='${ph.priceDate}' var='pricedate'
						type='date' pattern='yyyy-MM-dd' /> ${ pricedate }</td>
					<td style="text-align:right;padding-right:610px">
					$<fmt:formatNumber value='${ph.price/100}' type='number' minFractionDigits='2' maxFractionDigits='2'/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> 

</div>
</div>
</div>

<!-- footer -->
<div class="footer">
	<div class="container">
		<div class="row">
			<div class="col-md-5">&nbsp;</div>
			<div class="col-md-7">
				<p>&copy; Carnegie Financial Services &nbsp; 2015</p>
			</div>
		</div>
	</div>
</div>

<!-- Bootstrap core JavaScript
	================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/docs.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="js/ie10-viewport-bug-workaround.js"></script>
<script type="text/javascript"
	src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1','packages':['corechart']}]}&language=en"></script>
<script type="text/javascript">

    google.load('visualization', '1', {packages: ['corechart']});
    google.setOnLoadCallback(drawChart);

    function drawChart() {

      var data = new google.visualization.DataTable();
      data.addColumn('date', 'Transition Date');
      data.addColumn('number', 'Closing Price');
      
      data.addRows([    
      <c:forEach var="ph" items="${ fph }">
	  <fmt:formatDate value='${ph.priceDate}' var='year' type='date' pattern='yyyy'/>
	  <fmt:formatDate value='${ph.priceDate}' var='month' type='date' pattern='MM'/>
	  <fmt:formatDate value='${ph.priceDate}' var='day' type='date' pattern='dd'/>
	  <fmt:formatNumber value='${ph.price/100}' var='price' type='number' minFractionDigits='2' maxFractionDigits='2'/>
	  [new Date(${year}, ${month - 1}, ${day}), ${price}],
	  </c:forEach>
      ]); 
      
      var options = {
        width: 1000,
        height: 563,
        /* curveType: 'function', */
        hAxis: {
          title: 'Transition Date'
        },
        vAxis: {
          title: 'Closing Price'
        },
        backgroundColor: '#f5f5f5'
      };

      var chart = new google.visualization.LineChart(document.getElementById('ex1'));
      chart.draw(data, options);
    }
    </script>

</body>
</html>
<!-- /footer -->
