package dom.contact.project.model;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.ConnectionString;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDbDataAccess implements ContactDataAccess
{
	private MongoDatabase database;
	private MongoCollection <Document> contactCollection;
	private String username;
	
	public MongoDbDataAccess(String username)
	{
			connectToDb(username);
	}
	
	private void connectToDb(String username)
	{
		ConnectionString connectionString = new ConnectionString("mongodb+srv://bigdom1023:****@dom-cluster-1numt.mongodb.net/test?retryWrites=true&w=majority");
		MongoClient mongoClient = MongoClients.create(connectionString);
		MongoDatabase database = mongoClient.getDatabase("ContactDB");
		setDatabase(database);
		setCollection();
		setUsername(username);
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
	
	private void setUsername(String username)
	{
		this.username = username;
	}
	
	public void saveToDb(String jsonBody) 
	{
		Bson bsonFilter = Filters.eq("_id", this.username);
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
		doc.put("Contact List", jsonBody);
		this.contactCollection.findOneAndReplace(bsonFilter, doc);
	}
	
	public String loadFromDb()
	{
		Bson bsonFilter = Filters.eq("_id", this.username);
		FindIterable <Document> iterable = this.contactCollection.find(bsonFilter);
		if(iterable == null)
		{
			return new String("[]");
		}
		Document doc = iterable.first();
		if(doc == null)
		{
			return new String("[]");
		}
		String jsonBody = doc.getString("Contact List");
		if (jsonBody == null)
		{
			return new String ("[]");
		}
		return jsonBody;
	}
}

