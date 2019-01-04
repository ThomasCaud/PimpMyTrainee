package models.forms;

import dao.DAOFactory;
import dao.interfaces.UserDAO;
import junit.framework.TestCase;
import models.beans.User;

public class LoginFormTest extends TestCase {
	public LoginFormTest(String testName) {
		super(testName);
	}

	public void testProcessEmailValidation() {
		DAOFactory factory = DAOFactory.getInstance();
		UserDAO userDAO = factory.getUserDAO();
		LoginForm lf = new LoginForm(userDAO);

		User user = new User();
		lf.processEmailValidation("false", user);
		assert (!lf.getErrors().isEmpty());
		assert (user.getEmail().length() == 0);
	}
}
