package models.forms;

import javax.servlet.http.HttpServletRequest;

import dao.interfaces.UserDAO;
import models.beans.User;

public class LoginForm extends Form {
	// Variables that represents each field of the form
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PASSWORD = "password";
	private UserDAO userDAO;
	
	public LoginForm( UserDAO userDAO ) {
		this.userDAO = userDAO;
	}
	
	// Main method called by the servlet to process the login
	public User connectUser(HttpServletRequest request) {
		String email = getFieldValue(request,FIELD_EMAIL);
		String password = getFieldValue(request,FIELD_PASSWORD);
		
		User user = new User();
		try {
			validateEmail(email);
		} catch (Exception e) {
			setError(FIELD_EMAIL, e.getMessage());
		}
		user.setEmail(email);
		
		try {
			validatePassword(password);
		} catch (Exception e) {
			setError(FIELD_PASSWORD, e.getMessage());
		}
		user.setPassword(password);
		
		if( this.getErrors().isEmpty() ) {
			User existingUser = userDAO.findUserByEmail(email);
			
			if( existingUser == null ) {
				setError(FIELD_EMAIL, "The email address doesn't exist.");
				return user;
			} else {
				return existingUser;
			}
		}
		return user;
	}
}
