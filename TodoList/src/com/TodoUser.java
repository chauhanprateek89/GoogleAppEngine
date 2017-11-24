package com;

//imports
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

//class definition
@PersistenceCapable
public class TodoUser implements Serializable{
	// the id of the task user
	@PrimaryKey
	@Persistent
	private Key id;
	
	// the list of tasks that are belonging to this user
	// the parent variable of the Todo class will map the
	// task to the TodoUser.
	@Persistent(mappedBy="parent")
	private List<Todo> todos;
	
	// setter and getter for the id of this user
	public Key id(){return id;}
	public void setId(final Key id){this.id = id;}
	
	// adds a Todo to the list
	public void addTask(Todo t) {
		if(todos == null){todos = new ArrayList<Todo>();}
		todos.add(t); }
	
	// removes a Todo from the list
	public void deleteTask(Todo t) { todos.remove(t); }
	
	// returns a specific task from the list
	public Todo getTask(final int n){
		Todo td = new Todo();
		td = todos.get(n);
		return td;
	}
	
	// determines if there is a task with the same name in the list
	public boolean isTaskPresent(final Todo t){
	    boolean isPresent = false;
	    for( int i = 0; i < todos.size(); i++){
	        if(todos.get(i).isTaskSame(t)){isPresent = true;}
	    }
	    return isPresent;
	}
	
	// returns the number of tasks in the list
	public int numTasks(){ return todos.size(); }
	
	//returns the entire list of tasks associated with this user
	public List<Todo> tasks() { return todos; }
}