package code.dataObjects;

import java.io.Serializable;

/* Author: Cole Stoltz
 * 
 * Purpose: Hold basic information about a kid. A client can have multiple kids.
 */

public class Kid implements Serializable {

	private static final long serialVersionUID = -6436574444431456691L;

	// Instance variables
	String kidInformation;
	
	// Default constructor
	public Kid() {
		kidInformation = "none";
	}
	
	// Parameterized constructor
	public Kid(String kidInformation) {
		this.kidInformation = kidInformation;
	}
	
	// Getters / Setters

	public String getAgeIdentifier() {
		return kidInformation;
	}

	public void setAgeIdentifier(String kidInformation) {
		this.kidInformation = kidInformation;
	}
	
}
