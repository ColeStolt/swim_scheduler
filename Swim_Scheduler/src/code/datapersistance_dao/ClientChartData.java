package code.datapersistance_dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import code.dataObjects.MonthClientEntry;

// The first month added will use the size of the clientDB
/*
 * After the first months a calculation will be done to determine the 
 * the clients added for each month.
 * 
 * 1. Create new month entry on new month
 * 2. Set currentMonth
 * 3. Set clients to zero
 * 4. Every time a client is added to the application add it to the most
 * 	  recent client entry.
 * 
 * This makes sure each months client entries start at 0.
 */

public class ClientChartData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8871968382061622179L;

	private static final ClientChartData instance = new ClientChartData();
	
	String dataFilePath = "src\\data\\monthClient.data";
	
	private ArrayList<MonthClientEntry> monthlyEntries = new ArrayList<MonthClientEntry>();

	private ClientChartData() {
		loadData();
	}
	
	// Loads data for clientEntries
	@SuppressWarnings("unchecked")
	public void loadData() {
		try {
			FileInputStream fileIn = new FileInputStream(dataFilePath);
			ObjectInputStream stream = new ObjectInputStream(fileIn);
			
			monthlyEntries = ((ArrayList<MonthClientEntry>)stream.readObject());
			
			fileIn.close();
			stream.close();
			
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
		
		// Check for new month
		addMonth();
	}
	
	public void saveData() {
		try {
			FileOutputStream fileOut = new FileOutputStream(dataFilePath);
			ObjectOutputStream stream = new ObjectOutputStream(fileOut);
			stream.writeObject(monthlyEntries);
			
			fileOut.close();
			stream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addMonth() {

		// Check if current month is different from most recently added month
		if(monthlyEntries.get(monthlyEntries.size()-1).getMonth() != LocalDate.now().getMonth()) {
			// Add the current month if above is true, set clients to zero
			monthlyEntries.add(new MonthClientEntry(LocalDate.now().getMonth(), 0));
		}
	}

	// Instance for this singleton
	public static ClientChartData getInstance() {
		return instance;
	}
	
	public ArrayList<MonthClientEntry> getMonthlyEntries(){
		return monthlyEntries;
	}
}
