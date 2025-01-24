package com.eislyn.utilcord.domain;

import java.util.List;

/**
 * Stores the definition list of one part of speech of the word. 
 * @author Eislyn
 * @since 17/10/2022
 */
public class PartOfSpeech {
	private String partOfSpeech; 
	private List<String> definitionList;
	
	public PartOfSpeech(String partOfSpeech, List<String> definitionList) {
		this.partOfSpeech = partOfSpeech;
		this.definitionList = definitionList;
	}

	public List<String> getDefinitionList() {
		return definitionList;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}
	
	@Override
	public String toString() {
		String defintionFull = "Part of speech: " + this.partOfSpeech + "\n";
		
		for(String defitnionStr : definitionList) {
			defintionFull += "Definition: " + defitnionStr + "\n";
		}
		
		return defintionFull;
		
	}

}
