package code.datapersistance_dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import code.dataObjects.Instructor;

/**
 * 
 * @author Cole Stoltz
 * Another singleton class that allows the storage of instructors
 *
 */

public class InstructorDataDB implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6472698938887892306L;
	
	private static final InstructorDataDB instance = new InstructorDataDB();
	
	private ArrayList<Instructor> instructorData = new ArrayList<Instructor>();
	
	private String filePath = "src\\data\\instructor.data";
	
	// Constructor
	private InstructorDataDB() {
		readAndFill();
	}
	
	// Read from file and fill list table
	@SuppressWarnings("unchecked")
	// Do not need to check if it is an arraylist object
	// This will be the only object stored in the client.data
	// file.
	public void readAndFill() {
		try {
			FileInputStream dataFile = new FileInputStream(filePath);
			ObjectInputStream arrayObject = new ObjectInputStream(dataFile);
			
			instructorData = ((ArrayList<Instructor>)arrayObject.readObject());
			
			dataFile.close();
			arrayObject.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	// Store (save) data to file
	public void saveData() {
		try {
			FileOutputStream dataFile = new FileOutputStream(filePath);
			ObjectOutputStream arrayObjectOut = new ObjectOutputStream(dataFile);
			
			arrayObjectOut.writeObject(instructorData);
			
			dataFile.close();
			arrayObjectOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// Setters / Getters
	public ArrayList<Instructor> getInstructorDB(){
		return instructorData;
	}
	
	public static InstructorDataDB getInstance() {
		return instance;
	}
}
