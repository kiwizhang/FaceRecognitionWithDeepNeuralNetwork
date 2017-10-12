<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<p class="content" style="font-size:medium; color:red;margin-top:8px;margin-left:15px">
<c:set var="errors" value="${ errors }"/>
<c:if test="${ errors != null && fn:length(errors) > 0 }">
    <c:forEach var="error" items="${ errors }">
        ${error} <br/>
    </c:forEach>
</c:if>
</p>

<p class="content" style="font-size:medium;color:#44C881; margin-top:8px;margin-left:15px">
   ${message}
</p>