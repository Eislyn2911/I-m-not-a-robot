package com.eislyn.utilcord.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Stores time information based on a given time zone and formats it, setTimeZone first before using formatTimeAndDate().
 * @author Eislyn
 * @since 18/10/2022
 */
public class Time {
	private String timeZone;
	private String currentDateAndTime;
	
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getCurrentDateAndTime() {
		return currentDateAndTime;
	}
	
	/**
	 * Formats the dateAndTime, setTimeZone first before using this method.
	 */
	public void formatTimeAndDate(){
		if(timeZone == null || timeZone == "") {
			throw new IllegalArgumentException();
		}
		
		String[] availableTimeZoneIDs = TimeZone.getAvailableIDs();
		boolean isLegalArgument = false;
		
		for(int i=0; i<availableTimeZoneIDs.length; i++) {
			if(this.timeZone.equals(availableTimeZoneIDs[i])) {
				isLegalArgument = true;
				break;
			}
		}
		if(isLegalArgument == false) {
			throw new IllegalArgumentException();
		}

		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		Date date = new Date();
		
		currentDateAndTime = dateFormat.format(date);
	}
	
	
	

}
