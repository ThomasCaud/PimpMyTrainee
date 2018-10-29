package models.forms;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;

import common.GmailEmailSendor;
import common.RandomStringGenerator;
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.User;

public class RegisterUserForm extends AbstractForm {
	// Variables that represents each field of the form
		private static final String FIELD_FIRSTNAME = "firstname";
		private static final String FIELD_LASTNAME = "lastname";
		private static final String FIELD_EMAIL = "email";
		private static final String FIELD_COMPANY = "company";
		private static final String FIELD_PHONE = "phone";
		private UserDAO userDAO;
		
		public RegisterUserForm( UserDAO userDAO ) {
			super();
			this.userDAO = userDAO;
		}
		
		public void processFirstnameValidation( String firstname, User user ) {
			if( isNullOrEmpty(firstname) ) {
				setError(FIELD_FIRSTNAME, "The firstname is empty.");
			}
			user.setFirstname(firstname);
		}
		
		public void processLastnameValidation( String lastname, User user ) {
			if( isNullOrEmpty(lastname) ) {
				setError(FIELD_LASTNAME, "The lastname is empty.");
			}
			user.setLastname(lastname);
		}
		
		public void processEmailValidation( String email, User user ) {
			try {
				validateEmail(email);
			} catch (Exception e) {
				setError(FIELD_EMAIL, e.getMessage());
			}
			user.setEmail(email);
		}
		
		public void processCompanyValidation( String company, User user ) {
			if( isNullOrEmpty(company) ) {
				setError(FIELD_COMPANY, "The company is empty.");
			}
			user.setCompany(company);
		}
		
		public void processPhoneValidation( String phone, User user ) {
			try {
				validatePhone(phone);
			} catch (Exception e) {
				setError(FIELD_PHONE, e.getMessage());
			}
			user.setPhone(phone);
		}
		
		// Main method called by the servlet to process the registration
		public User registerUser(HttpServletRequest request) throws EmailException {
			String firstname = getFieldValue(request,FIELD_FIRSTNAME);
			String lastname = getFieldValue(request,FIELD_LASTNAME);
			String email = getFieldValue(request,FIELD_EMAIL);
			String company = getFieldValue(request,FIELD_COMPANY);
			String phone = getFieldValue(request,FIELD_PHONE);
			
			User user = new User();
			
			processFirstnameValidation(firstname,user);
			processLastnameValidation(lastname,user);
			processEmailValidation(email,user);
			processCompanyValidation(company,user);
			processPhoneValidation(phone,user);
			
			if( this.getErrors().isEmpty() ) {
				User existingUser = userDAO.findActiveUserByEmail(email);
				
				if( existingUser != null ) {
					setError(FIELD_EMAIL, "This email address is already taken.");
					return user;
				}
				
				String password = RandomStringGenerator.getRandomString(10);
				
				user.setPassword(passwordEncryptor.encryptPassword(password));
				user.setRole(E_Role.TRAINEE);
				user.setIsActive(true);
				user.setCreationDate(new Timestamp(System.currentTimeMillis()));
				
				userDAO.createUser(user);

				/*GmailEmailSendor.getInstance().sendSimpleEmail(
					"Your password for PimpMyTrainee",
					"Thanks for subscribing! Your password is " + password,
					user.getEmail()
				);*/
				
				System.out.println("Password = "+password);
				
				return user;
			}
			return user;
		}
}
