package com.eislyn.utilcord.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class CurrencyExchangeTest {
	
	
	@Test
	public void testValidExchangeCurrencyNotNullCode() {
		CurrencyExchange currencyExchange = new CurrencyExchange("USD", "USD", 100.0);
		Currency actualCurrency = currencyExchange.exchangeCurrency();
		
		Currency expectedCurrency = new Currency("", "USD", 1.0, 100.0);
		
		assertEquals(expectedCurrency.toString(), actualCurrency.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "paramTestInvalidExchangeCurrency")
	public void testInvalidExchangeCurrency(String baseCurrency, String targetCurrency, double amountToExchange) {
		CurrencyExchange currencyExchange = new CurrencyExchange(baseCurrency, targetCurrency, amountToExchange);
		currencyExchange.exchangeCurrency();
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestInvalidExchangeCurrency() {
		return new Object[] {
			new Object[] {null, null, -1.0},
			new Object[] {"", "", -2.0},
			new Object[] {null, "", -3.0},
			new Object[] {"", null, -4.0},
			new Object[] {null, null, 1.0},
			new Object[] {"", "", 2.0},
			new Object[] {null, "", 3.0},
			new Object[] {"", null, 4.0},
		};
	}
}
