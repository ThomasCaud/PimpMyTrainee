package controllers.admin;

import java.io.IOException;
import java.io.InputStream;
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
import dao.interfaces.UserDAO;

@WebServlet("/" + Config.URL_DATABASE_ADMINISTRATION)
public class DatabaseAdministrationController extends AbstractController {

	private static final long serialVersionUID = 1L;
	private static final String PROPERTIES_FILE = "/common.properties";
	private static final String VIEW = "/WEB-INF/admin_database_administration.jsp";
	private static final String SCRIPT_DROP_ALL_TABLES = "WEB-INF/sql/drop_all_tables.sql";
	private static final String SCRIPT_CREATE_ALL_TABLES = "/WEB-INF/sql/create_all_tables.sql";
	private static final String SCRIPT_DEMO_DATASET = "/WEB-INF/sql/demo_dataset_";
	private UserDAO userDAO;

	public void init() throws ServletException {
		this.userDAO = ((DAOFactory) getServletContext()
				.getAttribute(Config.CONF_DAO_FACTORY)).getUserDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = getActionFromRequest(request);
		if (action != null) {
			switch (action) {
				case "drop_all_tables" :
					processDropAllTables();
					processResponseSending(response, "");
					break;
				case "create_all_tables" :
					processCreateAllTables();
					processResponseSending(response, "");
					break;
				case "demo_dataset" :
					int datasetNumber = Integer
							.parseInt(request.getParameter("datasetNumber"));
					processDataset(datasetNumber);
					processResponseSending(response, "");
					break;
			}
		}

	}

	public String getActionFromRequest(HttpServletRequest request) {
		return request.getParameter("action");
	}

	public void processDropAllTables() {
		InputStream is = getServletContext()
				.getResourceAsStream(SCRIPT_DROP_ALL_TABLES);
		try {
			DAOFactory.getInstance().executeSqlScript(
					DAOFactory.getInstance().getConnection(), is);
			is.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processCreateAllTables() {
		InputStream is = getServletContext()
				.getResourceAsStream(SCRIPT_CREATE_ALL_TABLES);
		try {
			DAOFactory.getInstance().executeSqlScript(
					DAOFactory.getInstance().getConnection(), is);
			is.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processResponseSending(HttpServletResponse response,
			Object data) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(data));
	}

	public void processDataset(int number) {
		InputStream is = getServletContext()
				.getResourceAsStream(SCRIPT_DEMO_DATASET + number + ".sql");
		try {
			DAOFactory.getInstance().executeSqlScript(
					DAOFactory.getInstance().getConnection(), is);
			Thread.sleep(1000);
			is.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
