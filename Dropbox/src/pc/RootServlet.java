package pc;

import java.io.IOException;
import java.util.List;

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
		resp.setContentType("text/plain");
		
				//get the attribute path from the session
				String path = (String) req.getSession().getAttribute("path");
				if(path == null || path.length() == 0) {
					path="root/";
					req.getSession().setAttribute("path", path);
				}
				
				//google user service
				UserService us = UserServiceFactory.getUserService();
				User u = us.getCurrentUser();
				String login_url = us.createLoginURL("/");
				String logout_url = us.createLogoutURL("/");
				
				// attach a few things to the request so that we can access them in the
				// jsp
				req.setAttribute("path", path);
				req.setAttribute("user", u);
				req.setAttribute("login_url", login_url);
				req.setAttribute("logout_url", logout_url);
				
				
				PersistenceManager pm = null;
				JDOUser user = null;
				
				if(u != null){
					String userID = u.getUserId();
					Key k1 = KeyFactory.createKey("JDOUser", userID);
					pm = PMF.get().getPersistenceManager();
					try {
						user = pm.getObjectById(JDOUser.class, k1);
					} catch(Exception e) {
						user = new JDOUser();
						user.setId(k1);
						Key k2 = KeyFactory.createKey("JDOSubDirectories", userID+"root/");
						JDOSubDirectories root = new JDOSubDirectories();
						root.setId(k2);
						root.setName("root/");
						pm.makePersistent(user);pm.makePersistent(root);
					}
					Key currentKey = KeyFactory.createKey("JDOSubDirectories", userID+path);
					JDOSubDirectories currentDirectory = pm.getObjectById(JDOSubDirectories.class, currentKey);
					List<JDOFiles> files = currentDirectory.files();
					List<String> subDirectories = currentDirectory.directories();
					req.setAttribute("files", files);
					req.setAttribute("directories", subDirectories);
					pm.close();
				}
				// forward on the request to the JSP
				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
				rd.forward(req, resp);
			}
	}

