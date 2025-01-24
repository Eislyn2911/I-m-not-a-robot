package com.eislyn.utilcord.ui;

import java.util.EventListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

import com.eislyn.utilcord.domain.Controller;
import com.eislyn.utilcord.domain.Currency;
import com.eislyn.utilcord.domain.PartOfSpeech;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Recives input from user to sends the input to controller or receive output from controller and displays it to the user.
 * @author Eislyn
 * @since 15/10/2022
 */
public class MessageReceived extends ListenerAdapter implements EventListener {
	private Controller controller;
	private String prefix;

	public MessageReceived(Controller controller, String prefix) {
		this.controller = controller;
		this.prefix = prefix;
	}

	/**
	 * Overrides onMessageReceived in ListenerAdapter to receive input from discord and checks with supported commands, do not change method name, must return after the operation ends to stop typing. 
	 */
	@Override
	public void onMessageReceived(MessageReceivedEvent event) { 
		// split("\\s+") will split the string into string of array with separator as
		// space or multiple spaces. \s+ is a regular expression for one or more spaces.
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		char[] firstArgArray = args[0].toCharArray();
		
		String guildName = event.getGuild().getName();
		
		if (event.getAuthor().isBot()) { return; }
		
		else if (args[0].equalsIgnoreCase(prefix + "help")) {
			if (args.length == 1) {
				help(event, guildName);
				return;
			}
			else {
				sendMessage(event, "Invalid help command. Type ``e!help`` to get the help menu.");
				return;
			}
		}
		else if(args[0].equalsIgnoreCase(prefix + "ts")) {
			if(args.length >= 3) {
				translate(event, args, args[1]);
				return;
			}
			else {
				sendMessage(event, "Invalid translate command. Type ``e!ts targetLanguage message to translate.``");
				return;
			}
		}
		else if(args[0].equalsIgnoreCase(prefix + "helpts")) {
			if(args.length == 1) {
				sendMessage(event, "See https://cloud.google.com/translate/docs/languages for the full list of supported languages.");
				return;
			}
			else {
				sendMessage(event, "Invalid help command. Type ``e!help`` to get the help menu.");
				return;
			}
		}
		else if(args[0].equalsIgnoreCase(prefix + "d")) {
			if(args.length == 2) {
				String word = args[1];
				try {
					dictionary(event, guildName, word);
					return;
				}catch (JSONException e) {
					sendMessage(event,  "Ops, cannot find this word in the dictionary!");
					return;
				}
			}
			else {
				sendMessage(event, "Invalid dictionary command. Enter a word to get its definition. ``e!d word``.");
				return;
			}
		}
		else if(args[0].equalsIgnoreCase(prefix + "e")) {
			if(args.length == 4) {
				try {
					//Must be upper case for the currency api url
					String baseCurrency = args[1].toUpperCase();
					String targetCurrency = args[2].toUpperCase();
					double amountToConvert = Double.parseDouble(args[3]);
				    currencyExchange(event, baseCurrency, targetCurrency, amountToConvert);
					return;
				}catch (JSONException e) {
					sendMessage(event, "Invalid base currency or target currency. See https://currencyapi.com/docs/ for the full supported currency list.");
					return;
				} catch (IllegalArgumentException e) {
					sendMessage(event, "Invalid exchange amount. Enter only a positive value.");
					return;
				}
			}
			else {
				sendMessage(event, "Invalid exchange command. Type ``e!e baseCurrency targetCurrency amountToExchange`` to translate a currency from one to another.");
				return;
			}
		}
		else if(args[0].equalsIgnoreCase(prefix + "helpct")) {
			if(args.length == 1) {
				sendMessage(event, "https://currencyapi.com/docs/ for the full supported currency list.");
				return;
			}
			else {
				sendMessage(event, "Invalid help command. Type ``e!help`` to get the help menu.");
				return;
			}
		}
		else if(args[0].equalsIgnoreCase(prefix + "time")) {
			if(args.length == 2) {
				String timeZoneName = args[1];
				try {
					String time = controller.currentDateAndTime(timeZoneName);
					sendMessage(event, time);
					return;
				} catch (IllegalArgumentException e) {
					sendMessage(event, "Invalid time zone name. See https://mkyong.com/java/java-display-list-of-timezone-with-gmt/ for the full supported time zone list.");
					return;
				}
			}
			else {
				sendMessage(event, "Invalid time command. Type ``e!time timeZoneName`` / ``e!time timeZoneCode`` to get the current date and time froma time zone.");
				return;
			}
		}
		else if(args[0].equalsIgnoreCase(prefix + "helptime")) {
			sendMessage(event, "See https://mkyong.com/java/java-display-list-of-timezone-with-gmt/ for the full supported time zone list.");
			return;
		}
		else if(args[0].equalsIgnoreCase(prefix + "timer")) {
			try {
				long minutes = Long.parseLong(args[1]);
				timer(event, minutes);
				return;
			} catch(IllegalArgumentException e) {
				sendMessage(event, "Invalid timer command. Type ``e!timer minutes`` to set a ping timer in number of minutes. Minutes must be in Integers only.");
				return;
			}
		}
		else if(args[0].equalsIgnoreCase(prefix + "info")){
			if(args.length == 2) {
				Member member = event.getGuild().getMemberById(args[1].replace("<@", "").replace(">", ""));
				
				EmbedTemplate userEmbed = new UserEmbed(member);
				EmbedBuilder userEmbedBuilder = userEmbed.buildEmbed(guildName);
				sendEmbedMessage(event, userEmbedBuilder);
				return;
			}
			else {
				sendMessage(event, "Invalid info command. Type ``e!info @user`` to get the server info of a user.");
				return;
			}
		}
		else if(firstArgArray[0] == 'e' && firstArgArray[1] == '!') {
			sendMessage(event, "Invalid command. Type ``e!help`` to get the help menu.");
			return;
		}
	}

