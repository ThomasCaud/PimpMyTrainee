package controllers;

import static java.lang.Math.toIntExact;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private static QuizDAO quizDAO;
	private static RecordDAO recordDAO;
	private static RecordAnswerDAO recordAnswerDAO;

	public void init() throws ServletException {
		RunQuizController.quizDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getQuizDAO();
		RunQuizController.recordDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getRecordDAO();
		RunQuizController.recordAnswerDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getRecordAnswerDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// The quiz & the question are determined by what is in the session, thanks to
			// the contextID
			Record record = getRecordFromSessionContextId(request, response);
			Quiz quiz = quizDAO.find(record.getQuiz().getId());
			Integer questionIndex = record.getAnswers().size();

			// If the user has answered all question of the quiz, then the quiz is finished.
			if (questionIndex >= quiz.getQuestions().size()) {
				this.getServletContext().getRequestDispatcher(VIEW_END).forward(request, response);
				return;
			}

			// Prepare the request for the dispatch
			request.setAttribute(ATT_QUESTION_NUMBER, questionIndex + 1);
			request.setAttribute(ATT_QUESTION, quiz.getQuestions().get(questionIndex));
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		} catch (Exception e) {
			logger.error("Une erreur interne est survenue : " + e.getMessage());
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * The answerIndex is set by a javascript script in the JSP, when the user
		 * choose an answer. This allows to know what the user has answered to a
		 * question that is determined by the sessionContextId.
		 */
		String answerIndexStr = request.getParameter("quizAnswerIndex");
		Integer answerIndex = -1;
		try {
			answerIndex = Integer.parseInt(answerIndexStr);
		} catch (Exception e) {
			logger.error("An error occured while parsing " + answerIndexStr + " to integer : " + e.getMessage());
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

		/*
		 * Fetching every object needed to process the score, thanks to the
		 * sessionContextId
		 */
		Record record = getRecordFromSessionContextId(request, response);
		Integer questionAnsweredIndex = record.getAnswers().size();
		Quiz quiz = quizDAO.find(record.getQuiz().getId());
		Question questionAnswered = quiz.getQuestions().get(questionAnsweredIndex);
		Answer answerToRecord = questionAnswered.getPossibleAnswers().get(answerIndex);

		// Calculating the time elapsed since the beginning of the test
		Timestamp beginningTimestamp = (Timestamp) request.getSession()
				.getAttribute(Config.ATT_SESSION_QUIZ_BEGINNING_TIMESTAMP);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		long timeElapsedSinceQuizBeginning = (long) ((now.getTime() - beginningTimestamp.getTime()) * 0.001);

		// Determining whether this is a correct or a wrong answer
		boolean isCorrectAnswer = false;
		if (questionAnswered.getCorrectAnswer().getId() == answerToRecord.getId())
			isCorrectAnswer = true;

		// Updating the attributes of the record
		Integer newDuration = toIntExact(timeElapsedSinceQuizBeginning);
		record.setDuration(newDuration);
		if (isCorrectAnswer)
			record.setScore(record.getScore() + 1);

		// Updating the database with the previous calculated results
		RecordAnswerForm recordAnswerForm = new RecordAnswerForm(RunQuizController.recordAnswerDAO,
				RunQuizController.recordDAO);
		try {
			recordAnswerForm.recordAnAnswer(record, answerToRecord);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		/*
		 * Refreshing the page, in order to prepare the next question to display to the
		 * user
		 */
		response.sendRedirect(request.getServletContext().getContextPath() + "/" + Config.URL_RUN_QUIZ);
	}

	/*
	 * Common function that allows to fetch a record, regarding a specific contextId
	 * that is stored in session. It's important that only one record is fetched ;
	 * otherwise, an error is thrown
	 */
	Record getRecordFromSessionContextId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UUID contextId;
		try {
			contextId = (UUID) request.getSession().getAttribute(Config.ATT_SESSION_CONTEXT_ID);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		HashMap<String, Object> filters = new HashMap<String, Object>();
		filters.put("contextId", contextId.toString());
		ArrayList<Record> records = recordDAO.findBy(filters);

		if (records.size() != 1) {
			logger.error("Trying to answer to a quiz associated to the contextId " + contextId
					+ " whereas there is/are " + records.size() + " record(s)");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		return records.get(0);
	}
}
