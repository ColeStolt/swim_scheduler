package code.dataObjects;

import java.io.Serializable;
import java.time.Month;

public class MonthClientEntry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3304507695088546327L;
	private Month month;
	private int clients;
	
	public MonthClientEntry() {
		month = null;
		clients = 0;
	}
	
	public MonthClientEntry(Month month, int clients) {
		this.month = month;
		this.clients = clients;
	}
	
	public Month getMonth() {
		return month;
	}
	public int getClients() {
		return clients;
	}
	public void setMonth(Month month) {
		this.month = month;
	}
	public void setClients(int clients) {
		this.clients = clients;
	}
	
	
	
}
