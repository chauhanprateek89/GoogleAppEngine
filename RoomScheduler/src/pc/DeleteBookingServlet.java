package pc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DeleteBookingServlet extends HttpServlet
{
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws IOException, ServletException
	{
		res.setContentType("text/html");
		ArrayList<String> parameterNames = new ArrayList<String>();
		 Enumeration enumeration = req.getParameterNames();
		    while (enumeration.hasMoreElements()) {
		        String parameterName = (String) enumeration.nextElement();
		        parameterNames.add(parameterName);
		    }
		    
		String bookingID = parameterNames.get(0);    
		String roomName = parameterNames.get(1);
		System.out.println(bookingID+" "+roomName);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Rooms rooms = null;
		Bookings booking = null;
		try
		{
			rooms = pm.getObjectById(Rooms.class, roomName);
		}
		catch(Exception e)
		{}
		
		List<Bookings> bookings = rooms.bookings();
		for(Bookings b : bookings)
		{
			if(b.getBookingName().equals(bookingID));
			{
				try {
					booking = pm.getObjectById(Bookings.class, b.getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println(booking.getBookingName());
		if(booking.getBookingName()!=null)
		{
			pm.deletePersistent(booking);
			pm.close();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			res.sendRedirect("/");
		}
		else
		{pm.close();}
	}
}
