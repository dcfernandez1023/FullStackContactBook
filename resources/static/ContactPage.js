 //sends a get request to retrieve all contacts from database
function loadAllContacts()
        {
            var xmlHttp = new XMLHttpRequest();
            xmlHttp.open("GET", "http://localhost:8080/getAllContacts", true);
            xmlHttp.onreadystatechange = function()
            {
                if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
                { 
					var contacts = JSON.parse(xmlHttp.responseText);
		            createMainPageElements(contacts);
                }
            };
            xmlHttp.send();
        }

function redirectPage(location)
{
	return function()
	{
		window.location.href = location;
	}
}


//generate main page elements based on the information of all contacts received
 function createMainPageElements(contacts)
 {
	 var pageHeader = document.createElement("H1");
	 var text = document.createTextNode("Contacts");
	 
	 var addContactButton = document.createElement("input");
	 addContactButton.type = "button";
	 
	 pageHeader.appendChild(text);
	 pageHeader.appendChild(addContactButton);
	 
     for(x = 0; x < contacts.length; x++)
     {
         var id = contacts[x].id;
         var firstName= contacts[x].firstName;
         var lastName = contacts[x].lastName;
         var fullName = "".concat(firstName, " ", lastName);

         var viewButton = document.createElement("input");
         viewButton.type = "button";
         viewButton.value = "Details";
         viewButton.id = id;
         viewButton.addEventListener("click", redirectPage("viewPage.html?contactId=".concat(id, "?eraseCache=true")));
         
         var deleteButton = document.createElement("input");
         deleteButton.type = "button";
         deleteButton.value = "Delete";
         deleteButton.id = id;
         deleteButton.addEventListener("click", deleteAction(deleteButton.id));
         
         var separator = document.createElement("hr");
         
         var contactBlock = document.createElement("div");
         contactBlock.className = fullName;
         contactBlock.innerHTML = fullName;
         contactBlock.appendChild(viewButton);
         contactBlock.appendChild(deleteButton);
         
         document.body.appendChild(contactBlock);
         document.body.appendChild(separator);
     }
 }
 
function deleteContact(buttonId)
{
	var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("DELETE", "http://localhost:8080/deleteContact/".concat(buttonId), true);
    xmlHttp.onreadystatechange = function()
    {
        if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
        { 
        	console.log("Success");
        }
    };
    xmlHttp.send();
	setTimeout(function(){location.reload(true);}, 1000);
}

 
 function deleteAction(id)
 {
	 return function()
	 {
		 if(confirm("Delete Contact?"))
		 {
		 	deleteContact(id)
		 }
		 else
		{
			 return; 
		}
	 }

 }
 
 //temporary test method for buttons
 function testButtonFunction(buttonId)
 {
	 return function()
	 {
		 alert(buttonId);
	 }
 }
 

 window.onload = loadAllContacts();