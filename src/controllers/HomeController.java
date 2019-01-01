package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Config;
import dao.DAOFactory;
import dao.interfaces.RecordDAO;
import dao.interfaces.StatsDAO;
import models.beans.E_Role;
import models.beans.Record;
import models.beans.StatsAdminGlobal;
import models.beans.User;
import utils.QuizzesManager;

@WebServlet(urlPatterns = { "/home", "" })
public class HomeController extends AbstractController {
	private static final String ATT_RECORDS = "records";

	private static final long serialVersionUID = 1L;
	private static final String VIEW_ADMIN = "/WEB-INF/admin_index.jsp";
	private static final String VIEW_TRAINEE = "/WEB-INF/trainee_index.jsp";

	private static RecordDAO recordDAO;
	private static StatsDAO statsDAO;

	public static void setDAOs(RecordDAO recordDAO, StatsDAO statsDAO) {
		HomeController.recordDAO = recordDAO;
		HomeController.statsDAO = statsDAO;
	}

	public void init() throws ServletException {
		DAOFactory daoFactory = (DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY);

		HomeController.setDAOs(daoFactory.getRecordDAO(), daoFactory.getStatsDAO());
	}

	private ArrayList<Record> search(User trainee, String search) {
		return recordDAO.get(trainee, search);
	}

	private void getQuizzes(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		QuizzesManager quizzesManager = new QuizzesManager(
				((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getQuizDAO());
		quizzesManager.setSearchAndPaginationData(request, response, user);
	}

	private void getRecords(HttpServletRequest request, User user) {
		String search = null;
		ArrayList<Record> records = search(user, search);

		request.setAttribute(ATT_RECORDS, records);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = checkSessionUser(request, response);

		if (user != null) {
			if (user.getRole() == E_Role.ADMIN) {
				StatsAdminGlobal stats = statsDAO.get(user);
				HttpSession session = request.getSession();
				session.setAttribute(Config.ATT_STATS_NB_ACTIVE_USERS, stats.getNbActiveUser());
				session.setAttribute(Config.ATT_STATS_NB_INACTIVE_USERS, stats.getNbInactiveUser());
				session.setAttribute(Config.ATT_STATS_NB_CREATED_QUIZZES, stats.getNbCreatedQuizzes());
				session.setAttribute(Config.ATT_STATS_NB_RECORDS, stats.getNbRecords());
				this.getServletContext().getRequestDispatcher(VIEW_ADMIN).forward(request, response);

			} else {
				getQuizzes(request, response, user);
				getRecords(request, user);
				this.getServletContext().getRequestDispatcher(VIEW_TRAINEE).forward(request, response);
			}
		}
	}
}