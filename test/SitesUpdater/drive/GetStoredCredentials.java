package SitesUpdater.drive;

import org.junit.Test;

import de.hdm.ast.drive.*;

public class GetStoredCredentials {

	@Test
	public void test() {
		
		System.out.println(MyClass.getStoredCredentials("web").getRefreshToken());
		System.out.println(MyClass.getStoredCredentials("web").getAccessToken());
		
	}

}
