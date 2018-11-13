package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import common.Config;
import dao.DAOFactory;
import dao.interfaces.QuizDAO;
import models.beans.E_Role;
import models.beans.Quiz;
import models.beans.User;

@WebServlet("/" + Config.URL_QUIZZES)
public class QuizzesController extends AbstractController {

	private static Logger logger = Logger.getLogger(QuizzesController.class);

	private static final long serialVersionUID = 1L;
	private static final String VIEW_ADMIN = "/WEB-INF/admin_quizzes_management.jsp";
	private static final String VIEW_TRAINEE = "/WEB-INF/trainee_quizzes.jsp";
	private static final String ATT_SEARCH = "search";
	private static final String ATT_QUIZZES = "quizzes";
	private static final String ATT_PAGINATION_ACTIVE = "paginationActive";
	private static final String ATT_PAGINATION_TOTAL = "paginationTotal";
	private QuizDAO quizDAO;

	public void init() throws ServletException {
		this.quizDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getQuizDAO();
	}

	private int getCount(User user) {
		return user.getRole() == E_Role.ADMIN ? quizDAO.count() : quizDAO.count("creator", user.getManager().getId());
	}

	private ArrayList<Quiz> findWithOffsetLimit(User user, int offset, int limit) {
		if (user.getRole() == E_Role.ADMIN) {
			return quizDAO.findAll((offset - 1) * limit, limit);
		} else if (user.getRole() == E_Role.TRAINEE) {
			return quizDAO.findBy("creator", user.getManager().getId(), (offset - 1) * limit, limit);
		} else {
			logger.warn("L'utilisateur courant n'est pas Admin ni Trainee");
			return new ArrayList<Quiz>();
		}
	}

	private ArrayList<Quiz> searchQuizzes(User user, String search) {
		if (user.getRole() == E_Role.ADMIN) {
			return quizDAO.searchQuizzes(search);
		} else if (user.getRole() == E_Role.TRAINEE) {
			return quizDAO.searchQuizzes(user.getManager().getId(), search);
		} else {
			logger.warn("L'utilisateur courant n'est pas Admin ni Trainee");
			return new ArrayList<Quiz>();
		}
	}

	private int getNbNeededPage(User user, int nbQuizzesPerPage) {
		Integer nbAllQuizzes = getCount(user);

		Integer res = nbAllQuizzes % nbQuizzesPerPage;
		Integer nbNeededPages = (int) nbAllQuizzes / nbQuizzesPerPage;
		if (res != 0)
			nbNeededPages++;

		return nbNeededPages;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User sessionUser = checkSessionUser(request, response);

		ArrayList<Quiz> quizzes = null;

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

		Integer nbQuizzesPerPage = Config.NB_QUIZZES_PER_PAGE;
		String nbQuizzesPerPageUrl = request.getParameter("n");

		if (nbQuizzesPerPageUrl != null) {
			try {
				nbQuizzesPerPage = Integer.parseInt(nbQuizzesPerPageUrl);

				if (nbQuizzesPerPage <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}

		Integer nbNeededPages = getNbNeededPage(sessionUser, nbQuizzesPerPage);

		String search = request.getParameter(ATT_SEARCH);

		if (search != null) {
			quizzes = searchQuizzes(sessionUser, search);
		} else {
			quizzes = findWithOffsetLimit(sessionUser, offset, nbQuizzesPerPage);
		}

		request.setAttribute(ATT_QUIZZES, quizzes);
		request.setAttribute(ATT_SEARCH, search);
		request.setAttribute(ATT_PAGINATION_ACTIVE, offset);
		request.setAttribute(ATT_PAGINATION_TOTAL, nbNeededPages);

		String view = sessionUser.getRole() == E_Role.ADMIN ? VIEW_ADMIN : VIEW_TRAINEE;

		this.getServletContext().getRequestDispatcher(view).forward(request, response);
	}
}
