window.onload = viewContactInfo(getContactId());
var CONTACT;
var ADDRESSLIST;
var PHONELIST;

function viewContactInfo(contactId)
{
	return function()
	{
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", "http://localhost:8080/getContact/".concat(contactId), true);
		 xmlHttp.onreadystatechange = function()
         {
             if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
             { 
					CONTACT = JSON.parse(xmlHttp.responseText);
					ADDRESSLIST = CONTACT.addressList;
					PHONELIST = CONTACT.phoneList;
					createViewPageElements();
             }
         };
             xmlHttp.send();
	}
}

function createViewPageElements()
{
	document.write("<h1> Contact Information </h1>");
	var headerBlock = document.createElement("div");
	var previousButton = document.createElement("input");
	previousButton.type = "button";
	previousButton.value = "Previous Page";
	previousButton.style = "float: right;";
	previousButton.addEventListener("click", redirectPage("http://localhost:8080"));
	headerBlock.appendChild(previousButton);
	document.body.appendChild(headerBlock);
	
	 var nameBlock = document.createElement("div");
	 nameBlock.className = "name";
	 nameBlock.id = "".concat(nameBlock.className, CONTACT.id);
	 
	var editButton = document.createElement("input");
    editButton.type = "button";
    editButton.value = "Edit";
    editButton.id = "".concat(editButton.type, nameBlock.className, CONTACT.id);
    editButton.addEventListener("click", editContact(nameBlock, CONTACT));

	 var fullName = "".concat(CONTACT.firstName, " ", CONTACT.lastName);
	 nameBlock.innerHTML = fullName + "<br>";
	 nameBlock.appendChild(editButton);
	 document.body.appendChild(nameBlock);
	 document.write("<hr>");
	 
	 //displaying address information on the page
	 var addressHeader = document.createElement("H3");
	 var headerText = document.createTextNode("Address Information");
	 addressHeader.append(headerText);
	 var addAddressButton = document.createElement("input");
	 addAddressButton.type = "button";
	 addAddressButton.value = "Add Address";
	 addAddressButton.style = "float: right;";
	 addAddressButton.addEventListener("click", createAddressPage());
	 addressHeader.append(addAddressButton);
	 var headerBlock = document.createElement("div");
	 headerBlock.appendChild(addressHeader);
	 document.body.appendChild(headerBlock);
	 
	 for(i = 0; i < ADDRESSLIST.length; i++)
		 {
		 	var address = ADDRESSLIST[i];
		 	
		 	var addressBlock = document.createElement("div");
		 	addressBlock.className = "address";
		 	addressBlock.id = "".concat(addressBlock.className, address.id);
		 	
		 	 var deleteButton = document.createElement("input");
	         deleteButton.type = "button";
	         deleteButton.value = "Delete";
	         deleteButton.id = "".concat(deleteButton.type, addressBlock.className, address.id);
	         deleteButton.addEventListener("click", deleteContact(addressBlock, address));
	         
	         var editButton = document.createElement("input");
	         editButton.type = "button";
	         editButton.value = "Edit";
	         editButton.id = "".concat(editButton.type, addressBlock.className, address.id);
	         editButton.addEventListener("click", editContact(addressBlock, address));
		 	
		 	addressBlock.className = "address";
	         console.log("".concat("Address Block id: ", addressBlock.id));
		 	addressBlock.innerHTML = ("Type: " + address.type + "<br>" +
		 										"Street: " + address.street + "<br>" +
		 										"City: " + address.city + "<br>" +
		 										"State: " + address.state + "<br>" +
		 										"Zip Code: " + address.zipCode + "<br>");
		 
		 	addressBlock.appendChild(deleteButton);
		 	addressBlock.appendChild(editButton);
		 	document.body.appendChild(addressBlock);
		 	document.write("<hr>");
		 }
	 
	 var phoneHeader = document.createElement("H3");
	 var headerText = document.createTextNode("Phone Information");
	 phoneHeader.append(headerText);
	 var addPhoneButton = document.createElement("input");
	 addPhoneButton.type = "button";
	 addPhoneButton.value = "Add Phone";
	 addPhoneButton.style = "float: right;";
	 addPhoneButton.addEventListener("click", createPhonePage());
	 phoneHeader.append(addPhoneButton);
	 var headerBlock = document.createElement("div");
	 headerBlock.appendChild(phoneHeader);
	 document.body.appendChild(headerBlock);
	 
	 for(x = 0; x < PHONELIST.length; x++)
		 {
			 var phone = PHONELIST[x];
			 var phoneId = phone.id;
			 
			 var phoneBlock = document.createElement("div");
	         phoneBlock.className = "phone";
	         phoneBlock.id = "".concat(phoneBlock.className, phone.id);
			 
			 var deleteButton = document.createElement("input");
	         deleteButton.type = "button";
	         deleteButton.value = "Delete";
	         deleteButton.id = "".concat(deleteButton.type, phoneBlock.className, phone.id);
	         deleteButton.addEventListener("click", deleteContact(phoneBlock, phone));
	         
	         var editButton = document.createElement("input");
	         editButton.type = "button";
	         editButton.value = "Edit";
	         editButton.id = "".concat(editButton.type, phoneBlock.className, phone.id);
	         editButton.addEventListener("click", editContact(phoneBlock, phone));
	         
	         	phoneBlock.className = "phone";
		         console.log("".concat("Phone Block id: ", phoneBlock.id));
			 	phoneBlock.innerHTML = ("Type: " + phone.type + "<br>" +
			 								"Number: " + phone.number + "<br>");
			 	
			 	phoneBlock.appendChild(deleteButton);
			 	phoneBlock.appendChild(editButton);
			 	document.body.appendChild(phoneBlock);
			 	document.write("<hr>");
		 }
		 
}

