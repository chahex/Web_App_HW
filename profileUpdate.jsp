<!--Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Mar 23, 2012-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TestSNS - Update Profile</title>
<link href="css/home.css" rel="stylesheet" type="text/css" />
<style>
	dt{
		display:inline-block;
		width:180px;
	}
	dd{
		width:200px;
		display:inline-block;
	}
	dd input, select, textarea{
		width:inherit;
	}
	
	.content{
		width:500px;
	}
	
	#headimg{
		padding:5px;
		border:2px solid #6FC;
		width:60px;
	}
	
	#userinfo{
		
	}
	
	#submitbtn{
		float:right;
	}
</style>

</head>
<body>
<div class="pagewrapper">
<jsp:include page="header.jsp"/>
<div class="header">
<h1>Update Profile</h1>
</div>
<jsp:include page="message-list.jsp" />
<div class="content">
	<form action="photoUpload.do" method="post"  enctype="multipart/form-data">
	<img id="headimg" src="image.do?id=${user.profilePicId }"/>
	<dl>
    	<dt><label for="file">Change Profile Picture.</label></dt>
        <dd><input name="file" id="file" type="file"/><input type="submit" name="button" value="Submit Picture"/></dd>
    </dl>
	</form>
	
	<form action="profileUpdate.do" method="post">
    <dl>
    	<dt><label for="username">Username:</label></dt>
        <dd><input type="text" id="username" value="${user.username}" disabled="disabled"/></dd>
    </dl>
    <dl>
    	<dt><label for="fname">First Name:</label></dt>
        <dd><input id="fname" name="fname" type="text" value="${form.fname }"/></dd>
   	</dl>
    <dl>
  		<dt><label for="lname">Last Name:</label></dt>
        <dd><input id="lname" name="lname" type="text" value="${form.lname}"/></dd>
    </dl>
    <dl>
        <dt><label for="timezone">Time Zone:</label></dt>
        <dd>
            <select id="timezone" name="timezone">
        	<c:forEach var="gmtId" items="${gmtIDStrings}">
<c:if test="${gmtId==form.timezone }" var="fp"><option value="${gmtId}" selected="selected">${gmtId}</option></c:if>
<c:if test="${!fp }"><option value="${gmtId}">${gmtId}</option></c:if>
         	</c:forEach>
            </select>
         </dd>
     </dl>
     <dl>
    	<dt><label for="profileText"></label></dt>
        <dd><textarea rows="5" id="profileText" name="profileText">${form.profileText}</textarea></dd>
    </dl>
    <dl>
    	<dt></dt>
    	<dd><input id="submitbtn" type="submit" value="Update"/></dd>
    </dl>
    </form>
</div>
<jsp:include page="footer.jsp"/>
</div>
</body>
</html>