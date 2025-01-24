package com.eislyn.utilcord.domain;

import java.util.List;

import org.json.JSONException;

/**
 * Directs input and output to the corresponding upper and lower layers.
 * @author Eislyn
 * @since 15/10/2022
 */
public class Controller {
	/**
	 * Pass in the parameters needed(owns business layer) from setup class, don't create it here.
	 * @param embedTemplate
	 * @param translator
	 */
	public Controller() {
		
	}
	
	/**
	 * Directs Translator class to do translation.
	 * @param langTo target language
	 * @param text text to translate
	 * @return response translated text
	 */
	public String translate(String langTo, String text) {
		Translator translator = new Translator();
		if(langTo == null || langTo == "" || text == null || text == "")
			throw new IllegalArgumentException();
		
		translator.setLangTo(langTo);
		translator.setText(text);
		String response = translator.translate();
		return response;
	}
	
	/**
	 * Directs Dictionary class to get definitions.
	 * @param guildName Discord guild name
	 * @param word Word to get definition
	 * @return partOfSpeechList A list of PartOfSpeech objects
	 */
	public List<PartOfSpeech> dictionary(String word) {
		if(word == null || word == "") {
			throw new IllegalArgumentException();
		}
		
		Dictionary dictionary = new Dictionary(word);
		List<PartOfSpeech> partOfSpeechList = dictionary.getResponseAsPartOfSpeechList();
		return partOfSpeechList;
	}
	
	/**
	 * Directs CurrencyExchange class to exchange currency.
	 * @param authorName Discord guild name
	 * @param baseCurrency Base currency to exchange
	 * @param targetCurrency Target currency to exchange to
	 * @param amountToExchange Amount to exchange 
	 * @return currency Mapped currency class
	 */
	public Currency exchangeCurrency(String baseCurrency, String targetCurrency, double amountToExchange) throws JSONException{
		if(baseCurrency == null || baseCurrency == "" || targetCurrency == null || targetCurrency == "" || amountToExchange < 0) {
			throw new IllegalArgumentException();
		}
		
		CurrencyExchange currencyExchange = new CurrencyExchange(baseCurrency, targetCurrency, amountToExchange);
		Currency currency = currencyExchange.exchangeCurrency();
		
		return currency;
	}
	
	/**
	 * Directs Time class to format time and date given a time zone name or id.
	 * @param timeZone The time zone name or id
	 * @return 
	 */
	public String currentDateAndTime(String timeZone) {
		if(timeZone == null || timeZone == "") {
			throw new IllegalArgumentException();
		}
		
		Time time = new Time();
		time.setTimeZone(timeZone);
		time.formatTimeAndDate();
	    return time.getCurrentDateAndTime();
	}
}
