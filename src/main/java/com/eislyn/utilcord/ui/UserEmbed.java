package com.eislyn.utilcord.ui;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;

/**
 * Creates a help embed using EmbedTemplate by overriding abstract methods.
 * @author Eislyn
 * @since 22/10/2022
 */
public class UserEmbed extends EmbedTemplate {
	// Passed in
	private Member member;

	// Generated
	private String memberTag;;
	private String name;
	private String nickname;
	private String memberId;
	private String avatarUrl;
	private String onlineStatus;
	private String joinDate;
	private String createTime;

	/**
	 * Generates information from member and formats it.
	 * @param member
	 */
	public UserEmbed(Member member) {
		this.member = member;
		this.memberTag = member.getAsMention();
		this.name = member.getEffectiveName();
		this.nickname = member.getNickname();
		
		if(this.nickname == null) {
			this.nickname = "No nickname";
		}
		
		this.memberId = member.getId().replace("@<", "").replace(">", "");
		this.avatarUrl = member.getEffectiveAvatarUrl();
		this.onlineStatus = member.getOnlineStatus().toString().toLowerCase().substring(0, 1).toUpperCase() + member.getOnlineStatus().toString().toLowerCase().substring(1).replace("_", " ");
		this.joinDate = member.getTimeJoined().toString();
		this.createTime = member.getTimeCreated().toString();
	}

	@Override
	public void setTitle() {
		embedBuilder.setTitle("User Information");
	}

	@Override
	public void setThumbnail() {
		embedBuilder.setThumbnail(avatarUrl);
	}

	@Override
	public void setDescription() {
		embedBuilder.setDescription("--------------------------------------------------------------------\r\n" + "User info of " + memberTag + "\r\n--------------------------------------------------------------------");
	}

	@Override
	public void addField() {
		embedBuilder.addField("▼__Info__",
				"Name: " + name + "\n" + "Nickname: " + nickname + "\n" + "Id: " + memberId + "\n" + "Online Status: "
						+ onlineStatus + "\n" + "Join Date: " + joinDate + "\n" + "Create Date: " + createTime + "\r\n--------------------------------------------------------------------",
				false);
		embedBuilder.addField("▼__Activities__", generatesActivitiesString() + "--------------------------------------------------------------------", false);
		embedBuilder.addField("▼__Roles__", generateRolesString() + "--------------------------------------------------------------------", false);
	}

	/**
	 * Generates a string from the list of activities from the member.
	 * @return activitesString
	 */
	private String generatesActivitiesString() {
		List<Activity> activities = null;
		String activitesString = "";

		try {
			activities = member.getActivities();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < activities.size(); i++) {
			activitesString += (activities.get(i).getType().toString().toLowerCase().substring(0, 1).toUpperCase()
					+ activities.get(i).getType().toString().replace("_", " ").toLowerCase().substring(1)) + ": "
					+ activities.get(i).getName() + "\n";
		}

		return activitesString;
	}

	/**
	 * Generates a string from the list of roles from the member.
	 * @return rolesString
	 */
	private String generateRolesString() {
		List<String> roles = new ArrayList<String>();
		String rolesString = "";

		for (int i = 0; i < member.getRoles().size(); i++) {
			String role = member.getRoles().get(i).getAsMention();
			roles.add(role);
			rolesString += role + "\n";
		}

		return rolesString;
	}
}
