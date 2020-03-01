var block = document.createElement("div");
var typeBox = "<input type=text size=50 id=typeBox></input>";
var numberBox = "<input type=text size=50 id=numberBox></input>";

var createButton = document.createElement("input");
createButton.type = "button";
createButton.value = "Create";
createButton.addEventListener("click", createPhone());

var cancelButton = document.createElement("input");
cancelButton.type = "button";
cancelButton.value = "Cancel";
cancelButton.addEventListener("click", cancelAction());

block.innerHTML = "Type: " + typeBox + "<br>"
				+ "Number: " + numberBox + "<br>";

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
	jsonObject.number = document.getElementById("numberBox").value;
	var jsonString = JSON.stringify(jsonObject);
	console.log(jsonString);
	console.log(typeof jsonString);
	return jsonString; 
}

function createPhone()
{
	return function()
	{
		var jsonString = getInputValues();
		var contactId = getContactId();
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("POST", "http://localhost:8080/createPhone/".concat(contactId), true);
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