function getContactId()
{
	 var urlString = window.location.search;
	 var urlParam = new URLSearchParams(urlString);
	 var contactId = urlParam.get("contactId");
	 return contactId;
}

function editContact(infoBlock, dataObject)
{
	return function()
	{
		if(infoBlock.className == "name")
			{
				var firstNameBox = "<input type=text value=".concat(quoteAString(dataObject.firstName), " ", "id=", "firstName", dataObject.id, ">", "</input>");
				var lastNameBox = "<input type=text value=".concat(quoteAString(dataObject.lastName), " ", "id=", "lastName", dataObject.id, ">", "</input>");
				infoBlock.innerHTML = "First Name: " + firstNameBox + "<br>" +
									"Last Name: " + lastNameBox;
				
				var cancelButton = document.createElement("input");
				cancelButton.type = "button";
				cancelButton.value = "Cancel";
				cancelButton.addEventListener("click", cancelAction(infoBlock));
				
				var saveButton = document.createElement("input");
				saveButton.type = "button";
				saveButton.value = "Save";
				saveButton.addEventListener("click", saveChanges(infoBlock, dataObject));
				
				infoBlock.appendChild(cancelButton);
				infoBlock.appendChild(saveButton);
				
			}
		else if (infoBlock.className == "address")
			{

				var typeBox = "<input type=text value=".concat(quoteAString(dataObject.type), " ", "id=", "type", dataObject.id, ">", "</input>");
				var streetBox = "<input type=text value=".concat(quoteAString(dataObject.street), " ", "id=", "street", dataObject.id, ">", "</input>");
				var cityBox = "<input type=text value=".concat(quoteAString(dataObject.city), " ", "id=", "city", dataObject.id, ">", "</input>");
				var stateBox = "<input type=text value=".concat(quoteAString(dataObject.state), " ", "id=", "state", dataObject.id, ">", "</input>");
				var zipCodeBox = "<input type=text value=".concat(quoteAString(dataObject.zipCode), " ", "id=", "zipCode", dataObject.id, ">", "</input>");
				infoBlock.innerHTML = "Type: " + typeBox + "<br>" + 
									"Street: " + streetBox + "<br>" +
									"City: " + cityBox + "<br>" +
									"State: " + stateBox + "<br>" +
									"Zip Code: " + zipCodeBox + "<br>";
				
				var cancelButton = document.createElement("input");
				cancelButton.type = "button";
				cancelButton.value = "Cancel";
				cancelButton.addEventListener("click", cancelAction(infoBlock));
				
				var saveButton = document.createElement("input");
				saveButton.type = "button";
				saveButton.value = "Save";
				saveButton.addEventListener("click", saveChanges(infoBlock, dataObject));
				
				infoBlock.appendChild(cancelButton);
				infoBlock.appendChild(saveButton);
			}
		else if (infoBlock.className == "phone")
			{
				var typeBox = "<input type=text value=".concat(quoteAString(dataObject.type), " ", "id=", "type", dataObject.id, ">", "</input>");
				var numberBox = "<input type=text value=".concat(quoteAString(dataObject.number), " ", "id=", "number", dataObject.id, ">", "</input>");
				infoBlock.innerHTML = "Type: " + typeBox + "<br>" + 
									"Number: " + numberBox + "<br>";
				
				var cancelButton = document.createElement("input");
				cancelButton.type = "button";
				cancelButton.value = "Cancel";
				cancelButton.addEventListener("click", cancelAction(infoBlock));
				
				var saveButton = document.createElement("input");
				saveButton.type = "button";
				saveButton.value = "Save";
				saveButton.addEventListener("click", saveChanges(infoBlock, dataObject));
				infoBlock.appendChild(cancelButton);
				infoBlock.appendChild(saveButton);
			}
	}											
}

