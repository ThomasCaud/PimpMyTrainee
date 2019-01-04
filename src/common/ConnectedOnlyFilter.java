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

@WebFilter(urlPatterns = "/*")
public class ConnectedOnlyFilter implements Filter {

	public static final String URL_LOGIN = "/login";

	@Override
	public void destroy() {
	}

	/**
	 * Check if the user is connected in order to access to the request asked
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		// Cast des objets request et response
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// Initialisation de l'encoding g�n�ral
		req.setCharacterEncoding("UTF-8");

		// R�cup�ration de la session depuis la requ�te
		HttpSession session = request.getSession();
		String path = request.getRequestURI().substring(request.getContextPath().length());

		// Non-filtrage des ressources statiques
		if (path.startsWith("/assets") || path.startsWith("/" + Config.URL_DATABASE_ADMINISTRATION)) {
			chain.doFilter(request, response);
			return;
		}

		/*
		 * Si aucun utilisateur en session (donc pas connect�), alors
		 * redirection vers la page de login Si d�j� connect� et essaye
		 * d'acc�der � la page de login, alors redirection sur la page
		 * principale
		 */
		if (session.getAttribute(Config.ATT_SESSION_USER) == null) {
			if (!path.equals("/" + Config.URL_LOGIN))
				response.sendRedirect(request.getServletContext().getContextPath() + URL_LOGIN);
			else
				chain.doFilter(request, response);
		} else {
			if (!path.equals("/" + Config.URL_LOGIN))
				chain.doFilter(request, response);
			else
				response.sendRedirect(request.getServletContext().getContextPath() + Config.URL_ROOT);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
