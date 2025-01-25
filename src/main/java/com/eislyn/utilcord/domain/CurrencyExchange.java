package com.eislyn.utilcord.domain;

import org.json.JSONException;
import org.json.JSONObject;

import com.eislyn.utilcord.dataAccess.HttpConnector;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Stores data from user required to exchange currency using the api, set baseCurrency, targetCurrency and amountToConvert first before using exchangeCurrency() 
 * @author Eislyn
 * @since 18/10/2022
 */
public class CurrencyExchange {
	private static final String apiKeyName = "CURRENCY_TRANSLATOR_API_KEY";
	
	private String baseCurrency;
	private String targetCurrency;
	private double amountToExchange;

	public CurrencyExchange(String baseCurrency, String targetCurrency, double amountToExchange) {
		this.baseCurrency = baseCurrency;
		this.targetCurrency = targetCurrency;
		this.amountToExchange = amountToExchange;
	}

	/**
	 * Loads the apiKeyValue from the apiKeyName
	 * @return
	 */
	private String getApiKey() {
		String deploymentKey = System.getenv(apiKeyName);
		if (deploymentKey == null || deploymentKey.isEmpty()) {
			Dotenv dotenv = Dotenv.load();  // Load Dotenv only if needed
			deploymentKey = dotenv.get(apiKeyName);
		}
		return deploymentKey;
	}

	/**
	 * Exchange currency based on an amount, set baseCurrency, targetCurrency and amountToConvert first before using this.
	 * @return currency Currency object
	 * @throws JSONException
	 */
	public Currency exchangeCurrency() throws JSONException{
		if(baseCurrency == null || baseCurrency == "" || targetCurrency == null || targetCurrency == "" || amountToExchange < 0) {
			throw new IllegalArgumentException();
		}
		
		//Gets the apiKeyValue and use it to get the response 
		String apiKey = getApiKey();
		String urlStr = "https://api.currencyapi.com/v3/latest?apikey=" + apiKey + "&currencies=" + targetCurrency
				+ "&base_currency=" + baseCurrency;
		String responseStr = HttpConnector.getResponse(urlStr);

		//Last updated
		JSONObject conversionObject = new JSONObject(responseStr);
		JSONObject metaObject = conversionObject.getJSONObject("meta");
		String lastUpdated = metaObject.getString("last_updated_at");

		JSONObject dataObject = conversionObject.getJSONObject("data");
		JSONObject currencyObject = dataObject.getJSONObject(targetCurrency);
		
		double conversionRate = currencyObject.getDouble("value");
		double exchangedAmount = conversionRate * amountToExchange;
		//Maps the json data into a currency object
		Currency currency = new Currency(lastUpdated, targetCurrency, conversionRate, exchangedAmount);
	
		//return the mapped currency object
		return currency;
	}



}
