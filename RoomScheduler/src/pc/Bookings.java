package pc;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class Bookings 
{
	//the id of the bookings as this is not going to be directly 
	//retrieved we will allow the keys for this to be auto generated
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	//name of the booking
	@Persistent
	private String bookingName;
	//starting date-time of the booking
	@Persistent
	private Date fromDate;
	
	// ending date-time of the booking
	@Persistent
	private Date toDate;
	
	//user who made the booking
	@Persistent
	private String madeBy;
	
	//the room which owns this booking
	@Persistent
	private Rooms parent;
	
	//getter method for the id, we don't have the setter as the app engine
	//will generate the key for us
	public Key getId() {return id;}
	
	//getter and setter for booking name
	public String getBookingName() {return bookingName;}
	public void setBookingName(final String bookingName) {this.bookingName = bookingName;}

	//getter and setter for starting date of booking
	public Date getFromDate() {return fromDate;}
	public void setFromDate(final Date fromDate) {this.fromDate = fromDate;}
	
	//getter and setter for ending date of booking
	public Date getToDate() {return toDate;}
	public void setToDate(final Date toDate) {this.toDate = toDate;}

	//getter and setter method for the user who made this booking
	public String getMadeBy() {	return madeBy;}	
	public void setMadeBy(final String madeBy) {this.madeBy = madeBy;}
	
	// setter method for setting the parent to establish the parent child
	// relationship
	public void setParent(final Rooms parent) {this.parent = parent;}
}