	/**
	 * Communicates with controller to print out help menu.
	 * @param event MessageReceivedEvent
	 * @param guildName Discord guild name
	 */
	private void help(MessageReceivedEvent event, String guildName) {
		EmbedTemplate embedTemplate = new HelpEmbed();
		EmbedBuilder embedBuilder = embedTemplate.buildEmbed(guildName);
		sendEmbedMessage(event, embedBuilder);
		return;
	}
	
	/**
	 * Combine array of input to text and communicates with controller to translate the text.
	 * @param event MessageReceivedEvent
	 * @param args array of input after the prefix and command
	 * @param langTo Target language
	 * @param text Text to translate
	 */
	private void translate(MessageReceivedEvent event, String[] args, String langTo) {
		String text = "";
		for(int i=2; i<args.length; i++) {
			text += args[i] + " ";
		}
		String response = controller.translate(langTo, text);
		
		if(response.contains("<!DOCTYPE html>")) {
			sendMessage(event, "Invalid target language. See https://cloud.google.com/translate/docs/languages for the full list of supported languages.");
			return;
		}
		else {
			sendMessage(event, "Translation result: " + response);
			return;
		}
	}
	
	/**
	 * Get a dictionary embed from the given guildName and word
	 * @param guildName Discord guild name
	 * @param word word to get definitions
	 * @return dictionaryEmbed a dictionary Embed
	 */
	private void dictionary(MessageReceivedEvent event, String guildName, String word) {
		
		List<PartOfSpeech> partOfSpeechList = controller.dictionary(word);
		
		EmbedTemplate embedTemplate = new DictionaryEmbed(partOfSpeechList, word);
		EmbedBuilder dictionaryEmbedBuilder = embedTemplate.buildEmbed(guildName);
		sendEmbedMessage(event, dictionaryEmbedBuilder);
		return;
	}
	
	/**
	 * converts currency from baseCurrency to targetCurrency given amountToCOnvert.
	 * @param event
	 * @param baseCurrency
	 * @param targetCurrency
	 * @param amountToConvert
	 */
	private void currencyExchange(MessageReceivedEvent event, String baseCurrency, String targetCurrency, double amountToConvert) {
		String guildName = event.getGuild().getName();

		Currency currency = controller.exchangeCurrency(baseCurrency, targetCurrency, amountToConvert);
	
		EmbedTemplate embedTemplate = new CurrencyExchangeEmbed(baseCurrency, targetCurrency, amountToConvert, currency);
		EmbedBuilder currencyExchangeEmbedBuilder = embedTemplate.buildEmbed(guildName);
		
		sendEmbedMessage(event, currencyExchangeEmbedBuilder);
		return;
	}
	
	/**
	 * Schedules a timer given number of  minutes.
	 * @param event
	 * @param minutes
	 */
	private void timer(MessageReceivedEvent event, long minutes){
		if(minutes < 0) {
			throw new IllegalArgumentException();
		}
		
		sendMessage(event, minutes +" minute(s) timer has been set for " + event.getAuthor().getAsMention() + ".");
		long milliseconds = minutes*60000;
		
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				sendMessage(event, minutes + " minute(s) time is completed " + event.getAuthor().getAsMention() + ".");
				return;
			}
		};
		timer.schedule(timerTask, milliseconds);
		return;
	}

	/**
	 * sends a message in discord event channel with the message given, must return to stop typing.
	 * @param event MessageReceivedEvent
	 * @param message message to send
	 */
	private void sendMessage(MessageReceivedEvent event, String message) {
		event.getChannel().sendTyping().queue();
		event.getChannel().sendMessage(message).queue();
		return;
	}

	/**
	 * Sends an embed in discord event channel with the embedBuilder given, must return to stop typing.
	 * @param event MessageReceivedEvent
	 * @param embed embedBuilder to send
	 */
	private void sendEmbedMessage(MessageReceivedEvent event, EmbedBuilder embed) {
		event.getChannel().sendTyping().queue();
		event.getChannel().sendMessageEmbeds(embed.build()).queue();
		return;
	}

}
