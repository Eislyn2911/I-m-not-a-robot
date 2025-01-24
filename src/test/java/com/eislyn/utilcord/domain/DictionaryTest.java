package com.eislyn.utilcord.domain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class DictionaryTest {
	@Test
	public void testValidGetResponseAsListOfClasses() {
		Dictionary dictionary = new Dictionary("multithreading");
		String actualResult = "";
		
		List<PartOfSpeech> partOfSpeechList = dictionary.getResponseAsPartOfSpeechList();
		for(int i=0; i<partOfSpeechList.size(); i++) {
			actualResult += "Part of speech: " + partOfSpeechList.get(i).getPartOfSpeech();
			List<String> definitionList = partOfSpeechList.get(i).getDefinitionList();
			
			for(int j=0; j<definitionList.size(); j++) {
				actualResult += "- " + definitionList.get(j);
			}
		}
		
		String expectedResult = "Part of speech: noun"
				+ "- The use of multithreaded code.";

		assertEquals(expectedResult, actualResult);
	}
	
	@Test (expected = IllegalArgumentException.class)
	@Parameters(method = "paramTestInvalidGetResponseAsListOfClasses")
	public void testInvalidArgumentGetResponseAsListOfClasses(String word) {
		Dictionary dictionary = new Dictionary(word);
		dictionary.getResponseAsPartOfSpeechList();
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestInvalidGetResponseAsListOfClasses(){
		return new Object[] {
			new Object[] {null},
			new Object[] {""}
		};
	}
	
	@Test (expected = JSONException.class)
	public void testJsonExceptionGetResponseAsListOfClasses() {
		Dictionary dictionary = new Dictionary("asjdknsadna");
		dictionary.getResponseAsPartOfSpeechList();
	}

}
