package models.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import common.PasswordManager;
import dao.interfaces.UserDAO;
import models.beans.User;

public class TraineeSettingsForm extends AbstractForm {
	private static Logger logger = Logger.getLogger(TraineeSettingsForm.class);

	private static final String FIELD_NEW_PASSWORD = "newPassword";
	private static final String FIELD_PASSWORD_CONFIRMATION = "newPasswordConfirmation";
	private static final String FIELD_PHONE = "phone";
	private UserDAO userDAO;

	public TraineeSettingsForm(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	public void processPasswordValidation(String password, String passwordConfirmation, User user) {
		if (null == password) {
			setError(FIELD_PASSWORD_CONFIRMATION, "Password confirm doesn't work.");
			return;
		}
		if (password.length() < 6) {
			setError(FIELD_NEW_PASSWORD, "The password must contain at least 6 characters.");
			return;
		}
		if (!password.equals(passwordConfirmation)) {
			setError(FIELD_PASSWORD_CONFIRMATION, "Password confirm doesn't work.");
			return;
		}
		user.setPassword(PasswordManager.getInstance().getEncryptedValue(password));
	}

	public void processPhoneValidation(String phone, User user) {
		try {
			validatePhone(phone);
		} catch (Exception e) {
			setError(FIELD_PHONE, e.getMessage());
		}
		user.setPhone(phone);
	}

	// main method
	public User updateUser(HttpServletRequest request, User previousUser) {
		String newPassword = getFieldValue(request, FIELD_NEW_PASSWORD);
		String newPasswordConfirmation = getFieldValue(request, FIELD_PASSWORD_CONFIRMATION);
		String phone = getFieldValue(request, FIELD_PHONE);

		User user = new User(previousUser);

		if ((null != newPassword && newPassword.length() > 0)
				|| (null != newPasswordConfirmation && newPasswordConfirmation.length() > 0))
			processPasswordValidation(newPassword, newPasswordConfirmation, user);

		if (!previousUser.getPhone().equals(phone))
			processPhoneValidation(phone, user);

		if (this.getErrors().isEmpty())
			userDAO.updateUser(user);

		return user;
	}

}
