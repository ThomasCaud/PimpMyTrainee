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
import dao.exceptions.DAOException;
import dao.interfaces.QuizDAO;
import dao.interfaces.RecordAnswerDAO;
import dao.interfaces.RecordDAO;
import models.beans.Answer;
import models.beans.Question;
import models.beans.Quiz;
import models.beans.Record;
import models.forms.RecordAnswerForm;

@WebServlet("/" + Config.URL_RUN_QUIZ)
public class RunQuizController extends AbstractController {

	private static Logger logger = Logger.getLogger(RunQuizController.class);
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/run_quiz.jsp";
	private static final String VIEW_END = "/WEB-INF/run_end_quiz.jsp";
	private static final String ATT_QUESTION = "question";
	private static final String ATT_QUESTION_NUMBER = "questionNumber";
	private QuizDAO quizDAO;
	private RecordDAO recordDAO;
	private RecordAnswerDAO recordAnswerDAO;

	public void init() throws ServletException {
		this.quizDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getQuizDAO();
		this.recordDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getRecordDAO();
		this.recordAnswerDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getRecordAnswerDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();

			UUID contextId;
			try {
				contextId = (UUID) session.getAttribute(Config.ATT_SESSION_CONTEXT_ID);
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			HashMap<String, Object> filters = new HashMap<String, Object>();
			filters.put("contextId", contextId.toString());
			ArrayList<Record> records = recordDAO.findBy(filters);

			if (records.size() != 1) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			Record record = records.get(0);
			Quiz quiz = quizDAO.find(record.getQuiz().getId());
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
		// The answerIndex is set by a javascript script in the JSP, when the user
		// choose an answer.
		String answerIndexStr = request.getParameter("quizAnswerIndex");
		Integer answerIndex = -1;
		try {
			answerIndex = Integer.parseInt(answerIndexStr);
		} catch (Exception e) {
			logger.error(
					"Une erreur interne est survenue lors de la conversion en entier de la valeur : " + answerIndexStr);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

		UUID contextId;
		try {
			contextId = (UUID) request.getSession().getAttribute(Config.ATT_SESSION_CONTEXT_ID);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		HashMap<String, Object> filters = new HashMap<String, Object>();
		filters.put("contextId", contextId.toString());
		ArrayList<Record> records = recordDAO.findBy(filters);

		if (records.size() != 1) {
			logger.error("Trying to answer to a quiz associated to the contextId " + contextId
					+ " whereas there is/are " + records.size() + " record(s)");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		Record record = records.get(0);
		Quiz quiz = quizDAO.find(record.getQuiz().getId());
		Integer questionIndex = record.getAnswers().size();
		Question question = quiz.getQuestions().get(questionIndex);

		if (answerIndex < 0 || answerIndex >= question.getPossibleAnswers().size()) {
			logger.error("Trying to answer with the index " + answerIndex + " whereas there is/are "
					+ question.getPossibleAnswers().size() + " possible answer(s)");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		Answer answerToRecord = question.getPossibleAnswers().get(answerIndex);

		RecordAnswerForm recordAnswerForm = new RecordAnswerForm(this.recordAnswerDAO);

		try {
			recordAnswerForm.recordAnAnswer(record, answerToRecord);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		response.sendRedirect(request.getServletContext().getContextPath() + "/" + Config.URL_RUN_QUIZ);
	}
}
