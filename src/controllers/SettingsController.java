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
import models.forms.TraineeSettingsForm;

@WebServlet(urlPatterns = { "/settings" })
public class SettingsController extends AbstractController {
	private static final long serialVersionUID = 1L;
	private static final String VIEW_TRAINEE = "/WEB-INF/trainee_settings.jsp";
	private static final String ATT_USER = "user";
	private static final String ATT_FORM = "form";

	private static UserDAO userDAO;

	public void init() throws ServletException {
		SettingsController.userDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getUserDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = checkSessionUser(request, response);
		checkTraineeOnly(user, response);

		User updatedUser = userDAO.find(user.getId());
		request.setAttribute(ATT_USER, updatedUser);
		request.setAttribute(Config.ATT_SESSION_USER, updatedUser);

		this.getServletContext().getRequestDispatcher(VIEW_TRAINEE).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = checkSessionUser(request, response);
		checkTraineeOnly(user, response);

		TraineeSettingsForm updateUserForm = new TraineeSettingsForm(userDAO);
		User updatedUser = updateUserForm.updateUser(request, user);

		request.setAttribute(ATT_FORM, updateUserForm);
		request.setAttribute(ATT_USER, updatedUser);

		this.getServletContext().getRequestDispatcher(VIEW_TRAINEE).forward(request, response);
	}
}