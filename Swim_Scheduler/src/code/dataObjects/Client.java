package code.dataObjects;

import java.util.ArrayList;
import java.util.UUID;

/**
 * 
 * @author Cole Stoltz
 * Client class which contains the behaviors and data
 * of a client such as name, phone number, etc... 
 */

public class Client implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6012455717038070211L;
	// Data
	private String clientName;
	private String addressOfLessons;
	private String phoneNumber;
	private short numberOfKids;
	// instructor may be changed to an instructor object
	private ArrayList<Instructor> instructor;
	private ArrayList<Kid> kids;
	private short numberOfLessons;
	private float amountPerLesson;
	private float totalAmountForLessons;
	private boolean paidInFull;
	private boolean scheduled;
	private double amountPaid;
	
	private final String clientID = UUID.randomUUID().toString();
	// Data structure to hold schedule data
	// Schedule object will be added for easier parsing
	// private Schedule scheduleData;
	
	public Client() {
		clientName = "null";
		addressOfLessons = "null";
		phoneNumber = "null";
		numberOfKids = 0;
		instructor = null;
		numberOfLessons = 0;
		amountPerLesson = 0f;
		totalAmountForLessons = 0f;
		paidInFull = false;
		scheduled = false;
	}
	
	public Client(String clientName, String addressOfLessons, String phoneNumber, short numberOfKids, ArrayList<Instructor> instructor, short numberOfLessons, float amountPerLesson, boolean paidInFull) {
		this.clientName = clientName;
		this.addressOfLessons = addressOfLessons;
		this.phoneNumber = phoneNumber;
		this.numberOfKids = numberOfKids;
		this.instructor = instructor;
		this.numberOfLessons = numberOfLessons;
		this.amountPerLesson = amountPerLesson;
		
		// Checking for if kids are zero (zero means client is doing lessons)
		if (this.numberOfKids == 0) {
			this.totalAmountForLessons = numberOfLessons * amountPerLesson;
		} else {
			this.totalAmountForLessons = numberOfKids * numberOfLessons * amountPerLesson;
		}
		
		this.paidInFull = paidInFull;
		scheduled = false;
	}

	// Called when edits to the prices have been made
	public void updateTotal() {
		// Checking for if kids are zero (zero means client is doing lessons)
		if (this.numberOfKids == 0) {
			this.totalAmountForLessons = numberOfLessons * amountPerLesson;
		} else {
			this.totalAmountForLessons = numberOfKids * numberOfLessons * amountPerLesson;
		}
	}
	
	
	// Getters and Setters
	
	public String getClientName() {
		return clientName;
	}

	public String getAddressOfLessons() {
		return addressOfLessons;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public short getNumberOfKids() {
		return numberOfKids;
	}

	public ArrayList<Instructor> getInstructor() {
		return instructor;
	}

	public short getNumberOfLessons() {
		return numberOfLessons;
	}

	public float getAmountPerLesson() {
		return amountPerLesson;
	}

	public float getTotalAmountForLessons() {
		return totalAmountForLessons;
	}

	public boolean isPaidInFull() {
		return paidInFull;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setAddressOfLessons(String addressOfLessons) {
		this.addressOfLessons = addressOfLessons;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setNumberOfKids(short numberOfKids) {
		this.numberOfKids = numberOfKids;
	}

	public void setInstructor(ArrayList<Instructor> instructor) {
		this.instructor = instructor;
	}

	public void setNumberOfLessons(short numberOfLessons) {
		this.numberOfLessons = numberOfLessons;
	}

	public void setAmountPerLesson(float amountPerLesson) {
		this.amountPerLesson = amountPerLesson;
	}

	public void setTotalAmountForLessons(float totalAmountForLessons) {
		this.totalAmountForLessons = totalAmountForLessons;
	}

	public void setPaidInFull(boolean paidInFull) {
		this.paidInFull = paidInFull;
	}

	public String getClientID() {
		return clientID;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public ArrayList<Kid> getKids() {
		return kids;
	}

	public void setKids(ArrayList<Kid> kids) {
		this.kids = kids;
	}
	
	
	
}
