package pc;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class JDOSubDirectories 
{
		//the id of the directory
		@PrimaryKey
		@Persistent
		private Key id;
		
		@Persistent
		private String name;
		//the name of this directory
		
		@Persistent
		private List<String> subDir;
		//the list of files that belong to this directory
		
		//the parent variable
		@Persistent(mappedBy="parent")
		private List<JDOFiles> files;
		
		// Constructor
		public JDOSubDirectories() 
		{
			subDir = new ArrayList<String>();
			files = new ArrayList<JDOFiles>();
		}
		
		public void setId(Key id) 
		{
			this.id = id;
		}
		
		public void setName(String name)
		{
			this.name = name;
		}
		// adding a sub directory
		public void addSubDir(String subDirectory)
		{
			if(!subDir.contains(subDirectory))
				subDir.add(subDirectory);
		}
				
		// Add a file to this directory
		public void addFile(JDOFiles file) 
		{
			files.add(file);
		}
		// Delete the given file from this directory
		
		public void deleteFile(int index)
		{
			files.remove(index);
		}
		// Delete the given sub directory from this directory
		public void deleteSubDir(String subDirectory)
		{
			subDir.remove(subDirectory);
		}
		// Return the list of directories belonging to this directory
		public List<String> directories()
		{
			/*List<String> newList = new ArrayList<>();
			newList.addAll(subDirectories);*/
			return subDir;
		}
		// Return the list of files belonging to this directory
		public List<JDOFiles> files()
		{return files;}
}
