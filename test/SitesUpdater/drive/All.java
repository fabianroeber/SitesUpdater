package SitesUpdater.drive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.Test;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import de.hdm.ast.drive.Authorization;
import de.hdm.ast.drive.Authorization.CodeExchangeException;
import de.hdm.ast.drive.Authorization.NoRefreshTokenException;
import de.hdm.ast.drive.FileDownload;
import de.hdm.ast.drive.Service;

public class All {

	Service service = new Service();
	String authorizationCode = "4/runBwTS7uvFzp2XvFGRVbb7G7asPk1DAag6WxWO284w.0rLqKn5uJzkeJvIeHux6iLZZUT71lgI";
	String state = new BigInteger(130, new SecureRandom()).toString(32);
	Credential credentials;
	
	@Test
	public void test() throws CodeExchangeException, NoRefreshTokenException, IOException {
		credentials = Authorization.getCredentials(authorizationCode, state);
//		credentials = Authorization.getStoredCredentials("1234");
		Drive drive = Service.buildService(credentials);
		
		System.out.println(drive.files().list().size());
	}

}
