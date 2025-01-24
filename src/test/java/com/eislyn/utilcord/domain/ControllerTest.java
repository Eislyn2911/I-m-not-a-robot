package com.eislyn.utilcord.domain;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ControllerTest {
	Controller controller = new Controller();
	
	@Test
	@Parameters(method = "paramTestTranslate")
	public void testValidTranslate(String langTo, String text, String expectedResult) {
		String actualResult = controller.translate(langTo, text);
		assertEquals(expectedResult, actualResult);
	}
	
	@SuppressWarnings("unused")
	// Please replace the expected results in this test after purchasing your own Google Cloud Translation API.
	private Object[] paramTestTranslate() {
		return new Object[] {
			new Object[] {"fr", "hello", "Sorry, translator API free trial is over. The other commands are fine."},
			new Object[] {"en", "你好", "Sorry, translator API free trial is over. The other commands are fine."}
		};		
	}
	
	@Test
	public void testValidDictionary() {
		String word = "multithreading";
		
		List<PartOfSpeech> actualPartOfSpeechList = controller.dictionary(word);
		
		List<PartOfSpeech> expectedPartOfSpeechList = new ArrayList<>();

		PartOfSpeech partOfSpeechNoun;
		List<String> definitionListNoun = new ArrayList<>();
		definitionListNoun.add("The use of multithreaded code.");
		partOfSpeechNoun = new PartOfSpeech("noun", definitionListNoun);
		expectedPartOfSpeechList.add(partOfSpeechNoun);
		
		assertEquals(expectedPartOfSpeechList.toString(), actualPartOfSpeechList.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "paramTestInvalidDictionary")
	public void testInvalidDictionary(String word) {
		controller.dictionary(word);
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestInvalidDictionary() {
		return new Object[] {
			new Object[] {null},
			new Object[] {""}
		};
	}
	
	@Test
	public void testValidExchangeCurrency() {
		String baseCurrency = "USD";
		String targetCurrency = "USD";
		double conversionRate = 1.0;
		double amountToExchange = 100.0;
		double exchangedAmount = 100.0;
		Currency expectedCurrency = new Currency("", targetCurrency, conversionRate, exchangedAmount);
		
		Currency actualCurrency = controller.exchangeCurrency(baseCurrency, targetCurrency, amountToExchange);
		assertEquals(expectedCurrency.toString(), actualCurrency.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "paramTestInvalidExchangeCurrency")
	public void testInvalidExchangeCurrency(String baseCurrency, String targetCurrency, double amountToExchange) {
		controller.exchangeCurrency(baseCurrency, targetCurrency, amountToExchange);
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestInvalidExchangeCurrency() {
		return new Object[] {
			new Object[] {null, null, -1},
			new Object[] {"", null, -2},
			new Object[] {null, "", -3},
			new Object[] {"", "", -4},
		};		
	}
	
	@Test
	@Parameters(method = "paramTestValidCurrentDateAndTime")
	public void testValidCurrentDateAndTime(String timeZone) {
		String actualDateAndTime = controller.currentDateAndTime(timeZone);
		
		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		Date date = new Date();
		String expectedCurrentDateAndTime = dateFormat.format(date);
		assertEquals(expectedCurrentDateAndTime, actualDateAndTime);
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestValidCurrentDateAndTime() {
		return new Object[] {
			new Object[] {"Asia/Kuala_Lumpur"},
			new Object[] {"Canada/Pacific"},
			new Object[] {"Australia/Sydney"},
		};		
	}
}
