var block = document.createElement("div");
var typeBox = "<input type=text size=50 id=typeBox></input>";
var streetBox = "<input type=text size=50 id=streetBox></input>";
var cityBox = "<input type=text size=50 id=cityBox></input>";
var stateBox = "<input type=text size=50 id=stateBox></input>";
var zipBox = "<input type=text size=50 id=zipBox></input>";

var createButton = document.createElement("input");
createButton.type = "button";
createButton.value = "Create";
createButton.addEventListener("click", createAddress());

var cancelButton = document.createElement("input");
cancelButton.type = "button";
cancelButton.value = "Cancel";
cancelButton.addEventListener("click", cancelAction());

block.innerHTML = "Type: " + typeBox + "<br>"
				+ "Street: " + streetBox + "<br>"
				+ "City: " + cityBox + "<br>"
				+ "State: " + stateBox + "<br>"
				+ "Zip Code: " + zipBox + "<br>";

block.appendChild(cancelButton);
block.appendChild(createButton);

document.body.appendChild(block);

function getContactId()
{
	 var urlString = window.location.search;
	 var urlParam = new URLSearchParams(urlString);
	 var contactId = urlParam.get("contactId");
	 return contactId;
}

function getInputValues()
{
	var jsonObject = new Object();
	jsonObject.type = document.getElementById("typeBox").value;
	jsonObject.street = document.getElementById("streetBox").value;
	jsonObject.city = document.getElementById("cityBox").value;
	jsonObject.state = document.getElementById("stateBox").value;
	jsonObject.zipCode = document.getElementById("zipBox").value;
	var jsonString = JSON.stringify(jsonObject);
	return jsonString; 
}

function createAddress()
{
	return function()
	{
		var jsonString = getInputValues();
		var contactId = getContactId();
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("POST", "http://localhost:8080/createAddress/".concat(contactId), true);
		xmlHttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
		xmlHttp.onload = function()
		{
			if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
				{
					console.log("Success");

				}
		};
		xmlHttp.send(jsonString);
		setTimeout(function(){toViewPage()}, 1000);
	}

}

function cancelAction()
{
	return function()
	{
		toViewPage();
	}
}

function toViewPage()
{
	var id = getContactId();
	window.location.href = "viewPage.html?contactId=".concat(id, "?eraseCache=true");
}