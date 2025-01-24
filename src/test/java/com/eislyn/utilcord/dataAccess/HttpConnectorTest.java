package com.eislyn.utilcord.dataAccess;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class HttpConnectorTest {
	@Test
	public void testValidGetResponse() {
		final String deploymentKeyName = "GOOGLE_SCRIPT_TRANSLATOR_DEPLOYMENT_ID";

		String deploymentKey = System.getenv(deploymentKeyName);
		if (deploymentKey == null) {
			Dotenv dotenv = Dotenv.load();  // Load Dotenv only if needed
			deploymentKey = dotenv.get(deploymentKeyName);
		}

		String text = "bonjour";
		String langTo = "en";
		String langFrom = "";
		String urlStr = "https://script.google.com/macros/s/" + deploymentKey + "/exec" + "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) + "&target=" + langTo +"&source=" + langFrom;
		String actualResult = HttpConnector.getResponse(urlStr);
		assertTrue(actualResult.contains("<!DOCTYPE html>"));
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestValidGetResponse() throws UnsupportedEncodingException {
		return new Object[] {
				new Object[] {"fr", "hello", "<!DOCTYPE html>"},
		};
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "paramTestInvalidGetResponse")
	public void testInvalidGetResponse(String url) {
		HttpConnector.getResponse(url);
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestInvalidGetResponse() throws UnsupportedEncodingException {
		return new Object[] {
			new Object[] {null},
			new Object[] {""},
		};
	}

}
