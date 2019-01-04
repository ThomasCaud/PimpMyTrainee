package common;

import java.lang.reflect.Field;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import dao.DAOFactory;

@WebListener
public class InitServletContext implements ServletContextListener {

	private static Logger logger = Logger.getLogger(InitServletContext.class);
	private static final String ATT_DAO_FACTORY = "daofactory";
	private DAOFactory daoFactory;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// nothing to do
	}

	@Override
	/**
	 * Initialize the application context
	 */
	public void contextInitialized(ServletContextEvent event) {
		// Récupération du ServletContext lors du chargement de l'application
		ServletContext servletContext = event.getServletContext();
		// Instanciation de notre DAOFactory
		this.daoFactory = DAOFactory.getInstance();
		// Enregistrement dans un attribut ayant pour portée toute l'application
		servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);

		// Chargement dans l'application des URLs de l'appli
		for (Field field : Config.class.getDeclaredFields()) {
			String fieldName = field.getName();
			if (fieldName.startsWith("URL_")) {
				try {
					servletContext.setAttribute(fieldName, field.get(fieldName));
				} catch (SecurityException e) {
					logger.error(e.getStackTrace());
				} catch (IllegalArgumentException e) {
					logger.error(e.getStackTrace());
				} catch (IllegalAccessException e) {
					logger.error(e.getStackTrace());
				}
			}
		}
	}

}
