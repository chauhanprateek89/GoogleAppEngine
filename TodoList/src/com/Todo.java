package com;

//imports
import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Todo implements Serializable{
	// the id of the task as this is not going to be directly retrieved 
	// we will allow the keys for this to be auto generated
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	// name of the task
	@Persistent
	private String name;	
	
	// parent of the task
	@Persistent
	private TodoUser parent;		
	
	// state of the task
	@Persistent
	private boolean complete;  
	
	// getter and setter for the name
	public String getName() { return name; }
	public void setName(final String name) { this.name = name; }
	
	// setter method for setting the parent to establish the parent child relationship
	public void setParent(final TodoUser parent) { this.parent = parent; }
	
	// getter and setter for the state of the task
	public boolean getComplete(){ return complete; }
	public void setCompleted(final boolean complete) { this.complete = complete; }
	
	// flips the state of the task
	public void swapComplete() { complete = !complete; }
	
	// compares against another Todo to see if they share the same name
	public boolean isTaskSame(final Todo t) {
		Todo td = (Todo) t;
		return this.name.equals(td.getName());
	}
}
