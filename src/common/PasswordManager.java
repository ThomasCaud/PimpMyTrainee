package common;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

public class PasswordManager {
	private static PasswordManager instance;
	private ConfigurablePasswordEncryptor passwordEncryptor;
	protected final String ALGO_CHIFFREMENT = "SHA-256";

	private PasswordManager() {
		passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm(ALGO_CHIFFREMENT);
		passwordEncryptor.setPlainDigest(false);
	}

	public static PasswordManager getInstance() {
		if (instance == null)
			instance = new PasswordManager();
		return instance;
	}

	public String getNewPassword() {
		return RandomStringGenerator.getRandomString(10);
	}

	public String getEncryptedValue(String password) {
		return passwordEncryptor.encryptPassword(password);
	}

	public boolean checkPasswords(String password1, String password2) {
		return passwordEncryptor.checkPassword(password1, password2);
	}
}
