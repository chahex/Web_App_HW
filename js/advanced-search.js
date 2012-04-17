// This method is called when the keyUp event occurs in some of the inputs in the form.
// This method gets all the parameters and sends them in a request to the server.
function advancedSearch() {
	//We send the request to the server if the request object is ready.
	if (request.readyState != 0) return;

	var url="advanced-search-ajax.do";

	//We set the callback page to updatePage.
	request.onreadystatechange = updatePage;
	
	//We open a POST request.
	request.open("POST",url,true);
	
	//Because the request is POST we must set its content-type
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	//We send the request.
	request.send("lastName="+escape(document.getElementById("lastName").value)+
			"&firstName="+escape(document.getElementById("firstName").value)+
			"&anyPhone="+escape(document.getElementById("anyPhone").value)+
			"&email="+escape(document.getElementById("email").value)+
			"&additional="+escape(document.getElementById("additional").value)+
			"&address="+escape(document.getElementById("address").value)+
			"&city="+escape(document.getElementById("city").value)+
			"&state="+escape(document.getElementById("state").value)+
			"&country="+escape(document.getElementById("country").value)+
			"&zip="+escape(document.getElementById("zip").value)+
			"&receivedCards="+escape(document.getElementById("receivedCards").value)+
			"&sentCards="+escape(document.getElementById("sentCards").value));
}

//This method receives the response from the server, and updates the DOM with the search results.
function updatePage() {
	if (request.readyState != 4) return;
	
	if (request.status != 200) {
		alert("Error, request status is "+request.status);
		return;
	}

	//We clean the last search results from the HTML DOM.
	cleanSearchResults();
	
	//We get the XML from the server.
	var xmlDoc=request.responseXML;
			
	//We display each of the results in the HTML DOM.
	var results = xmlDoc.getElementsByTagName("result");
	
	if (results.length > 0) {
		//We create the heading that displays "Search Results:"
		var searchResultsEl = document.getElementById("searchResults");
		var headingEl = document.createElement("h2");
		var headingText = document.createTextNode("Search Results");
		searchResultsEl.appendChild(headingEl);
		headingEl.appendChild(headingText);
	}

	for (var i=0; i<results.length; i++){
		//We get the parameters from the XML doc.
		var id        = results[i].getElementsByTagName("id")[0].firstChild.nodeValue;
		var last      = results[i].getElementsByTagName("lastName")[0].firstChild.nodeValue;
		var first     = results[i].getElementsByTagName("firstNames")[0].firstChild.nodeValue;
		
		//We add the results to the DOM
		
		var aEl = document.createElement("a");
		aEl.href = "lookup-entry.do?id="+id;
		aEl.appendChild(document.createTextNode(last + ", "+ first));
		searchResultsEl.appendChild(aEl);

		var brEl = document.createElement("br");
		searchResultsEl.appendChild(brEl);
	}
	
	// After finishing processing the request, we create a new
	// request object for future requests.
	request = createRequest();
}

// This method cleans the search results from the HTML.
function cleanSearchResults() {
	var searchResultsEl = document.getElementById("searchResults");
	while (searchResultsEl.hasChildNodes()) {
		searchResultsEl.removeChild(searchResultsEl.firstChild);
	}
}
 			