package common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter( urlPatterns = "/*" )
public class ConnectedOnlyFilter implements Filter {
	
	public static final String URL_LOGIN  = "/login";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		 /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        /* Initialisation de l'encoding général */
        req.setCharacterEncoding("UTF-8");

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        String path = request.getRequestURI().substring( request.getContextPath().length() );
        
        /* Non-filtrage des ressources statiques */
        if ( path.startsWith("/assets") ) {
            chain.doFilter( request, response );
            return;
        }

		/* Si aucun utilisateur en session (donc pas connecté), alors redirection vers la page de login */
        /* Si déjà connecté et essaye d'accéder à la page de login, alors redirection sur la page principale */
        if ( session.getAttribute( Config.ATT_SESSION_USER ) == null ) {
            if( !path.equals("/"+Config.URL_LOGIN) ) 
        		response.sendRedirect(request.getServletContext().getContextPath()+URL_LOGIN);
        	else 
        		chain.doFilter( request, response );
        } else {
        	if( !path.equals("/"+Config.URL_LOGIN) ) 
        		chain.doFilter( request, response );
        	else
        		response.sendRedirect(request.getServletContext().getContextPath()+Config.URL_ROOT);
        }

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
