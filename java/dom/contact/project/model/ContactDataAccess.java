package dom.contact.project.model;

public interface ContactDataAccess 
{
	public void saveToDb(String jsonBody);
	public String loadFromDb();
}
