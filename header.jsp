<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="header">
		<a href="home.do"  style="height:100%;text-decoration:none;color:white;"><div class="logodiv" >
			<span style="text-decoration:none;">TestSNS</span></div></a>
		<span>Welcome,
			<a href="profileUpdate.do">${user.firstname } ${user.lastname }</a>!(click
			to change profile settings.) 
			<span style="float:right;position:static;">Username:${user.username }   <a href="home.do" class="headimg"><img src="image.do?id=${user.profilePicId}" /></a> <span class="logout"><a href="logout.do"><input type="button" value="Logout" onclick=""></input></a></span></span>
	</div>
