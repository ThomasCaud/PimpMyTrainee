package models.forms;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import common.GmailEmailSendor;
import common.RandomStringGenerator;
import dao.interfaces.ThemeDAO;
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.Theme;
import models.beans.User;

public class CreateThemeForm extends AbstractForm {
	
	// Variables that represents each field of the form
		private static final String FIELD_LABEL = "label";
		private ThemeDAO themeDAO;
		
		public CreateThemeForm( ThemeDAO themeDAO ) {
			super();
			this.themeDAO = themeDAO;
		}
		
		public void processLabelValidation( String label, Theme theme ) {
			if( isNullOrEmpty(label) ) {
				setError(FIELD_LABEL, "The label is empty.");
			}
			theme.setLabel(label);
		}
		
		// Main method called by the servlet to process the registration
		public Theme createTheme(HttpServletRequest request) {
			String label = getFieldValue(request,FIELD_LABEL);
			
			Theme theme = new Theme();
			
			processLabelValidation(label,theme);
			
			if( this.getErrors().isEmpty() ) {
				Theme existingTheme = themeDAO.findThemeByLabel(label);
				
				if( existingTheme != null ) {
					setError(FIELD_LABEL, "This theme already exists.");
					return theme;
				}
				
				themeDAO.createTheme(theme);
			}
			return theme;
		}
}