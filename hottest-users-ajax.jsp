<?xml version="1.0" encoding="utf-8"?>
<%--Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Mar 23, 2012--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% response.setHeader("Content-Type","text/xml"); %>
<results>
<c:forEach var="huser" items="${hottestUsers}">
	<result>
		<id><c:out value="${huser.userId}"/></id>
		<firstnames><c:out value="${huser.firstname}"/></firstnames>
		<lastname><c:out value="${huser.lastname}"/></lastname>
		<profilePicId><c:out value="${huser.profilePicId}"/></profilePicId>
		<friendCount><c:out value="${huser.friendCount}"/></friendCount>
	</result>
</c:forEach>
</results>