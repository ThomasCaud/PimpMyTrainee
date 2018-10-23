package controllers.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.Config;
import models.beans.E_Role;
import models.beans.User;

@WebServlet( "/users" )
public class UsersController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/admin_users_management.jsp";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute(Config.ATT_SESSION_USER);
		if(user != null) {
			if(user.getRole() == E_Role.ADMIN )
				this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
			else
				response.sendRedirect(request.getServletContext().getContextPath()+Config.URL_REDIRECT_ROOT);		
		}
	}

}
