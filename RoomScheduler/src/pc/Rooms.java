package pc;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class Rooms 
{
	//id of the Room
	@Persistent
	@PrimaryKey
	private Key id;
	
	//name of the room
	@Persistent
	private String roomName;
	
	//room type
	@Persistent
	private String smoking;
	
	//the list of bookings that belong to this user 
	//the parent variable of the Bookings class will map the
	//booking to the Rooms
	@Persistent(mappedBy="parent")
	private List<Bookings> bookings;
	
	//setter and getter for id of the room
	public Key getID() {return id;}
	public void setID(final Key id) {this.id = id;}
	
	//setter and getter for room name
	public String getRoomName() {return roomName;}
	public void setRoomName(final String roomName) {this.roomName = roomName;}
	
	//setter and getter for smoking
	public String getSmoking() {return smoking;}
	public void setSmoking(final String smoking) {this.smoking = smoking;}
	
	//adding a booking
	public void addBooking(Bookings b)
	{
		if(bookings == null)
			bookings = new ArrayList<Bookings>();
		bookings.add(b);
	}
	
	// return the entire list of bookings
	public List<Bookings> bookings() {return bookings;}
	
}
