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

@WebServlet("/" + Config.URL_VIEW_QUIZ + "/*")
public class ViewQuizController extends AbstractController {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/admin_view_quiz.jsp";
	private static final String ATT_QUIZ = "quiz";
	private static final String ATT_THEMES = "themes";
	private static final String ATT_FORM = "form";
	private static final String FIELD_SUBMIT = "submit";
	private static final String[] ALLOWED_SUBMIT_PATTERNS = { "newQuestion", "newAnswer_([0-9]+)",
			"deleteQuestion_([0-9]+)", "deleteAnswer_([0-9]+)_fromQuestion_([0-9]+)", "moveUpQuestion_([0-9]+)",
			"moveDownQuestion_([0-9]+)", "moveUpAnswer_([0-9]+)_fromQuestion_([0-9]+)",
			"moveDownAnswer_([0-9]+)_fromQuestion_([0-9]+)", "updateQuiz" };
	private static QuizDAO quizDAO;
	private static ThemeDAO themeDAO;
	private static QuestionDAO questionDAO;
	private static AnswerDAO answerDAO;

	public void init() throws ServletException {
		ViewQuizController.quizDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getQuizDAO();
		ViewQuizController.themeDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getThemeDAO();
		ViewQuizController.questionDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getQuestionDAO();
		ViewQuizController.answerDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getAnswerDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User sessionUser = checkSessionUser(request, response);
		checkAdminOnly(sessionUser, response);

		String pathInfo = request.getPathInfo();

		if (pathInfo == null || pathInfo.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String urlId = request.getPathInfo().substring(1, request.getPathInfo().length());
		Integer id;

		try {
			id = Integer.parseInt(urlId);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		Quiz quiz = quizDAO.find(id);

		if (quiz == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		ArrayList<Theme> themes = themeDAO.findAll();

		request.setAttribute(ATT_THEMES, themes);
		request.setAttribute(ATT_QUIZ, quiz);

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submitAction = request.getParameter(FIELD_SUBMIT);

		List<String> tmp = Arrays.asList(ALLOWED_SUBMIT_PATTERNS).stream().filter(s -> Pattern.matches(s, submitAction))
				.collect(Collectors.toList());
		if (tmp.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String submitPattern = tmp.get(0);
		QuizForm createQuizForm = new QuizForm(quizDAO, themeDAO, questionDAO, answerDAO);
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
		case "updateQuiz":
			quiz = createQuizForm.updateQuiz(request);
			break;
		}

		ArrayList<Theme> themes = themeDAO.findAll();

		request.setAttribute(ATT_THEMES, themes);
		request.setAttribute(ATT_FORM, createQuizForm);
		request.setAttribute(ATT_QUIZ, quiz);

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

}
