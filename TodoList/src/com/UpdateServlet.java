package com;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class UpdateServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		// take input from jsp
		int userIndex=Integer.parseInt(req.getParameter("index"));
		String action_name=req.getParameter("action_name");
		
		// get access to the user service to get our user
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
						
		// determine if the user has clicked the delete button or the complete button
		PersistenceManager pm = null;
		TodoUser tu;
		Todo t;
		Key user_key = KeyFactory.createKey("TodoUser", u.getUserId());
		// if delete button clicked, task will be delete from the list and redirect to / 
		// if the complete button clicked, switch the state of the task and redirect to /
		if(action_name.equals("Delete")){			
			try{
				pm = PMF.get().getPersistenceManager();
				tu = pm.getObjectById(TodoUser.class, user_key);
				t = tu.getTask(userIndex);
				tu.deleteTask(t);
				pm.makePersistent(tu);
			}catch(Exception e){}
			finally
			{
				pm.close();
				resp.sendRedirect("/");
			}
		}else if(action_name.equals("Complete") || action_name.equals("Incomplete")){
			try{
				pm = PMF.get().getPersistenceManager();
				tu = pm.getObjectById(TodoUser.class, user_key);
				t = tu.getTask(userIndex);
				t.swapComplete();
				pm.makePersistent(t);
				pm.makePersistent(tu);
			}catch(Exception e){}
			finally
			{
				pm.close();
				resp.sendRedirect("/");
			}
		}else if(action_name.equals("Edit"))
		{
			try {
				pm = PMF.get().getPersistenceManager();
				tu = pm.getObjectById(TodoUser.class, user_key);
				t = tu.getTask(userIndex);
				String task_name = t.getName();
				pm.close();
				//req.setAttribute("todoUser", tu);
				//req.setAttribute("todo", t);
				//req.getSession().setAttribute("todoUser", tu);
				req.getSession().setAttribute("todo", t);
				req.setAttribute("task_name", task_name);
				//req.setAttribute("userIndex", userIndex);
				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/editTask.jsp");
				rd.forward(req, resp);
				/*resp.getWriter().println("Edit task<br/>");
				resp.getWriter().println("<form action=\"/editTask\" method=\"post\">");
				resp.getWriter().println("<input type=\"text\" name=\"edit_task\" value="+task_name+">");
				resp.getWriter().println("<input type=\"submit\" value=\"Change Task\">");
				resp.getWriter().println("</form>");*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
