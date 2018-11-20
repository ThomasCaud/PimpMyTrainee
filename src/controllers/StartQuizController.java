package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import common.Config;
import dao.DAOFactory;
import dao.interfaces.QuizDAO;
import dao.interfaces.RecordDAO;
import models.beans.Quiz;
import models.beans.Record;
import models.beans.User;

@WebServlet("/" + Config.URL_START_QUIZ + "/*")
public class StartQuizController extends AbstractController {

	private static Logger logger = Logger.getLogger(QuizzesController.class);
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/start_quiz.jsp";
	private static final String ATT_QUIZ = "quiz";
	private QuizDAO quizDAO;
	private RecordDAO recordDAO;
	private String step;

	public void init() throws ServletException {
		this.quizDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getQuizDAO();
		this.recordDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getRecordDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = checkSessionUser(request, response);
		try {
			Integer quizId = getIntegerFromURL(request, response);

			HashMap<String, Object> filters = new HashMap<String, Object>();
			filters.put("quiz", quizId);
			filters.put("trainee", user.getId());
			ArrayList<Record> records = recordDAO.findBy(filters);

			if (records.size() != 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			filters = new HashMap<String, Object>();
			filters.put("creator", user.getManager().getId());
			filters.put("id", quizId);
			ArrayList<Quiz> quizzes = quizDAO.findBy(filters);

			if (quizzes.size() != 1) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			Quiz quiz = quizzes.get(0);
			request.setAttribute(ATT_QUIZ, quiz);

			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		} catch (Exception e) {
			logger.error("Un entier n'a pas pu être extrait de la requête : " + request.getPathInfo());
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = checkSessionUser(request, response);
		try {
			Integer quizId = getIntegerFromURL(request, response);

			HashMap<String, Object> filters = new HashMap<String, Object>();
			filters.put("creator", user.getManager().getId());
			filters.put("id", quizId);
			ArrayList<Quiz> quizzes = quizDAO.findBy(filters);

			if (quizzes == null || quizzes.size() != 1) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			Quiz quiz = quizzes.get(0);

			request.setAttribute(ATT_QUIZ, quiz);

			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		} catch (Exception e) {
			logger.error("Un entier n'a pas pu être extrait de la requête : " + request.getPathInfo());
		}
	}
}
