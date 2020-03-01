package dom.contact.project.model;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.lang.Exception;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class ContactsFile
{
	private String path;
	private File contactsFile;

	// constructor
	public ContactsFile(String path) throws IOException
	{
		initialize(path);
	}
	private void initialize(String path) throws IOException
	{
		setPath(path);
		createFile();
	}
	
	// setters
	public void setPath(String path)
	{
		this.path = path;
	}
	
	// getter
	public String getPath()
	{
		return this.path;
	}
	
	// method to create a new file 
	public void createFile() throws IOException
	{
		if(this.contactsFile == null)
		{
			this.contactsFile = new File(this.path);
		}
		
			this.contactsFile = new File(this.path);
			if (this.contactsFile.createNewFile() == false)
			{
				//System.out.println("File already exists");
				;
			}
	}
	
	// method to write to the file 
	public void writeFile(ContactArrayList obj) throws IOException, Exception
	{
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null; 
		
		if(this.contactsFile == null)
		{
			this.contactsFile = new File(this.path);
		}
		try
		{
			fos = new FileOutputStream(this.path);
			oos = new ObjectOutputStream(fos);		
			oos.writeObject(obj);
			oos.close();
			fos.close();
		}
		catch(IOException ioe)
		{
			System.out.println("IOException: Cannot write to specified file");
		}
		catch(Exception e)
		{
			System.out.println("General Exception found in method 'writeFile()'");
		}
	}
	
	// method to read from the file
	public ContactArrayList readFile() throws Exception
	{
		ContactArrayList obj = new ContactArrayList();
		FileInputStream fis = null; 
		ObjectInputStream ois = null; 
		
		if(this.contactsFile == null)
		{
			this.contactsFile = new File(this.path);
		}
		try
		{
			fis = new FileInputStream(this.path);
			ois = new ObjectInputStream(fis);
			obj = (ContactArrayList) ois.readObject();
			ois.close();
			fis.close();
		}
		catch(Exception e)
		{
			if (e instanceof EOFException || e instanceof IOException) 
			{
				System.out.println("File IO warning - this is expected.");
				if (ois != null)
				{
					System.out.println("DEBUG: closing ObjectInputStream (ois).");
					ois.close();
				}
				if (fis != null)
				{
					System.out.println("DEBUG: closing FileInputStream (fis).");
					fis.close();
				}
				return obj;
			}
			throw e;
		}
		return obj;
	}
}