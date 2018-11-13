package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Config;
import dao.DAOFactory;
import dao.interfaces.RecordDAO;
import models.beans.Record;
import models.beans.User;

@WebServlet("/" + Config.URL_RESULTS)
public class ResultsController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/results.jsp";

	private static final String ATT_SEARCH = "search";
	private static final String ATT_RECORDS = "records";
	private static final String ATT_PAGINATION_ACTIVE = "paginationActive";
	private static final String ATT_PAGINATION_TOTAL = "paginationTotal";
	private RecordDAO recordDAO;

	public void init() throws ServletException {
		this.recordDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getRecordDAO();
	}

	private int getCount(User user) {
		return recordDAO.get(user).size();
	}

	// private ArrayList<Quiz> findWithOffsetLimit(User user, int offset, int
	// limit) {
	// if (user.getRole() == E_Role.ADMIN) {
	// return quizDAO.findAll((offset - 1) * limit, limit);
	// } else if (user.getRole() == E_Role.TRAINEE) {
	// return quizDAO.findBy("creator", user.getManager().getId(), (offset - 1)
	// * limit, limit);
	// } else {
	// Logger.logMsg(Logger.WARNING, "L'utilisateur courant n'est pas Admin ni
	// Trainee");
	// return new ArrayList<Quiz>();
	// }
	// }

	private ArrayList<Record> search(User user, String search) {
		return recordDAO.findBy("trainee", user.getId());
		// if (user.getRole() == E_Role.ADMIN) {
		// return quizDAO.searchQuizzes(search);
		// } else if (user.getRole() == E_Role.TRAINEE) {
		// return quizDAO.searchQuizzes(user.getManager().getId(), search);
		// } else {
		// Logger.logMsg(Logger.WARNING, "L'utilisateur courant n'est pas Admin
		// ni Trainee");
		// return new ArrayList<Quiz>();
		// }
	}

	private int getNbNeededPage(User user, int nbPerPage) {
		Integer nbAll = getCount(user);

		Integer res = nbAll % nbPerPage;
		Integer nbNeededPages = (int) nbAll / nbPerPage;
		if (res != 0)
			nbNeededPages++;

		return nbNeededPages;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		User sessionUser = (User) session.getAttribute(Config.ATT_SESSION_USER);

		if (sessionUser == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		ArrayList<Record> records = null;

		// Integer offset = 1;
		// String offsetUrl = request.getParameter("p");
		//
		// if (offsetUrl != null) {
		// try {
		// offset = Integer.parseInt(offsetUrl);
		//
		// if (offset <= 0)
		// throw new NumberFormatException();
		// } catch (NumberFormatException e) {
		// response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		// return;
		// }
		// }
		//
		// Integer nbQuizzesPerPage = Config.NB_QUIZZES_PER_PAGE;
		// String nbQuizzesPerPageUrl = request.getParameter("n");
		//
		// if (nbQuizzesPerPageUrl != null) {
		// try {
		// nbQuizzesPerPage = Integer.parseInt(nbQuizzesPerPageUrl);
		//
		// if (nbQuizzesPerPage <= 0)
		// throw new NumberFormatException();
		// } catch (NumberFormatException e) {
		// response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		// return;
		// }
		// }
		//
		// Integer nbNeededPages = getNbNeededPage(sessionUser,
		// nbQuizzesPerPage);
		//
		String search = request.getParameter(ATT_SEARCH);
		records = search(sessionUser, search);

		if (search != null) {
			// records = search(sessionUser, search);
		} else {
			// quizzes = findWithOffsetLimit(sessionUser, offset,
			// nbQuizzesPerPage);
		}
		//
		request.setAttribute(ATT_RECORDS, records);
		// request.setAttribute(ATT_SEARCH, search);
		// request.setAttribute(ATT_PAGINATION_ACTIVE, offset);
		// request.setAttribute(ATT_PAGINATION_TOTAL, nbNeededPages);
		//

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
