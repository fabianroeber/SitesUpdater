package de.hdm.ast.drive;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;

public class FilePrint {

	/**
	 * Print a file's metadata.
	 *
	 * @param service
	 *            Drive API service instance.
	 * @param fileId
	 *            ID of the file to print metadata for.
	 */
	static void printFile(Drive service, String fileId) {
		try {
			File file = service.files().get(fileId).execute();

			System.out.println("Title: " + file.getTitle());
			System.out.println("Description: " + file.getDescription());
			System.out.println("MIME type: " + file.getMimeType());
		} catch (HttpResponseException e) {
			if (e.getStatusCode() == 401) {
				// Credentials have been revoked.
				// TODO: Redirect the user to the authorization URL.
				throw new UnsupportedOperationException();
			}
		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
	}

}