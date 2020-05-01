package dom.contact.project.model;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.ConnectionString;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class DbManager 
{
	private MongoDatabase database;
	private MongoCollection <Document> contactCollection;
	
	public DbManager()
	{
		connectToDb();
	}
	
	private void connectToDb()
	{
		ConnectionString connectionString = new ConnectionString("mongodb+srv://bigdom1023:********@dom-cluster-1numt.mongodb.net/test?retryWrites=true&w=majority");
		MongoClient mongoClient = MongoClients.create(connectionString);
		MongoDatabase database = mongoClient.getDatabase("ContactDB");
		setDatabase(database);
		setCollection();
	}
	
	private void setDatabase(MongoDatabase database)
	{
		this.database = database;
	}
	
	private void setCollection()
	{
		this.contactCollection = this.database.getCollection("Contacts");
		if(this.contactCollection.countDocuments() == 0)
		{
			Document doc = new Document();
			doc.put("_id", "bigdom1023");
			this.contactCollection.insertOne(doc);
		}
	}
	
	
	// insert method
	public void insertContacts(ArrayList <String> contactList, String username)
	{
		Bson bsonFilter = Filters.eq("_id", username);
		FindIterable <Document> iterable = this.contactCollection.find(bsonFilter);
		if(iterable == null)
		{
			return;
		}
		Document doc = iterable.first();
		if(doc == null)
		{
			return;
		}
		doc.put("Contact List", contactList);
		this.contactCollection.findOneAndReplace(bsonFilter, doc);
	}
	
	// retrieve method
	public ArrayList <Contact> retrieveContactObjects(String username)
	{
		ArrayList <Contact> contactList = new ArrayList <Contact> ();
		Bson bsonFilter = Filters.eq("_id", username);
		FindIterable <Document> iterable = this.contactCollection.find(bsonFilter);
		if(iterable == null)
		{
			return new ArrayList<Contact> ();
		}
		Document doc = iterable.first();
		if(doc == null)
		{
			return new ArrayList<Contact> ();
		}
		ArrayList <String> JSONContactList = (ArrayList<String>) doc.get("Contact List");
		//System.out.println("JSON CONTACT LIST FROM retrieveContactObjects METHOD");
		//System.out.println(JSONContactList);
		if(JSONContactList == null)
		{
			return new ArrayList <Contact> ();
		}
		/*
		if(JSONContactList.isEmpty())
		{
			return contactList;
		}
		*/
		for(int i = 0; i < JSONContactList.size(); i++)
		{
			String jsonBody = JSONContactList.get(i);
			JSONObject jsonObject = new JSONObject(jsonBody);
			//System.out.println("PRINTING OUT JSON CONTACT BEFORE PARSING IN THE retreiveContactObjects METHOD");
			//System.out.println(jsonObject.toString());
			Integer id = jsonObject.getInt("id");
			String firstName = jsonObject.getString("firstName");
			String lastName = jsonObject.getString("lastName");
			Contact contact = new Contact(firstName, lastName, id);
			
			JSONArray addressArray = jsonObject.getJSONArray("addressList");
			JSONArray phoneArray = jsonObject.getJSONArray("phoneList");
			if(addressArray.isEmpty() && phoneArray.isEmpty())
			{
				System.out.println("EMPTY!");
				contactList.add(contact);
			}
			else
			{
				for(int x = 0; x < addressArray.length(); x++)
				{
					JSONObject jsonAddress = addressArray.getJSONObject(x);
					String addressType = jsonAddress.getString("type");
					String street = jsonAddress.getString("street");
					String city = jsonAddress.getString("city");
					String state = jsonAddress.getString("state");
					String zipCode = jsonAddress.getString("zipCode");
					Integer addressId = jsonAddress.getInt("ID");
					Address address = new Address(addressType, street, city, state, zipCode, addressId);
					contact.addAddress(address);
					//System.out.println(contact.getAddressList());
				}
				for(int n = 0; n < phoneArray.length(); n++)
				{
					JSONObject jsonPhone = phoneArray.getJSONObject(i);
					String phoneType = jsonPhone.getString("type");
					String number = jsonPhone.getString("number");
					Integer phoneId = jsonPhone.getInt("ID");
					Phone phone = new Phone(phoneType, number, phoneId);
					contact.addPhone(phone);
				}
				
			contactList.add(contact);
			}
		}
		return contactList;
	}
	
	public ArrayList <String> retrieveJSONContacts(String username)
	{
		ArrayList <Contact> contactList = this.retrieveContactObjects(username);
		ArrayList <String> JSONContactList = new ArrayList <String> ();
		for(int i = 0; i < contactList.size(); i++)
		{
			Contact currentContact = contactList.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", currentContact.getID());
			jsonObject.put("firstName", currentContact.getFirstName());
			jsonObject.put("lastName", currentContact.getLastName());
			JSONArray jsonAddressArray = new JSONArray(currentContact.getAddressList());
			JSONArray jsonPhoneArray = new JSONArray(currentContact.getPhoneList());
			jsonObject.put("addressList", jsonAddressArray);
			jsonObject.put("phoneList", jsonPhoneArray);
			String jsonContactString = jsonObject.toString();
			JSONContactList.add(jsonContactString);
		}
		return JSONContactList;
	}
}
