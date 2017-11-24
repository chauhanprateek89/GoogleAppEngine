package com;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	
	private static final PersistenceManagerFactory pmf_instance = 
			JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PMF() {}
	
	public static PersistenceManagerFactory get()
	{
		return pmf_instance;
	}

}
