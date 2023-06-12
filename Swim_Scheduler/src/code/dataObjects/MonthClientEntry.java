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
	private double money;
	
	public MonthClientEntry() {
		month = null;
		clients = 0;
	}
	
	public MonthClientEntry(Month month, int clients) {
		this.month = month;
		this.clients = clients;
	}
	
	public MonthClientEntry(Month month, double money) {
		this.month = month;
		this.money = money;
	}
	
	public Month getMonth() {
		return month;
	}
	public int getClients() {
		return clients;
	}
	public double getMoney() {
		return money;
	}
	public void setMonth(Month month) {
		this.month = month;
	}
	public void setClients(int clients) {
		this.clients = clients;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	
	
}
