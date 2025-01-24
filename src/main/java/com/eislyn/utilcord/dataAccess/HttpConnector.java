package com.eislyn.utilcord.dataAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http connection manager class that gets a response as string from an api.
 * @author Eislyn
 * @since 16/10/2022
 */
public final class HttpConnector {
	/**
	 * Gets a response from an api given a string.
	 * @param urlStr Url string
	 * @return response response from an api
	 */
	public static String getResponse(String urlStr) {
		if(urlStr == null || urlStr == "") {
			throw new IllegalArgumentException();
		}
		
		try {
			URL url = new URL(urlStr);
			StringBuilder response = new StringBuilder();

			HttpURLConnection connection;
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestProperty("User-Agent", "Mozilla/5.0");

			BufferedReader reader;
			
			int responseCode = connection.getResponseCode();
			String inputLine;
			if (responseCode < 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				reader.close();
			}
			return response.toString();

		} catch (IOException e) {
			return "Can't connect to the network, please try again later.";
		} 
	}
}
