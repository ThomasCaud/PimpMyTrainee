package controllers.trainee;

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
	private static final String VIEW = "/WEB-INF/trainee_records.jsp";

	private static final String ATT_SEARCH = "search";
	private static final String ATT_RECORDS = "records";
	private RecordDAO recordDAO;

	public void init() throws ServletException {
		this.recordDAO = ((DAOFactory) getServletContext().getAttribute(Config.CONF_DAO_FACTORY)).getRecordDAO();
	}

	private ArrayList<Record> search(User trainee, String search) {
		return recordDAO.get(trainee, search);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		User sessionUser = (User) session.getAttribute(Config.ATT_SESSION_USER);

		if (sessionUser == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		String search = null;
		ArrayList<Record> records = search(sessionUser, search);

		request.setAttribute(ATT_RECORDS, records);

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}