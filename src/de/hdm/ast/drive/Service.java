package de.hdm.ast.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

public class Service {

	/**
	 * Build a Drive service object.
	 *
	 * @param credentials
	 *            OAuth 2.0 credentials.
	 * @return Drive service object.
	 */
	public static Drive buildService(Credential credentials) {
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();

		return new Drive.Builder(httpTransport, jsonFactory, credentials)
				.setApplicationName("SitesUpdater").build();
	}

}