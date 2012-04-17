<!--Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Mar 23, 2012-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--
Since the action of the register page could only be register, there will be no action scene devised
 But the action could be implemented as:
 
 --if action is null or is action.length()==0, read other parameters as well, and fill them to the blanks
 --And the tip should be: Missing action
 --if with action, then check all the things left, if there is only an action and no other parameters
 --just return "missing parameters". Note, we don't need existNull and existNotNull to be true to 
 --print this out, in the past this is only a condition where we think a new request.
 --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/home.css" rel="stylesheet" type="text/css" />
<title>Welcome to TestSNS - Register</title>
<!--make label and content in the same line display-->

<style>
body,h,p,li,ul,ol,div,span {
	margin: 0;
	padding: 0;
}

dd,dt {
	width: 200px;
	display: inline;
}
</style>

</head>

<body>
<div class="pagewrapper">
	<div class="head">
		<h1>Register</h1>
	</div>
	<%--Error page included --%>
	<jsp:include page="message-list.jsp" />
	<div>
		<form action="register.do" method="post">
			<dl>
				<dt>
					<label for="username">User name:</label>
				</dt>
				<dd>
					<input id="username" name="username" value="${ form.username }"
						type="text" />
				</dd>
			</dl>
			<dl>
				<dt>
					<label for="password">Password:</label>
				</dt>
				<dd>
					<input id="password" name="password" type="password" />
				</dd>
			</dl>
			<dl>
				<dt>
					<label for="fname">First Name:</label>
				</dt>
				<dd>
					<input id="fname" name="fname" value="${ form.fname }" type="text" />
				</dd>
			</dl>
			<dl>
				<dt>
					<label for="lname">Last Name:</label>
				</dt>
				<dd>
					<input id="lname" name="lname" value="${ form.lname}" type="text" />
				</dd>
			</dl>
			<dl>
				<dt>
					<label for="timezone">Time Zone:</label>
				</dt>
				<dd>
					<select id="timezone" name="timezone">
<c:forEach var="gmtId" items="${gmtIDStrings}">
<c:choose>
<%--If form does not provide time zone--%>
<c:when test="${!form.present}"><c:if var="ff" test="${gmtId=='US/Eastern'}"><option value="${gmtId }" selected="selected">${gmtId}</option></c:if><c:if test="${!ff }"><option value="${gmtId}">${gmtId}</option>	</c:if></c:when>
<c:otherwise>
<%--If form provides the time zone --%>
<c:if test="${gmtId==form.timezone }" var="fp"><option value="${gmtId}" selected="selected">${gmtId}</option></c:if>
<c:if test="${!fp }"><option value="${gmtId}">${gmtId}</option></c:if>
</c:otherwise>
</c:choose></c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dd>
					<input type="submit" name="action" value="Register" />
				</dd>
			</dl>
		</form>
	</div>
	</div>
</body>
</html>