// We create a request object when the page loads, 
// so that all our javascript methods can use it.
var request = createRequest();

//Method for creating the request object independent of the browser.
function createRequest() {
	var request = null;
	try {
	 	request = new XMLHttpRequest();
	} catch (trymicrosoft) {
		try {
			request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (othermicrosoft) {
			try {
				request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (failed) {
				request = null;
			}
		}
	}
	
	if (request == null) {
		alert("Error creating request object!");
	} else {
		return request;
	}
}
