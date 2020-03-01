package dom.contact.project.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Address implements Serializable
{
	static final long serialVersionUID = 42L;
	// class attributes
	private String type;
	private String street;
	private String city;
	private String state;
	private String zipCode;
	private Integer id; 
	// constructor
	public Address(String type, String street, String city, String state, String zipCode, Integer id)
	{
		setType(type);
		setStreet(street);
		setCity(city);
		setState(state);
		setZipCode(zipCode);
		setID(id);
	}
	public Address(String type, String street, String city, String state, String zipCode, ArrayList <Address> addressList)
	{
		initialize(type, street, city, state, zipCode, addressList);
	}
	private void initialize(String type, String street, String city, String state, String zipCode, ArrayList <Address> addressList)
	{
		generateId(addressList);
		setType(type);
		setStreet(street);
		setCity(city);
		setState(state);
		setZipCode(zipCode);
	}
	
	// setters
	public void setType(String type)
	{
		this.type = type;
	}
	public void setStreet(String street)
	{
		this.street = street;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}
	public void setID(Integer id)
	{
		this.id = id;
	}
	
	// getters
	public String getType()
	{
		return this.type;
	}
	public String getStreet()
	{
		return this.street;
	}
	public String getCity()
	{
		return this.city;
	}
	public String getState()
	{
		return this.state;
	}
	public String getZipCode()
	{
		return this.zipCode;
	}
	public Integer getID()
	{
		return this.id;
	}
	
	public void generateId(ArrayList <Address> addressList)
	{
		if(addressList.isEmpty())
		{
			//System.out.println("EMPTY");
			Integer generatedId = new Integer(0);
			setID(generatedId);
			//System.out.println(getID());
		}
		else if(addressList.size() == 1)
		{
			//System.out.println("1 id");
			//System.out.println("NOT EMPTY");
			Address lastAddress = addressList.get(addressList.size() - 1);
			int lastId = lastAddress.getID();
			int newId = lastId + 1;
			Integer generatedId = new Integer(newId);
			setID(generatedId);
		}
		else
		{
			int largestId = 0;
			for(int i = 0; i < addressList.size(); i++)
			{
				Address currentAddress = addressList.get(i);
				int currentId = currentAddress.getID();
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
}