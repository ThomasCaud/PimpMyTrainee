package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import common.Config;
import controllers.AbstractController;
import dao.DAOFactory;
import dao.interfaces.UserDAO;
import models.beans.User;

@WebServlet("/" + Config.URL_USERS)
public class UsersController extends AbstractController {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CreateQuizController.class);
	private static final String VIEW = "/WEB-INF/admin_users_management.jsp";
	private static final String ATT_SEARCH = "search";
	private static final String ATT_USERS = "users";
	private static final String ATT_PAGINATION_ACTIVE = "paginationActive";
	private static final String ATT_PAGINATION_TOTAL = "paginationTotal";
	private static final String ATT_PAGINATION_BEGIN = "paginationBegin";
	private static final String ATT_PAGINATION_END = "paginationEnd";
	private static final String ATT_ACTIVATE = "activate";
	private static final String ATT_DEACTIVATE = "deactivate";

	private static UserDAO userDAO;

	public static void setDAOs(UserDAO userDAO) {
		UsersController.userDAO = userDAO;
	}

	public void init() throws ServletException {
		UsersController.setDAOs(((DAOFactory) getServletContext()
				.getAttribute(Config.CONF_DAO_FACTORY)).getUserDAO());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		Integer nbAllUsers = 0;
		try {
			nbAllUsers = userDAO.count("managerId", sessionUser.getId());
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		Integer res = nbAllUsers % nbUsersPerPage;
		Integer nbNeededPages = (int) nbAllUsers / nbUsersPerPage;
		if (res != 0)
			nbNeededPages++;

		String search = request.getParameter(ATT_SEARCH);
		if (search != null) {
			try {
				users = userDAO.findUsersByNameOrLastnameOrCompany(
						sessionUser.getId(), search);
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
		} else {
			try {
				users = userDAO.findBy("managerId", sessionUser.getId(),
						(offset - 1) * nbUsersPerPage, nbUsersPerPage);
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
		}

		request.setAttribute(ATT_USERS, users);
		request.setAttribute(ATT_SEARCH, search);
		request.setAttribute(ATT_PAGINATION_ACTIVE, offset);
		request.setAttribute(ATT_PAGINATION_TOTAL, nbNeededPages);

		int paginationBegin = 1;
		int paginationEnd = nbNeededPages;

		if (nbNeededPages > Config.PAGINATION_MAX_SIZE)
			paginationEnd = Config.PAGINATION_MAX_SIZE;

		int midPagination = (int) Config.PAGINATION_MAX_SIZE / 2;
		if (Config.PAGINATION_MAX_SIZE % 2 != 0)
			midPagination++;

		if (offset > midPagination) {
			paginationBegin = offset - midPagination + 1;

			if (Config.PAGINATION_MAX_SIZE % 2 != 0)
				paginationBegin++;

			paginationEnd = offset + midPagination;

			if (paginationEnd > nbNeededPages) {
				paginationBegin = nbNeededPages - Config.PAGINATION_MAX_SIZE
						+ 1;
				paginationEnd = nbNeededPages;
			}
		}

		request.setAttribute(ATT_PAGINATION_BEGIN, paginationBegin);
		request.setAttribute(ATT_PAGINATION_END, paginationEnd);

		this.getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User sessionUser = checkSessionUser(request, response);
		checkAdminOnly(sessionUser, response);

		// if false, we deactivate an account, otherwise we re-activate it
		boolean newValueIsActive = false;
		String idUser;

		if (request.getParameter(ATT_ACTIVATE) != null) {
			newValueIsActive = true;
			idUser = request.getParameter(ATT_ACTIVATE);
		} else {
			idUser = request.getParameter(ATT_DEACTIVATE);
		}

		try {
			User user = userDAO.find("id", idUser);
			userDAO.updateIsActive(user, newValueIsActive);
			doGet(request, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}
}
