package controllers.admin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.Config;
import controllers.AbstractController;
import dao.DAOFactory;

@WebServlet("/" + Config.URL_DATABASE_ADMINISTRATION)
public class DatabaseAdministrationController extends AbstractController {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/admin_database_administration.jsp";
	private static final String SCRIPT_DROP_ALL_TABLES = "drop_all_tables.sql";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getActionFromRequest(request);

		if (action != null) {
			switch (action) {
			case "drop_all_tables":
				processDropAllTables();
				break;
			}
		}

		processResponseSending(response, "");
	}

	public String getActionFromRequest(HttpServletRequest request) {
		return request.getParameter("action");
	}

	public void processDropAllTables() {
		ClassLoader classLoader = getClass().getClassLoader();
		File scriptFile = new File(classLoader.getResource(SCRIPT_DROP_ALL_TABLES).getFile());
		try {
			DAOFactory.getInstance().executeSqlScript(DAOFactory.getInstance().getConnection(), scriptFile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void processResponseSending(HttpServletResponse response, Object data)
			throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
	}
}
