package SitesUpdater.drive;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.junit.Test;

import de.hdm.ast.drive.Authorization;

public class GetAuthorizationUrl {

	String state = new BigInteger(130, new SecureRandom()).toString(32);

	@Test
	public void test() throws IOException {
		System.out.println(Authorization.getAuthorizationUrl(
				"markusschmieder1986@googlemail.com", state));
	}

}
