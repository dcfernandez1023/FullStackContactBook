package dom.contact.project.controller;

import java.util.ArrayList;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dom.contact.project.model.Address;
import dom.contact.project.model.Contact;
import dom.contact.project.model.ContactArrayList;
import dom.contact.project.model.ContactUser;
import dom.contact.project.model.ContactsFile;
import dom.contact.project.model.DbManager;
import dom.contact.project.model.Phone;

@RestController
public class ContactController 
{
	
	// GET methods 
	@RequestMapping(method = RequestMethod.GET, value = "/getAllContacts")
	public ArrayList <Contact> getAllContacts()
	{
		try
		{
			ContactUser contactUser;
			contactUser = new ContactUser("bigdom1023");
			return contactUser.loadAllContacts();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ArrayList <Contact> ();
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/getContact/{id}")
	public Contact getContact(@PathVariable Integer id)
	{
		try
		{
			ContactUser contactUser;
			contactUser = new ContactUser("bigdom1023");
			return contactUser.loadContact(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/searchContacts/{name}")
	public ArrayList <Contact> searchContacts(@PathVariable String name)
	{
		try
		{
			ContactUser contactUser;
			contactUser = new ContactUser("bigdom1023");
			return contactUser.searchContacts(name);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ArrayList <Contact> ();
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAddressList/{id}")
	public ArrayList <Address> getAddressList(@PathVariable Integer id)
	{
		try
		{
			ContactUser contactUser;
			contactUser = new ContactUser("bigdom1023");
			ArrayList <Address> addressList = contactUser.loadAddressList(id);
			return addressList;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ArrayList <Address> ();
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/getPhoneList/{id}")
	public ArrayList <Phone> getPhoneList(@PathVariable Integer id)
	{
		try
		{
			ContactUser contactUser;
			contactUser = new ContactUser("bigdom1023");
			ArrayList <Phone> phoneList = contactUser.loadPhoneList(id);
			return phoneList;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ArrayList <Phone> ();
		}
	}
	
	
	// POST methods
	@RequestMapping(method = RequestMethod.POST, value = "/createContact")
	public Integer createContact(@RequestBody String jsonBody)
	{
		ContactUser contactUser;
		contactUser = new ContactUser("bigdom1023");
		try
		{
			Integer id = contactUser.createContact(jsonBody);
			return id;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/createAddress/{id}")
	public void createAddress(@RequestBody String jsonBody, @PathVariable Integer id)
	{
		ContactUser contactUser;
		contactUser = new ContactUser("bigdom1023");
		
		try
		{
			contactUser.createAddress(jsonBody, id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/createPhone/{id}")
	public void createPhone(@RequestBody String jsonBody, @PathVariable Integer id)
	{
		ContactUser contactUser;
		contactUser = new ContactUser("bigdom1023");
		
		try
		{
			contactUser.createPhone(jsonBody, id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	
	// PUT methods
	@RequestMapping(method = RequestMethod.PUT, value = "/editContact/{id}")
	public void editContact(@RequestBody String jsonBody, @PathVariable Integer id)
	{
		ContactUser contactUser;
		contactUser = new ContactUser("bigdom1023");
		
		try
		{
			contactUser.updateContact(jsonBody, id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/editAddress/{contactId}/{addressId}")
	public void editAddress(@RequestBody String jsonBody, @PathVariable Integer contactId, @PathVariable Integer addressId)
	{
		ContactUser contactUser;
		contactUser = new ContactUser("bigdom1023");
		
		try
		{
			contactUser.updateAddress(jsonBody, contactId, addressId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/editPhone/{contactId}/{phoneId}")
	public void editPhone(@RequestBody String jsonBody, @PathVariable Integer contactId, @PathVariable Integer phoneId)
	{
		ContactUser contactUser;
		contactUser = new ContactUser("bigdom1023");
		
		try
		{
			contactUser.updatePhone(jsonBody, contactId, phoneId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	
	//DELETE methods
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteContact/{id}")
	public void deleteCotnact(@PathVariable Integer id)
	{
		ContactUser contactUser;
		contactUser = new ContactUser("bigdom1023");
		
		try
		{
			contactUser.deleteContact(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteAllContacts")
	public void deleteAllContacts()
	{
		ContactUser contactUser;
		contactUser = new ContactUser("bigdom1023");
		
		try
		{
			contactUser.deleteAllContacts();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteAddress/{contactId}/{addressId}")
	public void deleteAddress(@PathVariable Integer contactId, @PathVariable Integer addressId)
	{
		ContactUser contactUser;
		contactUser = new ContactUser("bigdom1023");
		
		try
		{
			contactUser.deleteAddress(contactId, addressId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/deletePhone/{contactId}/{phoneId}")
	public void deletePhone(@PathVariable Integer contactId, @PathVariable Integer phoneId)
	{
		ContactUser contactUser;
		contactUser = new ContactUser("bigdom1023");
		
		try
		{
			contactUser.deletePhone(contactId, phoneId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
}
