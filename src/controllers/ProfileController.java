package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Config;
import dao.DAOFactory;
import dao.interfaces.UserDAO;
import models.beans.User;
import models.forms.UserForm;

@WebServlet("/" + Config.URL_MY_PROFILE)
public class ProfileController extends AbstractController {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/profile.jsp";
	private static final String ATT_FORM = "form";
	private static final String ATT_USER = "user";
	private static UserDAO userDAO;

	public void init() throws ServletException {
		this.userDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getUserDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User sessionUser = checkSessionUser(request, response);

		request.setAttribute(ATT_USER, sessionUser);

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserForm userForm = new UserForm(userDAO);

		User sessionUser = checkSessionUser(request, response);
		request.setAttribute(ATT_USER, sessionUser);

		userForm.changePassword(request, sessionUser);
		request.setAttribute(ATT_FORM, userForm);

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
