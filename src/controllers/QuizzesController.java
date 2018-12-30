package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import common.Config;
import dao.DAOFactory;
import models.beans.E_Role;
import models.beans.Quiz;
import models.beans.User;
import utils.QuizzesManager;

@WebServlet("/" + Config.URL_QUIZZES)
public class QuizzesController extends AbstractController {

	private static Logger logger = Logger.getLogger(QuizzesController.class);
	private static final long serialVersionUID = 1L;
	private static final String VIEW_ADMIN = "/WEB-INF/admin_quizzes_management.jsp";
	private static final String VIEW_TRAINEE = "/WEB-INF/trainee_quizzes.jsp";
	private static final String ATT_ACTIVATE = "activate";
	private static final String ATT_DEACTIVATE = "deactivate";
	private static QuizzesManager quizzesManager;

	public static void setManagers(QuizzesManager quizzesManager) {
		QuizzesController.quizzesManager = quizzesManager;
	}

	public void init() throws ServletException {
		QuizzesController.setManagers(new QuizzesManager(
				((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getQuizDAO()));
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User sessionUser = checkSessionUser(request, response);

		quizzesManager.setSearchAndPaginationData(request, response, sessionUser);

		String view = sessionUser.getRole() == E_Role.ADMIN ? VIEW_ADMIN : VIEW_TRAINEE;

		this.getServletContext().getRequestDispatcher(view).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User sessionUser = checkSessionUser(request, response);
		checkAdminOnly(sessionUser, response);

		// if false, we deactivate an account, otherwise we re-activate it
		boolean newValueIsActive = false;
		String idQuiz;

		if (request.getParameter(ATT_ACTIVATE) != null) {
			newValueIsActive = true;
			idQuiz = request.getParameter(ATT_ACTIVATE);
		} else {
			idQuiz = request.getParameter(ATT_DEACTIVATE);
		}

		try {
			Quiz quiz = quizzesManager.getQuizDAO().find("id", idQuiz);
			quizzesManager.getQuizDAO().updateIsActive(quiz, newValueIsActive);
			doGet(request, response);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
