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
	}
	
	public Client(String clientName, String addressOfLessons, String phoneNumber, short numberOfKids, String instructor, short numberOfLessons, float amountPerLessons) {
		
	}
	
}
