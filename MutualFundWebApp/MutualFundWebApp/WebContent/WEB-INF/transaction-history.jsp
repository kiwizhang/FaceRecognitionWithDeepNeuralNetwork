<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
    <c:when test="${ isEmployee == false }">
        <jsp:include page="cust-temp-top.jsp"/>
        <li class="active">View Transaction History</li>
    </ol>
</div>
<div>
	<h3 class="page-header" style="margin-left: 15px; font-size: 21px">
		View Transaction History 
		<span style="font-size: 19px; float: right; margin-top: 10px; margin-right: 5px">
		    Customer : ${customer.firstName} ${customer.lastName} ( ID : ${customer.customerId} )
		</span>
	</h3>
    </c:when>
    <c:otherwise>
        <jsp:include page="empl-temp-top.jsp"/>
        <li><a href="customerList.do">Manage Customer Account</a></li>
        <li class="active">View Transaction History</li>
    </ol>
</div>
<div>
	<h3 class="page-header" style="margin-left: 15px">
		View Transaction History 
		<span style="font-size: 19px; float: right; margin-top: 10px; margin-right: 5px">
		    Customer : ${currentCustomer.firstName} ${currentCustomer.lastName} ( ID: ${currentCustomer.customerId} )
		</span>
	</h3>
    </c:otherwise>
</c:choose>

<jsp:include page="error-list.jsp" />
                  
                  <div class="col-md-12" style="margin-top: 15px">
                  <div style="float:right; margin-right:5px; margin-bottom:10px">
                  <form role="form" action="viewAccount.do" method="POST">
		            <input type="hidden" name="id" value="${currentCustomer.customerId}">
		            <input class="btn btn-sm btn-primary" value="View Account" type="submit"
		            	style="font-size: 15px; float:right;margin-bottom:10px; width: 210px">
		         </form>
                  </div>
                    <table class="table table-striped" style="width:995px;height:30px;">
                     <tbody>
                       <tr style="text-align:center; font-size:15px">
                         <td style="height:40px; vertical-align:middle;width: 25px;line-height:200%"><strong>#</strong>
                         </td>
                         <td style="vertical-align:middle;width: 100px"><strong>Date</strong>
                         </td>
                         <td style="width: 110px;vertical-align:middle;"> <strong>Operation</strong>
                         </td>
                         <td style="width: 310px;vertical-align:middle;"> <strong>Fund Name</strong>
                         </td>
                         <td style="width: 65px;vertical-align:middle;"> <strong>Ticker</strong>
                         </td>
                         <td style="width: 80px;vertical-align:middle;"> <strong>Shares</strong>
                         </td>
                         <td style="width: 100px;vertical-align:middle;"> <strong>Share Price</strong>
                         </td>
                         <td style="width: 130px;vertical-align:middle;"> <strong>Dollar Amount</strong>
                         </td>
                       </tr>
                       
                       <c:forEach var="trans" items="${allTrans}" varStatus="count">
                           <tr style="text-align:center; vertical-align:middle;">
                             <td style="height:40px; vertical-align: middle;text-align:center;line-height:200%">${count.index+1}</td>
                             <td style="vertical-align:middle;">
                               <c:choose>
                                 <c:when test="${ trans.executeDate == null }">
                                   Pending
                                 </c:when>
                                 <c:otherwise>
                                   <fmt:formatDate pattern="yyyy-MM-dd" value="${ trans.executeDate }" />
                                 </c:otherwise>
                               </c:choose>
                             </td>
                             <td style="vertical-align:middle;"> ${trans.transactionType}
                             </td>
                             <td style="vertical-align:middle;text-align:left;text-indent:10px"> ${trans.name}
                             </td>
                             <td style="vertical-align:middle;"> ${trans.symbol}
                             </td>
                             <td style="vertical-align:middle; text-align:right;padding-right:6px">
                               <c:choose>
                                 <c:when test="${ trans.shares == 0 }">
                                 </c:when>
                                 <c:otherwise>
                                   <fmt:formatNumber type="number" value="${trans.shares/1000}" 
					                 minFractionDigits="3" maxFractionDigits="3"/>
                                 </c:otherwise>
                               </c:choose>
                             </td>
                             <td style="vertical-align:middle; text-align:right;padding-right:15px">
                               <c:choose>
                                 <c:when test="${ trans.price == 0 }">
                                 </c:when>
                                 <c:otherwise>
                                   $<fmt:formatNumber type="number" value="${trans.price/100}" 
					                 minFractionDigits="2" maxFractionDigits="2"/>
                                 </c:otherwise>
                               </c:choose>
                             </td>
                             <td style="vertical-align:middle;text-align:right; padding-right:10px">
                               <c:choose>
                                 <c:when test="${ trans.amount == 0 }">
                                 </c:when>
                                 <c:otherwise>
                                   $<fmt:formatNumber type="number" value="${trans.amount/100}" 
					                 minFractionDigits="2" maxFractionDigits="2"/>
                                 </c:otherwise>
                               </c:choose>
                             </td>
                           </tr>
                        </c:forEach>
                     </tbody>
                   </table>
                 </div>
                 <br><br><br>
               </div>
             </div>
           </div>
         </div>
         <!-- /body container -->

<jsp:include page="template-bottom.jsp" />
