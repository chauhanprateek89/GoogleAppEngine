package pc;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class BookingServlet extends HttpServlet 
{
	public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
	    return !start1.after(end2) && !start2.after(end1);
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException
	{
		PersistenceManagerFactory pmf = PMF.get();
		PersistenceManager pm = null;
		User u = UserServiceFactory.getUserService().getCurrentUser();
		try
		{
			pm = pmf.getPersistenceManager();
			Query q = pm.newQuery(Rooms.class);
			q.compile();
			@SuppressWarnings("unchecked")
			List<Rooms> results = (List<Rooms>)q.execute();
			List<String> jspVal = new ArrayList<String>();
			for(Rooms res : results)
			{
				jspVal.add(res.getRoomName());
				req.setAttribute("roomName", jspVal );
			}
			System.out.println(jspVal);
		}
		catch(Exception e)
		{}
		finally
		{pm.close();}
		req.setAttribute("user", u);
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/bookings.jsp");
		rd.forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm",Locale.ENGLISH);
		String roomName = req.getParameter("roomSelect");
		String from = req.getParameter("fromDate");
		String to = req.getParameter("toDate");
		//Date getFromDate = null;
		//Date getToDate = null;
		Date fromDate = null;
		try {
			fromDate = format.parse(from);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date toDate = null;
		try {
			toDate = format.parse(to);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String user = req.getParameter("user");
		String booking_name = "B"+roomName+from+"-"+to;
		PersistenceManagerFactory pmf = PMF.get();
		PersistenceManager pm = pmf.getPersistenceManager();
		
		List<Date> fd = new ArrayList<Date>();
		List<Date> td = new ArrayList<Date>();
		List<Boolean> overlap = new ArrayList<Boolean>();
		Key k1 = KeyFactory.createKey("Rooms", roomName);
		Rooms room = pm.getObjectById(Rooms.class,k1);
		Bookings book = new Bookings();
		int flag = 0;
		//if room.bookings is not empty
		if(room.bookings().size() != 0)
		{
			for(Bookings btemp : room.bookings())
			{						
					fd.add(btemp.getFromDate());
					td.add(btemp.getToDate());
			}
			for(int i=0; i<room.bookings().size(); i++ )
			{
				Boolean var = isOverlapping(fd.get(i), td.get(i), fromDate, toDate);
				overlap.add(var);
			}
			for(int j = 0; j<overlap.size();j++)
			{
				if(overlap.get(j) == true)
				{
					flag = 1;
					continue ;
				}
			}
			if(flag != 1)
			{
				book.setParent(room);
				book.setBookingName(booking_name);
				book.setFromDate(fromDate);
				book.setToDate(toDate);
				book.setMadeBy(user);
				
				room.addBooking(book);
				pm.makePersistent(book);
				pm.makePersistent(room);
				pm.close();
				resp.sendRedirect("/");
			}
			else
			{
				String message = "booking time overlap";
				req.setAttribute("message",message);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/bookings.jsp");
				dispatcher.forward(req, resp);
			}
			
		}
		else
		{
			
			book.setParent(room);
			book.setBookingName(booking_name);
			book.setFromDate(fromDate);
			book.setToDate(toDate);
			book.setMadeBy(user);
			
			room.addBooking(book);
			pm.makePersistent(book);
			pm.makePersistent(room);
			pm.close();
			resp.sendRedirect("/");
		}
			
		
	}
	
	
}
