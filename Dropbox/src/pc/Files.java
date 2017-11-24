package pc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class Files extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws	IOException {
		
		String action = req.getParameter("action");
		// get attribute path from the session
		String path = (String) req.getSession().getAttribute("path");
		
		String userID = UserServiceFactory.getUserService().getCurrentUser().getUserId();
		Key k1 = KeyFactory.createKey("JDOSubDirectories", userID+path);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		JDOSubDirectories currDir = pm.getObjectById(JDOSubDirectories.class, k1);
		int index = Integer.parseInt(req.getParameter("file_index"));
		JDOFiles currFile = currDir.files().get(index);
		BlobKey bk = currFile.getBlobKey();
		// we have an index attached to this request which is the file that the
		// user wants served to them. get the key and serve the file
		if(action.equals("Download")){
			BlobstoreServiceFactory.getBlobstoreService().serve(bk, resp);
		}else if(action.equals("Delete")){
			currDir.deleteFile(index);
			pm.deletePersistent(currFile);
			BlobstoreServiceFactory.getBlobstoreService().delete(bk);
			resp.sendRedirect("/");
		}
		
		pm.close();
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws	IOException {
		// get the blob collection from the datastore and add in the blob key
		// that was just added by the user.
		String path = (String) req.getSession().getAttribute("path");
		String userID = UserServiceFactory.getUserService().getCurrentUser().getUserId();
		Key k1 = KeyFactory.createKey("JDOSubDirectories", userID+path);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		JDOSubDirectories currDir = pm.getObjectById(JDOSubDirectories.class, k1);
		Map<String, List<BlobKey>> files_sent = BlobstoreServiceFactory.getBlobstoreService().getUploads(req);
		BlobKey b = files_sent.get("file").get(0);
		BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(b);
		JDOFiles file = new JDOFiles();
		file.setName(blobInfo.getFilename());
		file.setBlobKey(b);
		file.setParent(currDir);
		currDir.addFile(file);
		pm.makePersistent(file);
		pm.makePersistent(currDir);
		pm.close();
		resp.sendRedirect("/");
	}
}
