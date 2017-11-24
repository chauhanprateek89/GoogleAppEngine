package pc;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF 
{
	public static final PersistenceManagerFactory pmf_instance =
			JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PMF() {}
	
	public static PersistenceManagerFactory get()
	{
		return pmf_instance;
	}
}
