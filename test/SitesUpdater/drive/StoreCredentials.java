package SitesUpdater.drive;

import java.io.IOException;

import org.junit.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import de.hdm.ast.drive.*;

public class StoreCredentials {

	
	JacksonFactory jsonFactory = new JacksonFactory();
	HttpTransport httpTransport = new NetHttpTransport();
	
	
	@Test
	public void test() throws IOException {
		
		GoogleCredential credentials = new GoogleCredential.Builder()
		.setClientSecrets("1094795664207-1laga1k4105vks9vo9v6r7vmmbbcfth7.apps.googleusercontent.com", "A7r6WrInJXOwbzrHfxPAKsDx")
		.setJsonFactory(jsonFactory).setTransport(httpTransport)
		.build().setRefreshToken("1/bdJvFcFB2f4csDA5cdkXQNm6dbwzf-91hpoPiZIuqyg")
		.setAccessToken("ya29.FgGaT8wporrBZnZ7fQaZa_BUG77TVliNPuVr3ubLvM01XyObKry8PrKMQpU6Xmyl5BlfEFnDkdnFTg");
		
		Authorization.storeCredentials("1234", credentials);
		
	}

}
