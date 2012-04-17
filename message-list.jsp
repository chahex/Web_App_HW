<%--Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Mar 23, 2012--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty errors}">
<ul class="msg">
<c:forEach var="msg" items="${messages}">
	<li>${msg}</li>
</c:forEach>
</ul>
</c:if>
<c:if test="${!empty errors}">
<ul class="msg err">
<c:forEach var="error" items="${errors}">
	<li>${error}</li>
</c:forEach>
</ul>
</c:if>