package code.datapersistance_dao;

import com.google.api.services.calendar.Calendar;

public class AuthCalendarSingleton {
	private static final AuthCalendarSingleton instance = new AuthCalendarSingleton();
	
	private Calendar calendarReference = null;
	
	private AuthCalendarSingleton() {
		
	}

	public static AuthCalendarSingleton getInstance() {
		return instance;
	}
	
	public Calendar getCalendarReference() {
		return calendarReference;
	}

	public void setCalendarReference(Calendar calendarReference) {
		this.calendarReference = calendarReference;
	}
}
