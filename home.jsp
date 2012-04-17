<!--Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Mar 23, 2012-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TestSNS Home</title>

<link href="css/home.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/ajax.js"> </script>
<script type="text/javascript" src="js/model.js"> </script>

</head>

<body onload="homeonload();">
<div class="pagewrapper">
<jsp:include page="header.jsp"/>
<jsp:include page="message-list.jsp" />
<div class="friendlist" style="float:right;position:relative;display:block;">
		<ul>
		<li>
				<div class="friendsearchbox" id="searchbox">
					<form method="get" action="search.do" id="friendsearch">
						<input type="text" value="Input name to search friend..."
							name="query" /> <input type="submit" value="?" />
					</form>
				</div>
		</li>
		<li>My Friends</li>
		<hr/>
			<c:if test="${empty friends }">
			<li>Don't have any friends yet? Click search to find friends.</li>
			</c:if>
		<c:forEach items="${friends}" var="friend">
			<li>
				<div class="friend">
					<a href="#" class="headimg"> <img src="image.do?id=${friend.profilePicId}" />
					</a> <span>${friend.firstname} ${friend.lastname}</span>
					<div class="del">
					<form action="disFriend.do" method="post">
						<input type="submit" value="del"/>
						<input type="hidden" value="${friend.id }" name="id"/>
					</form>
					</div>
				</div>
			</li>
			<hr/>
			</c:forEach>
		</ul>
</div>
	<div class="profiledesc">
		<p style="font-size:1em;">Personal Introduction</p>
		<hr/>
		<c:if test="${empty user.profileText }"><p style="color:grey;font-size:0.5em;">Not filled yet. Enter in profile page.</p></c:if>
		<div>${user.profileText }</div>
		<%--Type specify both time and date,  now is filled on top of the page as bean--%>
		<span class="servertime">Current Time is: <fmt:formatDate type="both" value="${now}" timeZone="${user.timezone }" pattern="yyyy-MM-dd hh:mm:ss z"/></span>
	</div>

	<div class="posts">
		<div class="newpost">
			<a href="addStory.do"><input type="button" value="Post new Story" /></a>
		</div>
		<c:forEach items="${storyView.stories}" var="story">
		<div class="post">
			<a class="postheadimg" href="#"><img src="image.do?id=${story.picId}" /></a>
			<div class="entry">
				<c:if test="${user.id==story.ownerId}">
				<div class="del">
				<form method="post" action="deletePost.do">
				<input type="submit" value="del" ></input>
				<input type="hidden" name="id" value="${story.id}"/>
				<input type="hidden" name="ownerId" value="${story.ownerId}"/>
				<input type="hidden" name="type" value="story"/>
				</form>
				</div>
				</c:if>
				<h6>
					<a href="#">${story.ownerFirstname } ${story.ownerLastname}</a>
				</h6>
				<div class="story">${story.content }</div>
				<div class="storystat"><fmt:formatDate type="both" value="${story.date}" timeZone="${user.timezone }" pattern="yyyy-MM-dd hh:mm:ss z"/></div>
				<c:set var="sid" value="${story.id}"/>
				<div class="comments">
				<c:forEach items="${storyView.cmtMap[story.id]}" var="comment">
					<div class="comment">
						<a class="headimg" href="#"><img src="image.do?id=${comment.picId}" /></a>
						<c:choose>
						<c:when test="${story.ownerId==user.id}">
						<div class="del">
							<form method="post" action="deletePost.do">
							<input type="submit" value="del" />
							<input type="hidden" name="id" value="${comment.id}"/>
							<input type="hidden" name="type" value="comment"/>
							<%--Comment deletion sends back the ownerId and comment's parent story's ownerId too. --%>
							<input type="hidden" name="ownerId" value="${comment.ownerId}"/>
							<input type="hidden" name="storyOwnerId" value="${comment.storyOwnerId}"/>
							</form>
						</div>
						</c:when>
						<%--If story does not belongs to current user, but owner of comment is current user, deletable --%>
						<c:otherwise>
						<c:choose>
						<c:when test="${comment.ownerId==user.id}">
						<div class="del">
							<form method="post" action="deletePost.do">
							<input type="submit" value="del" ></input>
							<input type="hidden" name="id" value="${comment.id}"/>
							<input type="hidden" name="type" value="comment"/>
							<%--Comment deletion sends back the ownerId and comment's parent story's ownerId too. --%>
							<input type="hidden" name="ownerId" value="${comment.ownerId}"/>
							<input type="hidden" name="storyOwnerId" value="${comment.storyOwnerId}"/>
							</form>
						</div>
						</c:when>
						</c:choose>
						</c:otherwise>
						</c:choose>
						<div class="commentcontent">
							<a href="#">${comment.ownerFirstname} ${comment.ownerLastname}</a> <span>${comment.content}</span>
							<div class="commentstat"><fmt:formatDate type="both" value="${comment.date}" timeZone="${user.timezone }" pattern="yyyy-MM-dd hh:mm:ss z"/></div>
						</div>
					</div>
					<!--End of comment-->
				</c:forEach>
					<div class="addnewcomment">
						<form action="addPost.do" onsubmit="javascript:addNewComment();"
							method="post">
							<input type="hidden" name="type" value="comment"/>
							<input type="hidden" name="storyId" value="${story.id}"/>
							<input id="newcomment" type="text" value="Add new comment." name="content"/> <input type="submit" value="Add Comment" />
					<script>
				</script>
						</form>
					</div>
				</div><!--End of comments-->
			</div><!-- End of entry. -->
			</div> <!-- End of post. -->
		</c:forEach>
		</div>
		<!--End of post-->
<jsp:include page="footer.jsp"/>
</div>
</body>

</html>
