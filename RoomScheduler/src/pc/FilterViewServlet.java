package pc;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class FilterViewServlet extends HttpServlet
{
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException
	{
		String user = req.getParameter("user");
		String filterDate = req.getParameter("filter");
		System.out.println(user + filterDate);
		
		Date formattedDate = null;
		try {
			formattedDate = new SimpleDateFormat("yyyy-MM-dd").parse(filterDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Date> fromDateDB = new ArrayList<Date>();
		List<Date> toDateDB = new ArrayList<Date>();
		List<Key> IDList = new ArrayList<Key>();
		List<String> bookingNameDB = new ArrayList<String>();
		
		String givenFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(givenFormat);
		
		
		Query q = pm.newQuery(Bookings.class);
		q.compile();
		@SuppressWarnings("unchecked")
		List<Bookings> bookList = (List<Bookings>)q.execute();
		Date fromDate = null;
		Date toDate = null;
		for(Bookings b : bookList)
		{
			try {
				fromDate = sdf.parse(sdf.format(b.getFromDate()));
				toDate = sdf.parse(sdf.format(b.getFromDate()));
				fromDateDB.add(fromDate);
				toDateDB.add(toDate);
				IDList.add(b.getId().getParent());
				bookingNameDB.add(b.getBookingName());
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				pm.close();
			}
		}
		req.setAttribute("user", user);
		req.setAttribute("formattedDate", formattedDate);
		req.setAttribute("fromDateDB", fromDateDB);
		req.setAttribute("toDateDB", toDateDB);
		req.setAttribute("IDList", IDList);
		req.setAttribute("bookingNameDB", bookingNameDB);
		System.out.println(fromDateDB);
		System.out.println(toDateDB);
		System.out.println(IDList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/filterView.jsp");
		dispatcher.forward(req, resp);
		
		
	}
}
