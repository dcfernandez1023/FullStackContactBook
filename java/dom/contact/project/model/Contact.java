package dom.contact.project.model;

import java.lang.Exception; 
import java.util.ArrayList;
import java.io.Serializable;

public class Contact implements Serializable
{
	static final long serialVersionUID = 42L;
	
	// class attributes
	private Integer id;
	private String firstName;
	private String lastName;
	private ArrayList <Address> addressList;
	private ArrayList <Phone> phoneList;

	// constructor 
	public Contact(String firstName, String lastName, Integer id)
	{
		setFirstName(firstName);
		setLastName(lastName);
		setID(id);
		this.addressList = new ArrayList <Address> ();
		this.phoneList = new ArrayList <Phone> ();
	}
	public Contact(String firstName, String lastName, ArrayList <Contact> contactList) 
	{
		initialize(firstName, lastName, contactList);
	}
	private void initialize(String firstName, String lastName, ArrayList <Contact> contactList)
	{
		generateId(contactList);
		setFirstName(firstName);
		setLastName(lastName);
		this.addressList = new ArrayList <Address> ();
		this.phoneList = new ArrayList <Phone> ();
		
	}
	
	// setters
	public void setID(Integer ID)
	{ 
		this.id = ID;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	// getters
	public Integer getID()
	{
		return this.id;
	}
	public String getFirstName()
	{
		return this.firstName;
	}
	public String getLastName()
	{
		return this.lastName;
	}
	
	// id methods
	public void generateId(ArrayList <Contact> contactList)
	{
		if(contactList.isEmpty())
		{
			//System.out.println("EMPTY");
			Integer generatedId = new Integer(0);
			setID(generatedId);
			//System.out.println(getID());
		}
		else if(contactList.size() == 1)
		{
			//System.out.println("1 id");
			//System.out.println("NOT EMPTY");
			Contact lastContact = contactList.get(contactList.size() - 1);
			int lastId = lastContact.getID();
			int newId = lastId + 1;
			Integer generatedId = new Integer(newId);
			setID(generatedId);
		}
		else
		{
			int largestId = 0;
			for(int i = 0; i < contactList.size(); i++)
			{
				Contact currentContact = contactList.get(i);
				int currentId = currentContact.getID();
				if(currentId > largestId)
				{
					largestId = currentId;
				}
			}
			largestId = largestId + 1;
			Integer generatedId = new Integer(largestId);
			setID(generatedId);
			//System.out.println(generatedId);
		}
	}
	
	// address list methods 
	public void addAddress(Address address) 
	{
		if(this.addressList == null)
		{
			this.addressList = new ArrayList <Address> ();
		}
		this.addressList.add(address);	
	}
	public void removeAddress(Address address)
	{
		if(this.addressList == null)
		{
			this.addressList = new ArrayList <Address> ();
		}
		for (int i = 0; i < this.addressList.size(); i++)
		{
			if (this.addressList.get(i) == address)
			{
				this.addressList.remove(i);
			}
		}
	}
	public ArrayList <Address> getAddressList()
	{
		if(this.addressList == null)
		{
			this.addressList = new ArrayList <Address> ();
		}
		return this.addressList;
	}
	
	// phone list methods
	public void addPhone(Phone phone)
	{
		if(this.phoneList == null)
		{
			this.phoneList = new ArrayList <Phone> ();
		}
		this.phoneList.add(phone);	
	}
	public void removePhone(Phone phone)
	{
		if(this.phoneList == null)
		{
			this.phoneList = new ArrayList <Phone> ();
		}
		for (int i = 0; i < this.phoneList.size(); i++)
		{
			if (this.phoneList.get(i) == phone)
			{
				this.phoneList.remove(i);
			}
		}
	}
	public ArrayList <Phone> getPhoneList()
	{
	if(this.phoneList == null)
	{
		this.phoneList = new ArrayList <Phone> ();
	}
	{
		return this.phoneList;
	}
	}
	
	public Address getAddress(Integer addressId)
	{
		for(int i = 0; i < this.addressList.size(); i++)
		{
			Address currentAddress = this.addressList.get(i);
			Integer currentAddressId = currentAddress.getID();
			Integer compareInt = Integer.compare(addressId, currentAddressId);
			if(compareInt == 0)
			{
				return currentAddress;
			}
		}
		return null;
	}
	
	public Phone getPhone(Integer phoneId)
	{
		for(int i = 0; i < this.phoneList.size(); i++)
		{
			Phone currentPhone = this.phoneList.get(i);
			Integer currentPhoneId = currentPhone.getID();
			Integer compareInt = Integer.compare(phoneId, currentPhoneId);
			if(compareInt == 0)
			{
				return currentPhone;
			}
		}
		return null;
	}
		
	public void updateAddressList(Address address)
	{
		Integer id = address.getID();
		try
		{
			for(int i = 0; i < this.addressList.size(); i++)
			{
				Address currentAddress = this.addressList.get(i);
				Integer currentId = currentAddress.getID();
				Integer compareInt = Integer.compare(id, currentId);
				if(compareInt == 0)
				{
					this.addressList.set(i, address);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	public void updatePhoneList(Phone phone)
	{
		Integer id = phone.getID();
		try
		{
			for(int i = 0; i < this.phoneList.size(); i++)
			{
				Phone currentPhone = this.phoneList.get(i);
				Integer currentId = currentPhone.getID();
				Integer compareInt = Integer.compare(id, currentId);
				if(compareInt == 0)
				{
					this.phoneList.set(i, phone);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	public void printContact()
	{
		System.out.println(this.firstName);
		System.out.println(this.lastName);
		if(this.addressList == null)
		{
			return;
		}
		for(int i = 0; i < this.addressList.size(); i++)
		{
			Address address = this.addressList.get(i);
			System.out.println(address.getType());
			System.out.println(address.getStreet());
			System.out.println(address.getCity());
			System.out.println(address.getState());
			System.out.println(address.getZipCode());
			System.out.println(address.getID());
		}
		if(this.phoneList == null)
		{
			return;
		}
		for(int x = 0; x < this.phoneList.size(); x++) 
		{
			Phone phone = this.phoneList.get(x);
			System.out.println(phone.getType());
			System.out.println(phone.getNumber());
			System.out.println(phone.getID());
		}
	}
}