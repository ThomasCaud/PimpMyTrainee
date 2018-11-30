package controllers;

import java.io.IOException;
import java.sql.Timestamp;
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
import models.beans.Answer;
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
				logger.error("[startQuiz] L'utilisateur " + user.getFirstname() + " " + user.getLastname()
						+ " a déjà un record enregistré pour le Quiz " + quizId);

				HttpSession session = request.getSession();
				UUID contextId = (UUID) session.getAttribute(Config.ATT_SESSION_CONTEXT_ID);

				Record record = records.get(0);
				if (record.getContextId().toString().equals(contextId.toString())) {
					ArrayList<Answer> answers = record.getAnswers();
					Integer step = answers.size() + 1;
					logger.error(
							"[startQuiz] Le record existant pour cet utilisateur appartient à la session actuelle. Redirection de l'utilisateur à la question "
									+ step + " du quiz.");
					response.sendRedirect(
							request.getServletContext().getContextPath() + "/" + Config.URL_RUN_QUIZ + "/" + step);
					return;
				} else {
					logger.error(
							"[startQuiz] Le record existant pour cet utilisateur n'appartient pas à la session actuelle. Bad request.");
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
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
			logger.error("Une erreur interne est survenue : " + e.getMessage());
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

			HttpSession session = request.getSession();
			UUID contextID = (UUID) session.getAttribute(Config.ATT_SESSION_CONTEXT_ID);

			Record record = new Record();
			record.setDuration(0);
			record.setScore(0);
			record.setTrainee(user);
			record.setQuiz(quiz);
			record.setContextId(contextID);
			recordDAO.createRecord(record);

			Timestamp beginningTimestamp = new Timestamp(System.currentTimeMillis());
			session.setAttribute(Config.ATT_SESSION_QUIZ_BEGINNING_TIMESTAMP, beginningTimestamp);

			response.sendRedirect(request.getServletContext().getContextPath() + "/" + Config.URL_RUN_QUIZ);

		} catch (Exception e) {
			logger.error("Une erreur interne est survenue : " + e.getMessage());
		}
	}
}
