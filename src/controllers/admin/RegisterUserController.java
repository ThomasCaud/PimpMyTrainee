package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.Config;
import dao.DAOFactory;
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.User;

@WebServlet( "/registerUser" )
public class RegisterUserController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/admin_register_user.jsp";
	private UserDAO userDAO;
	
	public void init() throws ServletException {
        this.userDAO = ( (DAOFactory) getServletContext().getAttribute( Config.CONF_DAO_FACTORY ) ).getUserDAO();
	}
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute(Config.ATT_SESSION_USER);
		
		if(user == null || user.getRole() != E_Role.ADMIN ) {
			response.sendRedirect(request.getServletContext().getContextPath()+Config.URL_REDIRECT_ROOT);		
			return;
		}
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}
	
}
