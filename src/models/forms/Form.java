package models.forms;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract class Form {
		// Map that contains all the errors that are thrown during the form validation process
		private Map<String,String> errors = new HashMap<String,String>();
		
		public Map<String,String> getErrors() { return errors; }
		protected void setError( String field, String message ) { errors.put( field, message ); }
		
		/*
		 * Return null if the corresponding request's field is empty and its value otherwise.
		 */
		protected static String getFieldValue( HttpServletRequest request, String fieldName ) {
		    String value = request.getParameter( fieldName );
		    if ( value == null || value.trim().length() == 0 ) {
		        return null;
		    } else {
		        return value.trim();
		    }
		}
		
		protected void validateEmail(String email) throws Exception {
			if ( email != null ) {
		        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
		            throw new Exception( "The email address is not invalid." );
		        }
		    } else {
		        throw new Exception( "The email address is empty." );
		    }
		}
		
		protected void validatePassword(String password) throws Exception {
			if ( password != null ) {
		        if ( password.length() < 6 ) {
		            throw new Exception( "The password must contain at least 6 characters." );
		        }
		    } else {
		        throw new Exception( "The password is empty." );
		    }
		}
}
