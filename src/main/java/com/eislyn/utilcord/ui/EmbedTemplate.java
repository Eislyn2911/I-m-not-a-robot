package com.eislyn.utilcord.ui;

import net.dv8tion.jda.api.EmbedBuilder;

/**
 * A template class for creating embeds, only need to call buildEmbed() when using created embed, applied template pattern.
 * @author Eislyn
 * @since 15/10/2022
 */
public abstract class EmbedTemplate {
	protected EmbedBuilder embedBuilder = new EmbedBuilder();
	
	/**
	 * Creates and builds the embed step by step, insert author name from ui classes that has Discord Events.
	 * @param authorName Discord guild name
	 * @return embedBuilder EmbedBuilder
	 */
	public final EmbedBuilder buildEmbed(String authorName) {
		if(authorName == null || authorName.isEmpty())
			throw new IllegalArgumentException();
		
		setAuthor(authorName);
		setTitle();
		setThumbnail();
		setDescription();
		addField();
		setFooter();
		buildBuilder();
		return embedBuilder;
	}

	private void setAuthor(String authorName) {
		embedBuilder.setAuthor(authorName);
	}
	
	public abstract void setTitle();
	public abstract void setThumbnail();
	public abstract void setDescription();
	public abstract void addField();

	private void setFooter() {
		embedBuilder.setFooter("Thank you for using the bot.");
	}
	
	private void buildBuilder() {
		embedBuilder.build();
	}
}
