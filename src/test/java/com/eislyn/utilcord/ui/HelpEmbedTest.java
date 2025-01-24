package com.eislyn.utilcord.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import net.dv8tion.jda.api.EmbedBuilder;

@RunWith(JUnitParamsRunner.class)
public class HelpEmbedTest {
	@Test
	public void testValidBuildEmbed() {
		EmbedTemplate helpEmbed = new HelpEmbed();
		EmbedBuilder actualEmbedBuilder = helpEmbed.buildEmbed("Server Name");
		String actualEmbedBuilderString = actualEmbedBuilder.build().toData().toString();
		
		EmbedBuilder expectedEmbedBuilder = new EmbedBuilder();
		expectedEmbedBuilder.setAuthor("Server Name");
		expectedEmbedBuilder.setTitle("Help Menu");
		expectedEmbedBuilder.setDescription("-------------------------------------------------------------------------------------\r\n"
				+ "Description\r\n"
				+ "I'm not a robot is a multipurpose discord bot equipped with utility tools. No setup is required to use the bot.\r\n"
				+ "-------------------------------------------------------------------------------------");
		expectedEmbedBuilder.addField("Prefix and help", 
				  "1. ``e!``: Type this in front of a command.\r\n"
				+ "2. ``e!help``:  Gets a help menu.\r\n"
				+ "3. ``e!helpts``: Gets the full translation supported languages list.\r\n"
				+ "4. ``e!helpct``: Gets the currency table list.\r\n"
				+ "5. ``e!helptime``: Gets the time zone name and code list.\r\n"
				+ "-------------------------------------------------------------------------------------", false);
		expectedEmbedBuilder.addField("Core Utility Commands", 
				  "1. ``e!ts targetLanguage message``: Auto detects a language and translates the message to the target language.\r\n"
				+ "2. ``e!d word``: Gets the definition of a English word.\r\n"
				+ "3. ``e!e baseCurrency targetCurrency amountToExchange``: Translate a currency from one to another.\r\n"
				+ "4. ``e!time timeZoneName / e!time timeZoneCode``: Gets the current date and time from a time zone.\r\n"
				+ "5. ``e!timer minutes``: Set a ping timer in number of minutes.\r\n" + "-------------------------------------------------------------------------------------", false);
		expectedEmbedBuilder.addField("Other Commands", 
				  "1. ``e!info @user``: Gets the server info of a user.\r\n"
				+ "-------------------------------------------------------------------------------------\r\n"
				+ "", false);
		expectedEmbedBuilder.setFooter("Thank you for using the bot.");
		String expectedEmbedBuilderString = expectedEmbedBuilder.build().toData().toString();
		
		assertEquals(expectedEmbedBuilderString, actualEmbedBuilderString);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "paramTestInvalidBuildEmbed")
	public void testInvalidBuildEmbed(String serverName) {
		EmbedTemplate helpEmbed = new HelpEmbed();
		helpEmbed.buildEmbed(serverName);
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestInvalidBuildEmbed() {
		return new Object[] {
			new Object[] {null},
			new Object[] {""}
		};
	}

}
