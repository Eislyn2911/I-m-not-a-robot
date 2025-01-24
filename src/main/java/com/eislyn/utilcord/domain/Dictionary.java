package com.eislyn.utilcord.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eislyn.utilcord.dataAccess.HttpConnector;

/**
 * Get the response from HttpConnector class and map it into a list of PartOfSpeech objects,
 * setWord() first before using getResponseAsListOfClasses().
 * @author Eislyn
 * @since 16/10/2022
 */
public class Dictionary {
	private String word;
	
	public Dictionary(String word) {
		this.word = word;
	}
	
	/**
	 * Gets the response from HttpConnecter and maps the Json response into a list of PartOfSpeech objects.
	 * @return partOfSpeechList List of PartOfSpeech objects
	 */
	public List<PartOfSpeech> getResponseAsPartOfSpeechList() {
		if(word == null || word == "") {
			throw new IllegalArgumentException();
		}
		
		String urlStr = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
		String response = HttpConnector.getResponse(urlStr);
		
		//partOfSpeechJsonArray that has partOfSpeech JsonObjects
		JSONArray partOfSpeechJsonArray = new JSONArray(response);
		
		List<PartOfSpeech> partOfSpeechList = new ArrayList<PartOfSpeech>();
		
		//Loop through partOfSpeech JsonArray
		for(int i=0; i<partOfSpeechJsonArray.length(); i++) {
			//One partOfSpeech JsonObject
			JSONObject partOfSpeechJsonObject = partOfSpeechJsonArray.getJSONObject(i);
			//One partOfSpeech JsonObject has a meaningsJsonArray
			JSONArray meaningsJsonArray = partOfSpeechJsonObject.getJSONArray("meanings");
			
			//Loop through meaningsJsonArray
			for(int j=0; j<meaningsJsonArray.length(); j++) {
				//One meaningJsonObject
				JSONObject meaningJsonObject = meaningsJsonArray.getJSONObject(j);
				
				//*Got partOfString data here*
				//One meaningJsonObject has one partOfSpeechString
				String partOfSpeechString = meaningJsonObject.getString("partOfSpeech");
				
				//Create a PartOfSpeech class
				List<String> definitionList = new ArrayList<String>();
				PartOfSpeech partOfSpeechObject;
				
				//One meaningJsonObject has one defintionsJsonArray
				JSONArray definitionsJsonArray = meaningJsonObject.getJSONArray("definitions");
				//Loop through definitionsJsonArray
				for(int k=0; k<definitionsJsonArray.length(); k++) {
					//One definitionJsonObject
					JSONObject definitionJsonObject = definitionsJsonArray.getJSONObject(k);
					
					//*Got definitionString here*
					//One definitionJsonObject has one definitionString
					String definitionString = definitionJsonObject.getString("definition");
					
					//Add the definitionString into partOfSpeech object definitionList
					definitionList.add(definitionString);
				}
				
				partOfSpeechObject = new PartOfSpeech(partOfSpeechString, definitionList);
				//partOfSpeech object is constructed, add object to a list to store
				partOfSpeechList.add(partOfSpeechObject);
			}
		}
		//return the list
		return partOfSpeechList;
	}
}
