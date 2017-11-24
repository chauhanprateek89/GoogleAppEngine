package com;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.Todo;
import com.TodoUser;
import com.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class RootServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws
	IOException, ServletException{
		// we are outputting html
		resp.setContentType("text/html");
		
		// we need to get access to the google user service
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		String login_url = us.createLoginURL("/");
		String logout_url = us.createLogoutURL("/");
		
		// persistence manager and a user
		PersistenceManager pm = null;
		TodoUser todoUser = null;
		Key user_key = null;
		
		// get access to the user. if they do not exist in the datastore then
		// store a default version of them. also check if a user has logged in first
		if(u != null) {
			user_key = KeyFactory.createKey("TodoUser", u.getUserId());
			pm = PMF.get().getPersistenceManager();
			try {
				todoUser = pm.getObjectById(TodoUser.class, user_key);
			} catch(Exception e) {
				todoUser = new TodoUser();
				todoUser.setId(user_key);
				pm.makePersistent(todoUser);
			}
			pm.close();
		}
	
		// get the task list of the logged in user and forward its list of tasks to the JSP
		try{
			pm = PMF.get().getPersistenceManager();
			todoUser = pm.getObjectById(TodoUser.class, user_key);
			List<Todo> list = todoUser.tasks();
			req.setAttribute("tasks", list);
			pm.close();
		}catch(Exception e){}
		
		// attach a few things to the request such that we can access them in the jsp
		req.setAttribute("user", u);
		req.setAttribute("login_url", login_url);
		req.setAttribute("logout_url", logout_url);
		
		// get access to a request dispatcher and forward onto the root.jsp file
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
		rd.forward(req, resp);
	}
	
public void doPost(HttpServletRequest req, HttpServletResponse resp) throws
		IOException {
		// take input from jsp
		String name = "";
		boolean completed = false; // initial state of a task is incomplete
	
		// get the task name from the form
		try{
			name = req.getParameter("name");
		}catch(Exception e){
			// failed to update so just redirect to /
			resp.sendRedirect("/");
		}
		
		// get access to the user service to get our user
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		//req.setAttribute("user", u);	
				
		// update the list in the datastore and then redirect to /
		PersistenceManager pm = null;
		TodoUser tu;
		Todo t;
		Key user_key = KeyFactory.createKey("TodoUser", u.getUserId());
		try {
			pm = PMF.get().getPersistenceManager();
			tu = pm.getObjectById(TodoUser.class, user_key);
			t= new Todo();
			t.setParent(tu);
			t.setName(name);
			t.setCompleted(completed);
			// check if name is empty or task with same name on the list
			// if one of these tests fail then redirect to /
			if(tu.isTaskPresent(t) || name.isEmpty()){
				pm.close();
				resp.sendRedirect("/");
				return;
			}else{
				tu.addTask(t);
			}
			pm.makePersistent(t);
			pm.makePersistent(tu);
		} catch (Exception e) {}
		pm.close();
		resp.sendRedirect("/");
	}
}
