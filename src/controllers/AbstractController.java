package controllers;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import common.Config;
import models.beans.E_Role;
import models.beans.User;

public abstract class AbstractController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(AbstractController.class);

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

	public void checkTraineeOnly(User user, HttpServletResponse response) throws IOException {
		if (user == null || user.getRole() != E_Role.TRAINEE) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
	}

	public Integer getIntegerFromURL(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pathInfo = request.getPathInfo();

		if (pathInfo == null || pathInfo.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

		String urlId = request.getPathInfo().substring(1, request.getPathInfo().length());
		Integer id = -1;
		try {
			id = Integer.parseInt(urlId);
		} catch (NumberFormatException e) {
			logger.error("Un entier n'a pas pu être extrait de la requête : " + request.getPathInfo());
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

		return id;
	}
}
