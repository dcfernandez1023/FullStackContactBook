package dom.contact.project.model;

import java.util.ArrayList;
import java.lang.Exception;
import java.io.Serializable;
import java.util.ArrayList;

//main purpose of this class is to hold all contact objects in an arraylist

public class ContactArrayList implements Serializable
{
	static final long serialVersionUID = 42L;
	private ArrayList <Contact> contactArrayList;

	// constructor 
	public ContactArrayList()
	{
		initialize();
	}
	private void initialize()
	{
		setContactArrayList();
	}
	
	// setters
	public void setContactArrayList()
	{
		this.contactArrayList = new ArrayList <Contact> ();
	}
	
	public void replaceContactList(ArrayList <Contact> contactList)
	{
		this.contactArrayList = contactList;
	}
	
	// getters 
	public ArrayList <Contact> getContactArrayList()
	{
		return this.contactArrayList;
	}
	
	// update methods 
	public void addContact(Contact contact)
	{
		if(this.contactArrayList == null)
		{
			this.contactArrayList = new ArrayList <Contact> (); // reuse setter
		}
		this.contactArrayList.add(contact);
	}
	public void removeContact(Contact contact)
	{
		if(this.contactArrayList == null)
		{
			this.contactArrayList = new ArrayList <Contact> (); // reuse setter
		}
		for(int i = 0; i < this.contactArrayList.size(); i++)
		{
			if(this.contactArrayList.get(i) == contact)
			{
				this.contactArrayList.remove(i);
			}
		}
	}
	public void removeAllContacts()
	{
		this.contactArrayList = new ArrayList <Contact> ();
	}
	public Contact getContact(Integer id)
	{
		for(int i = 0; i < this.contactArrayList.size(); i++)
		{
			Contact currentContact = this.contactArrayList.get(i);
			Integer currentId = currentContact.getID();
			//System.out.println("Current ID: " + currentId);
			//System.out.println("ID: " + id);
			//System.out.println("Compare: " + Integer.compare(id, currentId));
			Integer compareInt = Integer.compare(id, currentId);
			if(compareInt == 0)
			{
				return currentContact;
			}
		}
		return null;
	}
	// search method
	public ArrayList <Contact> searchContacts(String userInput)
	{
		ArrayList <Contact> searchArrayList = new ArrayList <Contact> ();
		
		for(int i = 0; i < this.contactArrayList.size(); i++)
		{
			Contact contact = this.contactArrayList.get(i);
			if(userInput.equals(contact.getID()))
			{
				searchArrayList.add(contact);
			}
			if(userInput.equals(contact.getFirstName()))
			{
				searchArrayList.add(contact);
			}
			if(userInput.equals(contact.getLastName()))
			{
				searchArrayList.add(contact);
			}
		}
		return searchArrayList;
	}
	public void updateContactList(Contact contact)
	{
		Integer id = contact.getID();
		try
		{
			for(int i = 0; i < this.contactArrayList.size(); i++)
			{
				Contact currentContact = this.contactArrayList.get(i);
				Integer currentId = currentContact.getID();
				Integer compareInt = Integer.compare(id, currentId);
				if(compareInt == 0)
				{
					this.contactArrayList.set(i, contact);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
}