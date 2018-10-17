package models.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class LoginForm extends Form {
	// Variables that represents each field of the form
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PASSWORD = "password";
	private static final String FIELD_PASSWORD_CONFIRM = "password_confirm";
	
	// Main method called by the servlet to process the login
	public void connectUser(HttpServletRequest request) {
		String email = getFieldValue(request,FIELD_EMAIL);
		String password = getFieldValue(request,FIELD_PASSWORD);
		String password_confirm = getFieldValue(request,FIELD_PASSWORD_CONFIRM);
		
		try {
			validateEmail(email);
		} catch (Exception e) {
			setError(FIELD_EMAIL, e.getMessage());
		}
		
		try {
			validatePassword(password);
		} catch (Exception e) {
			setError(FIELD_PASSWORD, e.getMessage());
		}
	}
}
