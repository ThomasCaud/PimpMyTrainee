package utils;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import common.Config;
import dao.interfaces.QuizDAO;
import models.beans.E_Role;
import models.beans.Quiz;
import models.beans.User;

public class QuizzesManager {
	private static Logger logger = Logger.getLogger(QuizzesManager.class);

	private static final String ATT_SEARCH = "search";
	private static final String ATT_QUIZZES = "quizzes";
	private static final String ATT_PAGINATION_ACTIVE = "paginationActive";
	private static final String ATT_PAGINATION_TOTAL = "paginationTotal";

	private QuizDAO quizDAO;

	public QuizzesManager(QuizDAO dao) {
		this.quizDAO = dao;
	}

	public int getCount(User user) {
		return user.getRole() == E_Role.ADMIN ? quizDAO.count() : quizDAO.count("creator", user.getManager().getId());
	}

	public ArrayList<Quiz> findWithOffsetLimit(User user, int offset, int limit) {
		if (user.getRole() == E_Role.ADMIN) {
			return getQuizDAO().findAll((offset - 1) * limit, limit);
		} else if (user.getRole() == E_Role.TRAINEE) {
			return getQuizDAO().findBy("creator", user.getManager().getId(), (offset - 1) * limit, limit);
		} else {
			logger.warn("L'utilisateur courant n'est pas Admin ni Trainee");
			return new ArrayList<Quiz>();
		}
	}

	public ArrayList<Quiz> searchQuizzes(User user, String search) {
		if (user.getRole() == E_Role.ADMIN) {
			return getQuizDAO().searchQuizzes(search);
		} else if (user.getRole() == E_Role.TRAINEE) {
			return getQuizDAO().searchAvailableQuizzes(user, search);
		} else {
			logger.warn("L'utilisateur courant n'est pas Admin ni Trainee");
			return new ArrayList<Quiz>();
		}
	}

	public int getNbNeededPage(User user, int nbQuizzesPerPage) {
		Integer nbAllQuizzes = getCount(user);

		Integer res = nbAllQuizzes % nbQuizzesPerPage;
		Integer nbNeededPages = (int) nbAllQuizzes / nbQuizzesPerPage;
		if (res != 0)
			nbNeededPages++;

		return nbNeededPages;
	}

	public void setSearchAndQuizzesInRequest(HttpServletRequest request, User user, int offset, int nbQuizzesPerPage) {
		String search = request.getParameter(ATT_SEARCH);
		ArrayList<Quiz> quizzes = null;

		if (search != null) {
			quizzes = searchQuizzes(user, search);
		} else {
			quizzes = findWithOffsetLimit(user, offset, nbQuizzesPerPage);
		}

		request.setAttribute(ATT_QUIZZES, quizzes);
		request.setAttribute(ATT_SEARCH, search);
	}

	public int getOffset(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer offset = 1;
		String offsetUrl = request.getParameter("p");

		if (offsetUrl != null) {
			try {
				offset = Integer.parseInt(offsetUrl);

				if (offset <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		return offset;
	}

	public int getNbQuizzesPerPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String nbQuizzesPerPageUrl = request.getParameter("n");
		Integer nbQuizzesPerPage = Config.NB_QUIZZES_PER_PAGE;

		if (nbQuizzesPerPageUrl != null) {
			try {
				nbQuizzesPerPage = Integer.parseInt(nbQuizzesPerPageUrl);

				if (nbQuizzesPerPage <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		return nbQuizzesPerPage;
	}

	public void setSearchAndPaginationData(HttpServletRequest request, HttpServletResponse response, User user)
			throws IOException {
		Integer offset = getOffset(request, response);
		Integer nbQuizzesPerPage = getNbQuizzesPerPage(request, response);
		Integer nbNeededPages = getNbNeededPage(user, nbQuizzesPerPage);

		setSearchAndQuizzesInRequest(request, user, offset, nbNeededPages);
		request.setAttribute(ATT_PAGINATION_ACTIVE, offset);
		request.setAttribute(ATT_PAGINATION_TOTAL, nbNeededPages);
	}

	public QuizDAO getQuizDAO() {
		return quizDAO;
	}

	public void setQuizDAO(QuizDAO quizDAO) {
		this.quizDAO = quizDAO;
	}
}
