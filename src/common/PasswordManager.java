package common;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

/**
 * Used in order to manage Password encryption
 * 
 * @author Thomas
 *
 */
public class PasswordManager {
	private static PasswordManager instance;
	private ConfigurablePasswordEncryptor passwordEncryptor;
	protected final String ALGO_CHIFFREMENT = "SHA-256";

	private PasswordManager() {
		passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm(ALGO_CHIFFREMENT);
		passwordEncryptor.setPlainDigest(false);
	}

	/**
	 * @return instance of PasswordManager
	 */
	public static PasswordManager getInstance() {
		if (instance == null)
			instance = new PasswordManager();
		return instance;
	}

	/**
	 * @return a random string with 10 characters
	 */
	public String getNewPassword() {
		return RandomStringGenerator.getRandomString(10);
	}

	/**
	 * @param password
	 * @return encrypted password
	 */
	public String getEncryptedValue(String password) {
		return passwordEncryptor.encryptPassword(password);
	}

	/**
	 * @param password1
	 * @param password2
	 * @return true if params are equals
	 */
	public boolean checkPasswords(String password1, String password2) {
		return passwordEncryptor.checkPassword(password1, password2);
	}
}
