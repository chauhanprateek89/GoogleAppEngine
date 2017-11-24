package pc;

import java.io.IOException;

import javax.jdo.PersistenceManager;
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
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html");
		
		
		//need this to access google user service
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		String login_url = us.createLoginURL("/");
		String logout_url = us.createLogoutURL("/");
		
		//persistence manager, to extract the saved object Drivers, from the database
		PersistenceManager pm = null;
		Drivers settings = null;
		
		if(u!=null)
		{
			Key user_key = KeyFactory.createKey("Drivers", u.getUserId());
			pm = PMF.get().getPersistenceManager();
			try
			{
				settings = pm.getObjectById(Drivers.class, user_key);
			}
			catch(Exception e)
			{
				//e.printStackTrace();
			}
			finally
			{
				pm.close();
			}
		}
		
		//attaching few things to the request so that we can access them in the jsp
		req.setAttribute("user", u);
		req.setAttribute("login_url", login_url);
		req.setAttribute("logout_url", logout_url);
		
		//request dispatcher to forward onto JSP
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
		rd.forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/html");
		
		/*the first form in root.jsp is directed back to RootServlet.java with method set as post. so we take all the input values from the form
		 * and store them as strings*/
		String userEmail = UserServiceFactory.getUserService().getCurrentUser().getEmail();//req.getParameter("userEmail");
		String gpu_name = req.getParameter("driver_name");
		String geometryShader = req.getParameter("geometryShader");
		String tesselationShader = req.getParameter("tesselationShader");
		String shaderInt16 = req.getParameter("shaderInt16");
		String sparseBinding = req.getParameter("sparseBinding");
		String textureCompressionETC2 = req.getParameter("textureCompressionETC2");
		String vertexPipelineStoresAndAtomics = req.getParameter("vertexPipelineStoresAndAtomics");
		
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		
		PersistenceManager pm = null;
		Drivers settings = new Drivers();
		//an object of Drivers class is created. the values received from form in 
		//root.jsp are assigned to the features of GPU defined in Drivers.java
		try
		{
			pm = PMF.get().getPersistenceManager();
			settings = pm.getObjectById(Drivers.class, gpu_name);
			//settings.setId(id);
			resp.sendRedirect("/");
		}
		catch(Exception e)
		{
			settings.setGPU_name(gpu_name);
			settings.setGeometryShader(geometryShader);
			settings.setShaderInt16(shaderInt16);
			settings.setSparseBinding(sparseBinding);
			settings.setTesselationShader(tesselationShader);
			settings.setTextureCompressionETC2(textureCompressionETC2);
			settings.setVertexPipelineStoresAndAtomics(vertexPipelineStoresAndAtomics);
			//storing in database
			pm.makePersistent(settings);
		
		}

		//debugging purpose code
			System.out.println(userEmail+", "+gpu_name+", "
		+geometryShader+", "+tesselationShader+", "+shaderInt16
		+", "+sparseBinding+", "+textureCompressionETC2+", "+
		vertexPipelineStoresAndAtomics);
		
		//after storing the values, the flow is redirected to RootServlet 
		//itself, which in turns calls out root.jsp
		resp.sendRedirect("/");
	}
}
