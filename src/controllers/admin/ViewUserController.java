package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Config;
import dao.DAOFactory;
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.User;

@WebServlet( "/viewUser/*" )
public class ViewUserController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/admin_view_user.jsp";
	private static final String ATT_USER = "user";
	private UserDAO userDAO;
	
	public void init() throws ServletException {
        this.userDAO = ( (DAOFactory) getServletContext().getAttribute( Config.CONF_DAO_FACTORY ) ).getUserDAO();
	}
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		User sessionUser = (User) session.getAttribute(Config.ATT_SESSION_USER);
		
		if(sessionUser == null || sessionUser.getRole() != E_Role.ADMIN ) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);	
			return;
		}
		
		String pathInfo = request.getPathInfo();
		
		if( pathInfo == null || pathInfo.isEmpty() ) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String urlId = request.getPathInfo().substring(1, request.getPathInfo().length());
		Integer id;
		
		try {
			id = Integer.parseInt(urlId);
		} catch(NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		User user = userDAO.findUserByID(id);
		
		if( user == null ) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		request.setAttribute(ATT_USER, user);
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}

}
