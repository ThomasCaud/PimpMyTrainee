package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import common.Config;
import dao.DAOFactory;
import dao.interfaces.QuizDAO;
import dao.interfaces.RecordDAO;
import models.beans.Quiz;
import models.beans.Record;
import models.beans.User;

@WebServlet("/" + Config.URL_RUN_QUIZ + "/*")
public class RunQuizController extends AbstractController {

	private static Logger logger = Logger.getLogger(QuizzesController.class);
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/run_quiz.jsp";
	private static final String VIEW_END = "/WEB-INF/run_end_quiz.jsp";
	private static final String ATT_QUESTION = "question";
	private static final String ATT_QUESTION_NUMBER = "questionNumber";
	private QuizDAO quizDAO;
	private RecordDAO recordDAO;

	public void init() throws ServletException {
		this.quizDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getQuizDAO();
		this.recordDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getRecordDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = checkSessionUser(request, response);
		try {
			Integer quizId = getIntegerFromURL(request, response);

			HttpSession session = request.getSession();
			UUID contextId = (UUID) session.getAttribute(Config.ATT_SESSION_CONTEXT_ID);

			HashMap<String, Object> filters = new HashMap<String, Object>();
			filters.put("quiz", quizId);
			filters.put("trainee", user.getId());
			filters.put("contextId", contextId.toString());
			ArrayList<Record> records = recordDAO.findBy(filters);

			if (records.size() != 1) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			Record record = records.get(0);
			Quiz quiz = quizDAO.find(quizId);
			Integer questionIndex = record.getAnswers().size();

			if (questionIndex >= quiz.getQuestions().size()) {
				// Fin du quizz
				this.getServletContext().getRequestDispatcher(VIEW_END).forward(request, response);
				return;
			}

			request.setAttribute(ATT_QUESTION_NUMBER, questionIndex + 1);
			request.setAttribute(ATT_QUESTION, quiz.getQuestions().get(questionIndex));

			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		} catch (Exception e) {
			logger.error("Une erreur interne est survenue : " + e.getMessage());
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = checkSessionUser(request, response);
	}
}
