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
import dao.DAOFactory;
import dao.interfaces.QuizDAO;
import models.beans.E_Role;
import models.beans.Quiz;
import models.beans.User;

@WebServlet( "/"+Config.URL_QUIZZES )
public class QuizzesController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/admin_quizzes_management.jsp";
	private static final String ATT_SEARCH = "search";
	private static final String ATT_QUIZZES = "quizzes";
	private static final String ATT_PAGINATION_ACTIVE = "paginationActive";
	private static final String ATT_PAGINATION_TOTAL = "paginationTotal";
	private QuizDAO quizDAO;

	public void init() throws ServletException {
		this.quizDAO = ( (DAOFactory) getServletContext().getAttribute( Config.CONF_DAO_FACTORY ) ).getQuizDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		HttpSession session = request.getSession();

		User sessionUser = (User) session.getAttribute(Config.ATT_SESSION_USER);
		
		if(sessionUser == null || sessionUser.getRole() != E_Role.ADMIN ) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);	
			return;
		}
  
		ArrayList<Quiz> quizzes = null;

		Integer offset = 1;
		String offsetUrl = request.getParameter("p");
		
		if( offsetUrl != null) {
			try {
				offset = Integer.parseInt(offsetUrl);
				
				if( offset <= 0 ) throw new NumberFormatException();
			} catch(NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}
		
		Integer nbAllQuizzes = quizDAO.countAllQuizzes();
		Integer nbQuizzesPerPage = Config.NB_QUIZZES_PER_PAGE;
		
		Integer res = nbAllQuizzes % nbQuizzesPerPage;
		Integer nbNeededPages = (int) nbAllQuizzes / nbQuizzesPerPage;
		if( res != 0 ) nbNeededPages++;
    
		String search = request.getParameter(ATT_SEARCH);
    	if(search != null) {
    		// TODO
    		// quizzes = quizDAO.findQuizzesByTRUCMUCHE(search);
		} else {
			quizzes = quizDAO.findAllQuizzes((offset-1)*Config.NB_QUIZZES_PER_PAGE,nbQuizzesPerPage);
		}
		
		request.setAttribute(ATT_QUIZZES, quizzes);
		request.setAttribute(ATT_SEARCH, search);
		request.setAttribute(ATT_PAGINATION_ACTIVE, offset);
		request.setAttribute(ATT_PAGINATION_TOTAL, nbNeededPages);
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}
}
