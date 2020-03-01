package dom.contact.project.model;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import dom.contact.project.model.Address;
import dom.contact.project.model.Contact;
import dom.contact.project.model.ContactArrayList;
import dom.contact.project.model.Phone;

public class JSONParser 
{
	private String jsonBody;
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	
	public JSONParser()
	{
	}
	
	public JSONParser(String jsonBody) throws Exception
	{
		//System.out.println(jsonBody);
		if(jsonBody == null)
		{
			throw new Exception("Null parameter");
		}
		else if(jsonBody.charAt(0) != '[')
		{
			setJSONBody(jsonBody);
			setJSONObject(jsonBody);
		}
		else
		{
			this.jsonArray = new JSONArray(jsonBody);
		}
	}
	
	public void setJSONBody(String jsonBody)
	{
		this.jsonBody = jsonBody;
	}
	
	public void setJSONObject(String jsonBody)
	{
		this.jsonObject = new JSONObject(jsonBody);
	}
	
	public String getValue(String key) throws Exception
	{
		String value = this.jsonObject.getString(key);
		return value;
	}
	
	public ArrayList <String> contactListToJSONList(ArrayList <Contact> contactList) throws Exception
	{
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
	
	public ArrayList <Contact> JSONStringToContactList() throws Exception
	{
		//System.out.println(this.jsonArray);
		ArrayList <Contact> contactList = new ArrayList <Contact> ();
		//JSONArray JSONContactList = new JSONArray(this.jsonBody);
		//System.out.println(this.jsonArray);
		for(int i = 0; i < this.jsonArray.length(); i++)
		{
				//System.out.println(this.jsonArray.get(i).getClass());
				String jsonBody = this.jsonArray.getString(i);
				JSONObject jsonObject = new JSONObject(jsonBody);
				Integer id = jsonObject.getInt("id");
				String firstName = jsonObject.getString("firstName");
				String lastName = jsonObject.getString("lastName");
				Contact contact = new Contact(firstName, lastName, id);
				
				JSONArray addressArray = jsonObject.getJSONArray("addressList");
				JSONArray phoneArray = jsonObject.getJSONArray("phoneList");
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
					JSONObject jsonPhone = phoneArray.getJSONObject(n);
					String phoneType = jsonPhone.getString("type");
					String number = jsonPhone.getString("number");
					Integer phoneId = jsonPhone.getInt("ID");
					Phone phone = new Phone(phoneType, number, phoneId);
					contact.addPhone(phone);
				}
				
				
				contactList.add(contact);
			}
		return contactList;
	}
}
