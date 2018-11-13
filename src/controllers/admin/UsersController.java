package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Config;
import controllers.AbstractController;
import dao.DAOFactory;
import dao.interfaces.UserDAO;
import models.beans.User;

@WebServlet("/" + Config.URL_USERS)
public class UsersController extends AbstractController {

    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/admin_users_management.jsp";
    private static final String ATT_SEARCH = "search";
    private static final String ATT_USERS = "users";
    private static final String ATT_PAGINATION_ACTIVE = "paginationActive";
    private static final String ATT_PAGINATION_TOTAL = "paginationTotal";
    private UserDAO userDAO;

    public void init() throws ServletException {
	this.userDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getUserDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	User sessionUser = checkSessionUser(request, response);
	checkAdminOnly(sessionUser, response);

	ArrayList<User> users = null;

	Integer offset = 1;
	String offsetUrl = request.getParameter("p");

	if (offsetUrl != null) {
	    try {
		offset = Integer.parseInt(offsetUrl);

		if (offset <= 0)
		    throw new NumberFormatException();
	    } catch (NumberFormatException e) {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return;
	    }
	}

	Integer nbUsersPerPage = Config.NB_USERS_PER_PAGE;
	String nbUsersPerPageUrl = request.getParameter("n");

	if (nbUsersPerPageUrl != null) {
	    try {
		nbUsersPerPage = Integer.parseInt(nbUsersPerPageUrl);

		if (nbUsersPerPage <= 0)
		    throw new NumberFormatException();
	    } catch (NumberFormatException e) {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return;
	    }
	}

	Integer nbAllUsers = userDAO.count();

	Integer res = nbAllUsers % nbUsersPerPage;
	Integer nbNeededPages = (int) nbAllUsers / nbUsersPerPage;
	if (res != 0)
	    nbNeededPages++;

	String search = request.getParameter(ATT_SEARCH);
	if (search != null) {
	    users = userDAO.findUsersByNameOrLastnameOrCompany(search);
	} else {
	    users = userDAO.findAll((offset - 1) * nbUsersPerPage, nbUsersPerPage);
	}

	request.setAttribute(ATT_USERS, users);
	request.setAttribute(ATT_SEARCH, search);
	request.setAttribute(ATT_PAGINATION_ACTIVE, offset);
	request.setAttribute(ATT_PAGINATION_TOTAL, nbNeededPages);

	this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }
}
