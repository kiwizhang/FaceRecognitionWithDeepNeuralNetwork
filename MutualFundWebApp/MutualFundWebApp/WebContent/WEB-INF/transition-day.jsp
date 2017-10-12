<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Epoch DHTML JavaScript Calendar - Version 2.0.2 English Edition Primary JavaScript File(c) 2006-2007 MeanFreePath
Free for NON-COMMERCIAL use - see website for details and updates
http://www.meanfreepath.com/javascript_calendar/index.html-->

<script type="text/javascript" src="js/epoch_classes.js"></script>
<link rel="stylesheet" type="text/css" href="css/epoch_styles.css" />

<script type="text/javascript">
var bas_cal, dp_cal, ms_cal; // declare the calendars as global variables
window.onload = function () {
  /*initialize any calendars on the page - in this case 3.*/
  dp_cal  = new Epoch('dp_cal','popup',document.getElementById('date_field'));
}; 
</script>
<!--above: Epoch DHTML JavaScript Calendar  -->

<jsp:include page="empl-temp-top.jsp" />

                            <li class="active">Manage Transition</li>
						</ol>
					</div>
					<h3 class="page-header" style="margin-left: 15px;font-size:21px">Manage Transition</h3>
<jsp:include page="error-list.jsp" />

<form action="transitionDay.do" class="form-inline" method="POST">
<div style="margin-left: 15px; margin-top:30px; font-size: 19px"><strong>New Transition Date</strong></div>
<div style="margin-left: 15px; margin-top:10px; font-size: 15px"><strong>Please choose a date later than the last Transition Date.</strong></div>

<!--  JAN 27TH ADDED----------------------------------------------------->
                              
<!--The container for the standard verion of Epoch-->

<!--Datepicker container. The <input> text element is required, the button element is optional-->
<div style="margin-left: 15px;"> 
<!-- class="form-inline" -->
	<input type="text" id="date_field" name="date" readonly="readonly" class="form-control" style="width: 220"/>
	&nbsp;&nbsp;
	<input type="button" class="btn btn-primary" value="choose a date" onclick="dp_cal.toggle();" />
</div>

<h4 style="margin-left: 15px; margin-top:25px; font-size: 18px">Last Transition Date : ${fundList[0].executeDate}

</h4>
<!--Container for multiselect version of Epoch-->

<!--JAN 27TH ADDED ----------------------------------------------------- -->

<div style="margin-left: 15px; margin-top:35px; margin-bottom:15px; font-size: 19px"><strong>Fund Information</strong></div>
              <div class="table-responsive">
                <table class="table table-striped" style="width:980px;height:30px;border:hidden; margin-left: 15px;font-size: 14px">
                  <tbody>
                    <tr style="vertical-align: middle; font-size: 15px">
                      <th style="width:80px;text-align:center">Fund ID</th>
                      <th style="width:350px;text-align:center">Name</th>
                      <th style="width:100px;text-align:center">Ticker</th>
                      <th style="width:180px;text-align:center">Previous Closing Price</th>
                      <th style="width:240px;text-align:center">New Closing Price</th>
                    </tr>
                  
                  <c:forEach var="fund" items="${fundList}">
                    <tr>
                      <td style="vertical-align:middle;text-align:center">${ fund.fundId }</td>
                      <td style="vertical-align:middle"><a href="stats.do?fundId=${fund.fundId}">${fund.name} </a></td>
                      <td style="vertical-align:middle;text-align:center">${ fund.symbol }</td>
                      <td style="vertical-align:middle;text-align:right; padding-right:60px">
                        $<fmt:formatNumber type="number" value="${fund.price/100}" 
					      minFractionDigits="2" maxFractionDigits="2"/>
                      </td>
                     
                      <td style="padding-left:40px">
                        <div class="form-group">
                            <label class="sr-only" for="exampleInputAmount">Amount</label>
                              <div class="input-group">
                                <div class="input-group-addon" style="border: 1px solid">$</div>
                                <input type="hidden" name="ids" value="${ fund.fundId }">
                                <input type="text" class="form-control"
                                  style="border: 1px solid" placeholder="Dollar ( $1 to $1000 )" name="amount">
                              </div>
                        </div>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
              <div class="row">
                  <div class="col-md-9">
                    &nbsp;
                  </div>
                  <div class="col-md-3">
                    <div>
                      <input name="transition" class="btn btn-primary" style="width:185px" type="submit" value="Make Transition"> 
                    </div>
                  </div>
              </div>
            </form>
          </div>
        </div>
      </div>

     <!-- /body container -->

<jsp:include page="template-bottom.jsp" />
