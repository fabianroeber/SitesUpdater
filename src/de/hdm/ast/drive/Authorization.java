package de.hdm.ast.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// ...

public class Authorization {

	// Path to client_secrets.json which should contain a JSON document such as:
	// {
	// "web": {
	// "client_id": "[[YOUR_CLIENT_ID]]",
	// "client_secret": "[[YOUR_CLIENT_SECRET]]",
	// "auth_uri": "https://accounts.google.com/o/oauth2/auth",
	// "token_uri": "https://accounts.google.com/o/oauth2/token"
	// }
	// }
	private static final String CLIENTSECRETS_LOCATION = "client_secrets.json";

	private static final String CLIENT_ID = "1094795664207-1laga1k4105vks9vo9v6r7vmmbbcfth7.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "A7r6WrInJXOwbzrHfxPAKsDx";

	private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
	private static final List<String> SCOPES = Arrays.asList(
			"https://www.googleapis.com/auth/drive", "email", "profile");

	

	private static GoogleAuthorizationCodeFlow flow = null;

	/**
	 * Exception thrown when an error occurred while retrieving credentials.
	 */
	public static class GetCredentialsException extends Exception {
		private static final long serialVersionUID = -2759549467854873114L;
		protected String authorizationUrl;

		/**
		 * Construct a GetCredentialsException.
		 *
		 * @param authorizationUrl
		 *            The authorization URL to redirect the user to.
		 */
		public GetCredentialsException(String authorizationUrl) {
			this.authorizationUrl = authorizationUrl;
		}

		/**
		 * Set the authorization URL.
		 */
		public void setAuthorizationUrl(String authorizationUrl) {
			this.authorizationUrl = authorizationUrl;
		}

		/**
		 * @return the authorizationUrl
		 */
		public String getAuthorizationUrl() {
			return authorizationUrl;
		}
	}

	/**
	 * Exception thrown when a code exchange has failed.
	 */
	public static class CodeExchangeException extends GetCredentialsException {
		private static final long serialVersionUID = 2000878161378375281L;

		/**
		 * Construct a CodeExchangeException.
		 *
		 * @param authorizationUrl
		 *            The authorization URL to redirect the user to.
		 */
		public CodeExchangeException(String authorizationUrl) {
			super(authorizationUrl);
		}

	}

	/**
	 * Exception thrown when no refresh token has been found.
	 */
	public static class NoRefreshTokenException extends GetCredentialsException {
		private static final long serialVersionUID = -573734617291320828L;

		/**
		 * Construct a NoRefreshTokenException.
		 *
		 * @param authorizationUrl
		 *            The authorization URL to redirect the user to.
		 */
		public NoRefreshTokenException(String authorizationUrl) {
			super(authorizationUrl);
		}

	}

	/**
	 * Exception thrown when no user ID could be retrieved.
	 */
	private static class NoUserIdException extends Exception {
		private static final long serialVersionUID = -806754837878871672L;
	}

	/**
	 * Retrieved stored credentials for the provided user ID.
	 *
	 * @param userId
	 *            User's ID.
	 * @return Stored Credential if found, {@code null} otherwise.
	 */
	public static Credential getStoredCredentials(String userId) {
		// TODO: Implement this method to work with your database. Instantiate a
		// new
		// Credential instance with stored accessToken and refreshToken.

		JSONParser parser = new JSONParser();
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();

		try {

			Object obj = parser.parse(new FileReader(
					"//C:/Users/Markus Schmieder/Documents/tokens.json"));

			JSONObject jsonObject = (JSONObject) obj;

			String accessToken = (String) jsonObject.get("accessToken");
			String refreshToken = (String) jsonObject.get("refreshToken");

			GoogleCredential credentials = new GoogleCredential.Builder()
					.setClientSecrets(CLIENT_ID, CLIENT_SECRET)
					.setJsonFactory(jsonFactory).setTransport(httpTransport)
					.build().setRefreshToken(refreshToken)
					.setAccessToken(accessToken);

			return credentials;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Store OAuth 2.0 credentials in the application's database.
	 *
	 * @param userId
	 *            User's ID.
	 * @param credentials
	 *            The OAuth 2.0 credentials to store.
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void storeCredentials(String userId, Credential credentials)
			throws IOException {
		// TODO: Implement this method to work with your database.
		// Store the credentials.getAccessToken() and
		// credentials.getRefreshToken()
		// string values in your database.

		JSONObject obj = new JSONObject();
		obj.put("accessToken", credentials.getAccessToken());
		obj.put("refreshToken", credentials.getRefreshToken());

		// JSONArray company = new JSONArray();
		// company.add("Compnay: eBay");
		// company.add("Compnay: Paypal");
		// company.add("Compnay: Google");
		// obj.put("Company List", company);

		FileWriter file = new FileWriter(
				"//C:/Users/Markus Schmieder/Documents/tokens.json");
		try {
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			file.flush();
			file.close();
		}
	}

	/**
	 * Build an authorization flow and store it as a static class attribute.
	 *
	 * @return GoogleAuthorizationCodeFlow instance.
	 * @throws IOException
	 *             Unable to load client_secrets.json.
	 */
	static GoogleAuthorizationCodeFlow getFlow() throws IOException {
		if (flow == null) {
			HttpTransport httpTransport = new NetHttpTransport();
			JsonFactory jsonFactory = new JacksonFactory();
			Reader reader = new InputStreamReader(
					Authorization.class
							.getResourceAsStream(CLIENTSECRETS_LOCATION));
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
					jsonFactory, reader);
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport,
					jsonFactory, clientSecrets, SCOPES)
					.setAccessType("offline").setApprovalPrompt("force")
					.build();
		}
		return flow;
	}

