package code.datapersistance_dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import code.dataObjects.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Cole Stoltz
 * This will act as a database. Since this will be a simple database with less <1000
 * data will have to be reloaded each instance of its creation. When a controller
 * uses this class it will create an instance I.E. a new database. It takes data
 * from the .data files fills this database, changes what it needs to change and
 * then restores that data for later manipulation.
 *
 */

public class ClientDataDB {
	
	// Table
	private ArrayList<Client> ClientData = new ArrayList<Client>();
	
	// Constructor
	public ClientDataDB(String filePath) {
		readAndFill(filePath);
	}
	
	// Read from file and fill list table
	public void readAndFill(String filePath) {
		try {
			BufferedReader dataFile = new BufferedReader(new FileReader(filePath));
			String dataLine;
			String tokens[];
			while((dataLine = dataFile.readLine()) != null) {
				tokens = dataLine.split("`"); // Chose a more obscure delimiter to avoid issues
				Client tempClient = new Client(tokens[0], tokens[1], tokens[2], Short.parseShort(tokens[3]), tokens[4], Short.parseShort(tokens[5]), Float.parseFloat(tokens[6]), Boolean.parseBoolean(tokens[7]));
				ClientData.add(tempClient);
			}
			
			dataFile.close();
			
		} catch (FileNotFoundException e) {
			// Show error to user if file is not located
			Alert sceneAlert = new Alert(AlertType.ERROR);
			sceneAlert.setContentText("Could not load the \"client.data\" file.\nContact the developer about this issue.");
			sceneAlert.show();
		} catch (IOException e) {
			Alert sceneAlert = new Alert(AlertType.ERROR);
			sceneAlert.setContentText("Could not parse data file in \"client DB\".\nContact the developer about this issue.");
			sceneAlert.show();
		}
	}
	

	// Store (save) data to file
	public void saveData() {
		
	}
	
	// Setters / Getters
	public ArrayList<Client> getClientDB(){
		return ClientData;
	}
}
