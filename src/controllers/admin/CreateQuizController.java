package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import common.Config;
import dao.interfaces.ThemeDAO;
import dao.DAOFactory;
import models.beans.E_Role;
import models.beans.PossibleAnswer;
import models.beans.Question;
import models.beans.Quiz;
import models.beans.Theme;
import models.beans.User;

@WebServlet( "/"+Config.URL_CREATE_QUIZ )
public class CreateQuizController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String ATT_FORM = "form";
	private static final String ATT_QUIZ = "quiz";
	private static final String ATT_THEMES = "themes";
	private static final String VIEW_STEP1 = "/WEB-INF/admin_create_quiz_step1.jsp";
	private ThemeDAO themeDAO;
	
	public void init() throws ServletException {
        this.themeDAO = ( (DAOFactory) getServletContext().getAttribute( Config.CONF_DAO_FACTORY ) ).getThemeDAO();
	}
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		User sessionUser = (User) session.getAttribute(Config.ATT_SESSION_USER);
		
		if(sessionUser == null || sessionUser.getRole() != E_Role.ADMIN ) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);	
			return;
		}
		
		ArrayList<Theme> themes = themeDAO.findAllThemes();
		
		Quiz quiz = new Quiz();
		quiz.setTitle("L'histoire des Etats-Unis");
		quiz.setTheme(themes.get(3));
		
		Question q1 = new Question();
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
		
		quiz.getQuestions().add(q1);
		
		request.setAttribute(ATT_THEMES, themes);
		request.setAttribute(ATT_QUIZ, quiz);
		
		this.getServletContext().getRequestDispatcher( VIEW_STEP1 ).forward( request, response );
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		/*RegisterUserForm registerUserForm = new RegisterUserForm(userDAO);
		
		User user = null;
		try {
			user = registerUserForm.registerUser(request);
		} catch (EmailException e) {
			e.printStackTrace();
		}
		
		request.setAttribute(ATT_FORM, registerUserForm);
		request.setAttribute(ATT_USER, user);
		
		if( !registerUserForm.getErrors().isEmpty() ) {
			this.getServletContext().getRequestDispatcher( VIEW_STEP1 ).forward( request, response );
			return;
		} else {
			this.getServletContext().getRequestDispatcher( VIEW_STEP2 ).forward( request, response );
			return;
		}*/
	}
}