package dom.contact.project.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Phone implements Serializable
{
	static final long serialVersionUID = 42L;
	private String type;
	private String number;
	private Integer id;
	
	// constructor 
	public Phone(String type, String number, Integer id)
	{
		setType(type);
		setNumber(number);
		setID(id);
	}
	public Phone(String type, String number, ArrayList <Phone> phoneList)
	{
		initialize(type, number, phoneList);
	}
	private void initialize(String type, String number, ArrayList <Phone> phoneList)
	{
		generateId(phoneList);
		setType(type);
		setNumber(number);
	}
	
	// setters
	public void setType(String type)
	{
		this.type = type;
	}
	public void setNumber(String number)
	{
		this.number = number;
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
	public String getNumber()
	{
		return this.number;
	}
	public Integer getID()
	{
		return this.id;
	}
	
	public void generateId(ArrayList <Phone> phoneList)
	{
		if(phoneList.isEmpty())
		{
			//System.out.println("EMPTY");
			Integer generatedId = new Integer(0);
			setID(generatedId);
			//System.out.println(getID());
		}
		else if(phoneList.size() == 1)
		{
			//System.out.println("1 id");
			//System.out.println("NOT EMPTY");
			Phone lastPhone = phoneList.get(phoneList.size() - 1);
			int lastId = lastPhone.getID();
			int newId = lastId + 1;
			Integer generatedId = new Integer(newId);
			setID(generatedId);
		}
		else
		{
			int largestId = 0;
			for(int i = 0; i < phoneList.size(); i++)
			{
				Phone currentPhone = phoneList.get(i);
				int currentId = currentPhone.getID();
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