<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="cust-temp-top.jsp"/>

           <li class="active">Research Fund</li>
		</ol>
	</div>
<h3 class="page-header" style="margin-left: 15px;font-size:21px">Fund List</h3>
<jsp:include page="error-list.jsp" />
			
              <div class="row placeholders">
               <%-- <c:forEach var="fund" items="${ latestFund }" varStatus="count"> --%>
                <div class="col-xs-6 col-sm-3 placeholder">
                  <%-- <a href="stats.do?fundId=${fund.fundId}">
                  <img src="img/fund${fund.fundId}.png" class="img-rounded" alt="Generic placeholder thumbnail">
                  </a> --%>
                  <img src="img/fund1.png" class="img-rounded" alt="Generic placeholder thumbnail">
                  <!-- <h4>Fund 1</h4> -->
                </div>
                <%-- </c:forEach>  --%>
                 <div class="col-xs-6 col-sm-3 placeholder">
                  <img src="img/fund2.png" class="img-rounded" alt="Generic placeholder thumbnail">
                  <!-- <h4>Fund 2</h4> -->
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                  <img src="img/fund3.png" class="img-rounded" alt="Generic placeholder thumbnail">
                  <!-- <h4>Fund 3</h4> -->
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                  <img src="img/fund4.png" class="img-rounded" alt="Generic placeholder thumbnail">
                  <!-- <h4>Fund 4</h4> -->
                </div> 
              </div> 

              <h3 style="margin-left:15px;font-size:21px">Fund Information</h3>
              <div class="table-responsive">
                <table class="table table-striped" style="width:1000px;height:30px;margin-left:15px">
                  <tbody>
                    <tr style="font-size:15px">
                      <th style="height:40px; vertical-align:middle;text-align:center;width:40px;line-height:200%">#</th>
                      <th style="vertical-align:middle;text-align:center;width:270px;">Fund Name</th>
                      <th style="vertical-align:middle;text-align:center;width:110px;">Ticker</th>
                      <th style="vertical-align:middle;text-align:center;width:150px;">Transition Date</th>
                      <th style="vertical-align:middle;text-align:center;width:150px;">Closing Price</th>
                      <th style="vertical-align:middle;text-align:center;width:180px;">Research</th>
                    </tr>
                  <c:forEach var="fund" items="${ latestFund }" varStatus="count">
                    <tr>
                      <td style="vertical-align:middle;text-align:center;"> ${count.index+1} </td>
                      <td style="vertical-align:middle;text-indent:10px"> <a href="stats.do?fundId=${fund.fundId}">${fund.name} </a></td>
                      <td style="vertical-align:middle;text-align:center;"> ${fund.symbol} </td>
                      <td style="vertical-align:middle;text-align:center;">  
                        <fmt:formatDate value='${fund.priceDate}' type='date' pattern='yyyy-MM-dd'/>
                      </td>
                      <td style="vertical-align:middle;text-align:right;padding-right:50px">  
                        $<fmt:formatNumber value='${fund.price/100}' type='number' minFractionDigits='2' maxFractionDigits='2'/>
                      </td>
                      <td style="text-align:center;">
                        <a href="stats.do?fundId=${fund.fundId}" class="btn btn-primary btn-sm" style="width:90px">Statistics</a></td>
                    </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>

<jsp:include page="template-bottom.jsp" />
