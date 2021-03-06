package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import common.Config;
import controllers.AbstractController;
import dao.DAOFactory;
import dao.interfaces.AnswerDAO;
import dao.interfaces.QuestionDAO;
import dao.interfaces.QuizDAO;
import dao.interfaces.ThemeDAO;
import models.beans.Quiz;
import models.beans.Theme;
import models.beans.User;
import models.forms.QuizForm;

@WebServlet("/" + Config.URL_CREATE_QUIZ)
public class CreateQuizController extends AbstractController {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CreateQuizController.class);
	private static final String ATT_FORM = "form";
	private static final String ATT_QUIZ = "quiz";
	private static final String ATT_THEMES = "themes";
	private static final String FIELD_SUBMIT = "submit";
	private static final String VIEW_STEP1 = "/WEB-INF/admin_create_quiz_step1.jsp";
	private static final String VIEW_STEP2 = "/WEB-INF/admin_create_quiz_step2.jsp";
	private static final String[] ALLOWED_SUBMIT_PATTERNS = { "newQuestion", "newAnswer_([0-9]+)",
			"deleteQuestion_([0-9]+)", "deleteAnswer_([0-9]+)_fromQuestion_([0-9]+)", "moveUpQuestion_([0-9]+)",
			"moveDownQuestion_([0-9]+)", "moveUpAnswer_([0-9]+)_fromQuestion_([0-9]+)",
			"moveDownAnswer_([0-9]+)_fromQuestion_([0-9]+)", "createQuiz", "confirmQuiz" };
	private static ThemeDAO themeDAO;
	private static QuizDAO quizDAO;
	private static QuestionDAO questionDAO;
	private static AnswerDAO possibleAnswerDAO;

	public static void setDAOs(ThemeDAO themeDAO, QuizDAO quizDAO, QuestionDAO questionDAO,
			AnswerDAO possibleAnswerDAO) {
		CreateQuizController.themeDAO = themeDAO;
		CreateQuizController.quizDAO = quizDAO;
		CreateQuizController.questionDAO = questionDAO;
		CreateQuizController.possibleAnswerDAO = possibleAnswerDAO;
	}

	public void init() throws ServletException {
		DAOFactory daoFactory = (DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY);

		CreateQuizController.setDAOs(daoFactory.getThemeDAO(), daoFactory.getQuizDAO(), daoFactory.getQuestionDAO(),
				daoFactory.getAnswerDAO());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkSessionUser(request, response);

		ArrayList<Theme> themes = new ArrayList<Theme>();

		try {
			themes = themeDAO.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		request.setAttribute(ATT_THEMES, themes);

		this.getServletContext().getRequestDispatcher(VIEW_STEP1).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String submitAction = request.getParameter(FIELD_SUBMIT);

		List<String> tmp = Arrays.asList(ALLOWED_SUBMIT_PATTERNS).stream().filter(s -> Pattern.matches(s, submitAction))
				.collect(Collectors.toList());
		if (tmp.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String submitPattern = tmp.get(0);

		QuizForm createQuizForm = new QuizForm(quizDAO, themeDAO, questionDAO, possibleAnswerDAO);
		Quiz quiz = null;

		switch (submitPattern) {
		case "newQuestion":
			quiz = createQuizForm.newQuestion(request);
			break;
		case "newAnswer_([0-9]+)":
			quiz = createQuizForm.newAnswer(request);
			break;
		case "deleteQuestion_([0-9]+)":
			quiz = createQuizForm.deleteQuestion(request);
			break;
		case "deleteAnswer_([0-9]+)_fromQuestion_([0-9]+)":
			quiz = createQuizForm.deleteAnswerFromQuestion(request);
			break;
		case "moveUpQuestion_([0-9]+)":
			quiz = createQuizForm.moveUpQuestion(request);
			break;
		case "moveDownQuestion_([0-9]+)":
			quiz = createQuizForm.moveDownQuestion(request);
			break;
		case "moveUpAnswer_([0-9]+)_fromQuestion_([0-9]+)":
			quiz = createQuizForm.moveUpAnswerFromQuestion(request);
			break;
		case "moveDownAnswer_([0-9]+)_fromQuestion_([0-9]+)":
			quiz = createQuizForm.moveDownAnswerFromQuestion(request);
			break;
		case "createQuiz":
			quiz = createQuizForm.prepareQuiz(request);
			break;
		case "confirmQuiz":
			User sessionUser = (User) session.getAttribute(Config.ATT_SESSION_USER);
			quiz = (Quiz) session.getAttribute(Config.ATT_SESSION_QUIZ);
			quiz.setCreator(sessionUser);
			createQuizForm.createQuiz(quiz);
			break;
		}

		ArrayList<Theme> themes = new ArrayList<Theme>();
		try {
			themes = themeDAO.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		request.setAttribute(ATT_THEMES, themes);
		request.setAttribute(ATT_FORM, createQuizForm);
		request.setAttribute(ATT_QUIZ, quiz);

		if (submitPattern.equals("createQuiz") && createQuizForm.getErrors().isEmpty()) {
			session.setAttribute(Config.ATT_SESSION_QUIZ, quiz);
			this.getServletContext().getRequestDispatcher(VIEW_STEP2).forward(request, response);
			return;
		}

		if (submitPattern.equals("confirmQuiz") && createQuizForm.getErrors().isEmpty()) {
			response.sendRedirect(request.getServletContext().getContextPath() + "/" + Config.URL_QUIZZES);
			return;
		}

		this.getServletContext().getRequestDispatcher(VIEW_STEP1).forward(request, response);
	}
}