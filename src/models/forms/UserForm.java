package models.forms;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import common.GmailEmailSendor;
import common.PasswordManager;
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.User;

public class UserForm extends AbstractForm {
	private static Logger logger = Logger.getLogger(UserForm.class);

	// Variables that represents each field of the form
	private static final String FIELD_FIRSTNAME = "firstname";
	private static final String FIELD_LASTNAME = "lastname";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_COMPANY = "company";
	private static final String FIELD_PHONE = "phone";
	private static final String FIELD_ROLE = "role";
	private static final String FIELD_CURRENT_PASSWORD = "currentPassword";
	private static final String FIELD_NEW_PASSWORD = "newPassword";
	private static final String FIELD_CONFIRM_PASSWORD = "confirmPassword";
	private UserDAO userDAO;

	public UserForm(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	public void processFirstnameValidation(String firstname, User user) {
		if (isNullOrEmpty(firstname)) {
			setError(FIELD_FIRSTNAME, "The firstname is empty.");
		}
		user.setFirstname(firstname);
	}

	public void processLastnameValidation(String lastname, User user) {
		if (isNullOrEmpty(lastname)) {
			setError(FIELD_LASTNAME, "The lastname is empty.");
		}
		user.setLastname(lastname);
	}

	public void processEmailValidation(String email, User user) {
		try {
			validateEmail(email);
		} catch (Exception e) {
			setError(FIELD_EMAIL, e.getMessage());
		}
		user.setEmail(email);
	}

	public void processCompanyValidation(String company, User user) {
		if (isNullOrEmpty(company)) {
			setError(FIELD_COMPANY, "The company is empty.");
		}
		user.setCompany(company);
	}

	public void processPhoneValidation(String phone, User user) {
		try {
			validatePhone(phone);
		} catch (Exception e) {
			setError(FIELD_PHONE, e.getMessage());
		}
		user.setPhone(phone);
	}

	public void processRoleValidation(String role, User user) {
		try {
			validateRole(role);
			user.setRole(E_Role.valueOf(role));
		} catch (Exception e) {
			user.setRole(E_Role.TRAINEE);
			setError(FIELD_ROLE, e.getMessage());
		}
	}

	// Main method called by the servlet to process the registration
	public User registerUser(HttpServletRequest request, User creator) throws EmailException {
		String firstname = getFieldValue(request, FIELD_FIRSTNAME);
		String lastname = getFieldValue(request, FIELD_LASTNAME);
		String email = getFieldValue(request, FIELD_EMAIL);
		String company = getFieldValue(request, FIELD_COMPANY);
		String phone = getFieldValue(request, FIELD_PHONE);
		String role = getFieldValue(request, FIELD_ROLE);

		User user = new User();

		processFirstnameValidation(firstname, user);
		processLastnameValidation(lastname, user);
		processEmailValidation(email, user);
		processCompanyValidation(company, user);
		processPhoneValidation(phone, user);
		processRoleValidation(role, user);

		if (this.getErrors().isEmpty()) {
			User existingUser = userDAO.findActive("email", email);

			if (existingUser != null) {
				setError(FIELD_EMAIL, "This email address is already taken.");
				return user;
			}

			String password = PasswordManager.getInstance().getNewPassword();

			user.setPassword(PasswordManager.getInstance().getEncryptedValue(password));
			user.setIsActive(true);
			user.setCreationDate(new Timestamp(System.currentTimeMillis()));

			userDAO.createUser(user, creator);

			GmailEmailSendor.getInstance().sendSimpleEmail("Your password for PimpMyTrainee",
					"Thanks for subscribing! Your password is " + password, user.getEmail());
			logger.info("Generated password : " + password);
			return user;
		}
		return user;
	}

	public void changePassword(HttpServletRequest request, User pConnectedUser) {
		String currentPassword = getFieldValue(request, FIELD_CURRENT_PASSWORD);
		String newPassword = getFieldValue(request, FIELD_NEW_PASSWORD);
		String confirmPassword = getFieldValue(request, FIELD_CONFIRM_PASSWORD);

		processNotNullAndNotEmpty(currentPassword, FIELD_CURRENT_PASSWORD);
		processNotNullAndNotEmpty(newPassword, FIELD_NEW_PASSWORD);
		processNotNullAndNotEmpty(confirmPassword, FIELD_CONFIRM_PASSWORD);

		if (this.getErrors().isEmpty()) {
			User vConnectedUser = userDAO.find(pConnectedUser.getId());

			if (PasswordManager.getInstance().checkPasswords(currentPassword, vConnectedUser.getPassword())) {

				if (newPassword.equals(confirmPassword)) {
					String newPasswordEncrypted = PasswordManager.getInstance().getEncryptedValue(newPassword);
					userDAO.update("password", newPasswordEncrypted, "id", vConnectedUser.getId());
					setSuccessMessage("Password successfully changed !");
				} else {
					setError(FIELD_CONFIRM_PASSWORD, "The passwords are not the same");
				}

			} else {
				setError(FIELD_CURRENT_PASSWORD, "This is not your current password");
			}
		}
	}

	public void processNotNullAndNotEmpty(String value, String key) {
		if (isNullOrEmpty(value)) {
			setError(key, "This field cannot be empty.");
		}
	}
}