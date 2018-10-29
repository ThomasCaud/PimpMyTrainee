package common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import dao.DAOFactory;
import dao.exceptions.DAOConfigurationException;

public class GmailEmailSendor {
	
	private static final String PROPERTIES_FILE = "/common/common.properties";
	private static final String PROP_SMTP_SERVER = "smtp_server";
	private static final String PROP_SMTP_USERNAME = "smtp_username";
	private static final String PROP_SMTP_PASSWORD = "smtp_password";
	private static final String PROP_SMTP_SSL_PORT = "smtp_ssl_port";
	private static final String PROP_EMAIL_SENDER = "email_sender";
	
	private String emailSender;
	private String smtpServer;
	private String smtpUsername;
	private String smtpPassword;
	private Integer smtpPort;
	
	private GmailEmailSendor(String smtpServer, String smtpUsername, String smtpPassword, Integer smtpPort, String emailSender) {
		this.smtpServer = smtpServer;
		this.smtpUsername = smtpUsername;
		this.smtpPassword = smtpPassword;
		this.smtpPort = smtpPort;
		this.emailSender = emailSender;
	}
	
	public static GmailEmailSendor getInstance() {
		
		Properties properties = new Properties();
		
		String smtpServer;
		String smtpUsername;
		String smtpPassword;
		Integer smtpPort;
		String emailSender;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream( PROPERTIES_FILE );
		
        if ( propertiesFile == null ) {
            throw new DAOConfigurationException( "Le fichier properties " + PROPERTIES_FILE + " est introuvable." );
        }
        
        try {
            properties.load( propertiesFile );
            smtpServer = properties.getProperty( PROP_SMTP_SERVER );
            smtpUsername = properties.getProperty( PROP_SMTP_USERNAME );
            smtpPassword = properties.getProperty( PROP_SMTP_PASSWORD );
            smtpPort = Integer.valueOf(properties.getProperty( PROP_SMTP_SSL_PORT ));
            emailSender = properties.getProperty( PROP_EMAIL_SENDER );
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Impossible de charger le fichier properties " + PROPERTIES_FILE, e );
        }
        
        GmailEmailSendor instance = new GmailEmailSendor( smtpServer, smtpUsername, smtpPassword, smtpPort, emailSender);
        return instance;
        
	}
	
	public void sendSimpleEmail(String subject, String message, String recipient) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(this.smtpServer);
		email.setSmtpPort(this.smtpPort);
		email.setAuthenticator(new DefaultAuthenticator(this.smtpUsername, this.smtpPassword));
		email.setSSLOnConnect(true);
		email.setFrom(this.emailSender);
		email.setSubject(subject);
		email.setMsg(message);
		email.addTo(recipient);
		email.send();
	}
	
}