function deleteContact(infoBlock, dataObject)
{
	return function()
	{
		var xmlHttp = new XMLHttpRequest();
		if(infoBlock.className == "name")
			{
				xmlHttp.open("DELETE", "http://localhost:8080/deleteContact/".concat(CONTACT.id), true);
				xmlHttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
				xmlHttp.onload = function()
				{
					if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
						{
							console.log("Success");
						}
				};
			}
		else if(infoBlock.className == "address")
			{
				xmlHttp.open("DELETE", "http://localhost:8080/deleteAddress/".concat(CONTACT.id, "/", dataObject.id), true);
				xmlHttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
				xmlHttp.onload = function()
				{
					if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
						{
							console.log("Success");
						}
				};
			}
		else if(infoBlock.className == "phone")
			{
				xmlHttp.open("DELETE", "http://localhost:8080/deletePhone/".concat(CONTACT.id, "/", dataObject.id), true);
				xmlHttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
				xmlHttp.onload = function()
				{
					if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
						{
							console.log("Success");
						}
				};
			}
		xmlHttp.send();
		setTimeout(function(){location.reload(true);}, 1000);
	}

}

function createAddressPage()
{
	return function()
	{
		window.location.href = "createAddressPage.html?contactId=".concat(CONTACT.id, "?eraseCache=true");
	}
}

function createPhonePage()
{
	return function()
	{
		window.location.href = "createPhonePage.html?contactId=".concat(CONTACT.id, "?eraseCache=true");
	}
}

function quoteAString(str)
{
	return "'" + str + "'";
}

function getInputValues(infoBlock, dataObject) //returns a json string
{
	var jsonObject = new Object();
	if(infoBlock.className == "name")
		{
			jsonObject.firstName = document.getElementById("".concat("firstName", dataObject.id)).value;
			jsonObject.lastName =  document.getElementById("".concat("lastName", dataObject.id)).value;
			var jsonString = JSON.stringify(jsonObject);
			return jsonString;
		}
	else if(infoBlock.className == "address")
		{
		jsonObject.type = document.getElementById("".concat("type", dataObject.id)).value;
		jsonObject.street = document.getElementById("".concat("street", dataObject.id)).value;
		jsonObject.city = document.getElementById("".concat("city", dataObject.id)).value;
		jsonObject.state = document.getElementById("".concat("state", dataObject.id)).value;
		jsonObject.zipCode = document.getElementById("".concat("zipCode", dataObject.id)).value;
		var jsonString = JSON.stringify(jsonObject);
		return jsonString;
		}
	else if(infoBlock.className == "phone")
		{
			jsonObject.type = document.getElementById("".concat("type", dataObject.id)).value;
			jsonObject.number = document.getElementById("".concat("number", dataObject.id)).value;
			var jsonString = JSON.stringify(jsonObject);
			return jsonString;
		}
}

