package com.eislyn.utilcord.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.eislyn.utilcord.domain.Currency;

import net.dv8tion.jda.api.EmbedBuilder;

public class CurrencyExchangeEmbedTest {
	@Test
	public void testValidBuildEmbed() {
		
		String baseCurrency = "USD";
		String targetCurrency = "MYR";
		double amountToExchange = 100;
		Currency currency = new Currency("2022-10-17T23:59:59Z", "MYR", 4.0, 400.0);		
		
		EmbedTemplate currencyExchangeEmbed = new CurrencyExchangeEmbed(baseCurrency, targetCurrency, amountToExchange, currency);
		EmbedBuilder actualCurrencyEmbedBuilder = currencyExchangeEmbed.buildEmbed("Server Name");
		
		EmbedBuilder expectedCurrencyEmbedBuilder = new EmbedBuilder();
		expectedCurrencyEmbedBuilder.setAuthor("Server Name");
		expectedCurrencyEmbedBuilder.setFooter("Thank you for using the bot.");
		expectedCurrencyEmbedBuilder.setTitle("Currency Exchange");
		expectedCurrencyEmbedBuilder.setDescription("Converted from " + baseCurrency + " to " + targetCurrency);
		expectedCurrencyEmbedBuilder.addField("Amount to convert", amountToExchange + baseCurrency, false);
		expectedCurrencyEmbedBuilder.addField("Converted amount", currency.getExchangedAmount() + currency.getCode(), false);
		expectedCurrencyEmbedBuilder.addField("Last updated", currency.getLastUpdated(), false);
		
		assertEquals(expectedCurrencyEmbedBuilder.build().toData().toString(), actualCurrencyEmbedBuilder.build().toData().toString());
	}

}
