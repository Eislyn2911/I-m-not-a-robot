package com.eislyn.utilcord.ui;

import java.util.List;

import com.eislyn.utilcord.domain.PartOfSpeech;

/**
 * Creates a dictionary embed using EmbedTemplate by overriding abstract methods.
 * @author Eislyn
 * @since 16/10/2022
 */
public class DictionaryEmbed extends EmbedTemplate{

	private List<PartOfSpeech> partOfSpeechList;
	private String word;
	
	public DictionaryEmbed(List<PartOfSpeech> partOfSpeechList, String word) {
		this.partOfSpeechList = partOfSpeechList;
		this.word = word;
	}
	
	/**
	 * Sets the embed title with word provided in setter, also make the word's first letter uppercase.
	 */
	@Override
	public void setTitle() {
		word = word.substring(0, 1).toUpperCase() + word.substring(1);
		embedBuilder.setTitle(word + "\r\n-------------------------------------------------------------------------");
	}

	@Override
	public void setDescription() {
		
	}

	/**
	 * Constructs and adds embed fields from PartOfSpeech class.
	 */
	@Override
	public void addField() {
		String fieldName = "";
		String fieldValue = "";
		
		for(int i=0; i<partOfSpeechList.size(); i++) {
			fieldName = (i+1) + ". Part Of Speech: " + partOfSpeechList.get(i).getPartOfSpeech();
			
			List<String> definitionList = partOfSpeechList.get(i).getDefinitionList();
			
			for(int j=0; j<definitionList.size(); j++) {
				fieldValue = definitionList.get(j) + "\r\n";
			}
			embedBuilder.addField(fieldName, fieldValue, false);
		}
		embedBuilder.addField("------------------------------------------------------------------------------------", "", false);
	}

	@Override
	public void setThumbnail() {
		// TODO Auto-generated method stub
		
	}
}
