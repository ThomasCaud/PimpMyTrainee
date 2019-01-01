package models.forms;

import javax.servlet.http.HttpServletRequest;

import common.PasswordManager;
import dao.interfaces.UserDAO;
import models.beans.User;

public class LoginForm extends AbstractForm {
	// Variables that represents each field of the form
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PASSWORD = "password";
	private UserDAO userDAO;

	public LoginForm(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	public void processEmailValidation(String email, User user) {
		try {
			validateEmail(email);
		} catch (Exception e) {
			setError(FIELD_EMAIL, e.getMessage());
		}
		user.setEmail(email);
	}

	public void processPasswordValidation(String password, User user) {
		try {
			validatePassword(password);
		} catch (Exception e) {
			setError(FIELD_PASSWORD, e.getMessage());
		}
		user.setPassword(password);
	}

	// Main method called by the servlet to process the login
	public User connectUser(HttpServletRequest request) {
		String email = getFieldValue(request, FIELD_EMAIL);
		String password = getFieldValue(request, FIELD_PASSWORD);

		User user = new User();
		processEmailValidation(email, user);
		processPasswordValidation(password, user);

		if (this.getErrors().isEmpty()) {
			User existingUser = userDAO.findActive(FIELD_EMAIL, email);

			if (existingUser == null) {
				setError(FIELD_EMAIL, "The credentials don't match.");
				return user;
			} else {
				if (PasswordManager.getInstance().checkPasswords(user.getPassword(), existingUser.getPassword()))
					return existingUser;
				else {
					setError(FIELD_PASSWORD, "The credentials don't match.");
					return user;
				}
			}
		}
		return user;
	}
}