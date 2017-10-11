<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
    <c:when test="${ isEmployee == false }">
        <jsp:include page="cust-temp-top.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="empl-temp-top.jsp"/>
    </c:otherwise>
</c:choose>

<jsp:include page="error-list.jsp" />

<p class="content" style="font-size:large; color:red; margin-top:8px; margin-left:15px">
    ${message}
</p>

<br> <br> <br>
</div>
</div>
</div>
</div>

<jsp:include page="template-bottom.jsp" />
