package test;

import org.testng.annotations.Test;

import keywordengine.KeyWordEngine;

public class LoginTest {

	@Test
	public void loginTest() throws InterruptedException {
		KeyWordEngine engine=new KeyWordEngine();
		engine.startExecution("login");
		
	}
	
}
