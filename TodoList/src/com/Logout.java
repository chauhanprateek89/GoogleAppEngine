package com;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Logout extends HttpServlet
{
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/html");
		String logout_url = req.getParameter("logout_url");
		req.getSession().invalidate();
		/*resp.sendRedirect(logout_url);*/
		resp.getWriter().print("<h2>You have been logged out. REDIRECTING TO SIGN IN PAGE..................</h2><br/>");
		resp.getWriter().println("<script>");
		resp.getWriter().println("setTimeout(function() {");
		resp.getWriter().println("document.location = \""+logout_url+"\";");
		resp.getWriter().println("}, 3000); ");
		resp.getWriter().println("</script>");
		      
		  
		
	}
}
