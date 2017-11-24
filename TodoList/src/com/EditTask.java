package com;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class EditTask extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse resp) throws IOException
	{
		//TodoUser tu = (TodoUser)request.getAttribute("todoUser");
		Todo t = (Todo)request.getSession().getAttribute("todo");
		String task_name = request.getParameter("edit_task");
		//int userIndex = (Integer)request.getAttribute("userIndex");
		
		
		//User u = UserServiceFactory.getUserService().getCurrentUser();
		//Key user_key = KeyFactory.createKey("TodoUser", u.getUserId());
		//TodoUser tu;
		//Todo t;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//tu = pm.getObjectById(TodoUser.class, user_key);
		//t = tu.getTask(userIndex);
		t.setName(task_name);
		pm.makePersistent(t);
		pm.close();
		//tu.getTask(userIndex).setName(task_name);
		resp.sendRedirect("/");
	}
}
