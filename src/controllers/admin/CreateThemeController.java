package controllers.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Config;
import controllers.AbstractController;
import dao.DAOFactory;
import dao.interfaces.ThemeDAO;
import models.beans.Theme;
import models.beans.User;
import models.forms.CreateThemeForm;

@WebServlet("/" + Config.URL_CREATE_THEME)
public class CreateThemeController extends AbstractController {

	private static final long serialVersionUID = 1L;
	private static final String ATT_FORM = "form";
	private static final String ATT_THEME = "theme";
	private static final String VIEW = "/WEB-INF/admin_create_theme.jsp";
	private static ThemeDAO themeDAO;

	public void init() throws ServletException {
		CreateThemeController.themeDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY))
				.getThemeDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User sessionUser = checkSessionUser(request, response);
		checkAdminOnly(sessionUser, response);

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CreateThemeForm createThemeForm = new CreateThemeForm(themeDAO);

		Theme theme = null;
		theme = createThemeForm.createTheme(request);

		request.setAttribute(ATT_FORM, createThemeForm);
		request.setAttribute(ATT_THEME, theme);

		if (!createThemeForm.getErrors().isEmpty()) {
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
			return;
		} else {
			response.sendRedirect(request.getServletContext().getContextPath() + "/" + Config.URL_CREATE_QUIZ);
			return;
		}
	}
}