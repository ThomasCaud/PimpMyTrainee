package common;

import junit.framework.TestCase;

public class PasswordManagerTest extends TestCase {
	public PasswordManagerTest(String testName) {
		super(testName);
	}

	public void testGetInstance() {
		PasswordManager p1 = PasswordManager.getInstance();
		assert (p1 != null);

		PasswordManager p2 = PasswordManager.getInstance();
		assert (p1 == p2);
	}

	public void testGetNewPassword() {
		String pwd1 = PasswordManager.getInstance().getNewPassword();
		assert (pwd1 != null);
		assert (pwd1.length() == 10);

		String pwd2 = PasswordManager.getInstance().getNewPassword();
		assert (!pwd1.equals(pwd2));
	}

	public void testGetEncryptedValue() {
		String fakePwd = "fakepwd";
		String pwd1 = PasswordManager.getInstance().getEncryptedValue(fakePwd);
		assert (!pwd1.equals(fakePwd));

		String pwd2 = PasswordManager.getInstance().getEncryptedValue(fakePwd);
		assert (!pwd1.equals(pwd2));
	}

	public void testCheckPasswords() {
		String fakePwd = "fakepwd";
		String pwd1_a = PasswordManager.getInstance().getEncryptedValue(fakePwd);

		assert (PasswordManager.getInstance().checkPasswords("fakepwd", pwd1_a));
		assert (!PasswordManager.getInstance().checkPasswords("fakepwd_false", pwd1_a));
	}
}
