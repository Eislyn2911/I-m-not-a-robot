package com.eislyn.utilcord.ui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;

public class UserEmbedTest {
	@Test
	public void testBuildUserEmbed() {
		Member member = mock(Member.class);
		when(member.getAsMention()).thenReturn("<@Adam>");
		when(member.getEffectiveName()).thenReturn("Adam");
		when(member.getNickname()).thenReturn("AdamNickName");
		when(member.getId()).thenReturn("12345");
		when(member.getEffectiveAvatarUrl()).thenReturn("https://img-9gag-fun.9cache.com/photo/apGgbv9_460s.jpg");
		
		when(member.getOnlineStatus()).thenReturn(OnlineStatus.ONLINE);
		when(member.getTimeJoined()).thenReturn(OffsetDateTime.MAX);
		when(member.getTimeCreated()).thenReturn(OffsetDateTime.MAX);
		
		Activity activity = new Activity() {
			
			@Override
			public boolean isRich() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public String getUrl() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ActivityType getType() {
				return ActivityType.PLAYING;
			}
			
			@Override
			public Timestamps getTimestamps() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getName() {
				return "a game";
			}
			
			@Override
			public EmojiUnion getEmoji() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public RichPresence asRichPresence() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		List<Activity> activityList = new ArrayList<Activity>();
		activityList.add(activity);
		
		when(member.getActivities()).thenReturn(activityList);
		
		EmbedTemplate actualUserEmbed = new UserEmbed(member);
		EmbedBuilder actualUserEmbedBuilder = actualUserEmbed.buildEmbed("Server Name");
		
		EmbedBuilder expectedUserEmbedBuilder = new EmbedBuilder();
		
		expectedUserEmbedBuilder.setFooter("Thank you for using the bot.");
		expectedUserEmbedBuilder.setAuthor("Server Name");
		expectedUserEmbedBuilder.setTitle("User Information");
		expectedUserEmbedBuilder.setThumbnail("https://img-9gag-fun.9cache.com/photo/apGgbv9_460s.jpg");
		expectedUserEmbedBuilder.setDescription("--------------------------------------------------------------------\r\n" + "User info of " + "<@Adam>" + "\r\n--------------------------------------------------------------------");
		expectedUserEmbedBuilder.addField("▼__Info__",
				"Name: " + "Adam" + "\n" + "Nickname: " + "AdamNickName" + "\n" + "Id: " + "12345" + "\n" + "Online Status: "
						+ "Online" + "\n" + "Join Date: " + OffsetDateTime.MAX + "\n" + "Create Date: " + OffsetDateTime.MAX + "\r\n--------------------------------------------------------------------",
				false);
		expectedUserEmbedBuilder.addField("▼__Activities__", "Playing: a game\n" + "--------------------------------------------------------------------", false);
		expectedUserEmbedBuilder.addField("▼__Roles__", "" + "--------------------------------------------------------------------", false);
		
		assertEquals(expectedUserEmbedBuilder.build().toData().toPrettyString(), actualUserEmbedBuilder.build().toData().toPrettyString());
	}

}
