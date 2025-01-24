package com.eislyn.utilcord.domain;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class TimeTest {
	@Test
	public void testValidFormatTimeAndDate() {
		Time time = new Time();
		time.setTimeZone("Asia/Kuala_Lumpur");
		time.formatTimeAndDate();
		String actualCurrentDateAndTime = time.getCurrentDateAndTime();
		
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kuala_Lumpur"));
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		Date date = new Date();
		String expectedCurrentDateAndTime = dateFormat.format(date);
		
		assertEquals(expectedCurrentDateAndTime, actualCurrentDateAndTime);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "paramTestInvalidFormatTimeAndDate")
	public void testInvalidFormatTimeAndDate(String timeZone) {
		Time time = new Time();
		time.setTimeZone(timeZone);
		time.formatTimeAndDate();
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestInvalidFormatTimeAndDate() {
		return new Object[] {
			new Object[] {null},
			new Object[] {""},
			new Object[] {"qiuwhdnkqasc"}
		};
	}
}
