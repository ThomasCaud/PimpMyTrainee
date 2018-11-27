package controllers;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Config;
import dao.DAOFactory;
import dao.interfaces.UserDAO;
import models.beans.User;
import models.forms.LoginForm;

@WebServlet("/" + Config.URL_LOGIN)
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String ATT_FORM = "form";
	private static final String ATT_USER = "user";
	private static final String VIEW = "/WEB-INF/login.jsp";
	private UserDAO userDAO;

	public void init() throws ServletException {
		this.userDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getUserDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginForm loginForm = new LoginForm(userDAO);

		User user = loginForm.connectUser(request);

		request.setAttribute(ATT_FORM, loginForm);
		request.setAttribute(ATT_USER, user);

		if (!loginForm.getErrors().isEmpty()) {
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
			return;
		}

		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();
		session.setAttribute(Config.ATT_SESSION_USER, user);
		session.setAttribute(Config.ATT_SESSION_CONTEXT_ID, UUID.randomUUID());
		response.sendRedirect(request.getServletContext().getContextPath() + Config.URL_ROOT);
	}

}
