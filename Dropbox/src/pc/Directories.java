package pc;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class Directories extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String action = req.getParameter("action");
		// get the attribute path from the session 
		String path = (String) req.getSession().getAttribute("path");
		if(path == null || path.length() == 0) 
		{
			path="root/";
			req.getSession().setAttribute("path", path);
		}
		
		String userID = UserServiceFactory.getUserService().getCurrentUser().getUserId();
		Key k1 = KeyFactory.createKey("JDOSubDirectories", userID+path);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		JDOSubDirectories currDirectory = pm.getObjectById(JDOSubDirectories.class, k1);
		
		//if the button clicked is to add a new directory, this block of code will add the new directory along with it's path 
		//as a key in the JDOSubDirectories entity and also, will add the new directory being created into the list of 
		//directories that the current directory is holding
		if(action.equals("Add"))
		{
			String newDirName = req.getParameter("new_directory");
			// Check if directory name is empty
			if(newDirName == null || newDirName.length() == 0)
			{
				pm.close();
				resp.sendRedirect("/");
				return;
			}
			newDirName+="/";
			
			String newDirPath = path+newDirName;
			Key k2 = KeyFactory.createKey("JDOSubDirectories", userID+newDirPath);
			JDOSubDirectories newDir = null; 
			try 
			{
				newDir = pm.getObjectById(JDOSubDirectories.class, k2);
			} 
			catch(Exception e) 
			{
				currDirectory.addSubDir(newDirName);
				newDir = new JDOSubDirectories();
				newDir.setId(k2);
				newDir.setName(newDirName);
				pm.makePersistent(newDir);
			}
		}
		
		//this block of code will delete the directory from the datastore such that the key of the directory is deleted
		//also the directory is removed from the list of directories that the current directory is holding
		else if(action.equals("Delete"))
		{
			int index = Integer.parseInt(req.getParameter("dir_index"));
			String subDirName = currDirectory.directories().get(index);
			
			// Check if sub-directory is empty else delete it and redirect
			String subDirPath = path+subDirName;
			Key subDirKey = KeyFactory.createKey("JDOSubDirectories", userID+subDirPath);
			JDOSubDirectories subDir = pm.getObjectById(JDOSubDirectories.class, subDirKey);
			if(subDir.directories().size()==0 && subDir.files().size()==0)
			{
				pm.deletePersistent(subDir);
				currDirectory.deleteSubDir(currDirectory.directories().get(index));				
			}
		}
		
		//this block of code will take the user one step up and down in the subdirectories
		else if(action.equals("Change"))
		{
			int index = Integer.parseInt(req.getParameter("dir_index"));
			if(index!=-1)
			{
				String subDirName = currDirectory.directories().get(index);
				String subDirPath = path+subDirName;
				req.getSession().setAttribute("path", subDirPath);
			}
			else
			{
				path = path.substring(0,path.lastIndexOf('/'));
				path = path.substring(0,path.lastIndexOf('/'))+'/';
				req.getSession().setAttribute("path", path);
			}
		}

		pm.close();
		resp.sendRedirect("/");
	}
}
