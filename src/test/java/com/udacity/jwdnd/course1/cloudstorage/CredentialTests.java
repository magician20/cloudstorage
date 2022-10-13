package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for Credential Creation, Viewing, Editing, and Deletion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialTests extends CloudStorageApplicationTests {

	public static final String GOOGLE_URL = "https://www.google.com/";
	public static final String GOOGLE_USERNAME = "Magician";
	public static final String GOOGLE_PASSWORD = "magic";
	public static final String TIBIA_URL = "http://www.tibia.com/";
	public static final String TIBIA_USERNAME = "knight";
	public static final String TIBIA_PASSWORD = "knight";

	/**
	 * Test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed
	 * password is encrypted.
	 */
	@Test
	public void testCredentialCreation() {
		HomePage homePage = signUpAndLogin();
		createAndVerifyCredential(GOOGLE_URL, GOOGLE_USERNAME, GOOGLE_PASSWORD, homePage);
		homePage.deleteCredential();
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		homePage.logout();
	}

	private void createAndVerifyCredential(String url, String username, String password, HomePage homePage) {
		createCredential(url, username, password, homePage);
		homePage.navToCredentialsTab();
		Credential credential = homePage.getFirstCredential();
		Assertions.assertEquals(url, credential.getUrl());
		Assertions.assertEquals(username, credential.getUserName());
		Assertions.assertNotEquals(password, credential.getPassword());
	}

	private void createCredential(String url, String username, String password, HomePage homePage) {
		homePage.navToCredentialsTab();
		homePage.addNewCredential();
		setCredentialFields(url, username, password, homePage);
		homePage.saveCredentialChanges();
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		homePage.navToCredentialsTab();
	}

	private void setCredentialFields(String url, String username, String password, HomePage homePage) {
		homePage.setCredentialUrl(url);
		homePage.setCredentialUsername(username);
		homePage.setCredentialPassword(password);
	}

	/**
	 * Test that views an existing set of credentials, verifies that the viewable password is unencrypted,
	 * edits the credentials, and verifies that the changes are displayed.
	 */
	@Test
	public void testCredentialModification() {
		HomePage homePage = signUpAndLogin();
		createAndVerifyCredential(GOOGLE_URL, GOOGLE_USERNAME, GOOGLE_PASSWORD, homePage);
		Credential originalCredential = homePage.getFirstCredential();
		String firstEncryptedPassword = originalCredential.getPassword();
		homePage.editCredential();
		String newUrl = TIBIA_URL;
		String newCredentialUsername = TIBIA_USERNAME;
		String newPassword = TIBIA_PASSWORD;
		setCredentialFields(newUrl, newCredentialUsername, newPassword, homePage);
		homePage.saveCredentialChanges();
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		homePage.navToCredentialsTab();
		Credential modifiedCredential = homePage.getFirstCredential();
		Assertions.assertEquals(newUrl, modifiedCredential.getUrl());
		Assertions.assertEquals(newCredentialUsername, modifiedCredential.getUserName());
		String modifiedCredentialPassword = modifiedCredential.getPassword();
		Assertions.assertNotEquals(newPassword, modifiedCredentialPassword);
		Assertions.assertNotEquals(firstEncryptedPassword, modifiedCredentialPassword);
		homePage.deleteCredential();
		resultPage.clickOk();
		homePage.logout();
	}

	/**
	 * Test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
	 */
	@Test
	public void testDeletion() {
		HomePage homePage = signUpAndLogin();
		createCredential(GOOGLE_URL, GOOGLE_USERNAME, GOOGLE_PASSWORD, homePage);
		createCredential(TIBIA_URL, TIBIA_USERNAME, TIBIA_PASSWORD, homePage);
		createCredential("http://www.leagueoflegends.com/", "Crash", "cash", homePage);
		Assertions.assertFalse(homePage.noCredentials(driver));
		homePage.deleteCredential();
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickOk();
		homePage.navToCredentialsTab();
		homePage.deleteCredential();
		resultPage.clickOk();
		homePage.navToCredentialsTab();
		homePage.deleteCredential();
		resultPage.clickOk();
		homePage.navToCredentialsTab();
		Assertions.assertTrue(homePage.noCredentials(driver));
		homePage.logout();
	}
}
