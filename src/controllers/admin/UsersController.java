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

@WebServlet( "/users" )
public class UsersController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/admin_users_management.jsp";
	private static final String ATT_SEARCH = "search";
	private static final String ATT_USERS = "users";
	private static final String ATT_PAGINATION_ACTIVE = "paginationActive";
	private static final String ATT_PAGINATION_TOTAL = "paginationTotal";
	private UserDAO userDAO;

	public void init() throws ServletException {
        this.userDAO = ( (DAOFactory) getServletContext().getAttribute( Config.CONF_DAO_FACTORY ) ).getUserDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute(Config.ATT_SESSION_USER);
		
		if(user == null || user.getRole() != E_Role.ADMIN ) {
			response.sendRedirect(request.getServletContext().getContextPath()+Config.URL_REDIRECT_ROOT);		
			return;
		}
  
    ArrayList<User> users = null;

		Integer offset = 1;
		String offsetUrl = request.getParameter("p");
		
		if( offsetUrl != null) {
			try {
				offset = Integer.parseInt(offsetUrl);
				
				if( offset <= 0 ) throw new NumberFormatException();
			} catch(NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}
		
		Integer nbAllUsers = userDAO.countAllUsers();
		Integer nbUsersPerPage = Config.NB_USERS_PER_PAGE;
		
		Integer res = nbAllUsers % nbUsersPerPage;
		Integer nbNeededPages = (int) nbAllUsers / nbUsersPerPage;
		if( res != 0 ) nbNeededPages++;
    
		String search = request.getParameter(ATT_SEARCH);
    	if(search != null) {
			users = userDAO.findUsersByNameOrLastnameOrCompany(search);
		} else {
			users = userDAO.findAllUsers((offset-1)*Config.NB_USERS_PER_PAGE,nbUsersPerPage);
		}
		
		request.setAttribute(ATT_USERS, users);
		request.setAttribute(ATT_SEARCH, search);
		request.setAttribute(ATT_PAGINATION_ACTIVE, offset);
		request.setAttribute(ATT_PAGINATION_TOTAL, nbNeededPages);
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}
}
