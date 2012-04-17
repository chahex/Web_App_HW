<!--Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Mar 23, 2012-->
<%--This page works. However in HW4 the comment is added when "add comment" button is pressed. --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TestSNS - Add new Comment</title>

<style>
	dt{
		display:inline-block;
		width:100px;
	}
	dd{
		width:80px;
		display:inline-block;
	}
</style>
<link href="css/home.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="pagewrapper">
<jsp:include page="header.jsp"/>
    <div class="content">
    <form method="post" action="addPost.do">
	<input type="hidden" name="type" value="comment"/>
    <%--The story id are from last page. So it is actually awkward to post a comment like this. --%>
	<input type="hidden" name="storyId" value="${form.storyId}"/>
        <dl>
            <dd><label for="commentcontent">Comment:</label></dd>
            <dt><textarea id="commentcontent" rows="10" id="commentcontent" name="content">${form.content}</textarea></dt>
        </dl>
        <dl>
            <dd></dd>
            <dt><input type="submit" value="Submit"/></dt>
        </dl>
    </form>
    </div>
    <jsp:include page="footer.jsp"/>
    </div>
</body>
</html>
