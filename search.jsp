<!--Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Mar 23, 2012-->
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Friend Search List</title>
<link href="css/home.css" rel="stylesheet" type="text/css" />

</head>

<body onload="document.getElementById('query').select()">
<div class="pagewrapper">
<jsp:include page="header.jsp"/>
<jsp:include page="message-list.jsp" />
<div class="friendlist">
    	<ul>
        	<li>
            	<div class="friendsearchbox">
                	<form id="friendsearch" action="search.do">
                    	<input id="query" type="text"  onfocus="true" value="${form.query}" name="query" />
                        <input type="submit" value="?" />
                     </form>
                </div>
            </li>
            <c:if test="${empty result }">No friends found.</c:if>
            <c:forEach var="friend" items="${result }">
        	<li>
            	<div class="friend">
                	<a href="#" class="headimg">
                    	<img src="image.do?id=${friend.profilePicId }" />
                    </a>
                    <span>${friend.firstname } ${friend.lastname }</span>
                    <c:choose>
                    <c:when test="${user.id!=friend.id}">
                    <div class="del">
                    <form action="addFriend.do" method="post">
                    <input type="submit" value="add"/>
                    <input type="hidden" name="id" value="${friend.id }"/>
                    </form>
                    </div>
                    </c:when>
                    <c:otherwise>
                    <div class="del">Yourself</div>
                    </c:otherwise>
                    </c:choose>
                </div>
             </li>
             </c:forEach>
        </ul>
    </div>
    <c:if test="${test}" >
    <p>The input string can either be a word or two words with a blank between. In the first case result will include users f/l name begins with that word.
    in the second case either one is fname or last name. Input % to retrieve all users. Since the spec not asking to distinguish friend/non-friend, the function is not added in client side..</p>
    </c:if>
<jsp:include page="footer.jsp"/>
</div>
</body>
</html>
