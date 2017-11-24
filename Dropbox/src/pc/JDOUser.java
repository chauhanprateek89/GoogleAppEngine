package pc;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class JDOUser 
{
	@PrimaryKey
	@Persistent
	private Key id;

	public void setId(Key id) {
		this.id = id;
	}
	
}
