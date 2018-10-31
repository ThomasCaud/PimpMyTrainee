package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
	private static final String[] CORRECT_SUBMIT_VALUES = {"newQuestion"};
	private static final String VIEW_STEP1 = "/WEB-INF/admin_create_quiz_step1.jsp";
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
		
		/*Quiz quiz = new Quiz();
		quiz.setTitle("L'histoire des Etats-Unis");
		quiz.setTheme(themes.get(3));
		
		Question q1 = new Question();
		q1.setId(1);
		q1.setLabel("Comment s'appelle l'actuel président des Etats-Unis ?");
		
		PossibleAnswer p1 = new PossibleAnswer();
		p1.setLabel("Nicolas Sarkozy");
		PossibleAnswer p2 = new PossibleAnswer();
		p2.setLabel("Barack Obama");
		PossibleAnswer p3 = new PossibleAnswer();
		p3.setLabel("Donald Trump");
		p3.setIsCorrect(true);
		PossibleAnswer p4 = new PossibleAnswer();
		p4.setLabel("Georges W. Bush");
		
		q1.getPossibleAnswers().add(p1);
		q1.getPossibleAnswers().add(p2);
		q1.getPossibleAnswers().add(p3);
		q1.getPossibleAnswers().add(p4);
		
		Question q2 = new Question();
		q2.setId(2);
		q2.setLabel("Comment d'états composent les Etats-Unis ?");
		
		PossibleAnswer p5 = new PossibleAnswer();
		p5.setLabel("49");
		PossibleAnswer p6 = new PossibleAnswer();
		p6.setLabel("50");
		p6.setIsCorrect(true);
		PossibleAnswer p7 = new PossibleAnswer();
		p7.setLabel("51");
		
		q2.getPossibleAnswers().add(p5);
		q2.getPossibleAnswers().add(p6);
		q2.getPossibleAnswers().add(p7);
		
		quiz.getQuestions().add(q1);
		quiz.getQuestions().add(q2);*/
		
		request.setAttribute(ATT_THEMES, themes);
		//request.setAttribute(ATT_QUIZ, quiz);
		
		this.getServletContext().getRequestDispatcher( VIEW_STEP1 ).forward( request, response );
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		String submitAction = request.getParameter(FIELD_SUBMIT);
		if( !Arrays.asList(CORRECT_SUBMIT_VALUES).contains(submitAction) ) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		CreateQuizForm createQuizForm = new CreateQuizForm(quizDAO,themeDAO);
		Quiz quiz = null;
		
		switch( submitAction ) {
			case "newQuestion" :
				quiz = createQuizForm.newQuestion(request);
		}
		
		ArrayList<Theme> themes = themeDAO.findAllThemes();
		
		request.setAttribute(ATT_THEMES, themes);
		request.setAttribute(ATT_FORM, createQuizForm);
		request.setAttribute(ATT_QUIZ, quiz);
		
		this.getServletContext().getRequestDispatcher( VIEW_STEP1 ).forward( request, response );
		return;
	}
}