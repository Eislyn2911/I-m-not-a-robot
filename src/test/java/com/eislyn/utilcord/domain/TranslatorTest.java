package com.eislyn.utilcord.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class TranslatorTest {
	
	@Test
	@Parameters(method = "paramTestTranslate")
	public void testTranslate(String langTo, String text, String expectedResult) {
		Translator translator = new Translator();
		translator.setLangTo(langTo);
		translator.setText(text);
		
		String actualResult = translator.translate();
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

}
