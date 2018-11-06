package models.forms;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import models.beans.E_Role;

public abstract class AbstractForm {
    // Map that contains all the errors that are thrown during the form validation
    // process
    protected Map<String, String> errors = new HashMap<String, String>();
    protected static final String ALGO_CHIFFREMENT = "SHA-256";
    ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();

    public Map<String, String> getErrors() {
	return errors;
    }

    protected void setError(String field, String message) {
	errors.put(field, message);
    }

    AbstractForm() {
	passwordEncryptor.setAlgorithm(ALGO_CHIFFREMENT);
	passwordEncryptor.setPlainDigest(false);
    }

    /*
     * Return null if the corresponding request's field is empty and its value
     * otherwise.
     */
    protected static String getFieldValue(HttpServletRequest request, String fieldName) {
	String value = request.getParameter(fieldName);
	if (value == null || value.trim().length() == 0) {
	    return null;
	} else {
	    return value.trim();
	}
    }

    protected void validateEmail(String email) throws Exception {
	if (email != null) {
	    if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
		throw new Exception("The email address is not valid.");
	    }
	} else {
	    throw new Exception("The email address is empty.");
	}
    }

    protected void validatePhone(String phone) throws Exception {
	if (phone != null) {
	    if (!phone.matches(
		    "^(?:(?:\\+|00)33[\\s.-]{0,3}(?:\\(0\\)[\\s.-]{0,3})?|0)[1-9](?:(?:[\\s.-]?\\d{2}){4}|\\d{2}(?:[\\s.-]?\\d{3}){2})$")) {
		throw new Exception("The phone is not valid.");
	    }
	} else {
	    throw new Exception("The phone is empty.");
	}
    }

    protected void validatePassword(String password) throws Exception {
	if (password != null) {
	    if (password.length() < 6) {
		throw new Exception("The password must contain at least 6 characters.");
	    }
	} else {
	    throw new Exception("The password is empty.");
	}
    }

    protected void validateRole(String role) throws Exception {

	if (role != null) {
	    try {
		E_Role.valueOf(role);
	    } catch (IllegalArgumentException e) {
		throw new Exception("The role is incorrect. ");
	    }
	} else {
	    throw new Exception("The role is empty.");
	}
    }

    protected boolean isNullOrEmpty(String str) {
	if (str == null || str.isEmpty()) {
	    return true;
	}
	return false;
    }
}
