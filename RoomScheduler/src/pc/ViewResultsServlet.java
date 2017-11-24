package pc;

import java.io.IOException;/*
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;*/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Locale;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class ViewResultsServlet extends HttpServlet
{
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
	throws IOException, ServletException
	{
		resp.setContentType("text/html");
		String roomName = req.getParameter("room");
		String user = req.getParameter("hidden");
		
		PersistenceManagerFactory pmf = PMF.get();
		PersistenceManager pm = pmf.getPersistenceManager();
		
		Key k1 = KeyFactory.createKey("Rooms", roomName); 
		Rooms room = null;
		
			room = pm.getObjectById(Rooms.class,k1);
			String roomDB = room.getRoomName();
			
			List<String> bookNameDB = new ArrayList<String>();
			List<Date> fromDateDB = new ArrayList<Date>();
			List<Date> toDateDB = new ArrayList<Date>();
			List<String> userDB = new ArrayList<String>();
			
			for(Bookings bookings : room.bookings())
			{
				bookNameDB.add(bookings.getBookingName());
				fromDateDB.add(bookings.getFromDate());
				toDateDB.add(bookings.getToDate());
				userDB.add(bookings.getMadeBy());
			}
			pm.close();
			if(user.equals(userDB.get(0)))
			{
			req.setAttribute("bookNameDB", bookNameDB);
			req.setAttribute("fromDateDB", fromDateDB);
			req.setAttribute("toDateDB", toDateDB);
			req.setAttribute("userDB", userDB);
			req.setAttribute("roomDB", roomDB);
			req.setAttribute("user", user);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/viewResults.jsp");
			dispatcher.forward(req, resp);
			}
			else
			{
				resp.getWriter().print("No record found");
				resp.getWriter().print("<br>");
				resp.getWriter().print("<form action=\"viewResults\">"
						+ "<input type=\"submit\" value=\"Back\"/>"
						+ "</form>");
			}		 
	}
}
