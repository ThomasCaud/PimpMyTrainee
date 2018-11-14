package controllers;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Config;
import models.beans.E_Role;
import models.beans.User;

public abstract class AbstractController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public User checkSessionUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
	HttpSession session = request.getSession();

	User sessionUser = (User) session.getAttribute(Config.ATT_SESSION_USER);

	if (sessionUser == null) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	return sessionUser;
    }

    public void checkAdminOnly(User user, HttpServletResponse response) throws IOException {
	if (user == null || user.getRole() != E_Role.ADMIN) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}

    }
}
