package com.eislyn.utilcord.ui;

import com.eislyn.utilcord.domain.Currency;

/**
 * Creates a CurrencyExchangeEmbed using EmbedTemplate by overriding abstract methods.
 * @author Eislyn
 * @since 18/10/2022
 */
public class CurrencyExchangeEmbed extends EmbedTemplate{

	private String baseCurrency;
	private String targetCurrency;
	private double amountToExchange;
	private Currency currency;
	
	/**
	 * Constructs necessary information for CurrencyExchangeEmbed
	 * @param baseCurrency
	 * @param targetCurrency
	 * @param amountToExchange
	 * @param currency
	 */
	public CurrencyExchangeEmbed(String baseCurrency, String targetCurrency, double amountToExchange, Currency currency) {
		this.baseCurrency = baseCurrency;
		this.targetCurrency = targetCurrency;
		this.amountToExchange = amountToExchange;
		this.currency = currency;
	}
	
	@Override
	public void setTitle() {
		embedBuilder.setTitle("Currency Exchange");
	}

	@Override
	public void setDescription() {
		embedBuilder.setDescription("Converted from " + baseCurrency + " to " + targetCurrency);
	}

	@Override
	public void addField() {
		embedBuilder.addField("Amount to convert", amountToExchange + baseCurrency, false);
		embedBuilder.addField("Converted amount", currency.getExchangedAmount() + currency.getCode(), false);
		embedBuilder.addField("Last updated", currency.getLastUpdated(), false);
	}

	@Override
	public void setThumbnail(){

	}
}
