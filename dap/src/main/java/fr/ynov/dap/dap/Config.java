package fr.ynov.dap.dap;


import java.io.IOException;
import java.security.GeneralSecurityException;


/**
 * The Class Config.
 */
public class Config {

	/** The application name. */
	private final String APPLICATION_NAME = "Google Calendar API Java Quickstart";

	/** The tokens directory path. */
	private final String TOKENS_DIRECTORY_PATH = "tokens";

	/** The credentials file path. */
	private final String CREDENTIALS_FILE_PATH = "/credentials.json";

	/** The Auth 2 callback url. */
	private final String Auth2CallbackUrl = "/oAuth2Callback";

	/** The separator. */
	private String separator = System.getProperty("file.separator");

	/** The home path. */
	private String homePath = System.getProperty("user.home");	


	private String applicationName;
	private String tokensPath;
	private String credentialPath;
	private String auth2CallbackUrl;

	public Config() {
		   this.applicationName = APPLICATION_NAME;
		   this.tokensPath = TOKENS_DIRECTORY_PATH;
		   this.credentialPath = CREDENTIALS_FILE_PATH;
		   this.auth2CallbackUrl = Auth2CallbackUrl;
	}
	/**
	 * Constructor Config.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */



	public String getApplicationName() {
		return applicationName;
	}

	public Config(String separator, String homePath, String applicationName, String tokensPath, String credentialPath,
			String auth2CallbackUrl) {
		this.separator = separator;
		this.homePath = homePath;
		this.applicationName = applicationName;
		this.tokensPath = tokensPath;
		this.credentialPath = credentialPath;
		this.auth2CallbackUrl = auth2CallbackUrl;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getTokensPath() {
		return tokensPath;
	}

	public void setTokensPath(String tokensPath) {
		this.tokensPath = tokensPath;
	}

	public String getCredentialPath() {
		return credentialPath;
	}

	public void setCredentialPath(String credentialPath) {
		this.credentialPath = credentialPath;
	}

	public String getAuth2CallbackUrl() {
		return auth2CallbackUrl;
	}

	public void setAuth2CallbackUrl(String auth2CallbackUrl) {
		this.auth2CallbackUrl = auth2CallbackUrl;
	} 
}