function saveChanges(infoBlock, dataObject)
{
	return function()
	{
		var jsonString = getInputValues(infoBlock, dataObject);
		console.log("JSON String" + jsonString);
		console.log(dataObject);
		var xmlHttp = new XMLHttpRequest();
		
		if(infoBlock.className == "name")
		{
			xmlHttp.open("PUT", "http://localhost:8080/editContact/".concat(dataObject.id), true);
			xmlHttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
			xmlHttp.onload = function()
			{
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
					{
						console.log("Success");

					}
			};
		}
		else if (infoBlock.className == "address")
		{
				xmlHttp.open("PUT", "http://localhost:8080/editAddress/".concat(CONTACT.id, "/", dataObject.id), true);
				xmlHttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
				xmlHttp.onload = function()
			{
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
					{
						console.log("Success");

					}
			};
		}
		else if (infoBlock.className == "phone")
		{
			xmlHttp.open("PUT", "http://localhost:8080/editPhone/".concat(CONTACT.id, "/", dataObject.id), true);
			xmlHttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
			xmlHttp.onload = function()
			{
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
					{
						console.log("Success");

					}
			};
		}
		
		xmlHttp.send(jsonString);
		setTimeout(function(){location.reload(true);}, 1000);

	}
}

function cancelAction(infoBlock)
{
	return function()
	{
		if(infoBlock.className == "name")
		{
			console.log("NAME BLOCK CANCEL");
			infoBlock.innerHTML = CONTACT.firstName + " " + CONTACT.lastName;
			var editButton = document.createElement("input");
		    editButton.type = "button";
		    editButton.value = "Edit";
		    editButton.addEventListener("click", editContact(infoBlock, CONTACT));
		    infoBlock.appendChild(editButton);
		}
		else if(infoBlock.className == "address")
			{
				address = getAddress(infoBlock.id.slice(infoBlock.id.length - 1));
				if(address == "No Addresses...")
					{
						infoBlock.innerHTML = address;
						return;
					}
				infoBlock.innerHTML = "Type: " + address.type + "<br>" +
					"Street: " + address.street + "<br>" +
						"City: " + address.city + "<br>" +
						"State: " + address.state + "<br>" +
						"Zip Code: " + address.zipCode + "<br>";
					var editButton = document.createElement("input");
				    editButton.type = "button";
				    editButton.value = "Edit";
				    editButton.addEventListener("click", editContact(infoBlock, address));
				    
				    var deleteButton = document.createElement("input");
			         deleteButton.type = "button";
			         deleteButton.value = "Delete";
			         deleteButton.addEventListener("click", deleteContact(infoBlock, address));
			         
			         infoBlock.appendChild(deleteButton);
			         infoBlock.appendChild(editButton);

				    
				    
			}
		else if(infoBlock.className == "phone")
			{
				phone = getPhone(infoBlock.id.slice(infoBlock.id.length -1));
				if(phone == "No Phone Numbers...")
					{
						infoBlock.innerHTML = phone;
					}
				infoBlock.innerHTML =" Type: " + phone.type + "<br>" +
					"Number: " + phone.number + "<br>";
				var editButton = document.createElement("input");
			    editButton.type = "button";
			    editButton.value = "Edit";
			    editButton.addEventListener("click", editContact(infoBlock, phone));
			    
			    var deleteButton = document.createElement("input");
		         deleteButton.type = "button";
		         deleteButton.value = "Delete";
		         deleteButton.addEventListener("click", deleteContact(infoBlock, phone));
		         
		         infoBlock.appendChild(deleteButton);
		         infoBlock.appendChild(editButton);

			}
	}

}

function redirectPage(location)
{
	return function()
	{
		window.location.href = location;
	}
}



function getAddress(addressId)
{
	for(i = 0; i < ADDRESSLIST.length; i++)
		{
			address = ADDRESSLIST[i];
			if(address.id == addressId)
				{
					return address;
				}
		}
	return "No Addresses...";
}

function getPhone(phoneId)
{
	for(i = 0; i < PHONELIST.length; i++)
		{
			phone = PHONELIST[i];
			if(phone.id == phoneId)
				{
					return phone
				}
		}
	return "No Phone Numbers...";
}



function testButtonFunction(id)
{
	 return function()
	 {
		 alert(id);
	 }
}

