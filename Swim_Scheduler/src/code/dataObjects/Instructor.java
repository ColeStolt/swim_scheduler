package code.dataObjects;

import java.util.ArrayList;
import java.util.UUID;

import com.google.api.services.calendar.Calendar;

/**
 * 
 * @author Cole Stoltz
 * An instructor object. An instructor can have many clients.
 * Each client will have a schedule and then be assigned to
 * an instructor.
 * 
 */

public class Instructor implements java.io.Serializable {

	// Unique serial ID
	private static final long serialVersionUID = -2149813398793123594L;
	
	// Instructor traits
	private String instructorName;
	private String instructorPhoneNumber;
	private String instructorEmail;
	private String calendarID;
	
	// List of clients
	private ArrayList<Client> assignedClients = new ArrayList<Client>();
	
	// Unique instructor
	private final String instructorID = UUID.randomUUID().toString(); 
	
	// Instructor
	public Instructor() {
		this.instructorName = "null";
		this.instructorPhoneNumber = "(000) 000-0000";
		this.instructorEmail = "----------";
	}
	
	public Instructor(String instructorName, String instructorPhoneNumber, String instructorEmail) {
		this.instructorName = instructorName;
		this.instructorPhoneNumber = instructorPhoneNumber;
		this.instructorEmail = instructorEmail;
	}

	// Getters / setters
	

	public String getInstructorName() {
		return instructorName;
	}

	public String getInstructorPhoneNumber() {
		return instructorPhoneNumber;
	}

	public String getInstructorEmail() {
		return instructorEmail;
	}

	public ArrayList<Client> getAssignedClients() {
		return assignedClients;
	}

	public String getInstructorID() {
		return instructorID;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public void setInstructorPhoneNumber(String instructorPhoneNumber) {
		this.instructorPhoneNumber = instructorPhoneNumber;
	}

	public void setInstructorEmail(String instructorEmail) {
		this.instructorEmail = instructorEmail;
	}

	public void setAssignedClients(ArrayList<Client> assignedClients) {
		this.assignedClients = assignedClients;
	}
	
    public String getCalendarID() {
		return calendarID;
	}

	public void setCalendarID(String calendarID) {
		this.calendarID = calendarID;
	}

	@Override
    public String toString() {
        return instructorName;
    }
	
}
