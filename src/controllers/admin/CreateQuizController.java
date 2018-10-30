package controllers.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import common.Config;
import models.beans.E_Role;
import models.beans.User;

@WebServlet( "/"+Config.URL_CREATE_QUIZ )
public class CreateQuizController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String ATT_FORM = "form";
	private static final String ATT_QUIZ = "quiz";
	private static final String VIEW = "/WEB-INF/admin_create_quiz.jsp";
	
	public void init() throws ServletException {
        //this.userDAO = ( (DAOFactory) getServletContext().getAttribute( Config.CONF_DAO_FACTORY ) ).getUserDAO();
	}
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		User sessionUser = (User) session.getAttribute(Config.ATT_SESSION_USER);
		
		if(sessionUser == null || sessionUser.getRole() != E_Role.ADMIN ) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);	
			return;
		}
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
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