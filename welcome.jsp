<!--Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Mar 23, 2012-->
<%--Change log: Feb 9, trim the string when read from parameter and after null check. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to TestSNS, Login Page</title>
<script src="js/ajax.js" type="text/javascript"></script>
<script src="js/model.js" type="text/javascript"></script>
<link href="css/home.css" rel="stylesheet" type="text/css" />
</head>
<body onload="processRequest();">
<div class="pagewrapper">
<c:if test="${userCount==0 }">
<div>
<p style="color:red;">This message will only appear when userDAO.getCount() returns 0.</p>
<p>Dear grader: please click the link to populate the database with testing data.:<a href="test.do">Populating database.</a></p>
<p>The script is in controller.TestAction, it will automatically create 20 users and make friends, adding stories and comments for them.</p>
<p>After population, you can login with user name [test1] through [test20] and password of [123]</p>
<p>Note: the action first tests the count of the table related to DAOs, if table entries sum is not 0, it will throw error message.</p>
</div>
</c:if>
	<div class="header">
		<div>
			<h1>Welcome!</h1>
		</div>
		<div style="text-align: right" class="usercount">Join the
			${userCount } friends who's using Test!</div>
		<div style="text-align: right" class="friendrelations">Check out
			over ${friendshipCount} friend relationships.</div>
		<%--Error page included --%>
		<jsp:include page="message-list.jsp" />
		<div class="login">
			<form action="login.do" method="get">
				<table>
					<thead>
						<tr>
							<td>User name:</td>
							<td>Password:</td>
							<td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" name="username"
								value="${form.username }" /></td>
							<td><input type="password" name="password" /></td>
							<td><input type="submit" value="Login" /></td>
							<td><a href="register.do"><input type="button"
									value="Sign up" /></a></td>
							<td><a href="#">Forgotten Password?</a></td>
						</tr>
					</tbody>
				</table>
				<%--If there is redirect to link, send redirect. --%>
				<c:if test="${!empty form.redirectTo}"><input type="hidden" name="redirectTo" value="${form.redirectTo}"/></c:if>
			</form>
		</div>
	</div>

	<!--Better left align-->
	<div class="registration"></div>
	<!--Hottest Users-->
	<div class="hotuserlist" style="padding-right: 1em;">
		<table style="size: parent">
			<thead>
				<tr>
					<td colspan="3">Hottest Users</td>
				</tr>
			</thead>
			<tbody id="hotUsers">
			</tbody>
		</table>
	<c:if test="${test}"><p>TestNote: I think in real application the users won't be none. So 'hottest users' title does not have empty check.</p></c:if>
	</div>

	<div style="text-align: center">Copyright? 2012 Test. All rights
		reserved.</div>
</div>
</body>
</html>