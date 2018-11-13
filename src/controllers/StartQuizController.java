package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Config;

@WebServlet("/" + Config.URL_START_QUIZ + "/*")
public class StartQuizController extends AbstractController {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/start_quiz.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkSessionUser(request, response);

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

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
