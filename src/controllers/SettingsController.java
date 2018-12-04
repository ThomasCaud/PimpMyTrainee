package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Config;
import dao.DAOFactory;
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.User;

@WebServlet(urlPatterns = { "/settings" })
public class SettingsController extends AbstractController {
	private static final long serialVersionUID = 1L;
	private static final String VIEW_TRAINEE = "/WEB-INF/trainee_settings.jsp";

	private UserDAO userDAO;

	public void init() throws ServletException {
		this.userDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getUserDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = checkSessionUser(request, response);
		checkTraineeOnly(user, response);

		if (user != null) {
			if (user.getRole() == E_Role.TRAINEE) {

				this.getServletContext().getRequestDispatcher(VIEW_TRAINEE).forward(request, response);
			}
		}
	}
}