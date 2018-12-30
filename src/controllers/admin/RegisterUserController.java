package controllers.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;

import common.Config;
import controllers.AbstractController;
import dao.DAOFactory;
import dao.interfaces.UserDAO;
import models.beans.User;
import models.forms.RegisterUserForm;

@WebServlet("/" + Config.URL_REGISTER_USER)
public class RegisterUserController extends AbstractController {

	private static final long serialVersionUID = 1L;
	private static final String ATT_FORM = "form";
	private static final String ATT_USER = "user";
	private static final String VIEW_STEP1 = "/WEB-INF/admin_register_user_step1.jsp";
	private static final String VIEW_STEP2 = "/WEB-INF/admin_register_user_step2.jsp";
	private static UserDAO userDAO;

	public void init() throws ServletException {
		RegisterUserController.userDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getUserDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User sessionUser = checkSessionUser(request, response);
		checkAdminOnly(sessionUser, response);

		this.getServletContext().getRequestDispatcher(VIEW_STEP1).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RegisterUserForm registerUserForm = new RegisterUserForm(userDAO);

		User user = null;
		HttpSession session = request.getSession();
		User creator = (User) session.getAttribute(Config.ATT_SESSION_USER);

		try {
			user = registerUserForm.registerUser(request, creator);
		} catch (EmailException e) {
			e.printStackTrace();
		}

		request.setAttribute(ATT_FORM, registerUserForm);
		request.setAttribute(ATT_USER, user);

		if (!registerUserForm.getErrors().isEmpty()) {
			this.getServletContext().getRequestDispatcher(VIEW_STEP1).forward(request, response);
			return;
		} else {
			this.getServletContext().getRequestDispatcher(VIEW_STEP2).forward(request, response);
			return;
		}
	}
}