//name
var nameBlock = document.createElement("div");
nameBlock.id = "nameBlock";
var firstBox = "<input type=text size=50 id=firstBox></input>";
var lastBox = "<input type=text size=50 id=lastBox></input>";

var nCreateButton = document.createElement("input");
nCreateButton.type = "button";
nCreateButton.value = "Create";

var nCancelButton = document.createElement("input");
nCancelButton.type = "button";
nCancelButton.value = "Cancel";

nameBlock.innerHTML = "First Name: " + firstBox + "<br>"
					+ "Last Name: " + lastBox + "<br>";

//address 
var addressBlock = document.createElement("div");
addressBlock.id = "addressBlock";
var aTypeBox = "<input type=text size=50 id=aTypeBox></input>";
var streetBox = "<input type=text size=50 id=streetBox></input>";
var cityBox = "<input type=text size=50 id=cityBox></input>";
var stateBox = "<input type=text size=50 id=stateBox></input>";
var zipBox = "<input type=text size=50 id=zipBox></input>";

addressBlock.innerHTML = "Type: " + aTypeBox + "<br>"
				+ "Street: " + streetBox + "<br>"
				+ "City: " + cityBox + "<br>"
				+ "State: " + stateBox + "<br>"
				+ "Zip Code: " + zipBox + "<br>";

//phone
var phoneBlock = document.createElement("div");
phoneBlock.id = "phoneBlock";
var pTypeBox = "<input type=text size=50 id=pTypeBox></input>";
var numberBox = "<input type=text size=50 id=numberBox></input>";

phoneBlock.innerHTML = "Type: " + pTypeBox + "<br>"
					+ "Number: " + numberBox + "<br>";

nCreateButton.addEventListener("click", createContact(addressBlock.id, phoneBlock.id, nameBlock.id));
nCancelButton.addEventListener("click", cancelAction());

//for the buttons
var block = document.createElement("div");
block.appendChild(nCancelButton);
block.appendChild(nCreateButton);

//appending 
document.write("<h3>NAME</h3>");
document.body.appendChild(nameBlock);
document.write("<hr>");

document.write("<h3>ADDRESS</h3>");
document.body.appendChild(addressBlock);
document.write("<hr>");

document.write("<h3>PHONE</h3>");
document.body.appendChild(phoneBlock);
document.write("<hr>");
document.body.appendChild(block);

function getInputValues(id)
{
	var jsonObject = new Object();
	
	if(id == "addressBlock")
		{
			jsonObject.type = document.getElementById("aTypeBox").value;
			jsonObject.street = document.getElementById("streetBox").value;
			jsonObject.city = document.getElementById("cityBox").value;
			jsonObject.state = document.getElementById("stateBox").value;
			jsonObject.zipCode = document.getElementById("zipBox").value;
			var jsonString = JSON.stringify(jsonObject);
			return jsonString; 
		}
	else if(id == "phoneBlock")
		{
		jsonObject.type = document.getElementById("pTypeBox").value;
		jsonObject.number = document.getElementById("numberBox").value;
		var jsonString = JSON.stringify(jsonObject);
		return jsonString; 
		}
	else if(id == "nameBlock")
		{
			jsonObject.firstName = document.getElementById("firstBox").value;
			jsonObject.lastName = document.getElementById("lastBox").value;
			var jsonString = JSON.stringify(jsonObject);
			return jsonString; 
		}
	return null;
}

function create(newId, addressBlockId, phoneBlockId, nameBlockId)
{
	createContact(nameBlockId);
	console.log(newId);
	if(newId == -1)
		{
			return;
		}
	createAddress(newId, addressBlockId);
	//setTimeout(function(){return;});
	createPhone(newId, phoneBlockId);
	setTimeout(function(){toHomePage()}, 1000);
}

function createContact(addressBlockId, phoneBlockId, nameBlockId)
{
	return function()
	{
		var jsonString = getInputValues(nameBlockId);
		console.log(jsonString);
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("POST", "http://localhost:8080/createContact", false);
		xmlHttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
		xmlHttp.onload = function()
		{
			if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
				{
					newId = xmlHttp.response;
					create(newId, addressBlockId, phoneBlockId, nameBlockId);
				}
		};
		xmlHttp.send(jsonString);
	}

}

function createAddress(newId, addressBlockId)
{
	var jsonString = getInputValues(addressBlockId);
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open("POST", "http://localhost:8080/createAddress/".concat(newId), false);
	xmlHttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
	xmlHttp.onload = function()
	{
		if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
			{
				console.log("Success");
			}
	};
	xmlHttp.send(jsonString);
}

function createPhone(newId, phoneBlockId)
{
	var jsonString = getInputValues(phoneBlockId);
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open("POST", "http://localhost:8080/createPhone/".concat(newId), false);
	xmlHttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
	xmlHttp.onload = function()
	{
		if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
			{
				console.log("Success");
			}
	};
	xmlHttp.send(jsonString);
}


function cancelAction()
{
	return function()
	{
		toHomePage();
	}
}

function toHomePage()
{
	window.location.href = "http://localhost:8080";
}