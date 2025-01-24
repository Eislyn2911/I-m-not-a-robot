package com.eislyn.utilcord.domain;

import com.eislyn.utilcord.dataAccess.HttpConnector;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Setups and stores the information necessary for Translator API, set deploymentKey name, langTo and Text first before using the method called translate.
 * @author Eislyn
 * @since 15/10/2022
 */
public class Translator {
	private final static String deploymentKeyName = "GOOGLE_SCRIPT_TRANSLATOR_DEPLOYMENT_ID";
    private String langTo;
	private String text;
	
	public void setLangTo(String langTo) {
		this.langTo = langTo;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Gets the deploymentKey from the keyName
	 * @return deploymentKey
	 */
	private String getDeploymentKey() {
		if(Objects.equals(deploymentKeyName, "")) {
			throw new IllegalArgumentException();
		}

		String deploymentKey = System.getenv(deploymentKeyName);
		if (deploymentKey == null) {
			Dotenv dotenv = Dotenv.load();  // Load Dotenv only if needed
			deploymentKey = dotenv.get(deploymentKeyName);
		}
		return deploymentKey;
	}
	
	/**
	 * Creates URL string and get the response from HttpConnector
	 * @return response Response from API
	 */
	public String translate() {
		String deploymentKey = getDeploymentKey();
		String urlStr;
        //API: Google script Translator class
        final String langFrom = "";
        urlStr = "https://script.google.com/macros/s/" + deploymentKey + "/exec" + "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) + "&target=" + langTo +"&source=" + langFrom;

        if (HttpConnector.getResponse(urlStr).contains("<!DOCTYPE html>")) {
			return "Sorry, translator API free trial is over. The other commands are fine.";
		}
		else {
			return HttpConnector.getResponse(urlStr);
		}
	}
}
