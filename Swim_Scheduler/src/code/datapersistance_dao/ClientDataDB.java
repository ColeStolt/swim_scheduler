package code.datapersistance_dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import code.dataObjects.Client;

/**
 * 
 * @author Cole Stoltz
 * This will act as a database. It is a singleton that multiple classes
 * can access. There will only be one instance to access and data will
 * be serialized vs. storing individually. Serializing data just turns
 * the object into a byte stream.
 *
 */

public class ClientDataDB implements java.io.Serializable {
	

	private static final long serialVersionUID = -5168600904513136308L;

	private static final ClientDataDB instance = new ClientDataDB();
	
	private ArrayList<Client> clientData = new ArrayList<Client>();
	
	private String filePath = "src\\data\\client.data";
	
	// Constructor
	private ClientDataDB() {
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
			
			clientData = ((ArrayList<Client>)arrayObject.readObject());
			
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
	@SuppressWarnings("unchecked")
	public void saveData() {
		try {
			FileOutputStream dataFile = new FileOutputStream(filePath);
			ObjectOutputStream arrayObjectOut = new ObjectOutputStream(dataFile);
			
			arrayObjectOut.writeObject(clientData);
			
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
	public ArrayList<Client> getClientDB(){
		return clientData;
	}
}
