package models.forms;

import javax.servlet.http.HttpServletRequest;

import dao.interfaces.UserDAO;
import models.beans.User;

public class UpdateUserForm extends AbstractForm {
	
	// Variables that represents each field of the form
	private static final String FIELD_FIRSTNAME = "firstname";
	private static final String FIELD_LASTNAME = "lastname";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_COMPANY = "company";
	private static final String FIELD_PHONE = "phone";
	private static final String FIELD_IS_ACTIVE = "isActive";
	private UserDAO userDAO;
	
	public UpdateUserForm( UserDAO userDAO ) {
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
	
	public void processIsActiveValidation( String isActiveStr, User user ) {
		if( isActiveStr == null || (!isActiveStr.toUpperCase().equals("TRUE") && !isActiveStr.toUpperCase().equals("FALSE")))
			setError(FIELD_IS_ACTIVE, "One the two radio buttons must be checked.");
		
		boolean isActive = isActiveStr.toUpperCase().equals("TRUE");
		user.setIsActive(isActive);
	}
	
	// Main method called by the servlet to process the registration
	public User updateUser(HttpServletRequest request) {
		String firstname = getFieldValue(request,FIELD_FIRSTNAME);
		String lastname = getFieldValue(request,FIELD_LASTNAME);
		String email = getFieldValue(request,FIELD_EMAIL);
		String company = getFieldValue(request,FIELD_COMPANY);
		String phone = getFieldValue(request,FIELD_PHONE);
		String isActive = getFieldValue(request,FIELD_IS_ACTIVE);

		User previousUser = userDAO.findUserByID((int)request.getAttribute("id"));
		User user = new User(previousUser);
		
		if( !previousUser.getFirstname().equals(firstname) )
			processFirstnameValidation(firstname,user);
		
		if( !previousUser.getLastname().equals(lastname) )
			processLastnameValidation(lastname,user);
		
		if( !previousUser.getEmail().equals(email) )
			processEmailValidation(email,user);
		
		if( !previousUser.getCompany().equals(company) )
			processCompanyValidation(company,user);
		
		if( !previousUser.getPhone().equals(phone) )
			processPhoneValidation(phone,user);

		if( !previousUser.getIsActive().toString().equals(isActive) )
			processIsActiveValidation(isActive,user);

		if(this.getErrors().isEmpty()) {
			return user;
		}

		return previousUser;
	}
}

