package com.eislyn.utilcord.app;

import com.eislyn.utilcord.domain.Controller;
import com.eislyn.utilcord.ui.MessageReceived;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

/**
 * A singleton class that setups Discord JDA with the token got from .env file. 
 * @author Eislyn
 * @since 15/10/2022
 */
public class JDASetup {
	
	private static JDASetup instance;
	private static final String jdaKeyName = "DISCORD_JDA_KEY";
	private static final String PREFIX = "e!";
	private JDA jda;

	/**
	 * private constructor to avoid being created as an object directly.
	 */
	private JDASetup() {

	}

	/**
	 * Only allow one instance of this class, use this to create am instance.
	 * @return
	 */
	public static JDASetup getInstance() {
		if (instance == null) {
			instance = new JDASetup();
		}
		return instance;
	}
	
	public JDA getJda() {
		return jda;
	}

	/**
	 * Gets the discord JDA token from .env with the given JDAKeyName.
	 * @return token
	 */
	private String getJDAToken() {
		if(jdaKeyName == null || jdaKeyName == "") {
			throw new IllegalArgumentException();
		}

		String token = System.getenv(jdaKeyName);
		if (token == null) {
			Dotenv dotenv = Dotenv.load();  // Load Dotenv only if needed
			token = dotenv.get(jdaKeyName);
		}
		return token;
	}

	/**
	 * Setup and build the JDABuilder, manages layered architecture here, add or remove layer here.
	 * @return status Discord JDA connection status, will wait till STATUS.CONNECTED before returning 
	 * @throws InterruptedException
	 */
	public Status setupJDA() throws InterruptedException {
		String token = getJDAToken();
		
		if(token == null || token == "") {
			throw new IllegalArgumentException();
		}
		
		JDABuilder jdaBuilder = JDABuilder.createLight(token).setActivity(Activity.playing("Type e!help for commands"));

		jdaBuilder.setAutoReconnect(true);
		jdaBuilder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		jdaBuilder.enableIntents(GatewayIntent.GUILD_PRESENCES);
		jdaBuilder.enableIntents(GatewayIntent.GUILD_VOICE_STATES);
		jdaBuilder.enableIntents(GatewayIntent.GUILD_MESSAGES);
		jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
		jdaBuilder.enableIntents(GatewayIntent.GUILD_INVITES);
		jdaBuilder.enableIntents(GatewayIntent.DIRECT_MESSAGES);
		jdaBuilder.enableIntents(GatewayIntent.GUILD_EMOJIS_AND_STICKERS);
		jdaBuilder.enableIntents(GatewayIntent.GUILD_BANS);

		jdaBuilder.enableCache(CacheFlag.VOICE_STATE);
		jdaBuilder.enableCache(CacheFlag.ACTIVITY);
		jdaBuilder.enableCache(CacheFlag.ONLINE_STATUS);
		jdaBuilder.enableCache(CacheFlag.CLIENT_STATUS);
		jdaBuilder.enableCache(CacheFlag.ROLE_TAGS);
		jdaBuilder.enableCache(CacheFlag.EMOJI);
		jdaBuilder.enableCache(CacheFlag.MEMBER_OVERRIDES);
		jdaBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);
		
		Controller controller = new Controller();
		jdaBuilder.addEventListeners(new MessageReceived(controller, PREFIX));

		jda = jdaBuilder.build();
		jda.awaitStatus(Status.CONNECTED);
		return jda.getStatus();
	}
}
