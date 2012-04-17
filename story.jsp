<!--Xinkai He, xinkaih@andrew.cmu.edu, 46-864, Mar 23, 2012-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TestSNS - Post new Story</title>
<link href="css/home.css" rel="stylesheet" type="text/css" />
<style>
	dt{
		display:inline-block;
		width:100px;
	}
	dd{
		width:50px;
		display:inline-block;
	}
</style>
</head>

<body>
<div class="pagewrapper">
<jsp:include page="header.jsp" />
	<div class="header"></div>
    <div class="content"></div>
    <form method="post" action="addPost.do">
    <input type="hidden" name="type" value="story"/>
        <dl>
            <dd><label for="title">Title:</label></dd>
            <dt><input id="title" class="title" name="title" type="text" value="${form.title}"/></dt>
        </dl>
        <dl>
            <dd></dd>
            <dt><textarea rows="10" name="content">${form.content }</textarea></dt>
        </dl>
        <dl>
            <dd></dd>
            <dt><input type="submit" value="submit"/></dt>
        </dl>
    </form>
<jsp:include page="footer.jsp"/>
</div>
</body>
</html>
