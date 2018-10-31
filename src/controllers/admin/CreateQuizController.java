package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import common.Config;
import dao.interfaces.QuizDAO;
import dao.interfaces.ThemeDAO;
import dao.DAOFactory;
import models.beans.E_Role;
import models.beans.PossibleAnswer;
import models.beans.Question;
import models.beans.Quiz;
import models.beans.Theme;
import models.beans.User;
import models.forms.CreateQuizForm;

@WebServlet( "/"+Config.URL_CREATE_QUIZ )
public class CreateQuizController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String ATT_FORM = "form";
	private static final String ATT_QUIZ = "quiz";
	private static final String ATT_THEMES = "themes";
	private static final String FIELD_SUBMIT = "submit";
	private static final String VIEW_STEP1 = "/WEB-INF/admin_create_quiz_step1.jsp";
	private static final String[] ALLOWED_SUBMIT_PATTERNS = {"newQuestion","newAnswer_([0-9]+)"};
	private ThemeDAO themeDAO;
	private QuizDAO quizDAO;
	
	public void init() throws ServletException {
        this.themeDAO = ( (DAOFactory) getServletContext().getAttribute( Config.CONF_DAO_FACTORY ) ).getThemeDAO();
        this.quizDAO = ( (DAOFactory) getServletContext().getAttribute( Config.CONF_DAO_FACTORY ) ).getQuizDAO();
	}
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		User sessionUser = (User) session.getAttribute(Config.ATT_SESSION_USER);
		
		if(sessionUser == null || sessionUser.getRole() != E_Role.ADMIN ) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);	
			return;
		}
		
		ArrayList<Theme> themes = themeDAO.findAllThemes();
		
		request.setAttribute(ATT_THEMES, themes);
		
		this.getServletContext().getRequestDispatcher( VIEW_STEP1 ).forward( request, response );
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		String submitAction = request.getParameter(FIELD_SUBMIT);
		
		List<String> tmp = Arrays.asList(ALLOWED_SUBMIT_PATTERNS).stream().filter(s -> Pattern.matches(s, submitAction)).collect(Collectors.toList());
		if(tmp.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String submitPattern = tmp.get(0);

		CreateQuizForm createQuizForm = new CreateQuizForm(quizDAO,themeDAO);
		Quiz quiz = null;
		
		switch( submitPattern ) {
			case "newQuestion" :
				quiz = createQuizForm.newQuestion(request);
				break;
			case "newAnswer_([0-9]+)" :
				quiz = createQuizForm.newAnswer(request);
				break;
		}
		
		ArrayList<Theme> themes = themeDAO.findAllThemes();
		
		request.setAttribute(ATT_THEMES, themes);
		request.setAttribute(ATT_FORM, createQuizForm);
		request.setAttribute(ATT_QUIZ, quiz);
		
		this.getServletContext().getRequestDispatcher( VIEW_STEP1 ).forward( request, response );
		return;
	}
}