	/**
	 * Exchange an authorization code for OAuth 2.0 credentials.
	 *
	 * @param authorizationCode
	 *            Authorization code to exchange for OAuth 2.0 credentials.
	 * @return OAuth 2.0 credentials.
	 * @throws CodeExchangeException
	 *             An error occurred.
	 */
	static Credential exchangeCode(String authorizationCode)
			throws CodeExchangeException {
		try {
			GoogleAuthorizationCodeFlow flow = getFlow();
			GoogleTokenResponse response = flow
					.newTokenRequest(authorizationCode)
					.setRedirectUri(REDIRECT_URI).execute();
			return flow.createAndStoreCredential(response, null);
		} catch (IOException e) {
			System.err.println("An error occurred: " + e);
			throw new CodeExchangeException(null);
		}
	}

	/**
	 * Send a request to the UserInfo API to retrieve the user's information.
	 *
	 * @param credentials
	 *            OAuth 2.0 credentials to authorize the request.
	 * @return User's information.
	 * @throws NoUserIdException
	 *             An error occurred.
	 */
	static Userinfoplus getUserInfo(Credential credentials)
			throws NoUserIdException {
		Oauth2 userInfoService = new Oauth2.Builder(new NetHttpTransport(),
				new JacksonFactory(), credentials).build();
		Userinfoplus userInfo = null;
		try {
			userInfo = userInfoService.userinfo().get().execute();
		} catch (IOException e) {
			System.err.println("An error occurred: " + e);
		}
		if (userInfo != null && userInfo.getId() != null) {
			return userInfo;
		} else {
			throw new NoUserIdException();
		}
	}

	/**
	 * Retrieve the authorization URL.
	 *
	 * @param emailAddress
	 *            User's e-mail address.
	 * @param state
	 *            State for the authorization URL.
	 * @return Authorization URL to redirect the user to.
	 * @throws IOException
	 *             Unable to load client_secrets.json.
	 */
	public static String getAuthorizationUrl(String emailAddress, String state)
			throws IOException {
		GoogleAuthorizationCodeRequestUrl urlBuilder = getFlow()
				.newAuthorizationUrl().setRedirectUri(REDIRECT_URI)
				.setState(state);
		urlBuilder.set("user_id", emailAddress);
		return urlBuilder.build();
	}

	/**
	 * Retrieve credentials using the provided authorization code.
	 *
	 * This function exchanges the authorization code for an access token and
	 * queries the UserInfo API to retrieve the user's e-mail address. If a
	 * refresh token has been retrieved along with an access token, it is stored
	 * in the application database using the user's e-mail address as key. If no
	 * refresh token has been retrieved, the function checks in the application
	 * database for one and returns it if found or throws a
	 * NoRefreshTokenException with the authorization URL to redirect the user
	 * to.
	 *
	 * @param authorizationCode
	 *            Authorization code to use to retrieve an access token.
	 * @param state
	 *            State to set to the authorization URL in case of error.
	 * @return OAuth 2.0 credentials instance containing an access and refresh
	 *         token.
	 * @throws NoRefreshTokenException
	 *             No refresh token could be retrieved from the available
	 *             sources.
	 * @throws IOException
	 *             Unable to load client_secrets.json.
	 */
	public static Credential getCredentials(String authorizationCode,
			String state) throws CodeExchangeException,
			NoRefreshTokenException, IOException {
		String emailAddress = "markusschmieder1986@googlemail.com";
		try {
			Credential credentials = exchangeCode(authorizationCode);
			Userinfoplus userInfo = getUserInfo(credentials);
			String userId = userInfo.getId();
			emailAddress = userInfo.getEmail();
			if (credentials.getRefreshToken() != null) {
				storeCredentials(userId, credentials);
				return credentials;
			} else {
				credentials = getStoredCredentials(userId);
				if (credentials != null
						&& credentials.getRefreshToken() != null) {
					return credentials;
				}
			}
		} catch (CodeExchangeException e) {
			e.printStackTrace();
			// Drive apps should try to retrieve the user and credentials for
			// the current
			// session.
			// If none is available, redirect the user to the authorization URL.
			e.setAuthorizationUrl(getAuthorizationUrl(emailAddress, state));
			throw e;
		} catch (NoUserIdException e) {
			e.printStackTrace();
		}
		// No refresh token has been retrieved.
		String authorizationUrl = getAuthorizationUrl(emailAddress, state);
		throw new NoRefreshTokenException(authorizationUrl);
	}

}
