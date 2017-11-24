package pc;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class JDOFiles 
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	public Key id;
	
	//name of the file
	@Persistent
	public String name;
	
	//key to get access to the file inside the Blobstore
	@Persistent
	public BlobKey blobKey;
	
	// DropboxDirectory that is the parent of this file 
	@Persistent
	public JDOSubDirectories parent;
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setBlobKey(BlobKey blobKey)
	{
		this.blobKey = blobKey;
	}
	
	public void setParent(JDOSubDirectories parent)
	{
		this.parent = parent;
	}
	
	// Return the name of this file
	public String getName(){return name;}
	// Return the key to this file
	public BlobKey getBlobKey(){return blobKey;}
}
