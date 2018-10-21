package config;

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
    public static final String ATT_SESSION_USER = "user";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		 /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        String path = request.getRequestURI().substring( request.getContextPath().length() );
        
        /* Non-filtrage des ressources statiques */
        if ( path.startsWith("/assets") ) {
            chain.doFilter( request, response );
            return;
        }

        if ( session.getAttribute( ATT_SESSION_USER ) == null ) {
            if( !path.equals("/login") ) 
        		response.sendRedirect(request.getServletContext().getContextPath()+URL_LOGIN);
        	else 
        		chain.doFilter( request, response );
        } else {
        	if( !path.equals("/login") ) 
        		chain.doFilter( request, response );
        	else
        		response.sendRedirect(request.getServletContext().getContextPath());
        }

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
