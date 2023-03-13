package code.dataObjects;

/**
 * 
 * @author Cole Stoltz
 * Client class which contains the behaviors and data
 * of a client such as name, phone number, etc... 
 */

public class Client {
	
	// Data
	private String clientName;
	private String addressOfLessons;
	private String phoneNumber;
	private short numberOfKids;
	// instructor may be changed to an instructor object
	private String instructor;
	private short numberOfLessons;
	private float amountPerLesson;
	private float totalAmountForLessons;
	private boolean paidInFull;
	
	// Data structure to hold schedule data
	// Schedule object will be added for easier parsing
	// private Schedule scheduleData;
	
	public Client() {
		clientName = "null";
		addressOfLessons = "null";
		phoneNumber = "null";
		numberOfKids = 0;
		instructor = "null";
		numberOfLessons = 0;
		amountPerLesson = 0f;
		totalAmountForLessons = 0f;
		paidInFull = false;
	}
	
	public Client(String clientName, String addressOfLessons, String phoneNumber, short numberOfKids, String instructor, short numberOfLessons, float amountPerLesson, boolean paidInFull) {
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

	public String getInstructor() {
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

	public void setInstructor(String instructor) {
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
	
	
	
}
