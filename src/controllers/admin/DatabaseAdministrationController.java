package controllers.admin;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.Config;
import common.PasswordManager;
import controllers.AbstractController;
import dao.DAOFactory;
import dao.interfaces.UserDAO;
import models.beans.E_Role;
import models.beans.User;

@WebServlet("/" + Config.URL_DATABASE_ADMINISTRATION)
public class DatabaseAdministrationController extends AbstractController {

	private static final long serialVersionUID = 1L;
	private static final String PROPERTIES_FILE = "/common.properties";
	private static final String VIEW = "/WEB-INF/admin_database_administration.jsp";
	private static final String SCRIPT_DROP_ALL_TABLES = "WEB-INF/sql/drop_all_tables.sql";
	private static final String SCRIPT_CREATE_ALL_TABLES = "/WEB-INF/sql/create_all_tables.sql";
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

				case "create_admin_account" :
					Map<String, String> result = processCreateAdminAccount();
					processResponseSending(response, result);
					break;
			}
		}

	}

	public String getActionFromRequest(HttpServletRequest request) {
		return request.getParameter("action");
	}

	public void processDropAllTables() throws IOException {
		InputStream is = getServletContext()
				.getResourceAsStream(SCRIPT_DROP_ALL_TABLES);
		try {
			DAOFactory.getInstance().executeSqlScript(
					DAOFactory.getInstance().getConnection(), is);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void processCreateAllTables() {
		InputStream is = getServletContext()
				.getResourceAsStream(SCRIPT_CREATE_ALL_TABLES);
		try {
			DAOFactory.getInstance().executeSqlScript(
					DAOFactory.getInstance().getConnection(), is);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> processCreateAdminAccount() throws IOException {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream propertiesFile = classLoader
				.getResourceAsStream(PROPERTIES_FILE);
		properties.load(propertiesFile);
		String defaultAdminEmail = properties
				.getProperty("default_admin_email");
		String defaultPassword = properties.getProperty("default_password");

		User user = new User();
		user.setFirstname("admin");
		user.setEmail(defaultAdminEmail);
		user.setRole(E_Role.ADMIN);

		user.setPassword(PasswordManager.getInstance()
				.getEncryptedValue(defaultPassword));
		user.setIsActive(true);
		user.setCreationDate(new Timestamp(System.currentTimeMillis()));

		userDAO.createUser(user, null);

		Map<String, String> result = new HashMap<String, String>();
		result.put("email", defaultAdminEmail);
		result.put("password", defaultPassword);

		return result;
	}

	public void processResponseSending(HttpServletResponse response,
			Object data) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(data));
	}
}
