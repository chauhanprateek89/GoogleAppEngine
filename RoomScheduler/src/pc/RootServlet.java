package pc;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class RootServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("text/html");
		
		//we need to get access to the google user service
		
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		String login_url = us.createLoginURL("/");
		String logout_url = us.createLogoutURL("/");
				
		//attach in request to access them in jsp
		req.setAttribute("user", u);
		req.setAttribute("login_url", login_url);
		req.setAttribute("logout_url", logout_url);
		
		//request dispatcher to forward it to jsp
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
		rd.forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		String roomNo = "R0"+req.getParameter("roomNo");
		String smoking = req.getParameter("smoking");
		
		String submitAdd = req.getParameter("add");
		
		//retrieve values from Rooms entity from the datastore
		//if it exists, and show the rooms on root.jsp
		PersistenceManagerFactory pmf = PMF.get();
		User u = UserServiceFactory.getUserService().getCurrentUser();
		Key k1;
		Rooms r = null;
		if(submitAdd != null)
		{
			PersistenceManager pm = pmf.getPersistenceManager();
			k1 = KeyFactory.createKey("Rooms", roomNo);
			try
			{
				r = pm.getObjectById(Rooms.class, k1);
			}
			catch(Exception e)
			{//make persistent
				r = new Rooms();
				r.setID(k1);
				r.setRoomName(roomNo);
				r.setSmoking(smoking);
				pm.makePersistent(r);
			}
			finally
			{pm.close();}
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		req.setAttribute("user", u);
		//System.out.println(roomNo + " " + smoking + " ");
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
		rd.forward(req, resp);
	}
}
