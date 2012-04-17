var temp="";
function processRequest(invoker) {
	if (request.readyState != 0) return;
	var url = "hottestUsersAjax.do";
	request.onreadystatechange = showResult;
	request.open("GET", url, true);
	request.send(null);
}

function showResult() {
	if (request.readyState != 4)
		return;

	if (request.status != 200) {
		alert("Error, request status is " + request.status);
		return;
	}
	
	// TODO here the results is the key word that should be documented
	var xmlDoc = request.responseXML;
	var results = xmlDoc.getElementsByTagName("result");
	var user = new Object();
	var div = document.getElementById("hotUsers");
	var text = "";
	for (var i=0; i<results.length&&i<3; i++) {

		user.id        = results[i].getElementsByTagName("id")[0].firstChild.nodeValue;
		user.firstname     = results[i].getElementsByTagName("lastname")[0].firstChild.nodeValue;
		user.lastname     = results[i].getElementsByTagName("firstnames")[0].firstChild.nodeValue;
		user.friendCount    = results[i].getElementsByTagName("friendCount")[0].firstChild.nodeValue;
		user.profilePicId =  results[i].getElementsByTagName("profilePicId")[0].firstChild.nodeValue;
		
		var html = "<tr><td rowspan=\"2\"><img style=\"height: 3em; width: 3em;\"class=\"userhead\" src=\"image.do?id="+user.profilePicId+"\"/></td><td><a href=\"#\"/>"+user.firstname+" "+user.lastname+"</a></td><td><span>"+user.friendCount+" Friends</span></td></tr><tr></tr>";
		text = text+html;
	}
	div.innerHTML = text;
	request = createRequest();
}

function findtar(e){
	var targ; 
	if (!e) e = window.event;
	if (e.target) targ = e.target;	
	else if (e.srcElement) targ = e.srcElement;
	if (targ.nodeType == 3) // defeat Safari bug
	targ = targ.parentNode;
	return targ;
};
function clear(e){
	var tar = findtar(e);
	if(tar.value){
	temp = tar.value;
	tar.value='';
	}
};
function retrieve(e){
		var tar = findtar(e);
		if(temp&&!tar.value){
			tar.value=temp;
		temp=null;
		}
};

function homeonload(){
	try{
	document.getElementById('newcomment').onclick=clear;
	document.getElementById('newcomment').onblur=retrieve;
	}catch(notexist){
	}
	
	document.getElementById('searchbox').onclick=clear;
	document.getElementById('searchbox').onblur=retrieve;
}