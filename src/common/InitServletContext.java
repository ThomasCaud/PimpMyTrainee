package common;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dao.DAOFactory;

@WebListener
public class InitServletContext implements ServletContextListener {
	
	private static final String ATT_DAO_FACTORY = "daofactory";
    private DAOFactory daoFactory;
    
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// RIEN A FAIRE
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		/* Récupération du ServletContext lors du chargement de l'application */
        ServletContext servletContext = event.getServletContext();
        /* Instanciation de notre DAOFactory */
        this.daoFactory = DAOFactory.getInstance();
        /* Enregistrement dans un attribut ayant pour portée toute l'application */
        servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
        
        servletContext.setAttribute( "URL_LOGIN", Config.URL_LOGIN);
        servletContext.setAttribute( "URL_LOGOUT", Config.URL_LOGOUT);
        servletContext.setAttribute( "URL_ROOT", Config.URL_ROOT);
        servletContext.setAttribute( "URL_USERS", Config.URL_USERS);
        servletContext.setAttribute( "URL_QUIZZES", Config.URL_QUIZZES);
        servletContext.setAttribute( "URL_VIEW_USER", Config.URL_VIEW_USER);
        servletContext.setAttribute( "URL_REGISTER_USER", Config.URL_REGISTER_USER);
	}

}
