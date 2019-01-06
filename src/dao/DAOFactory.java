package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import dao.exceptions.DAOConfigurationException;
import dao.interfaces.AnswerDAO;
import dao.interfaces.QuestionDAO;
import dao.interfaces.QuizDAO;
import dao.interfaces.RecordAnswerDAO;
import dao.interfaces.RecordDAO;
import dao.interfaces.StatsDAO;
import dao.interfaces.ThemeDAO;
import dao.interfaces.UserDAO;
import dao.managers.AnswerDAOImpl;
import dao.managers.QuestionDAOImpl;
import dao.managers.QuizDAOImpl;
import dao.managers.RecordAnswerDAOImpl;
import dao.managers.RecordDAOImpl;
import dao.managers.StatsDAOImpl;
import dao.managers.ThemeDAOImpl;
import dao.managers.UserDAOImpl;

public class DAOFactory {

	private static final String PROPERTIES_FILE = "/common.properties";
	private static final String PROP_URL = "url";
	private static final String PROP_DRIVER = "driver";
	private static final String PROP_USER = "user";
	private static final String PROP_PASSWORD = "password";

	private String url;
	private String user;
	private String password;

	public DAOFactory(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public static DAOFactory getInstance() {
		Properties properties = new Properties();
		String url;
		String driver;
		String user;
		String password;

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream propertiesFile = classLoader
				.getResourceAsStream(PROPERTIES_FILE);

		if (propertiesFile == null) {
			throw new DAOConfigurationException("Le fichier properties "
					+ PROPERTIES_FILE + " est introuvable.");
		}

		try {
			properties.load(propertiesFile);
			url = properties.getProperty(PROP_URL);
			driver = properties.getProperty(PROP_DRIVER);
			user = properties.getProperty(PROP_USER);
			password = properties.getProperty(PROP_PASSWORD);
		} catch (IOException e) {
			throw new DAOConfigurationException(
					"Impossible de charger le fichier properties "
							+ PROPERTIES_FILE,
					e);
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAOConfigurationException(
					"Le driver est introuvable dans le classpath.", e);
		}

		DAOFactory instance = new DAOFactory(url, user, password);
		return instance;

	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public UserDAO getUserDAO() {
		return new UserDAOImpl(this);
	}

	public QuizDAO getQuizDAO() {
		return new QuizDAOImpl(this);
	}

	public ThemeDAO getThemeDAO() {
		return new ThemeDAOImpl(this);
	}

	public AnswerDAO getAnswerDAO() {
		return new AnswerDAOImpl(this);
	}

	public QuestionDAO getQuestionDAO() {
		return new QuestionDAOImpl(this);
	}

	public RecordDAO getRecordDAO() {
		return new RecordDAOImpl(this);
	}

	public RecordAnswerDAO getRecordAnswerDAO() {
		return new RecordAnswerDAOImpl(this);
	}

	public StatsDAO getStatsDAO() {
		return new StatsDAOImpl(this);
	}

	public void executeSqlScript(Connection conn, InputStream is) {

		// Delimiter
		String delimiter = ";";

		// Create scanner
		Scanner scanner = new Scanner(is).useDelimiter(delimiter);

		// Loop through the SQL file statements
		Statement currentStatement = null;
		while (scanner.hasNext()) {

			// Get statement
			String rawStatement = scanner.next() + delimiter;
			try {
				// Execute statement
				currentStatement = conn.createStatement();
				currentStatement.execute(rawStatement);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// Release resources
				if (currentStatement != null) {
					try {
						currentStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				currentStatement = null;
			}
		}
		scanner.close();
	}
}
