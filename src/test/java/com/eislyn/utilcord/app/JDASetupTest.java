package com.eislyn.utilcord.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.dv8tion.jda.api.JDA.Status;

public class JDASetupTest {	
	@Test
	public void testValidSetupJDA() throws InterruptedException {
		JDASetup jdaSetup = JDASetup.getInstance();

		Status actualStatus = jdaSetup.setupJDA();
		
		Status expectedStatus = Status.CONNECTED;
		assertEquals(expectedStatus, actualStatus);
	}
}
