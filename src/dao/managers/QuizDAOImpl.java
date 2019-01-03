package dao.managers;

import static dao.DAOCommon.initPreparedStatement;
import static dao.DAOCommon.silentClose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dao.DAOFactory;
import dao.exceptions.DAOException;
import dao.interfaces.QuestionDAO;
import dao.interfaces.QuizDAO;
import dao.interfaces.RecordDAO;
import dao.interfaces.ThemeDAO;
import dao.interfaces.UserDAO;
import models.beans.Question;
import models.beans.Quiz;
import models.beans.Theme;
import models.beans.User;

public class QuizDAOImpl extends AbstractDAOImpl<Quiz> implements QuizDAO {
	private static final String tableName = "Quizzes";
	private static final String SQL_SELECT_BY_MANAGER_WITH_OSSET_LIMIT = "SELECT * FROM Quizzes WHERE creator = ? LIMIT ?,?";
	private static final String SQL_SELECTED_BY_TITLE_OR_THEME_FOR_MANAGER_ID = "SELECT * FROM Quizzes JOIN Themes ON Quizzes.theme = Themes.id WHERE (Quizzes.title like ? OR Themes.label like ? ) and creator = ?";
	private static final String SQL_SELECTED_AVAILABLE_BY_TITLE = "SELECT * FROM Quizzes" + " LEFT JOIN ("
			+ "	SELECT Quizzes.id as idRespondent from Quizzes" + "   JOIN records ON quizzes.id = records.quiz"
			+ "   WHERE trainee = ?" + ") quizzesWithAnswers on quizzes.id = quizzesWithAnswers.idRespondent"
			+ " WHERE Quizzes.title like ?AND creator = ? AND idRespondent IS null AND Quizzes.isActive is TRUE limit ?,?;";
	private static final String SQL_COUNT_AVAILABLE_QUIZZES = "SELECT count(*) as count FROM Quizzes " + "LEFT JOIN ("
			+ "	SELECT Quizzes.id as idRespondent from Quizzes" + "    JOIN records ON quizzes.id = records.quiz"
			+ "    WHERE trainee = ?" + ") quizzesWithAnswers on quizzes.id = quizzesWithAnswers.idRespondent"
			+ " WHERE creator = ? AND idRespondent IS null AND Quizzes.isActive IS true;";
	private static final String SQL_INSERT_QUIZ = "INSERT INTO Quizzes (title, theme, creator, creationDate, isActive) VALUES (?,?,?,NOW(),?)";
	private static final String SQL_UPDATE_QUIZ = "UPDATE Quizzes set title = ?, theme = ?, isActive = ? WHERE id = ?";

	public QuizDAOImpl() {
		super(null, tableName);
	}

	public QuizDAOImpl(DAOFactory daoFactory) {
		super(daoFactory, tableName);
	}

	protected Quiz map(ResultSet resultSet) throws SQLException {
		Quiz quiz = new Quiz();

		quiz.setId(resultSet.getInt("id"));
		quiz.setTitle(resultSet.getString("title"));
		quiz.setIsActive(resultSet.getInt("isActive") == 1 ? true : false);
		quiz.setCreationDate(resultSet.getTimestamp("creationDate"));

		UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
		User user = userDAO.find(resultSet.getInt("creator"));
		quiz.setCreator(user);

		ThemeDAO themeDAO = DAOFactory.getInstance().getThemeDAO();
		Theme theme = themeDAO.find(resultSet.getInt("theme"));
		quiz.setTheme(theme);

		QuestionDAO questionDAO = DAOFactory.getInstance().getQuestionDAO();
		HashMap<String, Object> filters = new HashMap<String, Object>();
		filters.put("quiz", quiz.getId());
		filters.put("isActive", 1);
		ArrayList<Question> questions = questionDAO.findBy(filters);
		quiz.setQuestions(questions);

		RecordDAO recordDAO = DAOFactory.getInstance().getRecordDAO();
		filters = new HashMap<String, Object>();
		filters.put("quiz", quiz.getId());
		quiz.setNbOfRecords(recordDAO.findBy(filters).size());

		return quiz;
	}

	public void createQuiz(Quiz quiz) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Récupération d'une connexion depuis la Factory
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_INSERT_QUIZ, true, quiz.getTitle(),
					quiz.getTheme().getId(), quiz.getCreator().getId(), quiz.getIsActive());
			int status = preparedStatement.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la création du quiz, aucune ligne ajoutée dans la table.");
			}

			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				quiz.setId(resultSet.getInt(1));
			} else {
				throw new DAOException("Échec de la création du quiz en base, aucun ID auto-généré retourné.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}
	}

	public void updateQuiz(Quiz quiz) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_UPDATE_QUIZ, false, quiz.getTitle(),
					quiz.getTheme().getId(), quiz.getIsActive(), quiz.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(null, preparedStatement, connection);
		}
	}

	@Override
	public ArrayList<Quiz> searchQuizzes(Integer adminId, String value) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_SELECTED_BY_TITLE_OR_THEME_FOR_MANAGER_ID, false,
					'%' + value + '%', '%' + value + '%', adminId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Quiz quiz = map(resultSet);
				quizzes.add(quiz);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}

		return quizzes;
	}

	@Override
	public ArrayList<Quiz> findByManagerId(Integer managerId, Integer offset, Integer limit) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_SELECT_BY_MANAGER_WITH_OSSET_LIMIT, false,
					managerId, offset, limit);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Quiz quiz = map(resultSet);
				quizzes.add(quiz);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}

		return quizzes;
	}

	public ArrayList<Quiz> searchAvailableQuizzes(User user, String value, Integer offset, Integer limit)
			throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_SELECTED_AVAILABLE_BY_TITLE, false, user.getId(),
					'%' + value + '%', user.getManager().getId(), offset, limit);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Quiz quiz = map(resultSet);
				quizzes.add(quiz);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}

		return quizzes;
	}

	@Override
	public ArrayList<Quiz> searchAvailableQuizzes(User user, String value) throws DAOException {
		return searchAvailableQuizzes(user, value, 0, 100);
	}

	@Override
	public void updateIsActive(Quiz quiz, boolean isActive) {
		this.update("isActive", isActive, "id", quiz.getId());
	}

	@Override
	public Integer countAvailableQuizzes(User trainee) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer result = 0;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connection, SQL_COUNT_AVAILABLE_QUIZZES, false, trainee.getId(),
					trainee.getManager().getId());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result = resultSet.getInt("count");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentClose(resultSet, preparedStatement, connection);
		}

		return result;
	}
}
