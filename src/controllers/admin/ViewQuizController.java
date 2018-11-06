package controllers.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Config;
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.User;

@WebServlet("/" + Config.URL_VIEW_QUIZ + "/*")
public class ViewQuizController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/admin_view_quiz.jsp";
    private static final String ATT_QUIZ = "quiz";
    private static final String ATT_ID = "id";
    private static final String ATT_FORM = "form";
    private UserDAO userDAO;

    public void init() throws ServletException {
	// TODO
	// this.userDAO = ( (DAOFactory) getServletContext().getAttribute(
	// Config.CONF_DAO_FACTORY ) ).getQuizDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();

	User sessionUser = (User) session.getAttribute(Config.ATT_SESSION_USER);

	if (sessionUser == null || sessionUser.getRole() != E_Role.ADMIN) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}

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

	// TODO
	/*
	 * Quiz quiz = quizDAO.findQuizByID(id);
	 * 
	 * if( quiz == null ) { response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	 * return; }
	 * 
	 * request.setAttribute(ATT_USER, quiz);
	 */

	this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	/*
	 * UpdateQuizForm updateUserForm = new UpdateUserForm(userDAO);
	 * 
	 * String urlId = request.getPathInfo().substring(1,
	 * request.getPathInfo().length()); Integer id;
	 * 
	 * try { id = Integer.parseInt(urlId); } catch(NumberFormatException e) {
	 * response.sendError(HttpServletResponse.SC_BAD_REQUEST); return; }
	 * 
	 * request.setAttribute(ATT_ID, id);
	 * 
	 * User updatedUser = updateUserForm.updateUser(request);
	 * 
	 * request.setAttribute(ATT_FORM, updateUserForm);
	 * request.setAttribute(ATT_USER, updatedUser);
	 * 
	 * this.getServletContext().getRequestDispatcher(VIEW).forward(request,
	 * response); return;
	 */
    }

}
