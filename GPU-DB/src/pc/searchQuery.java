package pc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;

import javax.jdo.Query;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class searchQuery extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/html");
		/*the second form in root.jsp named as searchQuery posts the inputs values to searchQuery.java as the action is redireted here*/
		String geometryShader = req.getParameter("geometryShader");
		String tesselationShader = req.getParameter("tesselationShader");
		String shaderInt16 = req.getParameter("shaderInt16");
		String sparseBinding = req.getParameter("sparseBinding");
		String textureCompressionETC2 = req.getParameter("textureCompressionETC2");
		String vertexPipelineStoresAndAtomics = req.getParameter("vertexPipelineStoresAndAtomics");
		
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		// an empty string searchQuery defined which will be used to fetch the compatible GPU with respect to the features selected
		String searchQuery = "";
		
		PersistenceManager pm = null;		
		Drivers settings = new Drivers();
		Key user_key = KeyFactory.createKey("Drivers", u.getUserId());
		try
		{
			pm = PMF.get().getPersistenceManager();
			settings = pm.getObjectById(Drivers.class, user_key);
		}
		catch(Exception e)
		{
			settings.setGeometryShader(geometryShader);
			settings.setShaderInt16(shaderInt16);
			settings.setSparseBinding(sparseBinding);
			settings.setTesselationShader(tesselationShader);
			settings.setTextureCompressionETC2(textureCompressionETC2);
			settings.setVertexPipelineStoresAndAtomics(vertexPipelineStoresAndAtomics);
			//construction of the query with respect to the features selected in root.jsp
			
			if(geometryShader.equals("1"))
			{
				if(searchQuery.equals(""))
					searchQuery = " geometryShader == :g1 ";
				else
					searchQuery += "&& geometryShader == :g1 ";
			}
			
			if(shaderInt16.equals("1"))
			{
				if(searchQuery.equals(""))
					searchQuery = " shaderInt16 == :g2 ";
				else
					searchQuery += "&& shaderInt16 == :g2 ";
			}
			
			if(sparseBinding.equals("1"))
			{
				if(searchQuery.equals(""))
					searchQuery = " sparseBinding == :g3 ";
				else
					searchQuery += "&& sparseBinding == :g3 ";
			}
			
			if(tesselationShader.equals("1"))
			{
				if(searchQuery.equals(""))
					searchQuery = " tesselationShader == :g4 ";
				else
					searchQuery += "&& tesselationShader == :g4 ";
			}
			
			if(textureCompressionETC2.equals("1"))
			{
				if(searchQuery.equals(""))
					searchQuery = " textureCompressionETC2 == :g5 ";
				else
					searchQuery += "&& textureCompressionETC2 == :g5 ";
			}
			
			if(vertexPipelineStoresAndAtomics.equals("1"))
			{
				if(searchQuery.equals(""))
					searchQuery = " vertexPipelineStoresAndAtomics == :g6 ";
				else
					searchQuery += "&& vertexPipelineStoresAndAtomics == :g6 ";
			}
			
			// creating a list object to store the result when we execute the query
			List<Drivers> result_set = null;
			
			if(u!=null)
			{
				Query query = pm.newQuery(Drivers.class);
				query.setFilter(searchQuery);
						//mapping parameters using a Map object 
				Map<String,String> paramValues = new HashMap();
				if(geometryShader.equals("1"))
					paramValues.put("g1", geometryShader);
				if(shaderInt16.equals("1"))
					paramValues.put("g2", shaderInt16);
				if(sparseBinding.equals("1"))
					paramValues.put("g3", sparseBinding);
				if(tesselationShader.equals("1"))
					paramValues.put("g4", tesselationShader);
				if(textureCompressionETC2.equals("1"))
					paramValues.put("g5", textureCompressionETC2);
				if(vertexPipelineStoresAndAtomics.equals("1"))
					paramValues.put("g6", vertexPipelineStoresAndAtomics);
				//query is executed 
				result_set = (List<Drivers>) query.executeWithMap(paramValues);
				//pm.retrieveAll(result_set);
			}
			//results are printed out
			for(Drivers d : result_set)
			{
				resp.getWriter().println("GPU name: "+d.getGPU_name()+"<br/>");
				resp.getWriter().println("geometryShader: "+(d.getGeometryShader().equals("1") ? "True" : "False")+"<br/>");
				resp.getWriter().println("tessalationShader: "+(d.getTesselationShader().equals("1") ? "True" : "False")+"<br/>");
				resp.getWriter().println("shaderInt16: "+(d.getShaderInt16().equals("1") ? "True" : "False")+"<br/>");
				resp.getWriter().println("sparseBinding: "+(d.getSparseBinding().equals("1") ? "True" : "False")+"<br/>");
				resp.getWriter().println("textureCompression: "+(d.getTextureCompressionETC2().equals("1") ? "True" : "False")+"<br/>");
				resp.getWriter().println("vertexPipelineStoresAndAtomics: "+(d.getVertexPipelineStoresAndAtomics().equals("1") ? "True" : "False")+"<br/><br/>");
				
			}
			//back button to go the previous page
			resp.getWriter().println("<br/><br/><form action = \"/\">"
					+ "<input type = \"submit\" value=\"Go back\" />"
					+ "</form>");
			
			
			//Debugging code - to check whether the values are coming from form in root.jsp or not
			System.out.println(geometryShader+", "+tesselationShader+", "+shaderInt16
					+", "+sparseBinding+", "+textureCompressionETC2+", "+
					vertexPipelineStoresAndAtomics);
		}
		finally
		{//close the persistence manager
			pm.close();
		}	
	}
}
