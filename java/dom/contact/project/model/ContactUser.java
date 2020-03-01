package dom.contact.project.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class ContactUser 
{
	private String username;
	private String password;
	private ContactArrayList contactArrayList;
	ContactDataAccess contactDataAccess;
	
	// global variables for parsing json
	private static final String FIRSTNAME = "firstName";
	private static final String LASTNAME = "lastName";
	
	private static final String TYPE = "type";
	private static final String STREET = "street";
	private static final String CITY = "city";
	private static final String STATE = "state";
	private static final String ZIPCODE = "zipCode";
	private static final String NUMBER = "number";
	
	public ContactUser(String username)
	{
		initialize(username);
	}
	
	private void initialize(String username)
	{
		setUsername(username);
		//setPassword(password);
		setContactArrayList();
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setContactArrayList()
	{
		this.contactArrayList = new ContactArrayList();
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public ContactArrayList getContactArrayList()
	{
		return this.contactArrayList;
	}
	
	public ArrayList <Contact> getUserContactList()
	{
		return this.contactArrayList.getContactArrayList();
	}
	
	// methods to be called by REST controller
	
	//get methods
	public ArrayList <Contact> loadAllContacts() throws Exception
	{
		contactDataAccess = new MongoDbDataAccess(this.username);
		String jsonBody = contactDataAccess.loadFromDb();
		
		JSONParser jsonParser;

			jsonParser = new JSONParser(jsonBody);
			ArrayList <Contact> contactList = jsonParser.JSONStringToContactList();
			return contactList;
	}
	
	public Contact loadContact(Integer id) throws Exception
	{
		contactDataAccess = new MongoDbDataAccess(this.username);
		String jsonBody = contactDataAccess.loadFromDb();
		
		JSONParser jsonParser;

			jsonParser = new JSONParser(jsonBody);
			ArrayList <Contact> contactList = jsonParser.JSONStringToContactList();
			this.contactArrayList.replaceContactList(contactList);
			Contact contact = this.contactArrayList.getContact(id);
			return contact;
	}
	
	public ArrayList <Contact> searchContacts(String name) throws Exception
	{
		contactDataAccess = new MongoDbDataAccess(this.username);
		String jsonBody = contactDataAccess.loadFromDb();
		
		JSONParser jsonParser;
		jsonParser = new JSONParser(jsonBody);
		ArrayList <Contact> contactList = jsonParser.JSONStringToContactList();
		this.contactArrayList.replaceContactList(contactList);
		ArrayList <Contact> searchList = this.contactArrayList.searchContacts(name);
		return searchList;
	}
	
	public ArrayList <Address> loadAddressList(Integer id) throws Exception
	{
		Contact contact = loadContact(id);
		ArrayList <Address> addressList = contact.getAddressList();
		return addressList;
	}
	
	public ArrayList <Phone> loadPhoneList(Integer id) throws Exception
	{
		Contact contact = loadContact(id);
		ArrayList <Phone> phoneList = contact.getPhoneList();
		return phoneList;
	}
	
	// post methods
	public Integer createContact(String jsonBody) throws Exception
	{
		ArrayList <Contact> contactList = loadAllContacts();
		this.contactArrayList.replaceContactList(contactList);

		JSONParser jsonParser = new JSONParser(jsonBody);
		String firstName = jsonParser.getValue(FIRSTNAME);
		String lastName = jsonParser.getValue(LASTNAME);
		Contact contact = new Contact(firstName, lastName, contactList);
		this.contactArrayList.addContact(contact);
		
		System.out.println(this.contactArrayList.getContactArrayList());
		ArrayList <String> JSONContactList = jsonParser.contactListToJSONList(this.contactArrayList.getContactArrayList());
		System.out.println(JSONContactList);
		JSONArray jsonArray = new JSONArray(JSONContactList);
		contactDataAccess.saveToDb(jsonArray.toString());
		return contact.getID();
	}
	
	public void createAddress(String jsonBody, Integer id) throws Exception
	{
		ArrayList <Contact> contactList = loadAllContacts();
		this.contactArrayList.replaceContactList(contactList);
		Contact contact = this.contactArrayList.getContact(id);
		if(contact == null)
		{
			return;
		}

		JSONParser jsonParser = new JSONParser(jsonBody);
		String type = jsonParser.getValue(TYPE);
		String street = jsonParser.getValue(STREET);
		String city = jsonParser.getValue(CITY);
		String state = jsonParser.getValue(STATE);
		String zipCode = jsonParser.getValue(ZIPCODE);
		
		Address address = new Address(type, street, city, state, zipCode, contact.getAddressList());
		contact.addAddress(address);
		this.contactArrayList.updateContactList(contact);
		
		ArrayList <String> JSONContactList = jsonParser.contactListToJSONList(this.contactArrayList.getContactArrayList());
		JSONArray jsonArray = new JSONArray(JSONContactList);
		contactDataAccess.saveToDb(jsonArray.toString());
	}
	
	public void createPhone(String jsonBody, Integer id) throws Exception
	{
		ArrayList <Contact> contactList = loadAllContacts();
		this.contactArrayList.replaceContactList(contactList);
		Contact contact = this.contactArrayList.getContact(id);
		if(contact == null)
		{
			return;
		}
	
		JSONParser jsonParser = new JSONParser(jsonBody);
		String type = jsonParser.getValue(TYPE);
		String number = jsonParser.getValue(NUMBER);
		
		Phone phone = new Phone(type, number, contact.getPhoneList());
		contact.addPhone(phone);
		this.contactArrayList.updateContactList(contact);
		
		ArrayList <String> JSONContactList = jsonParser.contactListToJSONList(this.contactArrayList.getContactArrayList());
		JSONArray jsonArray = new JSONArray(JSONContactList);
		contactDataAccess.saveToDb(jsonArray.toString());
	}
	
	// put methods
	public void updateContact(String jsonBody, Integer id) throws Exception
	{
		ArrayList <Contact> contactList = loadAllContacts();
		this.contactArrayList.replaceContactList(contactList);
		Contact contact = this.contactArrayList.getContact(id);
		if(contact == null)
		{
			return;
		}
		
		JSONParser jsonParser = new JSONParser(jsonBody);
		String firstName = jsonParser.getValue(FIRSTNAME);
		String lastName = jsonParser.getValue(LASTNAME);
		
		contact.setFirstName(firstName);
		contact.setLastName(lastName);
		this.contactArrayList.updateContactList(contact);
		
		ArrayList <String> JSONContactList = jsonParser.contactListToJSONList(this.contactArrayList.getContactArrayList());
		JSONArray jsonArray = new JSONArray(JSONContactList);
		contactDataAccess.saveToDb(jsonArray.toString());
	}
	
	public void updateAddress(String jsonBody, Integer contactId, Integer addressId) throws Exception
	{
		ArrayList <Contact> contactList = loadAllContacts();
		this.contactArrayList.replaceContactList(contactList);
		Contact contact = this.contactArrayList.getContact(contactId);
		if(contact == null)
		{
			return;
		}
		Address address = contact.getAddress(addressId);
		if(address == null)
		{
			return;
		}
		
		JSONParser jsonParser = new JSONParser(jsonBody);
		String type = jsonParser.getValue(TYPE);
		String street = jsonParser.getValue(STREET);
		String city = jsonParser.getValue(CITY);
		String state = jsonParser.getValue(STATE);
		String zipCode = jsonParser.getValue(ZIPCODE);
		
		address.setType(type);
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setZipCode(zipCode);
		
		contact.updateAddressList(address);
		this.contactArrayList.updateContactList(contact);
		
		ArrayList <String> JSONContactList = jsonParser.contactListToJSONList(this.contactArrayList.getContactArrayList());
		JSONArray jsonArray = new JSONArray(JSONContactList);
		contactDataAccess.saveToDb(jsonArray.toString());
	}
	
	public void updatePhone(String jsonBody, Integer contactId, Integer phoneId) throws Exception
	{
		ArrayList <Contact> contactList = loadAllContacts();
		this.contactArrayList.replaceContactList(contactList);
		Contact contact = this.contactArrayList.getContact(contactId);
		if(contact == null)
		{
			return;
		}
		Phone phone = contact.getPhone(phoneId);
		if(phone == null)
		{
			return;
		}
		
		JSONParser jsonParser = new JSONParser(jsonBody);
		String type = jsonParser.getValue(TYPE);
		String number = jsonParser.getValue(NUMBER);
		
		phone.setType(type);
		phone.setNumber(number);
		
		contact.updatePhoneList(phone);
		this.contactArrayList.updateContactList(contact);
		
		ArrayList <String> JSONContactList = jsonParser.contactListToJSONList(this.contactArrayList.getContactArrayList());
		JSONArray jsonArray = new JSONArray(JSONContactList);
		contactDataAccess.saveToDb(jsonArray.toString());
	}
	
	// delete methods
	public void deleteAllContacts() throws Exception
	{
		ArrayList <Contact> contactList = loadAllContacts();
		this.contactArrayList.replaceContactList(contactList);
		this.contactArrayList.removeAllContacts();
		
		JSONParser jsonParser = new JSONParser();
		ArrayList <String> JSONContactList = jsonParser.contactListToJSONList(this.contactArrayList.getContactArrayList());
		JSONArray jsonArray = new JSONArray(JSONContactList);
		contactDataAccess.saveToDb(jsonArray.toString());
	}
	
	public void deleteContact(Integer id) throws Exception
	{
		ArrayList <Contact> contactList = loadAllContacts();
		this.contactArrayList.replaceContactList(contactList);
		Contact contact = this.contactArrayList.getContact(id);
		if(contact == null)
		{
			return;
		}
		this.contactArrayList.removeContact(contact);
		
		JSONParser jsonParser = new JSONParser();
		ArrayList <String> JSONContactList = jsonParser.contactListToJSONList(this.contactArrayList.getContactArrayList());
		JSONArray jsonArray = new JSONArray(JSONContactList);
		contactDataAccess.saveToDb(jsonArray.toString());
	}
	
	public void deleteAddress(Integer contactId, Integer addressId) throws Exception
	{
		ArrayList <Contact> contactList = loadAllContacts();
		this.contactArrayList.replaceContactList(contactList);
		Contact contact = this.contactArrayList.getContact(contactId);
		if(contact == null)
		{
			return;
		}
		Address address = contact.getAddress(addressId);
		if(address == null)
		{
			return;
		}
		contact.removeAddress(address);
		this.contactArrayList.updateContactList(contact);
		
		JSONParser jsonParser = new JSONParser();
		ArrayList <String> JSONContactList = jsonParser.contactListToJSONList(this.contactArrayList.getContactArrayList());
		JSONArray jsonArray = new JSONArray(JSONContactList);
		contactDataAccess.saveToDb(jsonArray.toString());
	}
	
	public void deletePhone(Integer contactId, Integer phoneId) throws Exception
	{
		ArrayList <Contact> contactList = loadAllContacts();
		this.contactArrayList.replaceContactList(contactList);
		Contact contact = this.contactArrayList.getContact(contactId);
		if(contact == null)
		{
			return;
		}
		Phone phone = contact.getPhone(phoneId);
		if(phone == null)
		{
			return;
		}
		contact.removePhone(phone);
		this.contactArrayList.updateContactList(contact);
		
		JSONParser jsonParser = new JSONParser();
		ArrayList <String> JSONContactList = jsonParser.contactListToJSONList(this.contactArrayList.getContactArrayList());
		JSONArray jsonArray = new JSONArray(JSONContactList);
		contactDataAccess.saveToDb(jsonArray.toString());
	}
}
