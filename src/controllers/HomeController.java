package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.beans.E_Role;
import models.beans.User;

@WebServlet(urlPatterns = { "/home", "" })
public class HomeController extends AbstractController {

    private static final long serialVersionUID = 1L;
    private static final String VIEW_ADMIN = "/WEB-INF/admin_index.jsp";
    private static final String VIEW_TRAINEE = "/WEB-INF/trainee_index.jsp";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	User user = checkSessionUser(request, response);

	if (user != null) {
	    if (user.getRole() == E_Role.ADMIN)
		this.getServletContext().getRequestDispatcher(VIEW_ADMIN).forward(request, response);
	    else
		this.getServletContext().getRequestDispatcher(VIEW_TRAINEE).forward(request, response);
	}

    }

}
