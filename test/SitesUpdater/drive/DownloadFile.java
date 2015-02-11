package SitesUpdater.drive;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.model.File;

import de.hdm.ast.drive.Authorization;
import de.hdm.ast.drive.FileDownload;
import de.hdm.ast.drive.Service;

public class DownloadFile {



	@SuppressWarnings("static-access")
	@Test
	public void test() throws IOException {
		JacksonFactory jsonFactory = new JacksonFactory();
		HttpTransport httpTransport = new NetHttpTransport();
		GoogleCredential credentials = new GoogleCredential.Builder()
		.setClientSecrets("1094795664207-1laga1k4105vks9vo9v6r7vmmbbcfth7.apps.googleusercontent.com", "A7r6WrInJXOwbzrHfxPAKsDx")
		.setJsonFactory(jsonFactory).setTransport(httpTransport)
		.build().setRefreshToken("1/bdJvFcFB2f4csDA5cdkXQNm6dbwzf-91hpoPiZIuqyg")
		.setAccessToken("ya29.FgGaT8wporrBZnZ7fQaZa_BUG77TVliNPuVr3ubLvM01XyObKry8PrKMQpU6Xmyl5BlfEFnDkdnFTg");
		
		Service service = new Service();

		service.buildService(credentials);

		
		//Data.downloadFile(service, file);
	}

}
