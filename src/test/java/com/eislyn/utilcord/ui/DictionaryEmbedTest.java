package com.eislyn.utilcord.ui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.eislyn.utilcord.domain.PartOfSpeech;

import net.dv8tion.jda.api.EmbedBuilder;

public class DictionaryEmbedTest {
	
	@Test
	public void testBuildEmbed() {
		List<String> definitionListNoun = new ArrayList<String>();
		definitionListNoun.add("The equipment needed to inject a drug (syringes, needles, swabs etc.)");
		PartOfSpeech partOfSpeechNoun = new PartOfSpeech("noun", definitionListNoun);
		
		List<String> definitionListVerb = new ArrayList<String>();
		definitionListVerb.add("To hurt; to ache.");
		PartOfSpeech partOfSpeechVerb = new PartOfSpeech("verb", definitionListVerb);
		
		List<PartOfSpeech> partOfSpeechList = new ArrayList<PartOfSpeech>();
		partOfSpeechList.add(partOfSpeechNoun);
		partOfSpeechList.add(partOfSpeechVerb);
		
		EmbedTemplate dictionaryEmbed = new DictionaryEmbed(partOfSpeechList, "work");
		EmbedBuilder actualDictionaryEmbedBuilder = dictionaryEmbed.buildEmbed("Server Name");
		
		EmbedBuilder expectedDictionaryEmbedBuilder = new EmbedBuilder();
		expectedDictionaryEmbedBuilder.setAuthor("Server Name");
		expectedDictionaryEmbedBuilder.setTitle("Work" + "\r\n-------------------------------------------------------------------------");
		expectedDictionaryEmbedBuilder.addField("1. Part Of Speech: noun", "The equipment needed to inject a drug (syringes, needles, swabs etc.)", false);
		expectedDictionaryEmbedBuilder.addField("2. Part Of Speech: verb", "To hurt; to ache.", false);
		expectedDictionaryEmbedBuilder.addField("------------------------------------------------------------------------------------", "", false);
		expectedDictionaryEmbedBuilder.setFooter("Thank you for using the bot.");
		
		assertEquals(expectedDictionaryEmbedBuilder.build().toData().toPrettyString(), actualDictionaryEmbedBuilder.build().toData().toPrettyString());
	}

}
