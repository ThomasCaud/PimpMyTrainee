package common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import dao.exceptions.DAOConfigurationException;

/**
 * Class used in order to send mail with the SendGrid API
 * 
 */
public class GmailEmailSendor {

	private static GmailEmailSendor instance;
	private static final Logger logger = Logger
			.getLogger(GmailEmailSendor.class);
	private static final String PROPERTIES_FILE = "/common.properties";
	private static final String PROP_SEND_GRID_KEY = "send_grid_key";
	private static final String FROM_EMAIL = "pimp.my@trainee.io";

	private SendGrid sg;

	/**
	 * Initialize the GmailEmailSendor by using the properties file
	 */
	private GmailEmailSendor() {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream propertiesFile = classLoader
				.getResourceAsStream(PROPERTIES_FILE);

		if (propertiesFile == null) {
			throw new DAOConfigurationException("Le fichier properties "
					+ PROPERTIES_FILE + " est introuvable.");
		}

		try {
			Properties properties = new Properties();
			properties.load(propertiesFile);
			String sendGridKey = properties.getProperty(PROP_SEND_GRID_KEY);
			this.sg = new SendGrid(sendGridKey);
		} catch (IOException e) {
			throw new DAOConfigurationException(
					"Impossible de charger le fichier properties "
							+ PROPERTIES_FILE,
					e);
		}
	}

	/**
	 * @return instance of GmailEmailSendor
	 */
	public static GmailEmailSendor getInstance() {
		if (GmailEmailSendor.instance == null) {
			GmailEmailSendor.instance = new GmailEmailSendor();
		}

		return GmailEmailSendor.instance;
	}

	/**
	 * send email using the sendGrid API
	 * 
	 * @param subject
	 * @param message
	 * @param recipient
	 * @throws EmailException
	 */
	public void sendSimpleEmail(String subject, String message,
			String recipient) throws EmailException {
		Email from = new Email(GmailEmailSendor.FROM_EMAIL);
		Email to = new Email(recipient);
		Content content = new Content("text/plain", message);
		Mail mail = new Mail(from, subject, to, content);

		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = this.sg.api(request);
			logger.info(response.getStatusCode());
			logger.info(response.getBody());
			logger.info(response.getHeaders());
		} catch (IOException ex) {
			logger.error(ex);
		}
	}
}
